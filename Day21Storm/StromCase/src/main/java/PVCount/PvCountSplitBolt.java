package PVCount;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * @author CDMloong
 */
public class PvCountSplitBolt implements IRichBolt {
  private OutputCollector outputCollector;
  private int pvNum = 0;

  /**
   * 数据初始化，用于接收数据
   * @param map
   * @param topologyContext
   * @param outputCollector
   */
  public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
    this.outputCollector = outputCollector;
  }

  /**
   * 业务逻辑
   * @param tuple
   */
  public void execute(Tuple tuple) {
    // 通过字段拿去Spout的数据
    String logs = tuple.getStringByField( "logs" );
    // 按照制表符进行切分
    String[] split = logs.split( "\t" );
    String seesion = split[1];
    // 局部累加
    if (seesion != null) {
      //累加
      pvNum++;
      //输出
      outputCollector.emit( new Values( Thread.currentThread().getId(), pvNum ) );
    }
  }

  /**
   * 清理，不一定被执行，正常退出会被执行，异常情况下退出可能不会被执行
   */
  public void cleanup() {

  }

  /**
   * 描述
   * @param outputFieldsDeclarer
   */
  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    outputFieldsDeclarer.declare( new Fields( "session", "sum" ) );
  }

  /**
   * 进行一些配置
   * @return
   */
  public Map<String, Object> getComponentConfiguration() {
    return null;
  }
}
