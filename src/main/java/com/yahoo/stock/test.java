package com.yahoo.stock;

import java.io.IOException;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockQuote;

public class test {

  public static void main(String[] args) throws IOException {
    // TODO Auto-generated method stub
    //
    // Stock stock = YahooFinance.get("INTC");
    // BigDecimal price = stock.getQuote().getPrice();
    // System.out.println("The price is " + price);
    YahooFinance yahoo = new YahooFinance();
    Stock stock = yahoo.get("GOOG");
    StockQuote sq = stock.getQuote();

    System.out.println("Symbol: " + sq.getSymbol());
    System.out.println("Price: " + sq.getPrice());
    System.out.println("Date: " + sq.getLastTradeTime().getTime());

  }

}
