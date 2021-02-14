package ca.jrvs.apps.trading.models;

import java.util.Optional;

public class Quote implements Entity<String> {

    private String ticker;
    private Double lastPrice;
    private Double bidPrice;
    private Integer bidSize;
    private Double askPrice;
    private Integer askSize;

    public Quote(){

    }

    public Quote(String ticker){
        this.setId(ticker);
        this.setAskSize(-1);
        this.setAskPrice(-1d);
        this.setBidSize(-1);
        this.setBidPrice(-1d);
        this.setLastPrice(-1d);
    }

    public Quote(IexQuote quote){
        this.setId(quote.getSymbol());
        this.setAskSize(Optional.ofNullable(quote.getIexAskSize()).map(Long::intValue).orElse(-1));
        this.setAskPrice(Optional.ofNullable(quote.getIexAskPrice()).orElse(-1d));
        this.setBidSize( Optional.ofNullable(quote.getIexBidSize()).map(Long::intValue).orElse(-1));
        this.setBidPrice(Optional.ofNullable(quote.getIexBidPrice()).orElse(-1d));
        this.setLastPrice(Optional.ofNullable(quote.getLatestPrice()).orElse(-1d));
    }

    public Double getAskPrice() {
        return askPrice;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setAskPrice(Double askPrice) {
        this.askPrice = askPrice;
    }

    public Integer getAskSize() {
        return askSize;
    }

    public void setAskSize(Integer askSize) {
        this.askSize = askSize;
    }

    @Override
    public String getId() {
        return ticker;
    }

    public Double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public Double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(Double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Integer getBidSize() {
        return bidSize;
    }

    public void setBidSize(Integer bidSize) {
        this.bidSize = bidSize;
    }

    @Override
    public void setId(String ticker) {
        this.ticker = ticker;
    }

}
