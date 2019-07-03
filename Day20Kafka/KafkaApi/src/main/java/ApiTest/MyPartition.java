package ApiTest;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * 自定义分区
 */
public class MyPartition implements Partitioner{
  /**
   * 设置分区逻辑
   */
  public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
    return 1;
  }

  /**
   * 关闭资源
   */
  public void close() {

  }

  /**
   * 配置文件
   */
  public void configure(Map<String, ?> map) {

  }
}
