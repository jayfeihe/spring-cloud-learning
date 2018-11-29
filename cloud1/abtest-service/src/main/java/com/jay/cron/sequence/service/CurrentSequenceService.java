package com.jay.cron.sequence.service;

import com.jay.cron.sequence.SequenceID;
import com.jay.cron.sequence.repo.SequenceRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrentSequenceService {
    @Resource
    private SequenceRepo sequenceRepo;


    @Transactional(value = "comTM",propagation = Propagation.REQUIRES_NEW)
    public int getCurrentSequenceValue(SequenceID seq, int step) {
        Map<String, Object> updateParamMap = new HashMap<String, Object>();
        updateParamMap.put("SequenceID", seq.getValue());
        updateParamMap.put("Step", step);
        sequenceRepo.updateSequence(updateParamMap);

        Map<String, Object> getParamMap = new HashMap<String, Object>();
        getParamMap.put("SequenceID", seq.getValue());
        Integer currentSequenceValue = sequenceRepo.getSequence(getParamMap);
        if (currentSequenceValue == null) {
            return -1;
        } else {
            return currentSequenceValue;
        }
    }
}
