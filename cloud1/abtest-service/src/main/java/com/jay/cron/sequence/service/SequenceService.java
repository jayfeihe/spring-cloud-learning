package com.jay.cron.sequence.service;

import com.jay.cron.sequence.Sequence;
import com.jay.cron.sequence.SequenceID;
import com.jay.cron.sequence.repo.SequenceRepo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@Service
public class SequenceService {
    private static Object _lockObj = new Object();
    private static java.util.ArrayList<Sequence> cachedSequences = new java.util.ArrayList<Sequence>();
    private static int STEP = 1000;
    private static int JUMP = 1;
    private static int MAX_STEP = 100000;

    @Resource
    private SequenceRepo sequenceRepo;

    @Resource
    private CurrentSequenceService currentSequenceService;

    public int next(SequenceID seq) {
        return next(seq, STEP, JUMP);
    }

    public int next(SequenceID seq, int step) {
        return next(seq, step, JUMP);
    }

    public int next(SequenceID seq, int step, int jump) {
        if (step <= 0 || step > MAX_STEP) {
            throw new RuntimeException("ArgumentOutOfRangeException");
        }
        synchronized (seq) {
            Sequence found = null;
            for (Sequence item : cachedSequences) {
                if (item.getID() == seq) {
                    found = item;
                    break;
                }
            }
            if (found == null) {
                Sequence tempVar = new Sequence();
                tempVar.setID(seq);
                tempVar.setCurrent(0);
                tempVar.setLast(0);
                found = tempVar;
                cachedSequences.add(found);
            }
            if (found.getCurrent() + jump >= found.getLast()) {

                Integer sequenceValue = currentSequenceService.getCurrentSequenceValue(seq, step);

                if (sequenceValue == null || sequenceValue == -1) {
                    found.setLast(step);

                    Map<String, Object> insertParamMap = new HashMap<String, Object>();
                    insertParamMap.put("SequenceID", seq.getValue());
                    insertParamMap.put("SequenceName", seq.name());
                    insertParamMap.put("SequenceValue", found.getLast());
                    sequenceRepo.insertSequence(insertParamMap);

                } else {
                    found.setLast(sequenceValue);
                }
                found.setCurrent(found.getLast() - step);
            }
            found.setCurrent(found.getCurrent() + jump);
            return found.getCurrent();
        }
    }

}
