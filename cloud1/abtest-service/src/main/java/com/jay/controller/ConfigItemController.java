package com.jay.controller;

import com.jay.abtest.AbTestConfigFactory;
import com.jay.abtest.InsuranceAbTestConfig;
import com.jay.bean.ConfigItem;
import com.jay.service.AbTestApi;
import com.jay.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/config")
public class ConfigItemController {

    @Autowired
    private ConfigService configService;
    @Autowired
    private AbTestApi abTestApi;
    @Autowired
    private AbTestConfigFactory abTestConfigFactory;

    @GetMapping("/insert")
    public String insert(ConfigItem configItem) {
//        ConfigItem configItem = new ConfigItem();
//        configItem.setCfgApp(cfgApp);
//        configItem.setCfgKey(cfgkey);
//        configItem.setCfgValue(cfgValue);
//        configItem.setCfgDesc(cfgDesc);
        configService.insert(configItem);
        return "Success";
    }

    @GetMapping("/update")
    public String update(String key, String value) {
        configService.update(key, value);
        return "Success";
    }

    @GetMapping("/query")
    public String query(String key) {
        configService.getString(key);
        return "Success";
    }

    @GetMapping("/all")
    public List<ConfigItem> findAll() {
        return configService.findAll();
    }

    @GetMapping("/test1")
    public String test(String applyId) {
        InsuranceAbTestConfig insuranceConfig = abTestConfigFactory.getInsuranceAbTestConfig();

        //applyId在InsuranceAbTestConfig测试组中的值，如果返回null，标识不在测试组中
        String result = abTestApi.get(insuranceConfig.getTestName(), applyId);

        //如果不在测试组中，进测试组，并返回测试组结果
        if (result == null) {
            String selectResult = abTestApi.determine(insuranceConfig, applyId);
            System.out.println("选中测试组值结果：" + selectResult);
        }

        //applyId是否需要买保险
        boolean flag = abTestApi.check(insuranceConfig, applyId);
        System.out.println("是否需要买保险：" + flag);
        return "Success";
    }
}
