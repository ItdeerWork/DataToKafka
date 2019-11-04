### 编译运行环境

```
编译环境：
JDK: 1.8+
Maven：3.3.9+

打包命令：
mvn clean package

运行命令：
在DataToKafka-3.0.0.jar所在的目录，创建名为config的目录，
在config目录中创建runtime.json的配置文件，配置一下必要的参数即可运行起来,3.0.0版本把配置全部更改成了JSON文件的配置方式

java -jar 在DataToKafka-3.0.0.jar

会在Jar包所在的目录创建logs目录，目录里面有相关的运行日志，可供查阅
```

### 更新日志

 - 配置全部使用JSON方式
 - 支持CSV和JSON两种格式数据发送
 - 可以随意配置数据字段
 - 字符串字段可以设置长度
 - 数值可以设置取值范围
 - 时间可以设置起始位置及格式
 - 发数频度设置
 - 支持这两种格式的属性模板定义
 - 有些字段可以设置固定值，有些可以设置活动值
 - 等等功能
 
 - 支持CSV文件配置具体点位及取值范围
 - 支持无限发送配置
 
 
### 说明

 - 系统采用灵活的配置方式进行发数，在发数速率上要有所下降
 - 不管是CSV还是JSON格式都能很好的支持
 - 在数据字段上灵活的添加或减少
 - 在字段的数据类型上可以灵活的配置
 - 目前支持的数据类型有：int double float boolean date string


### 应用程序整体的配置说明

```

{
  "kafka": {
    "bootstrapServers": "192.168.1.220:9092",   //用于初始化时建立链接到kafka集群的连接地址
    "acks": 0,      //用于消息的可靠性传输
    "retries": 3,    //poducer消息发送失败后，重试的次数
    "lingerMs": 100,    //缓冲区的消息被清空的时间间隔
    "batchSize": 102400,    //批量发送消息的大小
    "bufferMemory": 33554432,   //缓存消息的缓冲区大小
    "maxRequestSize": 10485760, //producer端能够发送的最大消息大小
    "compressionType": "lz4",   //消息压缩方式
    "requestTimeoutMs": 60000,  //消息发送的最长等待时间
    "maxInFlightRequestsPer": "1",  //限制producer在单个broker连接上能够发送的未响应请求的数量
    "keySerializer": "org.apache.kafka.common.serialization.StringSerializer", //提供将key序列化方式
    "valueSerializer": "org.apache.kafka.common.serialization.StringSerializer" //提供将value序列化方式
  },
  "commons": {
    "stringFieldDefaultLength": 4,  //字符串的长度为默认4个字符组成
    "stringFieldDefaultWrite": 7,   // 1 表示大写  2 表示小写 3 表示数字 4 表示大写小写混合 5 表示小写和数字 6 表示大写和数字 7 表示大写小写和数字
    "booleanFieldDefaultFlag": 0,   //1 表示true  -1 表示false 0 表示随机true或false
    "intFieldDefaultMin": 0,        //表示int最小取值范围
    "intFieldDefaultMax": 10000,    // 表示int最大取值范围
    "doubleFieldDefaultFormat": "#0.000",   // 表示保留的小数点位数
    "doubleFieldDefaultMin": 0,     // 表示double最小取值范围
    "doubleFieldDefaultMax": 10000, // 表示double最大取值范围
    "floatFieldDefaultFormat": "#0.00", // 表示保留的小数点位数
    "floatFieldDefaultMin": 0,  // 表示float最小取值范围
    "floatFieldDefaultMax": 10000,  // 表示float最大取值范围
    "dateFieldDefaultStartPoint": "now",    // 表示开始时间
    "dateFieldDefaultFormat": "yyyy-MM-dd HH:mm:ss" // 表示时间格式
  },
  "message": [
    {
      "topicName": "itdeer1",   //主题名称
      "threads": 1,         //启动的线程数
      "dataNumber": 100000, //发送的数据条数或批数 为0则为无限发送
      "timeFrequency": -1, //时间间隔
      "dataMapping": {
        "type": "json", //数据格式
        "appoint": "false", //是否是模板
        "fields": [ //字段列表
          {
            "field": "tagName==string(5)"   //字段名称为 tagName 类型为string 长度为5 长度不设置则使用默认值
          },
          {
            "field": "tagValue==double(0,10)" //字段名称为 tagValue 类型为double 取值范围 0,10，范围不设置则使用默认值
          },
          {
            "field": "isGood==boolean(0)" //字段名称为 isGood 类型为boolean 取值方式不设置则使用默认值 0 表示随机true或false
          },
          {
            "field": "sendTS==date(now,yyyy-MM-dd HH:mm:ss)" //字段名称为 sendTS 类型为date 起始值为当前时间，时间格式为yyyy-MM-dd HH:mm:ss
          },
          {
            "field": "piTS==date(2018-01-30 00:00:00.000,yyyy-MM-dd HH:mm:ss.SSS)" //字段名称为 piTS 类型为date 起始值为指定时间点（2018-01-30 00:00:00.000）格式要和后面的格式保持一致，时间格式为yyyy-MM-dd HH:mm:ss.SSS
          }
        ]
      }
    },
    {
      "topicName": "itdeer2",
      "threads": 1,
      "dataNumber": 100000,
      "timeFrequency": -1,
      "dataMapping": {
        "type": "csv",
        "appoint": "false",
        "separator": ",", //数据分隔符
        "fields": [
          {
            "field": "tagName==string" //字段名称为 tagName 类型为string 长度使用默认值
          },
          {
            "field": "tagValue==double" //字段名称为 tagValue 类型为double 取值范围使用默认值
          },
          {
            "field": "isGood==boolean"//字段名称为 isGood 类型为boolean 取值方式使用默认值
          },
          {
            "field": "sendTS==date"//字段名称为 sendTS 类型为date 起始时间和格式使用默认值
          },
          {
            "field": "piTS==date"//字段名称为 piTS 类型为date 起始时间和格式使用默认值
          }
        ]
      }
    },
    {
      "topicName": "itdeer3",
      "threads": 1,
      "dataNumber": 100000,
      "timeFrequency": -1,
      "dataMapping": {
        "type": "json",     //格式为JSON
        "appoint": "true",  //使用模板 字段名称和值之间用“==”连接 字段与字段之间使用“&&”连接
        "points": [
          {
            "point": "tagname==CH4.A4037&&tagvalue==int(0,10)&&isgood==boolean(0)&&sendts==date(now,yyyy-MM-dd HH:mm:ss)&&pits==date(now,yyyy-MM-dd HH:mm:ss.SSS)"
          },    //tagname字段 值为CH4.A4037， tagvalue字段 值为int类型 范围为（0,10）， isgood字段 取值为boolean随机，sendts字段和pits为时间类型，当前时间开始，格式不同
          {
            "point": "tagname==CH4.A4038&&tagvalue==int(10,30)&&isgood==boolean(1)&&sendts==date(now,yyyy-MM-dd HH:mm:ss)&&pits==date(now,yyyy-MM-dd HH:mm:ss)"
          },
          {
            "point": "tagname==H2.A00015&&tagvalue==int(30,50)&&isgood==boolean(-1)&&sendts==date(now,yyyy-MM-dd HH:mm:ss)&&pits==date(now,yyyy-MM-dd HH:mm:ss)"
          },
          {
            "point": "tagname==H2.A00010&&tagvalue==int(50,80)&&isgood==boolean(-1)&&sendts==date(now,yyyy-MM-dd HH:mm:ss)&&pits==date(now,yyyy-MM-dd HH:mm:ss)"
          },
          {
            "point": "tagname==NH3A00008&&tagvalue==int(80,100)&&isgood==boolean(-1)&&sendts==date(now,yyyy-MM-dd HH:mm:ss)&&pits==date(now,yyyy-MM-dd HH:mm:ss)"
          },
          {
            "point": "tagname==NH3A00018&&tagvalue==int(100,150)&&isgood==boolean(-1)&&sendts==date(now,yyyy-MM-dd HH:mm:ss)&&pits==date(now,yyyy-MM-dd HH:mm:ss)"
          },
          {
            "point": "tagname==2331.C800HTA50CQ101&&tagvalue==int(150,200)&&isgood==boolean(-1)&&sendts==date(now,yyyy-MM-dd HH:mm:ss)&&pits==date(now,yyyy-MM-dd HH:mm:ss)"
          },
          {
            "point": "tagname==2385.C800HTA50CQ101&&tagvalue==int(200,210)&&isgood==boolean(-1)&&sendts==date(now,yyyy-MM-dd HH:mm:ss)&&pits==date(now,yyyy-MM-dd HH:mm:ss)"
          },
          {
            "point": "tagname==2531.C700HTA50CQ101&&tagvalue==int(250,310)&&isgood==boolean(1)&&sendts==date(now,yyyy-MM-dd HH:mm:ss)&&pits==date(now,yyyy-MM-dd HH:mm:ss)"
          },
          {
            "point": "tagname==2581.C700HTA50CQ101&&tagvalue==int(310,410)&&isgood==boolean(0)&&sendts==date(now,yyyy-MM-dd HH:mm:ss)&&pits==date(now,yyyy-MM-dd HH:mm:ss.SSS)"
          }
        ]
      }
    },
    {
      "topicName": "itdeer4",
      "threads": 1,
      "dataNumber": 100000,
      "timeFrequency": -1,
      "dataMapping": {
        "type": "csv", //格式为CSV
        "appoint": "true", //使用模板
        "separator": ",", //分隔符
        "points": [
          {
            "point": "tagname==CH4.A4037&&tagvalue==int(0,10)&&isgood==boolean(0)&&sendts==date(now,yyyy-MM-dd HH:mm:ss)&&pits==date(now,yyyy-MM-dd HH:mm:ss.SSS)"
          },
          {
            "point": "tagname==CH4.A4038&&tagvalue==int(10,30)&&isgood==boolean(1)&&sendts==date(now,yyyy-MM-dd HH:mm:ss)&&pits==date(now,yyyy-MM-dd HH:mm:ss)"
          },
          {
            "point": "tagname==H2.A00015&&tagvalue==int(30,50)&&isgood==boolean(-1)&&sendts==date(now,yyyy-MM-dd HH:mm:ss)&&pits==date(now,yyyy-MM-dd HH:mm:ss)"
          },
          {
            "point": "tagname==H2.A00010&&tagvalue==int(50,80)&&isgood==boolean(-1)&&sendts==date(now,yyyy-MM-dd HH:mm:ss)&&pits==date(now,yyyy-MM-dd HH:mm:ss)"
          },
          {
            "point": "tagname==NH3A00008&&tagvalue==int(80,100)&&isgood==boolean(-1)&&sendts==date(now,yyyy-MM-dd HH:mm:ss)&&pits==date(now,yyyy-MM-dd HH:mm:ss)"
          },
          {
            "point": "tagname==NH3A00018&&tagvalue==int(100,150)&&isgood==boolean(-1)&&sendts==date(now,yyyy-MM-dd HH:mm:ss)&&pits==date(now,yyyy-MM-dd HH:mm:ss)"
          },
          {
            "point": "tagname==2331.C800HTA50CQ101&&tagvalue==int(150,200)&&isgood==boolean(-1)&&sendts==date(now,yyyy-MM-dd HH:mm:ss)&&pits==date(now,yyyy-MM-dd HH:mm:ss)"
          },
          {
            "point": "tagname==2385.C800HTA50CQ101&&tagvalue==int(200,210)&&isgood==boolean(-1)&&sendts==date(now,yyyy-MM-dd HH:mm:ss)&&pits==date(now,yyyy-MM-dd HH:mm:ss)"
          },
          {
            "point": "tagname==2531.C700HTA50CQ101&&tagvalue==int(250,310)&&isgood==boolean(1)&&sendts==date(now,yyyy-MM-dd HH:mm:ss)&&pits==date(now,yyyy-MM-dd HH:mm:ss)"
          },
          {
            "point": "tagname==2581.C700HTA50CQ101&&tagvalue==int(310,410)&&isgood==boolean(0)&&sendts==date(now,yyyy-MM-dd HH:mm:ss)&&pits==date(now,yyyy-MM-dd HH:mm:ss.SSS)"
          }
        ]
      }
    },
    
    {
      "topicName": "itdeer5",
      "threads": 1,
      "dataNumber": 100000,
      "timeFrequency": 5000,
      "dataMapping": {
        "type": "csv",
        "appoint": "true", //是否指定点位
        "separator": ",", // 分隔符
        "pointFile": {      // 点位从文件中获取
          "fields": "tagname==row_1&&tagvalue==int(row_2,row_3)&&isgood==boolean(0)&&sendts==date&&pits==date", // 点位文件的每一行数据在整体数据中所在位置
          "mapping": "row_1,row_2,row_3", //点位文件的具体行
          "fileName": "point.csv"  //点位文件名称
        }
      }
    }
  ]
}

```



### 应用程序整体的配置详细说明

[1] kafka

 - bootstrapServers

    ```
    参数名称：bootstrapServers
    默认值：192.168.1.220:9092
    参数说明：KAFKA_BOOTSTRAP_SERVERS:用于初始化时建立链接到kafka集群
         以host:port形式,多个以逗号分隔host1:port1,host2:port2
    ```
    
 - acks
 
    ```
    参数名称：acks
    默认值：0
    参数说明：
        acks:生产者需要server端在接收到消息后,进行反馈确认的尺度,主要用于消息的可靠性传输
        acks=0表示生产者不需要来自server的确认
        acks=1表示server端将消息保存后即可发送ack,而不必等到其他follower角色的都收到了该消息
        acks=all(or acks=-1)意味着server端将等待所有的副本都被接收后才发送确认
    ```

 - retries

    ```
    参数名称：retries
    默认值：3
    参数说明：
        retries: producer消息发送失败后，重试的次数
        默认值为0，不进行重试
    ```

 - lingerMs

    ```
    参数名称：lingerMs
    默认值：100
    参数说明：
        linger.ms: 默认值为0,默认情况下缓冲区的消息会被立即发送到服务端，即使缓冲区的空间并没有被用完。
        可以将该值设置为大于0的值，这样发送者将等待一段时间后，再向服务端发送请求，以实现每次请求可以尽可能多的发送批量消息。
        batch.size和linger.ms是两种实现让客户端每次请求尽可能多的发送消息的机制，它们可以并存使用，并不冲突
    ```

 - batchSize

    ```
    参数名称：batchSize
    默认值：102400
    参数说明：
        batch.size:当多条消息发送到同一个partition时,该值控制生产者批量发送消息的大小,
        批量发送可以减少生产者到服务端的请求数,有助于提高客户端和服务端的性能
        默认 1048576 B
    ```

 - bufferMemory

    ```
    参数名称：bufferMemory
    默认值：133554432
    参数说明：
        buffer.memory: 制定producer端用于缓存消息的缓冲区大小，保存的是还未来得及发送到server端的消息，
        如果生产者的发送速度大于消息被提交到server端的速度，该缓冲区将被耗尽
        默认值为 33554432 ,即 32MB
    ```

 - maxRequestSize

    ```
    参数名称：maxRequestSize
    默认值：10485760
    参数说明：
        max.request.size: 官网上解释该参数用于控制producer发送请求的大小
        实际上该参数控制的是producer端能够发送的最大消息大小
    ```

 - compressionType

    ```
    参数名称：compressionType
    默认值：lz4
    参数说明：
        压缩数据的压缩类型。压缩最好用于批量处理，批量处理消息越多，压缩性能越好
        none : 无压缩,默认值。
        gzip :
        snappy : 由于kafka源码的某个关键设置，使得snappy表现不如lz4
        lz4 : producer 结合lz4 的性能较好
        性能：lz4 >> snappy >> gzip
    ```

 - requestTimeoutMs

    ```
    参数名称：requestTimeoutMs
    默认值：60000（60s）
    参数说明：
        消息发送的最长等待时间
        当producer发送请求给broker后，broker需要在规定的时间范围内将处理结果返回给producer
        request.timeout.ms 即控制这个时间，默认值为30s
        通常情况下，超时会在回调函数中抛出TimeoutException异常交由用户处理
    ```

 - maxInFlightRequestsPer

    ```
    参数名称：maxInFlightRequestsPer
    默认值：1
    参数说明：
        限制producer在单个broker连接上能够发送的未响应请求的数量
    ```

 - keySerializer valueSerializer

    ```
    参数名称： keySerializer  valueSerializer
    默认值：
        org.apache.kafka.common.serialization.StringSerializer
        org.apache.kafka.common.serialization.StringSerializer
    参数说明：
        key.serializer, value.serializer说明了使用何种序列化方式将用户提供的key和vaule值序列化成字节
    ```
[2] commons

 - stringFieldDefaultLength
 
    ```
    参数名称： stringFieldDefaultLength
    默认值：4
    参数说明：
        字符串的长度为默认4个字符组成
    ```

 - stringFieldDefaultWrite
 
    ```
    参数名称： stringFieldDefaultWrite
    默认值：7
    参数说明：
         1 表示大写  2 表示小写 3 表示数字 4 表示大写小写混合 5 表示小写和数字 6 表示大写和数字 7 表示大写小写和数字
    ```
    
 - booleanFieldDefaultFlag
 
    ```
    参数名称： booleanFieldDefaultFlag
    默认值：0
    参数说明：
        1 表示true  -1 表示false 0 表示随机true或false
    ```
     
 - intFieldDefaultMin
 
    ```
    参数名称： intFieldDefaultMin
    默认值：0
    参数说明：
        表示int最小取值范围
    ```
       
 - intFieldDefaultMax
 
    ```
    参数名称： intFieldDefaultMax
    默认值：10000
    参数说明：
        表示int最大取值范围
    ```
         
 - doubleFieldDefaultFormat
 
    ```
    参数名称： doubleFieldDefaultFormat
    默认值：#0.000
    参数说明：
        表示保留的小数点位数
    ```      
 
 - doubleFieldDefaultMin
 
    ```
    参数名称： doubleFieldDefaultMin
    默认值：0
    参数说明：
       表示double最小取值范围
    ```

 - doubleFieldDefaultMax
 
    ```
    参数名称： doubleFieldDefaultMax
    默认值：10000
    参数说明：
         表示double最大取值范围
    ```
    
 - floatFieldDefaultFormat
 
    ```
    参数名称： floatFieldDefaultFormat
    默认值：#0.00
    参数说明：
        表示保留的小数点位数
    ```
     
 - floatFieldDefaultMin
 
    ```
    参数名称： floatFieldDefaultMin
    默认值：0
    参数说明：
        表示float最小取值范围
    ```
       
 - floatFieldDefaultMax
 
    ```
    参数名称： floatFieldDefaultMax
    默认值：10000
    参数说明：
        表示float最大取值范围
    ```
         
 - dateFieldDefaultStartPoint
 
    ```
    参数名称： dateFieldDefaultStartPoint
    默认值：now
    参数说明：
        表示开始时间
    ```   
       
 - dateFieldDefaultFormat
 
    ```
    参数名称： dateFieldDefaultFormat
    默认值：yyyy-MM-dd HH:mm:ss
    参数说明：
        表示时间格式
    ```  
  
[3] message

 - topicName
 
    ```
    参数名称： topicName
    默认值：
    参数说明：
        主题名称
    ```
         
 - threads
 
    ```
    参数名称： threads
    默认值：1
    参数说明：
        启动的线程数
    ```      
 
 - dataNumber
 
    ```
    参数名称： dataNumber
    默认值：10000
    参数说明：
       表示单线程发送的数据条数或批数 若为0则表示无限发送
    ```

 - timeFrequency
 
    ```
    参数名称： timeFrequency
    默认值：-1
    参数说明：
         表示时间间隔 -1表示没有时间间隔
    ```
    
 - type
 
    ```
    参数名称： type
    默认值：json
    参数说明：
        数据格式 支持JSON和CSV
    ```
     
 - appoint
 
    ```
    参数名称： appoint
    默认值：false
    参数说明：
        是否是模板不是模板的情况下需要配置fields字段，需要模板的话配置points
    ```
       
 - separator
 
    ```
    参数名称： separator
    默认值：,
    参数说明：
        CSV格式数据的分隔符，JSON格式则不需要配置
    ```
         
 - fields
 
    ```
    参数名称： fields
    默认值：
    参数说明：
        发送的一条数据中含有的字段信息，每一个字段都要分开写，指定好字段的名称和值的数据类型及取值范围，若为CSV格式也需要配置字段名称
        只是数据中不体现字段名称，但是数据是按照字段配置的顺序组织的数据，
        以上对单个字段进行了描述，这里就不赘述了，按照实例配制即可
    ```   
       
 - points
 
    ```
    参数名称： points
    默认值：
    参数说明：
        一批要发送的多条数据的配置，每一条数据都可以不一样，针对每一条数据进行特性配置，每次发送是一批数据，所以注意发数总量和线程数的问题
        以上对单个字段进行了描述，这里就不赘述了，按照实例配制即可
    ```
       
 - pointFile
  
    ```
    参数名称： pointFile
    默认值：
    参数说明：
        一批要发送的多条数据的配置，每一条数据都可以不一样，针对每一条数据进行特性配置，每次发送是一批数据，所以注意发数总量和线程数的问题
        需要配置字段和文件行以及文件名称
    ```
    
 - fields
  
    ```
    参数名称： fields
    默认值：
    参数说明：
        消息模板，指出具体的字段从文件列中来
    ```  

 - mapping
  
    ```
    参数名称： mapping
    默认值：
    参数说明：
        文件列的具体列
    ```
    
 - fileName
  
    ```
    参数名称： fileName
    默认值：
    参数说明：
        点位数据来源文件，文件名称
    ```
 
### 在本机的测试结果

> 本次测试只是针对我开发工具到我的虚机得结果，在服务器上性能应该要比这个要高一些。
 
[1] JSON格式，不使用模板

> 在这台小机器上性能只能说还行，多个线程在一定范围内是成倍增加的

```
ThreadNum 	TopicName 	TotleTime(s) 	InitTime(s) 	SendTime(s) 	StartDate 	EndDate 	TotleMessageNums 	Speed 	
Thread-1 	itdeer1 	259.0 	0.0 	259.0 	2019-08-30 23:32:05 	2019-08-30 23:36:25 	50000000 	193050.19305019305
```

[2] CSV格式，不使用模板

> 在这台小机器上性能只能说还行，多个线程在一定范围内是成倍增加的

```
ThreadNum 	TopicName 	TotleTime(s) 	InitTime(s) 	SendTime(s) 	StartDate 	EndDate 	TotleMessageNums 	Speed 	
Thread-1 	itdeer2 	242.0 	0.0 	242.0 	2019-08-30 23:26:23 	2019-08-30 23:30:26 	50000000 	206611.57024793388
```

[3] JSON格式，使用模板

> 在这台小机器上性能只能说还行，多个线程在一定范围内是成倍增加的，使用的模板竟然没有降低太多的性能

```
ThreadNum 	TopicName 	TotleTime(s) 	InitTime(s) 	SendTime(s) 	StartDate 	EndDate 	TotleMessageNums 	Speed 	
Thread-1 	itdeer3 	231.0 	0.0 	231.0 	2019-08-30 23:16:58 	2019-08-30 23:20:50 	5000000 	21645.021645021647
```

[4] CSV格式，使用模板

> 在这台小机器上性能只能说还行，多个线程在一定范围内是成倍增加的，使用的模板竟然没有降低太多的性能

```
ThreadNum 	TopicName 	TotleTime(s) 	InitTime(s) 	SendTime(s) 	StartDate 	EndDate 	TotleMessageNums 	Speed 	
Thread-1 	itdeer4 	177.0 	0.0 	177.0 	2019-08-30 23:09:37 	2019-08-30 23:12:35 	5000000 	28248.58757062147 
```