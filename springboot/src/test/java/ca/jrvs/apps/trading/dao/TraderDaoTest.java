package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.models.Quote;
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
public class TraderDaoTest {

    @Autowired
    private TraderDao traderDao;
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
    }


    @After
    public void deleteOne() {
        traderDao.deleteById(saveTrader.getId());
    }


    @Test
    public void save() {
    }

    @Test
    public void saveAll() {
        Trader saveTrader2 = new Trader();
        saveTrader2.setCountry("Canada");
        saveTrader2.setEmail("haotian2@gmail.com");
        saveTrader2.setDob(new Date(System.currentTimeMillis()));
        saveTrader2.setFirstName("haotian2");
        saveTrader2.setLastName("zhu2");
        traderDao.save(saveTrader2);

        saveTrader2.setLastName("changed");
        saveTrader.setLastName("changed");
        List<Trader> traders = new ArrayList<>();
        traders.add(saveTrader);
        traders.add(saveTrader2);
        traderDao.saveAll(traders);

        Optional<Trader> r = traderDao.findById(saveTrader.getId());
        assertTrue(r.isPresent());
        assertEquals(r.get().getLastName().intern(), "changed");

        r = traderDao.findById(saveTrader2.getId());
        assertTrue(r.isPresent());
        assertEquals(r.get().getLastName().intern(), "changed");
    }

    @Test
    public void findById() {
        Optional<Trader> r = traderDao.findById(saveTrader.getId());
        assertTrue(r.isPresent());
        assertEquals(r.get().getLastName().intern(), "zhu");
    }

    @Test
    public void existsById() {
        assertTrue(traderDao.existsById(saveTrader.getId()));
        assertFalse(traderDao.existsById(100));
    }

    @Test
    public void findAll() {
        Trader saveTrader2 = new Trader();
        saveTrader2.setCountry("Canada");
        saveTrader2.setEmail("haotian2@gmail.com");
        saveTrader2.setDob(new Date(System.currentTimeMillis()));
        saveTrader2.setFirstName("haotian2");
        saveTrader2.setLastName("zhu2");
        traderDao.save(saveTrader2);
        this.traderDao.findAll().forEach(x->{
            if(x.getId() == 1){
                assertEquals(x.getLastName().intern(), "zhu");
            } else if(x.getId() == 2){
                assertEquals(x.getLastName().intern(), "zhu2");
            } else {
                fail();
            }
        });
    }

    @Test
    public void findAllById() {
        Trader saveTrader2 = new Trader();
        saveTrader2.setCountry("Canada");
        saveTrader2.setEmail("haotian2@gmail.com");
        saveTrader2.setDob(new Date(System.currentTimeMillis()));
        saveTrader2.setFirstName("haotian2");
        saveTrader2.setLastName("zhu2");
        traderDao.save(saveTrader2);
        traderDao.findAllById(Arrays.asList(saveTrader.getId(),saveTrader2.getId())).forEach(x->{
            if(x.getId() == 1){
                assertEquals(x.getLastName().intern(), "zhu");
            } else if(x.getId() == 2){
                assertEquals(x.getLastName().intern(), "zhu2");
            } else {
                fail();
            }
        });
    }

    @Test
    public void deleteById() {
        Trader saveTrader2 = new Trader();
        saveTrader2.setCountry("Canada");
        saveTrader2.setEmail("haotian2@gmail.com");
        saveTrader2.setDob(new Date(System.currentTimeMillis()));
        saveTrader2.setFirstName("haotian2");
        saveTrader2.setLastName("zhu2");
        traderDao.save(saveTrader2);
        this.traderDao.deleteById(saveTrader2.getId());
        assertTrue(this.traderDao.existsById(saveTrader.getId()));
        assertFalse(this.traderDao.existsById(saveTrader2.getId()));
    }

}