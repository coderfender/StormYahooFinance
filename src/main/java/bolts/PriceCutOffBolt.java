package bolts;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

public class PriceCutOffBolt implements IRichBolt {
  Map<String, Integer> cutOffMap;
  Map<String, Boolean> resultMap;

  private OutputCollector collector;

  @Override
  public void prepare(Map conf, TopologyContext context,
      OutputCollector collector) {
    this.cutOffMap = new HashMap<String, Integer>();
    this.cutOffMap.put("INTC", 100);
    this.cutOffMap.put("AAPL", 100);
    this.cutOffMap.put("GOOGL", 100);

    this.resultMap = new HashMap<String, Boolean>();
    this.collector = collector;
  }

  @Override
  public void execute(Tuple tuple) {
    String company = tuple.getString(0);
    Double price = tuple.getDouble(1);

    if (this.cutOffMap.containsKey(company)) {
      Integer cutOffPrice = this.cutOffMap.get(company);

      if (price < cutOffPrice) {
        this.resultMap.put(company, true);
      } else {
        this.resultMap.put(company, false);
      }
    }

    collector.ack(tuple);
  }

  @Override
  public void cleanup() {
    for (Map.Entry<String, Boolean> entry : resultMap.entrySet()) {
      System.out.println(entry.getKey() + " : " + entry.getValue());
    }
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields("cut_off_price"));
  }

  @Override
  public Map<String, Object> getComponentConfiguration() {
    return null;
  }

}