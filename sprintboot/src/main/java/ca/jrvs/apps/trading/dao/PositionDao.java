package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.models.Position;
import ca.jrvs.apps.trading.models.Trader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;


@Repository
public class PositionDao {

    private JdbcTemplate jdbcTemplate;
    private final String TABLE_NAME = "position";
    private final String ID_COlUMN_NAME = "id";

    @Autowired
    public PositionDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Position> findAllByAccountId(Integer id){
        String selectSql = "SELECT * FROM " + TABLE_NAME + " where account_id=?";
        List<Position> positions =  jdbcTemplate
                .query(selectSql, BeanPropertyRowMapper.newInstance(Position.class), id);
        return positions;
    }

    public Position findAllByAccountIdAndTicker(Integer id, String ticker){
        String selectSql = "SELECT * FROM " + TABLE_NAME + " where account_id=? and ticker=?";
        Position position =  jdbcTemplate
                .queryForObject(selectSql, BeanPropertyRowMapper.newInstance(Position.class), id, ticker);
        return position;
    }

}
