package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.config.MarketDataConfig;
import ca.jrvs.apps.trading.models.IexQuote;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.util.*;

@Repository
public class MarketDataDao implements CrudRepository<IexQuote,String> {
    private String GET_QUOTE_PATH = "stock/%s/quote?token=";
    private String GET_QUOTES_PATH = "stock/market/batch?symbols=%s&types=quote&token=";
    private CloseableHttpClient client;
    private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);

    @Autowired
    public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager,
                         MarketDataConfig marketDataConfig){
        this.client = HttpClients.custom()
                .setConnectionManager(httpClientConnectionManager)
                .setConnectionManagerShared(true)
                .build();
        this.GET_QUOTE_PATH = marketDataConfig.getHost() + this.GET_QUOTE_PATH + marketDataConfig.getToken();
        this.GET_QUOTES_PATH = marketDataConfig.getHost() + this.GET_QUOTES_PATH + marketDataConfig.getToken();
    }

    @Override
    public <S extends IexQuote> S save(S s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Optional<IexQuote> findById(String s) {
        URI uri = URI.create(String.format(GET_QUOTE_PATH, s));
        HttpGet httpGet = new HttpGet(uri);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            HttpResponse response = this.client.execute(httpGet);
            String body = EntityUtils.toString(response.getEntity());

            if(Optional.of(response).filter(r -> r.getStatusLine().getStatusCode() == 200).isPresent()){
                return Optional.ofNullable(objectMapper.readValue(body, IexQuote.class));
            } else if(response.getStatusLine().getStatusCode() == 404){
               return Optional.empty();
            } else {
                throw new DataRetrievalFailureException(body);
            }
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsById(String s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Iterable<IexQuote> findAll() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public List<IexQuote> findAllById(Iterable<String> iterable) {

        String symbols = String.join(",", iterable);
        URI uri = URI.create(String.format(GET_QUOTES_PATH,symbols));
        HttpGet httpGet = new HttpGet(uri);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            HttpResponse response = this.client.execute(httpGet);
            String body = EntityUtils.toString(response.getEntity());
            if(response.getStatusLine().getStatusCode() == 200){
                JSONObject IexQuotesJson = new JSONObject(body);
                Iterator<String> keys = IexQuotesJson.keys();
                JSONArray quotesArray = new JSONArray();

                while(keys.hasNext()){
                    JSONObject quoteJson = IexQuotesJson
                            .getJSONObject(keys.next().toUpperCase())
                            .getJSONObject("quote");
                    quotesArray.put(quoteJson);
                }

                return Arrays.asList(objectMapper.readValue(quotesArray.toString(), IexQuote[].class));
            } else if(response.getStatusLine().getStatusCode() == 404){
                return new ArrayList<IexQuote>();
            } else {
                throw new RuntimeException(body);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e){
            throw e;
        }
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteById(String s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void delete(IexQuote iexQuote) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends IexQuote> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
