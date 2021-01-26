package cn.itdeer.kafka;

import cn.itdeer.kafka.common.config.Constants;
import cn.itdeer.kafka.core.control.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Description : 程序的入口
 * PackageName : cn.itdeer.kafka
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/11/17/8:16
 */

public class Main {

    private static final Logger log = LogManager.getLogger(Main.class);
    public static String CONFIG_PATH = null;

    public static void main(String[] args) {
        if (args.length > 0) {
            CONFIG_PATH = args[0];
        }

        new DataSource().start();
        log.info("Start the main program to work ......");
    }

}
