package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.models.IexQuote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@org.springframework.stereotype.Service
public class QuoteService extends Service {
    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);
    private MarketDataDao marketDao;

    @Autowired
    public QuoteService(MarketDataDao marketDao){
        this.marketDao = marketDao;
    }

    public IexQuote findIexQuoteByTicker(String ticker){
        return marketDao.findById(ticker).orElseThrow(()-> {
            logger.debug(ticker + "is invalid");
            return new IllegalArgumentException(ticker + " is invalid");
        });
    }

    public List<IexQuote> findIexQuoteByTickers(String... tickers){
        List<IexQuote> quotes = new ArrayList<>();
        marketDao.findAllById(Arrays.asList(tickers)).iterator().forEachRemaining(quotes::add);
        if(quotes.size() != tickers.length){
            logger.debug(tickers.toString() + " are invalid");
            throw new IllegalArgumentException(tickers.toString() + " are invalid");
        }
        return  quotes;
    }

}
