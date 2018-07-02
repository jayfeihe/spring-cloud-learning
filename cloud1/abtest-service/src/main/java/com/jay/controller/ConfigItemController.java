package com.jay.controller;

import com.jay.abtest.AbTestConfigFactory;
import com.jay.abtest.InsuranceAbTestConfig;
import com.jay.bean.ConfigItem;
import com.jay.service.AbTestApi;
import com.jay.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * AbTest ---  两个配置项    配置项的key在AbTest中指定
 *                          AB_TEST_INSURANCE_NEED
 *                          AB_TEST_INSURANCE_NOT_NEED
 *
 * 1.添加测试组配置
 * http://localhost:10011/config/insert?cfgApp=test&cfgKey=AB_TEST_INSURANCE_NEED&cfgValue=1&cfgDesc=test
 * http://localhost:10011/config/insert?cfgApp=test&cfgKey=AB_TEST_INSURANCE_NOT_NEED&cfgValue=1&cfgDesc=test
 * 解析：
 * 测试组设置为 1：1
 * AB_TEST_INSURANCE_NEED:AB_TEST_INSURANCE_NOT_NEED  = 1:1
 * <p>
 * 2.业务端，调用测试组
 * http://localhost:10011/config/test1?applyId=111116
 * 解析：
 * 业务端根据测试组配置，选择走哪个测试项
 *
 *
 *
 * AbTest ---  多个配置项   4个    配置项的key在AbTest中指定
 *                          AB_TEST_ALI_EXTAUTH_NEED
 *                          AB_TEST_ALI_EXTAUTH_NOT_NEED
 *                          AB_TEST_ALI_EXTAUTH_NOT_NEED_ZHIMA
 *                          AB_TEST_ALI_EXTAUTH_NEED_ALIPAY
 *
 * 1.添加测试组配置项和比例
 *      http://localhost:10011/config/insert?cfgApp=ali&cfgKey=AB_TEST_ALI_EXTAUTH_NEED&cfgValue=1&cfgDesc=ali
 *      http://localhost:10011/config/insert?cfgApp=ali&cfgKey=AB_TEST_ALI_EXTAUTH_NOT_NEED&cfgValue=1&cfgDesc=ali
 *      http://localhost:10011/config/insert?cfgApp=ali&cfgKey=AB_TEST_ALI_EXTAUTH_NOT_NEED_ZHIMA&cfgValue=1&cfgDesc=ali
 *      http://localhost:10011/config/insert?cfgApp=ali&cfgKey=AB_TEST_ALI_EXTAUTH_NEED_ALIPAY&cfgValue=1&cfgDesc=ali
 *      解析：
 *          这里讲4个测试项，比例设置为 1：1：1：1
 *
 * 2.业务端调用测试组
 *      http://localhost:10011/config/multi?applyId=test02
 *          返回：
 *              MultiAbTestConfig中的4个可选值中的一个
 *              eg：
 *                  noNeedZhima
 *      解析：
 *      业务端根据测试组配置，选择走哪个测试项
 *
 */
@RestController
@RequestMapping("/config")
public class ConfigItemController {

    @Autowired
    private ConfigService configService;
    @Autowired
    private AbTestApi abTestApi;
    @Autowired
    private AbTestConfigFactory abTestConfigFactory;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/insert")
    public String insert(ConfigItem configItem) {
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


    @GetMapping("/test2")
    public String test2(String applyId) {
        redisTemplate.opsForValue().set("cfg::AB_TEST_INSURANCE_NOT_NEED", "1");
        Object result = redisTemplate.opsForValue().get("cfg::AB_TEST_INSURANCE_NOT_NEED");
        return result.toString();
    }

    @GetMapping("/multi")
    public String multiTest(String applyId) {

        String detemination = abTestApi.determine(abTestConfigFactory.getMultiAbTestConfig(), applyId);

        String choice = abTestApi.get(abTestConfigFactory.getMultiAbTestConfig().getTestName(), applyId);

        System.out.println("detemination\t" + detemination + "\tchoice\t" + choice);

        return choice;
    }


}
