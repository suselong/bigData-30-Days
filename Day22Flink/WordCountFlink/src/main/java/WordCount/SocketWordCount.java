package WordCount;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * @author CDMloong
 */
public class SocketWordCount {
  public static void main(String[] args) throws Exception {
    //1. 定义连接端口
    final int port = 3333;
    //2. 创建执行环境对象
    final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    //3. 得到套接字对象(指定：主机、端口、分隔符)
    final DataStreamSource<String> text = env.socketTextStream( "192.168.244.129", port, "\n" );
    //4. 解析数据，统计数据-单词计数 list(list) 压平，把里面的list干掉,FlatMapFunction<String, WordWithCount>
    // 第一个参数是传入的数据，第二个是返回类型
    DataStream<WordWithCount> windosCounts = text.flatMap( new FlatMapFunction<String, WordWithCount>() {
      public void flatMap(String s, Collector<WordWithCount> collector) throws Exception {
        // 按照空白符进行切割
        for (String word : s.split( "\\s" )) {
          // <单词,1>
          collector.collect( new WordWithCount( word, 1L ) );
        }
      }
    } )
        //按照Key进行分组
        .keyBy( "word" )
        //设置窗口的时间长度，几秒进行一次计算, 5秒一次窗口，1秒一计算
        .timeWindow( Time.seconds( 5 ), Time.seconds( 1 ) )
        //聚合,聚合函数
        .reduce( new ReduceFunction<WordWithCount>() {
          public WordWithCount reduce(WordWithCount t1, WordWithCount t2) throws Exception {
            // 按照Key聚合
            return new WordWithCount( t1.word, t1.count + t2.count );
          }
        } );

    //5. 打印可以设置并发度
    windosCounts.print().setParallelism( 1 );

    //6. 执行程序
    env.execute( "Socket window WordCount" );

  }

  /**
   * 解析数据
   */
  public static class WordWithCount {
    String word;
    long count;

    public WordWithCount() {

    }

    WordWithCount(String word, long count) {
      this.word = word;
      this.count = count;
    }

    @Override
    public String toString() {
      return word + ":" + count;
    }
  }
}
