package cn.itdeer.kafka.core.control;

import cn.itdeer.kafka.common.Constants;
import cn.itdeer.kafka.common.InitConfig;
import cn.itdeer.kafka.common.Message;
import cn.itdeer.kafka.common.Points;
import cn.itdeer.kafka.core.type.AppointCsvProducer;
import cn.itdeer.kafka.core.type.AppointJsonProducer;
import cn.itdeer.kafka.core.type.NoAppointCsvProducer;
import cn.itdeer.kafka.core.type.NoAppointJsonProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : 生产者工厂类
 * PackageName : cn.itdeer.kafka.core
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/8/26/17:32
 */
@Slf4j
public class ProducerFactory {

    /**
     * 获取生产者的具体实例
     *
     * @param message 生成消息信息
     * @return 生产者的具体实例
     */
    public void createProducer(Message message, KafkaProducer<String, String> producer) {

        if (message.getDataMapping().getAppoint()) {
            ifFilePoint(message);
            switch (message.getDataMapping().getType()) {
                case Constants.CSV:
                    new AppointCsvProducer(message, producer).start();
                    break;
                case Constants.JSON:
                    new AppointJsonProducer(message, producer).start();
                    break;
                default:
                    log.error("Currently only JSON and CSV data formats are supported. Format [{}] not supported", message.getDataMapping().getType());
            }
        } else {
            switch (message.getDataMapping().getType()) {
                case Constants.CSV:
                    new NoAppointCsvProducer(message, producer).start();
                    break;
                case Constants.JSON:
                    new NoAppointJsonProducer(message, producer).start();
                    break;
                default:
                    log.error("Currently only JSON and CSV data formats are supported. Format [{}] not supported", message.getDataMapping().getType());
            }
        }
    }

    /**
     * 判断是否为点位CSV表格，如果是是CSV的点位表，需要做转换
     *
     * @param message 设置message的实例值
     */
    private void ifFilePoint(Message message) {

        if (message.getDataMapping().getPointFile() != null) {
            List<String> list = InitConfig.readFilePoin(message.getDataMapping().getPointFile().getFileName());
            List<Points> points = new ArrayList<>();

            for (String point : list) {
                String[] points_ = point.split(Constants.COMMA);
                String[] mapping_ = message.getDataMapping().getPointFile().getMapping().split(Constants.COMMA);
                String fields = message.getDataMapping().getPointFile().getFields();

                for (int i = 0; i < mapping_.length; i++) {
                    fields = fields.replace(mapping_[i], points_[i]);
                }

                Points p = new Points();
                p.setPoint(fields);
                points.add(p);
            }
            message.getDataMapping().setPoints(points);
        }

    }
}
