package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class PositionDaoTest {


    @Autowired
    private SecurityOrderDao securityOrderDao;
    private SecurityOrder saveOrder;
    private SecurityOrder saveOrder2;

    @Autowired
    private AccountDao accountDao;
    private Account saveAccount;

    @Autowired
    private TraderDao traderDao;
    private Trader saveTrader;

    @Autowired
    private QuoteDao quoteDao;
    private Quote saveQuote;

    @Autowired
    private PositionDao positionDao;

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

        saveTrader = new Trader();
        saveTrader.setCountry("Canada");
        saveTrader.setEmail("haotian@gmail.com");
        saveTrader.setDob(new Date(System.currentTimeMillis()));
        saveTrader.setFirstName("haotian");
        saveTrader.setLastName("zhu");
        traderDao.save(saveTrader);

        saveAccount = new Account();
        saveAccount.setAmount(50.0);
        saveAccount.setTraderId(saveTrader.getId());
        accountDao.save(saveAccount);

        saveOrder = new SecurityOrder();
        saveOrder.setAccountId(saveAccount.getId());
        saveOrder.setNotes("Note");
        saveOrder.setSize(100);
        saveOrder.setPrice(10d);
        saveOrder.setStatus("FILLED");
        saveOrder.setTicker("aapl");
        securityOrderDao.save(saveOrder);

        saveOrder2 = new SecurityOrder();
        saveOrder2.setAccountId(saveAccount.getId());
        saveOrder2.setNotes("Note2");
        saveOrder2.setSize(200);
        saveOrder2.setPrice(20d);
        saveOrder2.setStatus("FILLED");
        saveOrder2.setTicker("aapl");
        securityOrderDao.save(saveOrder2);
    }


    @After
    public void deleteOne() {
        securityOrderDao.deleteById(saveOrder.getId());
        securityOrderDao.deleteById(saveOrder2.getId());
        quoteDao.deleteById(saveQuote.getId());
        accountDao.deleteById(saveAccount.getId());
        traderDao.deleteById(saveTrader.getId());
    }

    @Test
    public void findAll() {

        List<Position> positionList = this.positionDao.findAllByAccountId(saveAccount.getId());
        assertEquals(positionList.size(), 1);
        assertEquals(positionList.get(0).getPosition(), new Long(300));
    }
}