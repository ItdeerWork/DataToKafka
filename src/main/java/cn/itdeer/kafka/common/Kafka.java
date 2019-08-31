package cn.itdeer.kafka.common;

import lombok.Data;

/**
 * Description : Kafka的通用配置
 * PackageName : cn.itdeer.kafka.common
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/8/27/10:05
 */
@Data
public class Kafka {

    private String bootstrapServers;
    private String acks;
    private Integer retries;
    private Integer lingerMs;
    private Integer batchSize;
    private Integer bufferMemory;
    private Integer maxRequestSize;
    private String compressionType;
    private Integer requestTimeoutMs;
    private String maxInFlightRequestsPer;
    private String keySerializer;
    private String valueSerializer;

}