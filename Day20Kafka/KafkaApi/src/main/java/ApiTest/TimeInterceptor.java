package ApiTest;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * 自定义拦截器
 */
public class TimeInterceptor implements ProducerInterceptor<String, String> {

  /**
   * 拦截器主体内容，业务逻辑
   * @param producerRecord
   * @return
   */
  public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
    return new ProducerRecord<String, String>(
        producerRecord.topic(),
        producerRecord.partition(),
        producerRecord.key(),
        System.currentTimeMillis() + "_" + producerRecord.value() );
  }

  /**
   * 发送失败调用，应答
   * @param recordMetadata
   * @param e
   */
  public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {

  }

  /**
   * 关闭资源
   */
  public void close() {

  }

  /**
   * 配置信息
   * @param map
   */
  public void configure(Map<String, ?> map) {

  }
}
