package cn.itdeer.kafka;

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

    public static void main(String[] args) {
        new DataSource().start();
        log.info("Start the main program to work ......");
    }

}
