package KafkaStream;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorSupplier;

import java.util.Properties;

/**
 * @author CDMloong
 * 需求：对数据进行清洗操作
 * 思路：along-jianchi，把-去掉
 */
public class Application {
  public static void main(String[] args) {
    //1. 定义主题发送到另外一个主题，过程中我做了数据清洗
    String topic01 = "t1";
    String topic02 = "t2";
    //2. 设置属性
    Properties proper = new Properties();
    //2.1 设置数据清洗接口
    proper.put( StreamsConfig.APPLICATION_ID_CONFIG, "LogProcessor" );
    //2.2 设置kafka服务器
    proper.put( StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,
        "192.128.244.129:9092,192.128.244.130:9092,192.128.244.131:9092" );
    //3. 实例对象
    StreamsConfig streamsConfig = new StreamsConfig( proper );
    //4. 流计算，拓扑图
    Topology topology = new Topology();
    //5. 定义kafka组件的数据源 -> 经过数据清洗的过程   source -> processor -> sink
    topology.addSource( "Source", topic01 ).addProcessor( "Processor", new ProcessorSupplier<byte[], byte[]>() {
      public Processor<byte[], byte[]> get() {
        return new LogProcessor();
      }
      //从哪里来
    }, "Source" ).addSink( "Sink", topic02, "Processor" );

    //6. 实例化kafkaStream
    KafkaStreams kafkaStreams = new KafkaStreams( topology, proper );

    //7. 启动程序
    kafkaStreams.start();
  }
}
