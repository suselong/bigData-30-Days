package WordCount;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

public class WordCountBloutMerge extends BaseRichBolt {
  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

  }

  public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {

  }

  public void execute(Tuple tuple) {

  }
}
