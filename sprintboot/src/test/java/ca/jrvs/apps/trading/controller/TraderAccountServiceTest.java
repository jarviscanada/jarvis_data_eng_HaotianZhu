package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.models.*;
import ca.jrvs.apps.trading.service.TraderAccountService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class TraderAccountServiceTest {
    TraderAccountView traderAccountView;
    @Autowired
    TraderAccountService traderAccountService;
    @Autowired
    AccountDao accountDao;
    @Autowired
    SecurityOrderDao securityOrderDao;
    @Autowired
    private QuoteDao quoteDao;
    private Quote saveQuote;


    @Before
    public void add(){
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
    public void delete(){
        this.quoteDao.deleteById(saveQuote.getId());
    }

    @Test
    public void createTraderAndAccount() {
        Trader trader = new Trader();
        try{
            this.traderAccountService.createTraderAndAccount(trader);
            fail();
        } catch (IllegalArgumentException e){
            assertTrue(true);
        }
        trader.setFirstName("haotian");
        trader.setLastName("zhu");
        trader.setCountry("z");
        trader.setDob(new Date(System.currentTimeMillis()));
        trader.setEmail("hhhhh@hhh.com");

        traderAccountView = this.traderAccountService.createTraderAndAccount(trader);
        assertTrue(this.accountDao.existsById(traderAccountView.getAccountId()));
        assertEquals(traderAccountView.getEmail(),trader.getEmail());

        traderAccountService.deleteTradeById(traderAccountView.getTraderId());

    }

    @Test
    public void deleteTradeById() {
        Trader trader = new Trader();
        trader.setFirstName("haotian");
        trader.setLastName("zhu");
        trader.setCountry("z");
        trader.setDob(new Date(System.currentTimeMillis()));
        trader.setEmail("hhhhh@hhh.com");
        traderAccountView = this.traderAccountService.createTraderAndAccount(trader);

        SecurityOrder saveOrder = new SecurityOrder();
        saveOrder.setAccountId(traderAccountView.getAccountId());
        saveOrder.setNotes("Note");
        saveOrder.setSize(100);
        saveOrder.setPrice(10d);
        saveOrder.setStatus("FILLED");
        saveOrder.setTicker("aapl");
        securityOrderDao.save(saveOrder);

        SecurityOrder saveOrder2 = new SecurityOrder();
        saveOrder2.setAccountId(traderAccountView.getAccountId());
        saveOrder2.setNotes("Note");
        saveOrder2.setSize(200);
        saveOrder2.setPrice(20d);
        saveOrder2.setStatus("FILLED");
        saveOrder2.setTicker("aapl");
        securityOrderDao.save(saveOrder2);

        try {
            this.traderAccountService.deleteTradeById(traderAccountView.getTraderId());
            fail();
        } catch (IllegalArgumentException e){
            assertTrue(true);
        }

        SecurityOrder saveOrder3 = new SecurityOrder();
        saveOrder3.setAccountId(traderAccountView.getAccountId());
        saveOrder3.setNotes("Note");
        saveOrder3.setSize(-300);
        saveOrder3.setPrice(20d);
        saveOrder3.setStatus("FILLED");
        saveOrder3.setTicker("aapl");
        securityOrderDao.save(saveOrder3);

        traderAccountService.deleteTradeById(traderAccountView.getTraderId());

        assertFalse(this.accountDao.existsById(traderAccountView.getAccountId()));
        assertFalse(this.securityOrderDao.existsById(saveOrder.getId()));
        assertFalse(this.securityOrderDao.existsById(saveOrder2.getId()));
    }

    @Test
    public void depositAndWidthdraw() {
        Trader trader = new Trader();
        trader.setFirstName("haotian");
        trader.setLastName("zhu");
        trader.setCountry("z");
        trader.setDob(new Date(System.currentTimeMillis()));
        trader.setEmail("hhhhh@hhh.com");
        traderAccountView = this.traderAccountService.createTraderAndAccount(trader);

        try{
            this.traderAccountService.deposit(this.traderAccountView.getTraderId(), -100d);
            fail();
        } catch (IllegalArgumentException e){
            assertTrue(true);
        }

        this.traderAccountService.deposit(this.traderAccountView.getTraderId(), 100d);
        assertEquals(this.accountDao.findByTraderId(this.traderAccountView.getTraderId()).get().getAmount(),
                new Double(100));

        try{
            this.traderAccountService.widthDraw(this.traderAccountView.getTraderId(), 200d);
            fail();
        } catch (IllegalArgumentException e){
            assertTrue(true);
        }
        this.traderAccountService.widthDraw(this.traderAccountView.getTraderId(), 100d);

        assertEquals(this.accountDao.findByTraderId(this.traderAccountView.getTraderId()).get().getAmount(),
                new Double(0));
        traderAccountService.deleteTradeById(traderAccountView.getTraderId());

    }

}