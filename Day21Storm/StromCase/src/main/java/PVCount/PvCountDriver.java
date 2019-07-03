package PVCount;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;


/**
 * @author CDMloong
 */
public class PvCountDriver {
  public static void main(String[] args) {
    //初始化拓扑
    TopologyBuilder topology = new TopologyBuilder();
    // 设置Spout，数据源
    topology.setSpout( "PvCountSpout", new PvCountSpout(), 4 );
    // 设置Bolt
    topology.setBolt( "PvCountSplitBlot", new PvCountSplitBolt(), 4 ).shuffleGrouping( "PvCountSpout" );
    topology.setBolt( "PvCountBolt", new PvCountBolt(), 1 ).shuffleGrouping( "PvCountSplitBolt" );
    // 初始化配置
    Config config = new Config();
    // 设置Worker数
    config.setNumWorkers( 2 );
    // 提交任务，本地提交
    LocalCluster localCluster = new LocalCluster();
    localCluster.submitTopology( "pvToplogy", config, topology.createTopology() );
  }
}
