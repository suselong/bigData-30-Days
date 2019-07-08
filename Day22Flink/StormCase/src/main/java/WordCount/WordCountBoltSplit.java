package WordCount;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * @author CDMloong
 */
public class WordCountBoltSplit extends BaseRichBolt {

  /*
   * 需要继续发送到下一个blot，因此还需要收集器，并且修改作用域
   */
  private OutputCollector outputCollector;

  /**
   * 初始化数据
   * @param map
   * @param topologyContext
   * @param outputCollector
   */
  public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
    this.outputCollector = outputCollector;
  }

  /**
   * 执行，进行业务逻辑编写
   * @param tupleIn
   */
  public void execute(Tuple tupleIn) {
    //1.获取数据,通过声明的别名来获取数据
    String along = tupleIn.getStringByField( "along" );

    //2.切分
    String[] fields = along.split( " " );

    //3.数组存储<单词,1>发送出去到下一个bolt(进行累加求和)
    for (String field : fields) {
      outputCollector.emit( new Values( field, 1 ) );
    }

  }

  /**
   * 声明描述
   * @param outputFieldsDeclarer
   */
  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    // 起别名
    outputFieldsDeclarer.declare( new Fields( "word", "count" ) );

  }
}
