package cn.itdeer.kafka.common;

import lombok.Data;

/**
 * Description : 点文件配置
 * PackageName : cn.itdeer.kafka.common
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/11/4/11:45
 */

@Data
public class PointFile {
    private String fields;
    private String mapping;
    private String fileName;
}
