package cn.itdeer.kafka.common;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Description : 解析运行配置文件
 * PackageName : cn.itdeer.kafka.common
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/8/25/22:46
 */
@Slf4j
public class InitConfig {
    private static String configFileName = "runtime.json";
    private static StringBuffer sb = new StringBuffer();
    private static ConfigBean cb;

    /**
     * 静态代码块，加载配置文件
     */
    static {
        String filePath = System.getProperty("user.dir") + File.separator + "config" + File.separator + configFileName;
        try (
                FileReader reader = new FileReader(filePath);
                BufferedReader br = new BufferedReader(reader)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            cb = JSON.parseObject(sb.toString(), ConfigBean.class);
            log.info("Reading the configuration file is complete [{}]", configFileName);
        } catch (IOException e) {
            log.error("Error reading configuration file [{}] error message is as follows:", configFileName, e.getStackTrace());
        }
    }

    /**
     * 获取配置的实体对象
     *
     * @return ConfigBean 配置实体对象
     */
    public static ConfigBean getConfigBean() {
        return cb;
    }

    /**
     * 获取通用默认配置
     *
     * @return Commons 通用配置实体对象
     */
    public static Commons getCommons() {
        return cb.getCommons();
    }
}
