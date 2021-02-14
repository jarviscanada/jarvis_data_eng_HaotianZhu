package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.models.*;
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
public class OrderServiceTest {

    @Autowired
    private QuoteDao quoteDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TraderAccountService traderAccountService;


    @Test
    public void executeMarketOrder() {
        Quote quote = new Quote();
        quote.setAskPrice(10d);
        quote.setAskSize(10);
        quote.setBidPrice(10d);
        quote.setBidSize(10);
        quote.setId("aapl");
        quote.setLastPrice(10.1d);
        quoteDao.save(quote);


        Trader trader = new Trader();
        trader.setDob(new Date(System.currentTimeMillis()));
        trader.setCountry("CA");
        trader.setFirstName("haotian");
        trader.setLastName("zhu");
        trader.setEmail("haotian@hhh.com");
        TraderAccountView traderAccountView = traderAccountService.createTraderAndAccount(trader);

        traderAccountService.deposit(traderAccountView.getTraderId(), 1000);
        MarketOrderDto marketOrderDto = new MarketOrderDto();
        marketOrderDto.setAccountId(traderAccountView.getAccountId());
        marketOrderDto.setNote("Note");
        marketOrderDto.setTicker("aapl");
        marketOrderDto.setSize(100);
        SecurityOrder order = orderService.executeMarketOrder(marketOrderDto);
        assertEquals(order.getPrice(), new Double(10));
        assertEquals(order.getStatus(), "FILLED");
        assertEquals(accountDao.findById(order.getAccountId()).get().getAmount(), new Double(0));
        marketOrderDto.setSize(-100);
        order = orderService.executeMarketOrder(marketOrderDto);
        assertEquals(order.getStatus(), "FILLED");
        assertEquals(accountDao.findById(order.getAccountId()).get().getAmount(), new Double(1000));

        Account account = accountDao.findById(marketOrderDto.getAccountId()).get();
        account.setAmount(0d);
        accountDao.save(account);
        traderAccountService.deleteTradeById(traderAccountView.getAccountId());
    }
}