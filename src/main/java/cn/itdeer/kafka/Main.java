package cn.itdeer.kafka;

import cn.itdeer.kafka.core.control.DataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * Description : 程序的入口
 * PackageName : cn.itdeer.kafka
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/11/17/8:16
 */

@Slf4j
public class Main {

    public static void main(String[] args) {
        new DataSource().start();
        log.info("Start the main program to work ......");
    }

}
