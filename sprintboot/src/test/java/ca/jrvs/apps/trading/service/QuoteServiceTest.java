package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.config.MarketDataConfig;
import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.models.IexQuote;
import ca.jrvs.apps.trading.models.Quote;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteServiceTest {

    @Autowired
    QuoteService quoteService;
    String ticker1 = "aapl";
    String ticker2 = "fb";
    Quote saveQuote;

    @Before
    public void insertOne() {
        saveQuote = new Quote();
        saveQuote.setAskPrice(1d);
        saveQuote.setAskSize(1);
        saveQuote.setBidPrice(1.2d);
        saveQuote.setBidSize(1);
        saveQuote.setLastPrice(1.1d);

        saveQuote.setId(ticker1);
        quoteService.saveQuote(saveQuote);

        saveQuote.setId(ticker2);
        quoteService.saveQuote(saveQuote);
    }


    @After
    public void delete() {
        quoteService.deleteQuoteById(ticker1);
        quoteService.deleteQuoteById(ticker2);
    }




    @Test
    public void findIexQuoteByTicker() {
        try{
            quoteService.findIexQuoteByTicker("appl2");
            fail();
        } catch (IllegalArgumentException e){
            assertTrue(true);
        } catch (Exception e){
            fail();
        }

        IexQuote iexQuote = quoteService.findIexQuoteByTicker("aapl");
        assertEquals(iexQuote.getCompanyName(), "Apple, Inc.");
    }

    @Test
    public void findIexQuoteByTickers() {
        try{
            quoteService.findIexQuoteByTickers("aapl2", "fb");
            fail();
        } catch (IllegalArgumentException e){
            assertTrue(true);
        } catch (Exception e){
            fail();
        }

        try{
            quoteService.findIexQuoteByTickers("aapl2", "fb2");
            fail();
        } catch (IllegalArgumentException e){
            assertTrue(true);
        } catch (Exception e){
            fail();
        }

        List<IexQuote> iexQuotes = quoteService.findIexQuoteByTickers("aapl", "fb");
        assertEquals(iexQuotes.size(), 2);
        Set<String> names = iexQuotes.stream().map(IexQuote::getCompanyName).collect(Collectors.toSet());
        assertTrue(names.contains("Apple, Inc."));
        assertTrue(names.contains("Facebook, Inc."));

    }

    @Test
    public void updateMarketData() {
        this.quoteService.updateMarketData();
        List<Quote> quotes = this.quoteService.findQuotes();
        quotes.stream().forEach(
                quote -> {
                    if(quote.getId().intern() == ticker1){
                        assertNotEquals(quote.getAskSize(), saveQuote.getAskSize());
                    } else if(quote.getId().intern() == ticker2){
                        assertNotEquals(quote.getAskSize(), saveQuote.getAskSize());
                    } else {
                        fail();
                    }
                }
        );
    }

}