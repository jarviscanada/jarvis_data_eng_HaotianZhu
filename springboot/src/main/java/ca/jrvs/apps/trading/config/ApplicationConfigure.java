package ca.jrvs.apps.trading.config;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfigure {

    @Bean
    public MarketDataConfig marketDataConfig(){
        MarketDataConfig config = new MarketDataConfig();
        config.setHost("https://cloud.iexapis.com/v1/");
        config.setToken(System.getenv("token"));
        return config;
    }

    @Bean
    public HttpClientConnectionManager poolingHttpClientConnectionManager(){
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);
        return cm;
    }

    @Bean
    public DataSource dataSource() {
        String jdbcUrl =
                "jdbc:postgresql://" +
                        System.getenv("PSQL_HOST") + ":" +
                        System.getenv("PSQL_PORT") +
                        "/" +
                        System.getenv("PSQL_DB");
        String user = System.getenv("PSQL_USER");
        String password = System.getenv("PSQL_PASSWORD");

        //Never log your credentials/secrets. Use IDE debugger instead
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(jdbcUrl);
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(password);
        return basicDataSource;
    }

}
