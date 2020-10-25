package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.models.IexQuote;
import ca.jrvs.apps.trading.models.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@org.springframework.stereotype.Service
public class QuoteService extends Service {
    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);
    private MarketDataDao marketDao;
    private QuoteDao quoteDao;

    @Autowired
    public QuoteService(MarketDataDao marketDao, QuoteDao quoteDao){
        this.marketDao = marketDao;
        this.quoteDao = quoteDao;
    }


    public void updateMarketData(){
         Iterable<Quote> oldQuotes = this.quoteDao.findAll();
         List<String> ids = new ArrayList<>();
         oldQuotes.forEach(x -> ids.add(x.getId()));
         List<IexQuote> updateQuotes = findIexQuoteByTickers(ids.toArray(new String[ids.size()]));
         List<Quote> quotes = updateQuotes.stream().map(Quote::new).collect(Collectors.toList());
         this.quoteDao.saveAll(quotes);
    }

    public Quote saveQuote(Quote quote){
        return this.quoteDao.save(quote);
    }

    public List<Quote> saveQuotes(List<Quote> quotes){
        return this.quoteDao.saveAll(quotes);
    }

    public List<Quote> findQuotes(){
        return this.quoteDao.findAll();
    }

    public List<Quote> findQuotesById(String...tickers){

        return this.quoteDao.findAllById(Arrays.asList(tickers));
    }


    public void deleteQuote(Quote q){
        this.quoteDao.deleteById(q.getId());
    }
    public void deleteQuoteById(String ticker){
        this.quoteDao.deleteById(ticker);
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
