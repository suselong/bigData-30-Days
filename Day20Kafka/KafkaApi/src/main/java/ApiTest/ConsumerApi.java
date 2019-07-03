package ApiTest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

/**
 * 消费者API
 */
public class ConsumerApi {
  public static void main(String[] args) {
    //1. 配置消费者属性
    Properties proper = new Properties();
    //1.1. 配置服务器地址
    proper.put( "bootstrap.servers", "192.168.244.130:9092" );
    //1.2. 配置消费者组
    proper.put( "group.id", "group01" );
    //1.3. 配置自动确认偏移量offset，可以实时确认当前的偏移量
    proper.put( "enable.auto.commit", "true" );
    //1.4. 必须配置序列化反序列化,和生产者不一样
    proper.put( "key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer" );
    proper.put( "value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer" );

    //2. 实例消费者
    final KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(proper);

    //4. 使用线程安全的方式释放资源
    Runtime.getRuntime().addShutdownHook(new Thread( new Runnable() {
      public void run() {
        if(consumer != null){
          consumer.close();
        }
      }
    } ));

    //5. 订阅消息
    consumer.subscribe(Collections.singletonList("shengdan"));

    //3. 接收消息,拉消息(Poll)
    while (true) {
      ConsumerRecords<String, String> records = consumer.poll( 500 );

      //3.1 遍历消息
      for (ConsumerRecord<String, String> record : records) {
        System.out.println( record.offset() + "----" + record.value() );
      }
    }

  }
}
