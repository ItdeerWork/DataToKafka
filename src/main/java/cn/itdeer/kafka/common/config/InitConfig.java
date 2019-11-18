package cn.itdeer.kafka.common.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.List;

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

    private static String configFileName = CONFIG_FILE_NAME;
    private static StringBuffer sb = new StringBuffer();
    private static ConfigBean cb;

    /**
     * 静态代码块，加载配置文件
     */
    static {
        String filePath = System.getProperty("user.dir") + File.separator + CONFIG_FILE_DIRECTORY + File.separator + configFileName;
        try (
                FileReader reader = new FileReader(filePath);
                BufferedReader br = new BufferedReader(reader)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            init();
            log.info("Reading the configuration file is complete [{}]", configFileName);
        } catch (IOException e) {
            log.error("Error reading configuration file [{}] error message is as follows:", configFileName, e.getStackTrace());
        }
    }

    /**
     * 初始化配置为实体对象
     */
    private static void init() {
        cb = JSON.parseObject(sb.toString(), ConfigBean.class);
    }

    /**
     * 获取通用默认配置
     *
     * @return 通用配置信息
     */
    public static Commons getCommonsConfig() {
        return cb.getCommons();
    }

    /**
     * 获取Kafka的配置
     *
     * @return Kafka的配置信息
     */
    public static Kafka getKafkaConfig() {
        return cb.getKafka();
    }

    /**
     * 获取需要发送数据的配置信息
     *
     * @return 需要发送的数据配置列表
     */
    public static List<Message> getMessageConfig() {
        return cb.getMessage();
    }

}
