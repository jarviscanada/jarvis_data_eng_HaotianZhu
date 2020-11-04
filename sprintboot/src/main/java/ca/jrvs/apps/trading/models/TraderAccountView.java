package ca.jrvs.apps.trading.models;

import java.sql.Date;

public class TraderAccountView {
    private Integer traderId;
    private String firstName;
    private String lastName;
    private String country;
    private String email;
    private Date dob;
    private Integer accountId;
    private Double amount;

    public Integer getTraderId() {
        return traderId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }

    public Date getDob() {
        return dob;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public Double getAmount() {
        return amount;
    }

    public TraderAccountView(Trader trader, Account account) {
        traderId = trader.getId();
        firstName = trader.getFirstName();
        lastName = trader.getLastName();
        country = trader.getCountry();
        email = trader.getEmail();
        dob = trader.getDob();
        accountId = account.getId();
        amount = account.getAmount();
    }
}
