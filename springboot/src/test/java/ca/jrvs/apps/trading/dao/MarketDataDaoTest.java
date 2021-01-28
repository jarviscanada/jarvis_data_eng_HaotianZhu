package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.config.MarketDataConfig;
import ca.jrvs.apps.trading.models.IexQuote;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class MarketDataDaoTest {
    MarketDataDao dataDao;

    @Before
    public void setDataDao(){
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);
        MarketDataConfig config = new MarketDataConfig();
        config.setHost("https://cloud.iexapis.com/v1/");
        config.setToken(System.getenv("token"));
        dataDao = new MarketDataDao(cm, config);
    }


    @Test
    public void findById() {
         Optional<IexQuote> result = dataDao.findById("aapl");
         String companyName = result.flatMap(iexQuote -> Optional.ofNullable(iexQuote.getCompanyName())).orElse("Error");
         assertEquals(companyName, "Apple, Inc.");
         assertFalse(dataDao.findById("aapl2").isPresent());
    }


    @Test
    public void findAllById() {
        String[] s = new String[]{"aapl", "fb"};
        Iterable<String> symbols = Arrays.asList(s);
        List<IexQuote> result = dataDao.findAllById(symbols);
        assertEquals(result.size(), s.length);
        s = new String[]{"aapl", "fb2"};
        symbols = Arrays.asList(s);
        result = dataDao.findAllById(symbols);
        assertNotEquals(result.size(), s.length);
    }


}