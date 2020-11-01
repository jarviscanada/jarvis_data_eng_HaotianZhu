package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.models.Account;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public class AccountDao extends CrudIntDao<Account>{
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertActor;
    private final String TABLE_NAME = "account";
    private final String ID_COlUMN_NAME = "id";
    private static final  String UPDATE_QUERY = "UPDATE account SET trader_id=?, amount=? " +
            "WHERE id=?";
    private static final  String FETCH_QUERY = "select * from account where account.id=?";
    private static final  String FETCH_QUERY_TRADER_ID = "select * from account where trader_id=?";
    private static final  String DELETE_QUERY = "delete from account where account.id=?";

    public AccountDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(this.ID_COlUMN_NAME);
    }

    public Object[] getUpdateValues(Account account){
        Object[] objects = new Object[3];
        objects[0] = account.getTraderId();
        objects[1] = account.getAmount();
        objects[2] = account.getId();
        return objects;
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return this.insertActor;
    }

    @Override
    public String getTableName() {
        return this.TABLE_NAME;
    }

    @Override
    public String getIdColumnName() {
        return ID_COlUMN_NAME;
    }

    @Override
    Class<Account> getEntityClass() {
        return Account.class;
    }

    private int updateOne(Account account){
        return this.jdbcTemplate.update(UPDATE_QUERY, this.getUpdateValues(account));
    }

    private void addOne(Account account){
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(account);
        Number key = this.insertActor.executeAndReturnKey(parameterSource);
        account.setId(key.intValue());
    }



    @Override
    public <S extends Account> S save(S s) {
        if(existsById((s.getId()))){
            Integer row = this.updateOne(s);
            if(row != 1){
                throw new DataRetrievalFailureException("unable to update account");
            }
        } else {
            this.addOne(s);
        }
        return s;
    }

    @Override
    public <S extends Account> Iterable<S> saveAll(Iterable<S> iterable) {
        List<S> accounts = new ArrayList<>();
        iterable.forEach(accounts::add);
        List<Object[]> batch = new ArrayList<>();
        accounts.stream().forEach(x->{
            if(!existsById(x.getId())){
                throw new RuntimeException("account "+x.getId().toString() +" does not exist");
            }
            batch.add(this.getUpdateValues(x));
        });
        int[] rows = jdbcTemplate.batchUpdate(UPDATE_QUERY, batch);
        int totalRow = Arrays.stream(rows).sum();
        if (totalRow != batch.size()) {
            throw new IncorrectResultSizeDataAccessException("Number of rows ", batch.size(), totalRow);
        }
        return accounts;
    }

    @Override
    public Optional<Account> findById(Integer id) {
        Account account = null;
        try {
            account = this.jdbcTemplate.queryForObject(this.FETCH_QUERY,
                    BeanPropertyRowMapper.newInstance(Account.class), id);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
        return Optional.ofNullable(account);
    }

    public Optional<Account> findByTraderId(Integer id) {
        Account account = null;
        try {
            account = this.jdbcTemplate.queryForObject(this.FETCH_QUERY_TRADER_ID,
                    BeanPropertyRowMapper.newInstance(Account.class), id);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
        return Optional.ofNullable(account);
    }




    @Override
    public boolean existsById(Integer id) {
        if(id == null){
            return false;
        }
        return this.findById(id).isPresent();
    }

    @Override
    public List<Account> findAll() {
        String selectSql = "SELECT * FROM " + TABLE_NAME;
        List<Account> accounts =  jdbcTemplate
                .query(selectSql, BeanPropertyRowMapper.newInstance(Account.class));
        return accounts;
    }

    @Override
    public List<Account> findAllById(Iterable<Integer> iterable) {
        String selectSql = "SELECT * FROM " + TABLE_NAME + " where id=";
        List<Integer> ids = new ArrayList<>();
        iterable.iterator().forEachRemaining(ids::add);
        selectSql += ids.stream().map(x->"'"+x+"'").collect(Collectors.joining(" or id="));
        List<Account> accounts =  jdbcTemplate
                .query(selectSql,BeanPropertyRowMapper.newInstance(Account.class));
        return accounts;
    }



    @Override
    public long count() {
        throw new UnsupportedOperationException("Not Implement");
    }

    @Override
    public void deleteById(Integer id) {
        if(id == null){
            throw  new DataRetrievalFailureException("unable to find trader"+id.toString());
        }
        Integer row = this.jdbcTemplate.update(DELETE_QUERY,id);
        if(row != 1){
            throw new IncorrectResultSizeDataAccessException("Number of rows ", 1, row);
        }
    }

    @Override
    public void delete(Account account) {
        deleteById(account.getId());
    }

    @Override
    public void deleteAll(Iterable<? extends Account> iterable) {
        throw new UnsupportedOperationException("Not Implement");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not Implement");
    }
}
