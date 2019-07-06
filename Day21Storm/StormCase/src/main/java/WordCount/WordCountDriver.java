package WordCount;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * @author CDMloong
 */
public class WordCountDriver {
  public static void main(String[] args) {
    //1.hadoop -> job; storm -> topology 创建topology
    TopologyBuilder builder = new TopologyBuilder();
    //2. 指定设置
    builder.setSpout( "WordCountSpout", new WordCountSpout(), 1 );
    //3. 指定Blout,设置分组策略
    builder.setBolt( "WordCountBoltSplit", new WordCountBoltSplit(), 4 ).
        fieldsGrouping( "WordCountSpout", new Fields( "along" ) );
    builder.setBolt( "WordCountBoltMerge", new WordCountBoltMerge(), 4 ).
        fieldsGrouping( "WordCountBoltSplit", new Fields( "word" ) );

    //4.创建配置信息
    Config config = new Config();

    //5.提交任务，本地提交
    LocalCluster localCluster = new LocalCluster();
    localCluster.submitTopology( "wordCountTopology", config, builder.createTopology() );
  }
}
