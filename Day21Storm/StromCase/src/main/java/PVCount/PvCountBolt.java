package PVCount;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

public class PvCountBolt implements IRichBolt {

  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

  }

  public Map<String, Object> getComponentConfiguration() {
    return null;
  }

  public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {

  }

  public void execute(Tuple tuple) {

  }

  public void cleanup() {

  }
}
