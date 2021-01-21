package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.models.SecurityOrder;
import ca.jrvs.apps.trading.models.Trader;
import org.hibernate.criterion.Order;
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
public class SecurityOrderDao extends CrudIntDao<SecurityOrder>{



    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertActor;
    private final String TABLE_NAME = "security_order";
    private final String ID_COlUMN_NAME = "id";
    private static final  String UPDATE_QUERY = "UPDATE security_order SET account_id=?, status=?, " +
            "ticker=?, size=?, price=?, notes=? WHERE id=?";
    private static final  String DELETE_QUERY = "delete from security_order where security_order.id=?";
    private static final  String FETCH_QUERY = "select * from security_order where security_order.id=?";



    @Autowired
    public SecurityOrderDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(this.ID_COlUMN_NAME);
    }

    public Object[] getUpdateValues(SecurityOrder securityOrder){
        Object[] objects = new Object[7];
        objects[0] = securityOrder.getAccountId();
        objects[1] = securityOrder.getStatus();
        objects[2] = securityOrder.getTicker();
        objects[3] = securityOrder.getSize();
        objects[4] = securityOrder.getPrice();
        objects[5] = securityOrder.getNotes();
        objects[6] = securityOrder.getId();
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


    private int updateOne(SecurityOrder order){
        return this.jdbcTemplate.update(UPDATE_QUERY, this.getUpdateValues(order));
    }

    private void addOne(SecurityOrder order){
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(order);
        Number key = this.insertActor.executeAndReturnKey(parameterSource);
        order.setId(key.intValue());
    }


    @Override
    Class<SecurityOrder> getEntityClass() {
        return SecurityOrder.class;
    }

    @Override
    public <S extends SecurityOrder> S save(S s) {
        if(existsById(s.getId())){
            Integer row = this.updateOne(s);
            if(row != 1){
                throw new DataRetrievalFailureException("unable to update SecurityOrder");
            }
        } else {
            this.addOne(s);
        }
        return s;
    }

    @Override
    public <S extends SecurityOrder> List<S> saveAll(Iterable<S> iterable) {
        List<S> orders = new ArrayList<>();
        iterable.forEach(orders::add);
        List<Object[]> batch = new ArrayList<>();
        orders.stream().forEach(x->{
            if(!existsById(x.getId())){
                throw new DataRetrievalFailureException("SecurityOrder "+x.getId().toString() +" does not exist");
            }
            batch.add(this.getUpdateValues(x));
        });
        int[] rows = jdbcTemplate.batchUpdate(UPDATE_QUERY, batch);
        int totalRow = Arrays.stream(rows).sum();
        if (totalRow != batch.size()) {
            throw new IncorrectResultSizeDataAccessException("Number of rows ", batch.size(), totalRow);
        }
        return orders;
    }

    @Override
    public Optional<SecurityOrder> findById(Integer id) {
        SecurityOrder order = null;
        try {
            order = this.jdbcTemplate.queryForObject(this.FETCH_QUERY,
                    BeanPropertyRowMapper.newInstance(SecurityOrder.class), id);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
        return Optional.ofNullable(order);
    }



    @Override
    public boolean existsById(Integer id) {
        return this.findById(id).isPresent();
    }

    @Override
    public List<SecurityOrder> findAll() {
        String selectSql = "SELECT * FROM " + TABLE_NAME;
        List<SecurityOrder> orders =  jdbcTemplate
                .query(selectSql, BeanPropertyRowMapper.newInstance(SecurityOrder.class));
        return orders;
    }

    @Override
    public List<SecurityOrder> findAllById(Iterable<Integer> iterable) {
        String selectSql = "SELECT * FROM " + TABLE_NAME + " where id=";
        List<Integer> ids = new ArrayList<>();
        iterable.iterator().forEachRemaining(ids::add);
        selectSql += ids.stream().map(x->"'"+x+"'").collect(Collectors.joining(" or id="));
        List<SecurityOrder> orders =  jdbcTemplate
                .query(selectSql,BeanPropertyRowMapper.newInstance(SecurityOrder.class));
        return orders;
    }

    public List<SecurityOrder> findAllByAccountId(Integer id) {
        String selectSql = "SELECT * FROM " + TABLE_NAME + " where account_id=?";
        List<SecurityOrder> orders =  jdbcTemplate
                .query(selectSql,BeanPropertyRowMapper.newInstance(SecurityOrder.class),id);
        return orders;
    }


    @Override
    public long count() {
        throw new UnsupportedOperationException("Not Implement");
    }

    @Override
    public void deleteById(Integer id) {
        if(id == null){
            throw  new DataRetrievalFailureException("unable to find SecurityOrder "+id.toString());
        }
        Integer row = this.jdbcTemplate.update(DELETE_QUERY,id);
        if(row != 1){
            throw new IncorrectResultSizeDataAccessException("Number of rows ", 1, row);
        }

    }

    @Override
    public void delete(SecurityOrder order) {
        deleteById(order.getId());
    }

    @Override
    public void deleteAll(Iterable<? extends SecurityOrder> iterable) {
        iterable.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not Implement");
    }
}

