package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.models.Account;
import ca.jrvs.apps.trading.models.Quote;
import ca.jrvs.apps.trading.models.SecurityOrder;
import ca.jrvs.apps.trading.models.Trader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class SecurityOrderDaoTest {

    @Autowired
    private SecurityOrderDao securityOrderDao;
    private SecurityOrder saveOrder;
    @Autowired
    private AccountDao accountDao;
    private Account saveAccount;
    @Autowired
    private TraderDao traderDao;
    private Trader saveTrader;

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


    }


    @After
    public void deleteOne() {
        securityOrderDao.deleteById(saveOrder.getId());
        quoteDao.deleteById(saveQuote.getId());
        accountDao.deleteById(saveAccount.getId());
        traderDao.deleteById(saveTrader.getId());
    }

    @Test
    public void saveAll() {
        SecurityOrder saveOrder2 = new SecurityOrder();
        saveOrder2.setAccountId(saveAccount.getId());
        saveOrder2.setNotes("Note");
        saveOrder2.setSize(100);
        saveOrder.setPrice(10d);
        saveOrder2.setStatus("FILLED");
        saveOrder2.setTicker("aapl");
        securityOrderDao.save(saveOrder2);
        saveOrder2.setSize(200);

        List<SecurityOrder> orders = new ArrayList<>();
        orders.add(saveOrder2);
        orders.add(saveOrder);

        securityOrderDao.saveAll(orders);

        Optional<SecurityOrder> r = securityOrderDao.findById(saveOrder2.getId());
        assertTrue(r.isPresent());
        assertEquals(r.get().getSize(), new Integer(200));

        securityOrderDao.delete(saveOrder2);
    }

    @Test
    public void findById() {
        Optional<SecurityOrder> r = securityOrderDao.findById(saveOrder.getId());
        assertTrue(r.isPresent());
        assertEquals(r.get().getSize(), new Integer(100));
    }

    @Test
    public void existsById() {
        assertTrue(traderDao.existsById(saveTrader.getId()));
        assertFalse(traderDao.existsById(100));
    }

    @Test
    public void findAll() {
        SecurityOrder saveOrder2;
        saveOrder2 = new SecurityOrder();
        saveOrder2.setAccountId(saveAccount.getId());
        saveOrder2.setNotes("Note");
        saveOrder2.setSize(200);
        saveOrder.setPrice(10d);
        saveOrder2.setStatus("FILLED");
        saveOrder2.setTicker("aapl");
        securityOrderDao.save(saveOrder2);

        this.securityOrderDao.findAll().forEach(x->{
            if(x.getId().intValue() == saveOrder2.getId().intValue()){
                assertEquals(x.getSize(), new Integer(200));
            } else if(x.getId() == saveOrder.getId().intValue()){
                assertEquals(x.getSize(), new Integer(100));
            } else {
                fail();
            }
        });

        securityOrderDao.delete(saveOrder2);
    }

    @Test
    public void findAllById() {
        SecurityOrder saveOrder2;
        saveOrder2 = new SecurityOrder();
        saveOrder2.setAccountId(saveAccount.getId());
        saveOrder2.setNotes("Note");
        saveOrder2.setSize(200);
        saveOrder.setPrice(10d);
        saveOrder2.setStatus("FILLED");
        saveOrder2.setTicker("aapl");
        securityOrderDao.save(saveOrder2);

        securityOrderDao.findAllById(Arrays.asList(saveOrder.getId(),saveOrder2.getId())).forEach(x->{
            if(x.getId().intValue() == saveOrder2.getId().intValue()){
                assertEquals(x.getSize(), new Integer(200));
            } else if(x.getId() == saveOrder.getId().intValue()){
                assertEquals(x.getSize(), new Integer(100));
            } else {
                fail();
            }
        });
        this.securityOrderDao.findAllByAccountId(saveAccount.getId()).forEach(x->{
            if(x.getId().intValue() == saveOrder2.getId().intValue()){
                assertEquals(x.getSize(), new Integer(200));
            } else if(x.getId() == saveOrder.getId().intValue()){
                assertEquals(x.getSize(), new Integer(100));
            } else {
                fail();
            }
        });

        securityOrderDao.delete(saveOrder2);
    }

}