package KafkaStream;


import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;

/**
 * @author CDMloong
 * 数据清洗
 */
public class LogProcessor implements Processor<byte[], byte[]> {

  private ProcessorContext context;

  /**
   * 初始化
   *
   * @param processorContext 传输的内容
   */
  public void init(ProcessorContext processorContext) {
    //传输数据
    this.context = processorContext;
  }

  /**
   * 具体的业务逻辑
   *
   * @param key 内容key
   * @param value 内容
   */
  public void process(byte[] key, byte[] value) {
    //1. 拿到消息数据,转换成字符串
    String message = new String(value);
    //2. 如果包含-，则去除
    if (message.contains("-")) {
      //3. 把-去掉，之后去掉左侧数据
      message = message.split("-")[1];
    }
    //4. 发送数据
    context.forward(key, message.getBytes());

  }

  /**
   * 释放资源
   */
  public void close() {

  }
}
