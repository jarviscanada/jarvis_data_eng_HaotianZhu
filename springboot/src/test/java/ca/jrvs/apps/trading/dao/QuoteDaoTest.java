package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.models.Quote;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteDaoTest {

    @Autowired
    private QuoteDao quoteDao;

    private Quote saveQuote;

    @Before
    public void insertOne() {
        saveQuote = new Quote();
        saveQuote.setAskPrice(10d);
        saveQuote.setAskSize(10);
        saveQuote.setBidPrice(10.2d);
        saveQuote.setBidSize(10);
        saveQuote.setId("aapl");
        saveQuote.setLastPrice(10.1d);
        quoteDao.save(saveQuote);
    }


    @After
    public void deleteOne() {
        quoteDao.deleteById(saveQuote.getId());
    }

    @Test
    public void saveAll() {
        Quote q1 = new Quote();
        q1.setAskPrice(10d);
        q1.setAskSize(10);
        q1.setBidPrice(10.2d);
        q1.setBidSize(10);
        q1.setId("fb");
        q1.setLastPrice(10.1d);

        Quote q2 = new Quote();
        q2.setAskPrice(10d);
        q2.setAskSize(10);
        q2.setBidPrice(10.2d);
        q2.setBidSize(10);
        q2.setId("fb2");
        q2.setLastPrice(10.1d);

        quoteDao.save(q1);
        quoteDao.save(q2);

        assertEquals(quoteDao.findById("fb").get().getAskSize(), new Integer(10));
        assertEquals(quoteDao.findById("fb2").get().getAskSize(), new Integer(10));

        // modify
        q1.setAskSize(1000);
        q2.setAskSize(2000);

        Iterable<Quote> qs = Arrays.asList(new Quote[]{q1, q2});
        this.quoteDao.saveAll(qs);
        assertEquals(quoteDao.findById("fb").get().getAskSize(), new Integer(1000));
        assertEquals(quoteDao.findById("fb2").get().getAskSize(), new Integer(2000));
    }

    @Test
    public void findById() {
        Optional<Quote> q = this.quoteDao.findById("aapl");
        assertTrue(q.isPresent());

        assertEquals(q.get().getId(), "aapl");
        assertEquals(q.get().getBidSize(), new Integer(10));
    }

    @Test
    public void existsById() {
        assertTrue(quoteDao.existsById(saveQuote.getId()));
        assertFalse(quoteDao.existsById("fb"));
    }

    @Test
    public void findAllById() {
        Quote q1 = new Quote();
        q1.setAskPrice(10d);
        q1.setAskSize(1000);
        q1.setBidPrice(10.2d);
        q1.setBidSize(10);
        q1.setId("fb");
        q1.setLastPrice(10.1d);

        Quote q2 = new Quote();
        q2.setAskPrice(10d);
        q2.setAskSize(2000);
        q2.setBidPrice(10.2d);
        q2.setBidSize(10);
        q2.setId("fb2");
        q2.setLastPrice(10.1d);

        quoteDao.save(q1);
        quoteDao.save(q2);

        Iterable<String> ids = Arrays.asList(new String[]{q1.getId(), q2.getId()});

        this.quoteDao.findAllById(ids).forEach( x->{
            if(x.getId().intern() == "fb"){
                assertEquals(x.getAskSize().intValue(), 1000);
            } else if(x.getId().intern() == "fb2"){
                assertEquals(x.getAskSize().intValue(), 2000);
            } else {
                fail();
            }
        });
    }

}