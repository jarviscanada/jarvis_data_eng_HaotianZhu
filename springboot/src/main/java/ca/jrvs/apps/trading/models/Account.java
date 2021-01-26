package ca.jrvs.apps.trading.models;

public class Account implements Entity<Integer> {

    private Integer id;
    private Integer traderId;
    private Double amount;

    public Integer getTraderId() {
        return traderId;
    }

    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public void depositAmount(Double fund){
        this.amount += fund;
    }

    public void withdrawAmount(Double fund){
        this.amount -= fund;
    }
}
