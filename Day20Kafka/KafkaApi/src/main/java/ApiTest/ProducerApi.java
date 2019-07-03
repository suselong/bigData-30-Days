package ApiTest;

import org.apache.kafka.clients.producer.*;

import java.util.ArrayList;
import java.util.Properties;

/**
 * 生成者API
 */

public class ProducerApi {
  public static void main(String[] args) {
    //1.配置生产者属性(指定多个参数)
    Properties proper = new Properties();
    //1.1. Kafka节点地址
    proper.put("bootstrap.servers", "192.168.244.129:9092");
    //1.2. 发送消息是否等待应答
    proper.put("acks", "all");
    //1.3. 发送消息失败重试
    proper.put("retries", "0");
    //1.4. 设置批量处理数据的大小
    proper.put("batch.size", "10241");
    //1.5. 设置批量处理数据的延迟
    proper.put("linger.ms", "10");
    //1.6. 设置内存缓冲区大小
    proper.put("buffer.memory", "10240000");
    //1.7. 数据发送前必须指定序列化
    proper.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    //1.8. 必须配置反序列化
    proper.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    //1.9. 指定分区
    proper.put("partitioner.class", "com.istart.kafka.Partition1");
    //2.0 配置拦截器
    ArrayList<String> inList = new ArrayList<String>();
    inList.add( "com.com.itstar.kafka.interceptor.TimeInterceptor" );
    proper.put( ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, inList );

    //2. 实例化Producer
    KafkaProducer<String, String> producer = new KafkaProducer<String, String>(proper);

    //3. 发送消息
    for (int i = 0; i < 100; i++) {
      producer.send(new ProducerRecord<String, String>("shengdan", "阿龙来了" + i + "次!"), new Callback() {
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
          if (recordMetadata != null) {
            System.out.println(recordMetadata.topic() + "------" + recordMetadata.toString() + "-----" + recordMetadata.partition());
          }
        }
      });
    }

    //4. 释放资源
    producer.close();

  }
}
