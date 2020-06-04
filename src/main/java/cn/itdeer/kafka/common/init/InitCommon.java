package cn.itdeer.kafka.common.init;

import cn.itdeer.kafka.common.config.Constants;
import cn.itdeer.kafka.common.config.Message;
import cn.itdeer.kafka.common.config.Points;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Description : 初始化通用的配置信息
 * PackageName : cn.itdeer.kafka.common.init
 * ProjectName : DataToKafka
 * CreatorName : itdeer.cn
 * CreateTime : 2019/11/17/0:08
 */

public class InitCommon {

    private static final Logger log = LogManager.getLogger(InitCommon.class);

    /**
     * 初始化文件点位配置的Message信息
     *
     * @param message Message的点位文件信息
     * @return 点位设置的列表
     */
    public List<Points> initFilePoints(Message message) {
        List<String> list = readFilePoints(message);
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

        return points;
    }

    /**
     * 读取点位文件的配置放入列表中
     *
     * @param message Message的点位文件信息
     * @return 读取之后的字符串列表
     */
    private List<String> readFilePoints(Message message) {

        String pointFileName = message.getDataMapping().getPointFile().getFileName();
        String path = System.getProperty("user.dir") + File.separator + "config" + File.separator + pointFileName;
        List<String> list = new ArrayList<>();

        try (CSVReader csvReader = new CSVReaderBuilder(new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), "UTF-8"))).build()) {
            Iterator<String[]> iterator = csvReader.iterator();
            while (iterator.hasNext()) {
                String point = "";
                for (String tmp : iterator.next()) {
                    point = point.concat(tmp).concat(",");
                }
                point = point.substring(0, point.length() - 1);
                list.add(point);
            }
            log.info("Read the point bit configuration file successfully The dot file name is [{}]", pointFileName);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("There was an error reading point [{}] configuration file. The error message is as follows [{}]", pointFileName, e.getStackTrace());
        }
        return null;
    }
}
