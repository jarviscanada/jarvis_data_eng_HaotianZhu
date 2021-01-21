package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.models.Trader;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TraderDao extends CrudIntDao<Trader> {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertActor;
    private final String TABLE_NAME = "trader";
    private final String ID_COlUMN_NAME = "id";
    private static final  String UPDATE_QUERY = "UPDATE trader SET first_name=?, last_name=?, " +
            "dob=?, country=?, email=? WHERE id=?";
    private static final  String DELETE_QUERY = "delete from trader where trader.id=?";
    private static final  String FETCH_QUERY = "select * from trader where trader.id=?";


    @Autowired
    public TraderDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(this.ID_COlUMN_NAME);
    }

    public Object[] getUpdateValues(Trader trader){
        Object[] objects = new Object[6];
        objects[0] = trader.getFirstName();
        objects[1] = trader.getLastName();
        objects[2] = trader.getDob();
        objects[3] = trader.getCountry();
        objects[4] = trader.getEmail();
        objects[5] = trader.getId();
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
        return this.ID_COlUMN_NAME;
    }

    @Override
    Class<Trader> getEntityClass() {
        return Trader.class;
    }

    private int updateOne(Trader trader){
        return this.jdbcTemplate.update(UPDATE_QUERY, this.getUpdateValues(trader));
    }

    private void addOne(Trader trader){
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(trader);
        Number key = this.insertActor.executeAndReturnKey(parameterSource);
        trader.setId(key.intValue());
    }

    @Override
    public <S extends Trader> S save(S s) {
        if(existsById(s.getId())){
            Integer row = this.updateOne(s);
            if(row != 1){
                throw new DataRetrievalFailureException("unable to update trader");
            }
        } else {
            this.addOne(s);
        }
        return s;
    }

    @Override
    public <S extends Trader> List<S> saveAll(Iterable<S> iterable) {
        List<S> traders = new ArrayList<>();
        iterable.forEach(traders::add);
        List<Object[]> batch = new ArrayList<>();
        traders.stream().forEach(x->{
            if(!existsById(x.getId())){
                throw new DataRetrievalFailureException("trader "+x.getId().toString() +" does not exist");
            }
            batch.add(this.getUpdateValues(x));
        });
        int[] rows = jdbcTemplate.batchUpdate(UPDATE_QUERY, batch);
        int totalRow = Arrays.stream(rows).sum();
        if (totalRow != batch.size()) {
            throw new IncorrectResultSizeDataAccessException("Number of rows ", batch.size(), totalRow);
        }
        return traders;
    }

    @Override
    public Optional<Trader> findById(Integer id) {
        Trader trader = null;
        try {
            trader = this.jdbcTemplate.queryForObject(this.FETCH_QUERY,
                    BeanPropertyRowMapper.newInstance(Trader.class), id);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
        return Optional.ofNullable(trader);
    }

    @Override
    public boolean existsById(Integer id) {
        if(id == null){
            return false;
        }
        return this.findById(id).isPresent();
    }

    @Override
    public List<Trader> findAll() {
        String selectSql = "SELECT * FROM " + TABLE_NAME;
        List<Trader> traders =  jdbcTemplate
                .query(selectSql, BeanPropertyRowMapper.newInstance(Trader.class));
        return traders;
    }

    @Override
    public List<Trader> findAllById(Iterable<Integer> iterable) {
        String selectSql = "SELECT * FROM " + TABLE_NAME + " where id=";
        List<Integer> ids = new ArrayList<>();
        iterable.iterator().forEachRemaining(ids::add);
        selectSql += ids.stream().map(x->"'"+x+"'").collect(Collectors.joining(" or id="));
        List<Trader> traders =  jdbcTemplate
                .query(selectSql,BeanPropertyRowMapper.newInstance(Trader.class));
        return traders;
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
    public void delete(Trader trader) {
        deleteById(trader.getId());
    }

    @Override
    public void deleteAll(Iterable<? extends Trader> iterable) {
        throw new UnsupportedOperationException("Not Implement");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not Implement");
    }
}
