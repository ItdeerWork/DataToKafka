package cn.itdeer.kafka.common.config;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static cn.itdeer.kafka.common.config.Constants.CONFIG_FILE_DIRECTORY;
import static cn.itdeer.kafka.common.config.Constants.CONFIG_FILE_NAME;

/**
 * Description : 解析运行配置文件
 * PackageName : cn.itdeer.kafka.common.config
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/11/16/23:22
 */

@Slf4j
public class InitConfig {

    private String configFileName = CONFIG_FILE_NAME;
    private StringBuffer sb = new StringBuffer();

    @Getter
    private ConfigBean cb;

    /**
     * 初始化配置为实体对象
     */
    private InitConfig() {

        String filePath = System.getProperty("user.dir") + File.separator + CONFIG_FILE_DIRECTORY + File.separator + configFileName;
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
     * 使用内部类，把加载配置改写成单例
     */
    private static class SingletonHolder {
        private static final InitConfig instance = new InitConfig();
    }

    /**
     * 提供静态可访问方法获取当前类实例
     *
     * @return InitConfig 的单例实例
     */
    public static InitConfig getInstance() {
        return SingletonHolder.instance;
    }

}
