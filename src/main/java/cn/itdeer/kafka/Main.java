package cn.itdeer.kafka;

import cn.itdeer.kafka.tools.Constants;
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
        for (Object topic_name : Constants.TOPIC_NAME_AND_THREADS.keySet()) {

            String thread_single_data = Constants.TOPIC_NAME_AND_THREADS.get(topic_name);
            String[] temp = thread_single_data.split("_");

            for (int i = 0; i < Integer.parseInt(temp[0]); i++) {
                Thread thread = new Producer(topic_name.toString(), Integer.parseInt(temp[1]));
                thread.start();
                log.info("Thread ID:{}    Thread Name:{}    for Topic Name:{}    Amount of data per thread:{}", thread.getId(), thread.getName(), topic_name.toString(), temp[1]);
            }
        }
    }
}
