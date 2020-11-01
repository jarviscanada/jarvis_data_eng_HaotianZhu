package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.models.Account;
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
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class AccountDaoTest {

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private TraderDao traderDao;

    private Account saveAccount;
    private Trader saveTrader;

    @Before
    public void insertOne() {
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
    }


    @After
    public void deleteOne() {
        accountDao.deleteById(saveAccount.getId());
        traderDao.deleteById(saveTrader.getId());
    }


    @Test
    public void save() {
        Account saveAccount2 = new Account();
        saveAccount2.setAmount(51.0);
        saveAccount2.setTraderId(saveTrader.getId());
        accountDao.save(saveAccount2);
        assertTrue(accountDao.existsById(saveAccount2.getId()));
        accountDao.deleteById(saveAccount2.getId());
    }

    @Test
    public void saveAll() {
        Account saveAccount2 = new Account();
        saveAccount2.setAmount(52.0);
        saveAccount2.setTraderId(saveTrader.getId());

        Account saveAccount3 = new Account();
        saveAccount3.setAmount(53.0);
        saveAccount3.setTraderId(saveTrader.getId());

        accountDao.save(saveAccount2);
        accountDao.save(saveAccount3);

        assertTrue(accountDao.existsById(saveAccount2.getId()));
        assertTrue(accountDao.existsById(saveAccount3.getId()));

        saveAccount2.setAmount(102.0);
        saveAccount3.setAmount(103.0);
        List<Account> accountList = new ArrayList<>();
        accountList.add(saveAccount2);
        accountList.add(saveAccount3);
        accountDao.saveAll(accountList);

        assertEquals(new Double(102.0),
                accountDao.findById(saveAccount2.getId()).get().getAmount());

        assertEquals(new Double(103.0),
                accountDao.findById(saveAccount3.getId()).get().getAmount());

        accountDao.deleteById(saveAccount2.getId());
        accountDao.deleteById(saveAccount3.getId());
    }

    @Test
    public void findById() {
        assertTrue(accountDao.findById(saveAccount.getId()).isPresent());
        assertEquals(accountDao.findById(saveAccount.getId()).get().getTraderId(),
                saveAccount.getTraderId());


        assertTrue(accountDao.findByTraderId(saveTrader.getId()).isPresent());
        assertEquals(accountDao.findByTraderId(saveTrader.getId()).get().getTraderId(),
                saveAccount.getTraderId());
    }


    @Test
    public void findAll() {
        Account saveAccount2 = new Account();
        saveAccount2.setAmount(52.0);
        saveAccount2.setTraderId(saveTrader.getId());

        Account saveAccount3 = new Account();
        saveAccount3.setAmount(53.0);
        saveAccount3.setTraderId(saveTrader.getId());

        accountDao.save(saveAccount2);
        accountDao.save(saveAccount3);

        List<Account> accounts  = accountDao.findAll();

        accounts.forEach(x->{
            if(x.getId().intValue() == saveAccount2.getId().intValue()){
                assertEquals(x.getAmount(), new Double(52.0));
            } else if(x.getId().intValue() == saveAccount3.getId().intValue()){
                assertEquals(x.getAmount(), new Double(53.0));
            }else if(x.getId().intValue() == saveAccount.getId().intValue()){
                assertEquals(x.getAmount(), new Double(50.0));
            } else {
                fail();
            }
        });

        accountDao.deleteById(saveAccount2.getId());
        accountDao.deleteById(saveAccount3.getId());
    }

    @Test
    public void findAllById() {
        Account saveAccount2 = new Account();
        saveAccount2.setAmount(52.0);
        saveAccount2.setTraderId(saveTrader.getId());

        Account saveAccount3 = new Account();
        saveAccount3.setAmount(53.0);
        saveAccount3.setTraderId(saveTrader.getId());

        accountDao.save(saveAccount2);
        accountDao.save(saveAccount3);

        List<Integer> accountList = new ArrayList<>();
        accountList.add(saveAccount2.getId());
        accountList.add(saveAccount3.getId());
        List<Account> accounts  = accountDao.findAllById(accountList);

        accounts.forEach(x->{
            if(x.getId().intValue() == saveAccount2.getId().intValue()){
                assertEquals(x.getAmount(), new Double(52.0));
            } else if(x.getId().intValue() == saveAccount3.getId().intValue()){
                assertEquals(x.getAmount(), new Double(53.0));
            } else {
                fail();
            }
        });


        accountDao.deleteById(saveAccount2.getId());
        accountDao.deleteById(saveAccount3.getId());
    }


    @Test
    public void delete() {
        Account saveAccount2 = new Account();
        saveAccount2.setAmount(51.0);
        saveAccount2.setTraderId(saveTrader.getId());
        accountDao.save(saveAccount2);
        assertTrue(accountDao.existsById(saveAccount2.getId()));
        accountDao.delete(saveAccount2);
        assertFalse(accountDao.existsById(saveAccount2.getId()));
    }
}