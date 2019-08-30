package cn.itdeer.kafka.tools;

import lombok.Data;

/**
 * Description : 数据消息实体
 * PackageName : cn.itdeer.kafka.tools
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/7/17/10:28
 */
@Data
public class Message {
    private String topic_name;
    private Integer threads = ResolverConfig.getValueByKey("DEFAULT_THREADS") != null ? Integer.parseInt(ResolverConfig.getValueByKey("DEFAULT_THREADS")) : 1;
    private Integer data_number = ResolverConfig.getValueByKey("DEFAULT_DATA_NUMBER") != null ? Integer.parseInt(ResolverConfig.getValueByKey("DEFAULT_DATA_NUMBER")) : 10000;
    private Integer point_number = ResolverConfig.getValueByKey("DEFAULT_POINT_NUMBER") != null ? Integer.parseInt(ResolverConfig.getValueByKey("DEFAULT_POINT_NUMBER")) : -1;
    private Integer time_frequency = ResolverConfig.getValueByKey("DEFAULT_TIME_FREQUENCY") != null ? Integer.parseInt(ResolverConfig.getValueByKey("DEFAULT_TIME_FREQUENCY")) : -1;
}
