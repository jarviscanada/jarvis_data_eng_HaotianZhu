package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.models.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public abstract class CrudIntDao<T extends Entity<Integer>> implements CrudRepository<T, Integer> {
    private final Logger logger = LoggerFactory.getLogger(CrudIntDao.class);

    abstract public JdbcTemplate getJdbcTemplate();

    abstract public SimpleJdbcInsert getSimpleJdbcInsert();

    abstract public String getTableName();

    abstract  public String getIdColumnName();

    abstract Class<T> getEntityClass();


}
