package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.config.MarketDataConfig;
import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.models.IexQuote;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class QuoteServiceTest {
    MarketDataDao dataDao;
    QuoteService quoteService;

    @Before
    public void setUp() throws Exception {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);
        MarketDataConfig config = new MarketDataConfig();
        config.setHost("https://cloud.iexapis.com/v1/");
        config.setToken(System.getenv("token"));
        dataDao = new MarketDataDao(cm, config);
        quoteService = new QuoteService(dataDao);
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
}