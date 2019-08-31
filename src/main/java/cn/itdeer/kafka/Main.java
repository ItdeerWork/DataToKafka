package cn.itdeer.kafka;

import cn.itdeer.kafka.common.ConfigBean;
import cn.itdeer.kafka.common.InitConfig;
import cn.itdeer.kafka.common.Message;
import cn.itdeer.kafka.core.control.InitProducer;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


/**
 * Description : 程序的入口
 * PackageName : cn.itdeer.kafka
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/8/27/14:29
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        ConfigBean cb = InitConfig.getConfigBean();

        List<Message> list = cb.getMessage();
        for (Message message : list) {
            for (int i = 0; i < message.getThreads(); i++) {
                new InitProducer(message, cb);
                log.info("Initializes the number of starts of the producer program for topic [{}]", message.getTopicName());
            }
        }
    }
}
