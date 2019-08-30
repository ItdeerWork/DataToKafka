package cn.itdeer.kafka.tools;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Properties;

/**
 * Description : 解析运行配置文件
 * PackageName : cn.itdeer.kafka.tools
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/7/9/15:27
 */
@Slf4j
public class ResolverConfig {

    private static Properties prop = null;
    private static FileInputStream in = null;

    /**
     * 静态代码块，加载配置文件
     */
    static {
        try {
            if (prop == null) {
                prop = new Properties();
                String runTimePath = System.getProperty("user.dir");
                in = new FileInputStream(new File(runTimePath + File.separator + "config" + File.separator + Constants.configFileName));
                prop.load(in);
                log.info("loading {} config file finish ...", Constants.configFileName);
            }
        } catch (FileNotFoundException ffe) {
            log.error("config file not found (not found {} file): {}", Constants.configFileName, ffe.getStackTrace());
        } catch (IOException ioe) {
            log.error("here was an error reading the {} file: {}", Constants.configFileName, ioe.getStackTrace());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    log.error("close InputStream appear exception: {}", e.getStackTrace());
                }
            }
        }
    }

    /**
     * 根据传递来的key值获取对应的Value值
     *
     * @return Value为String型
     */
    public static String getValueByKey(String key) {
        log.info("get Key: {} Value: {}", key, prop.getProperty(key));
        return prop.getProperty(key);
    }

}
