package cn.itdeer.kafka.common;

import lombok.Data;

/**
 * Description : 生成消息信息实体
 * PackageName : cn.itdeer.kafka.common
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/8/27/10:05
 */
@Data
public class Message {

    private String topicName;
    private int threads = 1;
    private int dataNumber = 10000;
    private int timeFrequency = -1;
    private DataMapping dataMapping;

}