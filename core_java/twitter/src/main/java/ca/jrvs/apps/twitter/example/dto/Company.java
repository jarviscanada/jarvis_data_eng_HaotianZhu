package ca.jrvs.apps.twitter.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
// @JsonPropertyOrder({
//         "symbol",
//         "companyName",
//         "exchange",
//         "description",
//         "CEO",
//         "sector",
//         "financials",
//         "dividends"
// })
public class Company {

    private String symbol;
    private String companyName;
    private String exchange;
    private String description;
    private String ceo;
    private String sector;


    private List<Financial> financials = null;

    private List<Dividend> dividends = null;

    @JsonProperty("symbol")
    public String getSymbol() {
        return symbol;
    }

    @JsonProperty("symbol")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @JsonProperty("companyName")
    public String getCompanyName() {
        return companyName;
    }

    @JsonProperty("companyName")
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @JsonProperty("exchange")
    public String getExchange() {
        return exchange;
    }

    @JsonProperty("exchange")
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("CEO")
    public String getCeo() {
        return ceo;
    }

    @JsonProperty("CEO")
    public void setCeo(String ceo) {
        this.ceo = ceo;
    }

    @JsonProperty("sector")
    public String getSector() {
        return sector;
    }

    @JsonProperty("sector")
    public void setSector(String sector) {
        this.sector = sector;
    }

    @JsonProperty("financials")
    public List<Financial> getFinancials() {
        return financials;
    }

    @JsonProperty("financials")
    public void setFinancials(List<Financial> financials) {
        this.financials = financials;
    }

    @JsonProperty("dividends")
    public List<Dividend> getDividends() {
        return dividends;
    }

    @JsonProperty("dividends")
    public void setDividends(List<Dividend> dividends) {
        this.dividends = dividends;
    }
}