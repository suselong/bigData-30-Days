package PVCount;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;


/**
 * @author CDMloong
 */
public class PvCountDriver {
  public static void main(String[] args) {
    //初始化拓扑
    TopologyBuilder builder = new TopologyBuilder();
    // 设置Spout，数据源
    builder.setSpout("PvCountSpout", new PvCountSpout(), 1);
    builder.setBolt("PvCountSplitBolt", new PvCountSplitBolt(), 6).setNumTasks(4)
        .fieldsGrouping("PvCountSpout", new Fields("logs"));
    builder.setBolt("PvCountSumBolt", new PvCountSumBolt(), 1).fieldsGrouping("PvCountSplitBolt", new Fields("pvnum"));
    // 初始化配置
    Config config = new Config();
    // 设置Worker数
    config.setNumWorkers( 2 );
    // 提交任务，本地提交
    LocalCluster localCluster = new LocalCluster();
    localCluster.submitTopology( "pvToplogy", config, builder.createTopology() );
  }
}
