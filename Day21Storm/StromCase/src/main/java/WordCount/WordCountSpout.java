package WordCount;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * @author CDMloong
 * 单词计数
 * 实现接口：IRichSpout IRichBlout
 * 继承抽象类：BaseRichSpout BaseRichBlout
 */
public class WordCountSpout extends BaseRichSpout {

  /*
   * 定义收集器
   */
  private SpoutOutputCollector spoutOutputCollector;

  /**
   * 创建收集器,目的是使用收集器来收集数据
   * @param map
   * @param topologyContext
   * @param spoutOutputCollector
   */
  public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
    this.spoutOutputCollector = spoutOutputCollector;
  }

  /**
   * 发送数据，收集到数据需要把数据发送处理,发送到Blout里面
   */
  public void nextTuple() {
    //1. 发送数据,需要使用两个Blout，一个进行切分，一个进行汇总
    spoutOutputCollector.emit( new Values( "i an along very shuai along xiaoyu i very shuai an an an an" ) );

    //2. 设置延迟，不能太快
    try {
      Thread.sleep( 500 );
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * 声明描述
   * @param outputFieldsDeclarer
   */
  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    // 起别名
    outputFieldsDeclarer.declare( new Fields( "along" ) );
  }
}
