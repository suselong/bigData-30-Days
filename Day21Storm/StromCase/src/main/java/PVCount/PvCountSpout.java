package PVCount;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.io.*;
import java.util.Map;

/**
 * @author CDMloong
 */
public class PvCountSpout implements IRichSpout{
  private SpoutOutputCollector spoutOutputCollector;
  private BufferedReader content;
  private String line;

  /**
   * 收集器，用于接收数据
   * @param map
   * @param topologyContext
   * @param spoutOutputCollector
   */
  public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
    this.spoutOutputCollector = spoutOutputCollector;
    try {
      content = new BufferedReader( new InputStreamReader( new FileInputStream( "E:/weblog.log" ) ) );
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * 关闭资源，不一定必须被调用
   */
  public void close() {
  }
  /**
   * 激活已经被禁止的拓扑
   */
  public void activate() {
  }

  /**
   * 禁止正在运行的拓扑
   */
  public void deactivate() {

  }

  /**
   * 发送数据
   */
  public void nextTuple() {
    // 发送数据
    while (true) {
      try {
        if (((line = content.readLine()) != null)) break;
      } catch (IOException e) {
        e.printStackTrace();
      }
      spoutOutputCollector.emit( new Values( content ) );
    }
    //延迟时间
    try {
      Thread.sleep( 500 );
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * 回调方法，用于发送到下个组件成功后回调
   * @param o
   */
  public void ack(Object o) {

  }

  /**
   * 失败后回调
   * @param o
   */
  public void fail(Object o) {
  }

  /**
   * 描述声明
   * @param outputFieldsDeclarer
   */
  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    outputFieldsDeclarer.declare( new Fields( "logs" ) );
  }

  /**
   * 加入一些配置
   * @return
   */
  public Map<String, Object> getComponentConfiguration() {
    return null;
  }
}
