package cn.itdeer.kafka;

import cn.itdeer.kafka.tools.Constants;
import cn.itdeer.kafka.tools.Message;
import lombok.extern.slf4j.Slf4j;

/**
 * Description : 函数执行入口
 * PackageName : cn.itdeer.kafka
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/7/9/11:06
 */
@Slf4j
public class Main {

    public static void main(String[] args) {

        /**
         * 执行多线程
         */
        for (Message message : Constants.TOPIC_DATA_MESSAGE) {
            for (int i = 0; i < message.getThreads(); i++) {
                Thread thread = new Producer(message.getTopic_name(), message.getData_number(), message.getPoint_number(), message.getTime_frequency());
                thread.start();
                log.info("Thread ID:{}    Thread Name:{}    for Topic Name:{}    Single Thread Send Data:{}    Point Number:{}", thread.getId(), thread.getName(), message.getTopic_name(), message.getData_number(), message.getPoint_number());
            }
        }
    }
}
