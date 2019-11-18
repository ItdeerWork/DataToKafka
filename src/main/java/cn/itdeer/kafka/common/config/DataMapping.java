package cn.itdeer.kafka.common.config;

import lombok.Data;

import java.util.List;

/**
 * Description : 数据映射
 * PackageName : cn.itdeer.kafka.common
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/8/27/10:05
 */

@Data
public class DataMapping {

    private String type;
    private Boolean appoint;
    private String separator = ",";
    private List<Fields> fields;
    private List<Points> points;
    private PointFile pointFile;

}