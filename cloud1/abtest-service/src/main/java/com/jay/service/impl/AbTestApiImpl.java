package com.jay.service.impl;

import com.jay.abtest.AbTestConfig;
import com.jay.bean.AbTest;
import com.jay.dao.AbTestDao;
import com.jay.service.AbTestApi;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class AbTestApiImpl implements AbTestApi {

    @Autowired
    private AbTestDao abTestDao;

    @Override
    public void insert(AbTest abTest) {
        abTestDao.save(abTest);
    }

    @Override
    public String determine(AbTestConfig abTestConfig, Serializable testKey) {
        return determine(abTestConfig.getTestName(), testKey, abTestConfig.getChoices(), abTestConfig.getProbabilities());
    }

    @Override
    public String determine(AbTestConfig abTestConfig, Serializable testKey, boolean isStore) {
        return determine(abTestConfig.getTestName(), testKey, abTestConfig.getChoices(), abTestConfig.getProbabilities(), isStore);
    }

    @Override
    public String determine(String testName, Serializable testKey, String[] choices, int[] probabilities) {
        return determine(testName, testKey, choices, probabilities, true);
    }

    @Override
    public String determine(String testName, Serializable testKey, String[] choices, int[] probabilities, boolean isStore) {
        AbTest abTest = new AbTest();
        abTest.setTestName(testName);
        abTest.setTestKey(testKey.toString());

        List<String> choicesList = new ArrayList<>();
        List<Integer> probabilitiesList = new ArrayList<>();
        for (int i = 0; i < probabilities.length; i++) {
            if (probabilities[i] > 0) {
                choicesList.add(choices[i]);
                probabilitiesList.add(probabilities[i]);
            }
        }

        choices = new String[choicesList.size()];
        probabilities = new int[probabilitiesList.size()];
        for (int i = 0; i < choices.length; i++) {
            choices[i] = choicesList.get(i);
            probabilities[i] = probabilitiesList.get(i);
        }

        int sum = 0;
        for (int probability : probabilities) {
            sum += probability;
        }
        int value = RandomUtils.nextInt(sum + 1);
        sum = 0;
        for (int i = 0; i < choices.length; i++) {
            sum += probabilities[i];
            if (value <= sum) {
                abTest.setTestValue(choices[i]);
                break;
            }
        }
        if (isStore) {
            this.insert(abTest);
        }
        return abTest.getTestValue();
    }

    @Override
    public String get(String testName, Serializable testKey) {
        AbTest abTest = abTestDao.getByTestNameAndTestKey(testName, testKey.toString());
        return abTest != null ? abTest.getTestValue() : null;
    }

    @Override
    public boolean check(AbTestConfig abTestConfig, Serializable testKey) {
        AbTest abTest = abTestDao.getByTestNameAndTestKey(abTestConfig.getTestName(), testKey.toString());
        return abTest != null && abTestConfig.testChoice(abTest.getTestValue());
    }

    @Override
    public boolean check(AbTestConfig abTestConfig, Serializable testKey, AbTestConfig defaultConfig) {
        AbTest abTest = abTestDao.getByTestNameAndTestKey(abTestConfig.getTestName(), testKey.toString());
        if (abTest == null && defaultConfig != null) {
            abTest = abTestDao.getByTestNameAndTestKey(defaultConfig.getTestName(), testKey.toString());
        }
        return abTest != null && abTestConfig.testChoice(abTest.getTestValue());
    }
}
