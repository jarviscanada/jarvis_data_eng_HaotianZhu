package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.models.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class QuoteDao implements CrudRepository<Quote,String> {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertActor;
    private static final String TABLE_NAME = "quote";
    private static final  String UPDATE_QUERY = "UPDATE quote SET last_price=?, bid_price=?, " +
            "bid_size=?, ask_price=?, ask_size=? WHERE ticker=?";
    private static final  String DELETE_QUERY = "delete from quote where quote.ticker=?";
    private static final  String FETCH_QUERY = "select * from quote where quote.ticker=?";
    private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);


    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.insertActor = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
    }


    public Object[] getUpdateValues(Quote q){
        Object[] objects = new Object[6];
        objects[0] = q.getLastPrice();
        objects[1] = q.getBidPrice();
        objects[2] = q.getBidSize();
        objects[3] = q.getAskPrice();
        objects[4] = q.getAskSize();
        objects[5] = q.getId();

        return objects;
    }


    private Integer update(Quote q){
        return jdbcTemplate.update(UPDATE_QUERY,this.getUpdateValues(q));
    }

    private Integer insert(Quote q){
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(q);
        int row = this.insertActor.execute(parameterSource);
        if(row!=1){
            throw new IncorrectResultSizeDataAccessException("Fail to insert", 1, row);
        }
        return row;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <S extends Quote> S save(S s) {
        if(existsById(s.getId())){
            this.update(s);
        } else {
            this.insert(s);
        }
        return s;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <S extends Quote> List<S> saveAll(Iterable<S> iterable) {
        List<S> quotes = new ArrayList<>();
        iterable.forEach(quotes::add);
        List<Object[]> batch = new ArrayList<>();
        quotes.stream().forEach(x->{
            if(!existsById(x.getId())){
                throw new RuntimeException("ticker "+x.getId() +" does not exist");
            }
            batch.add(this.getUpdateValues(x));
        });
        int[] rows = jdbcTemplate.batchUpdate(UPDATE_QUERY, batch);
        int totalRow = Arrays.stream(rows).sum();
        if (totalRow != batch.size()) {
            throw new IncorrectResultSizeDataAccessException("Number of rows ", batch.size(), totalRow);
        }
        return quotes;
    }

    @Override
    public Optional<Quote> findById(String s) {
        Quote quote = null;
        try{
            quote = this.jdbcTemplate.queryForObject(FETCH_QUERY, BeanPropertyRowMapper.newInstance(Quote.class), s);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }

        return Optional.ofNullable(quote);
    }

    @Override
    public boolean existsById(String s) {
        return this.findById(s).isPresent();
    }

    @Override
    public List<Quote> findAll() {
        String selectSql = "SELECT * FROM " + TABLE_NAME;
        List<Quote> quotes =  jdbcTemplate
                .query(selectSql, BeanPropertyRowMapper.newInstance(Quote.class));
        return quotes;
    }

    @Override
    public List<Quote> findAllById(Iterable<String> iterable) {
        String selectSql = "SELECT * FROM " + TABLE_NAME + " where ticker=";
        List<String> tickers = new ArrayList<>();
        iterable.iterator().forEachRemaining(tickers::add);
        selectSql += tickers.stream().map(x->"'"+x+"'").collect(Collectors.joining(" or ticker="));
        List<Quote> quotes =  jdbcTemplate
                .query(selectSql,BeanPropertyRowMapper.newInstance(Quote.class));
        return quotes;
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Not Implement");
    }

    @Override
    public void deleteById(String s) {
        if(!existsById(s)){
            throw new RuntimeException("ticker " + s+ " does not exist");
        }
        int row = this.jdbcTemplate.update(DELETE_QUERY, s);
        if(row != 1){
            throw new IncorrectResultSizeDataAccessException("Number of rows ", 1, row);
        }
    }

    @Override
    public void delete(Quote quote) {
        this.deleteById(quote.getId());
    }

    @Override
    public void deleteAll(Iterable<? extends Quote> iterable) {
        throw  new UnsupportedOperationException("Not Implement");
    }

    @Override
    public void deleteAll() {
        throw  new UnsupportedOperationException("Not Implement");
    }
}
