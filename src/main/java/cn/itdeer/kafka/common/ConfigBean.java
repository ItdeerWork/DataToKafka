package cn.itdeer.kafka.common;

import lombok.Data;

import java.util.List;

/**
 * Description : 应用配置实体
 * PackageName : cn.itdeer.kafka.common
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/8/27/10:05
 */
@Data
public class ConfigBean {

    private Kafka kafka;
    private List<Message> message;
    private Commons commons;

}