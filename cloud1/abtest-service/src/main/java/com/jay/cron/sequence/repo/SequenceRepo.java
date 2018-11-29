package com.jay.cron.sequence.repo;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface SequenceRepo {

    public void insertSequence(Map<String, Object> paramMap);

    public void updateSequence(Map<String, Object> paramMap);

    public Integer getSequence(Map<String, Object> paramMap);
}
