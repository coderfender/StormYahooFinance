package com.yahoo.stock;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import bolts.PriceCutOffBolt;
import spouts.YahooFinanceSpout;

public class YahooFinanceStorm {
  public static void main(String[] args) throws Exception {
    Config config = new Config();
    config.setDebug(false);

    TopologyBuilder builder = new TopologyBuilder();
    builder.setSpout("yahoo-finance-spout", new YahooFinanceSpout());

    builder.setBolt("price-cutoff-bolt", new PriceCutOffBolt())
        .fieldsGrouping("yahoo-finance-spout", new Fields("company"));

    LocalCluster cluster = new LocalCluster();
    cluster.submitTopology("YahooFinanceStorm", config,
        builder.createTopology());
    Thread.sleep(10000);
    cluster.shutdown();
    System.out.println("Done Processing your first storm project!!");
  }
}