package ca.jrvs.apps.trading.models;

public class Quote implements Entity<String> {

    private String ticker;
    private Double lastPrice;
    private Double bidPrice;
    private Integer bidSize;
    private Double askPrice;
    private Integer askSize;

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
