### 编译运行环境

```
编译环境：
JDK: 1.8+
Maven：3.3.9+

打包命令：
mvn clean package

运行命令：
在DataToKafka-1.0.0.jar所在的目录，创建名为config的目录，
在config目录中创建runtime.properties的配置文件，配置一下必要的参数即可运行起来

java -jar 在DataToKafka-1.0.0.jar

会在Jar包所在的目录创建logs目录，目录里面有相关的运行日志，可供查阅
```

### 关于程序运行参数设定

[1] DATA_FORMAT

```
参数名称：DATA_FORMAT(日期格式)
是否必须：否（不设置此参数则使用默认值）
默认值：yyyy-MM-dd HH:mm:ss
参数示例：DATA_FORMAT=yyyy-MM-dd HH:mm:ss
```

> 参数介绍

```
设置发送数据的时间格式
```

[2] TOPIC_NAME_AND_THREADS

```
参数名称：TOPIC_NAME_AND_THREADS(主题名称 启动的线程数 每一个线程需要发送的数据量)
是否必须：是
默认值：无
参数示例：TOPIC_NAME_AND_THREADS=[demo,2,1000000]
```

> 参数介绍

```
[demo,2,1000000]

三个参数：
    第一个参数是：主题的名称 不能为空（需要提前创建，默认创建的话只有一个分区）
    第二个参数是：启动的线程数 可以不设置（默认为1个线程）
    第三个参数是：每一个线程发送的数据量 可以不设置（默认为10000条消息）
```

[3] SINGLE_THREAD_SEND_DATA

```
参数名称：SINGLE_THREAD_SEND_DATA(每个线程默认发数的数据量)
是否必须：否
默认值：10000
参数示例：SINGLE_THREAD_SEND_DATA=10000
```

> 参数介绍

```
当不设置某一个Topic单线程发数的数据量时使用的默认值，此默认值可以通过此参数进行设置
当需要每一个线程都需要发送同样条数的数据时，可以在TOPIC_NAME_AND_THREADS 参数不设置单个线程的发数条数，此时就会使用的是默认值
```

### 关于Kafka运行参数设定

[1] KAFKA_BOOTSTRAP_SERVERS

```
参数名称：KAFKA_BOOTSTRAP_SERVERS
是否必须：是
默认值：172.24.4.235:6667,172.24.4.236:6667,172.24.4.237:6667
参数示例：KAFKA_BOOTSTRAP_SERVERS=172.24.4.35:6667,172.24.4.36:6667,172.24.4.37:6667
```

> 参数介绍

```
KAFKA_BOOTSTRAP_SERVERS:用于初始化时建立链接到kafka集群
以host:port形式,多个以逗号分隔host1:port1,host2:port2
```

[2] KAFKA_ACKS

```
参数名称：KAFKA_ACKS
是否必须：否
默认值：0
参数示例：KAFKA_ACKS=0
```

> 参数介绍

```
acks:生产者需要server端在接收到消息后,进行反馈确认的尺度,主要用于消息的可靠性传输
acks=0表示生产者不需要来自server的确认
acks=1表示server端将消息保存后即可发送ack,而不必等到其他follower角色的都收到了该消息
acks=all(or acks=-1)意味着server端将等待所有的副本都被接收后才发送确认
```

[3] KAFKA_RETRIES

```
参数名称：KAFKA_RETRIES
是否必须：否
默认值：3
参数示例：KAFKA_RETRIES=2
```

> 参数介绍

```
retries: producer消息发送失败后，重试的次数
默认值为0，不进行重试
```

[4] KAFKA_LINGER_MS

```
参数名称：KAFKA_LINGER_MS
是否必须：是
默认值：100
参数示例：KAFKA_LINGER_MS=200
```

> 参数介绍

```
linger.ms: 默认值为0,默认情况下缓冲区的消息会被立即发送到服务端，即使缓冲区的空间并没有被用完。
可以将该值设置为大于0的值，这样发送者将等待一段时间后，再向服务端发送请求，以实现每次请求可以尽可能多的发送批量消息。
batch.size和linger.ms是两种实现让客户端每次请求尽可能多的发送消息的机制，它们可以并存使用，并不冲突
```

[5] KAFKA_BATCH_SIZE

```
参数名称：KAFKA_BATCH_SIZE
是否必须：否
默认值：102400
参数示例：KAFKA_BATCH_SIZE=102400
```

> 参数介绍

```
batch.size:当多条消息发送到同一个partition时,该值控制生产者批量发送消息的大小,
批量发送可以减少生产者到服务端的请求数,有助于提高客户端和服务端的性能
默认 1048576 B
```

[6] KAFKA_BUFFER_MEMORY

```
参数名称：KAFKA_BUFFER_MEMORY
是否必须：否
默认值：133554432
参数示例：KAFKA_BUFFER_MEMORY=335544320
```

> 参数介绍

```
buffer.memory: 制定producer端用于缓存消息的缓冲区大小，保存的是还未来得及发送到server端的消息，
如果生产者的发送速度大于消息被提交到server端的速度，该缓冲区将被耗尽
默认值为 33554432 ,即 32MB
```

[7] KAFKA_MAX_REQUEST_SIZE

```
参数名称：KAFKA_MAX_REQUEST_SIZE
是否必须：否
默认值：10485760
参数示例：KAFKA_MAX_REQUEST_SIZE=10485760
```

> 参数介绍

```
max.request.size: 官网上解释该参数用于控制producer发送请求的大小
实际上该参数控制的是producer端能够发送的最大消息大小
```


[8] KAFKA_COMPRESSION_TYPE

```
参数名称：KAFKA_COMPRESSION_TYPE
是否必须：否
默认值：lz4
参数示例：KAFKA_COMPRESSION_TYPE=lz4
```

> 参数介绍

```
压缩数据的压缩类型。压缩最好用于批量处理，批量处理消息越多，压缩性能越好
none : 无压缩,默认值。
gzip :
snappy : 由于kafka源码的某个关键设置，使得snappy表现不如lz4
lz4 : producer 结合lz4 的性能较好
性能：lz4 >> snappy >> gzip
```

[9] KAFKA_REQUEST_TIMEOUT_MS

```
参数名称：KAFKA_REQUEST_TIMEOUT_MS
是否必须：否
默认值：60000（60s）
参数示例：KAFKA_REQUEST_TIMEOUT_MS=60000
```

> 参数介绍

```
消息发送的最长等待时间
当producer发送请求给broker后，broker需要在规定的时间范围内将处理结果返回给producer
request.timeout.ms 即控制这个时间，默认值为30s
通常情况下，超时会在回调函数中抛出TimeoutException异常交由用户处理
```

[10] KAFKA_MAX_IN_FLIGHT_REQUESTS_PER

```
参数名称：KAFKA_MAX_IN_FLIGHT_REQUESTS_PER
是否必须：否
默认值：1
参数示例：KAFKA_MAX_IN_FLIGHT_REQUESTS_PER=1
```

> 参数介绍

```
限制producer在单个broker连接上能够发送的未响应请求的数量
```

[11] KAFKA_KEY_SERIALIZER_CLASS_STRING KAFKA_VALUE_SERIALIZER_CLASS_STRING

```
参数名称：
    KAFKA_KEY_SERIALIZER_CLASS_STRING
    KAFKA_VALUE_SERIALIZER_CLASS_STRING
是否必须：否
默认值：
    org.apache.kafka.common.serialization.StringSerializer
    org.apache.kafka.common.serialization.StringSerializer
参数示例：
    KAFKA_KEY_SERIALIZER_CLASS_STRING=org.apache.kafka.common.serialization.StringSerializer
    KAFKA_VALUE_SERIALIZER_CLASS_STRING=org.apache.kafka.common.serialization.StringSerializer
```

> 参数介绍

```
key.serializer, value.serializer说明了使用何种序列化方式将用户提供的key和vaule值序列化成字节
```

