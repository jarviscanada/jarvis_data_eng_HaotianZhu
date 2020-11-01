package ca.jrvs.apps.trading.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class HttpResponseExceptionUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponseExceptionUtils.class);

    public static ResponseStatusException throwException (Exception ex){
        if (ex instanceof  IllegalArgumentException){
            logger.debug(ex.getMessage(), ex);
            return new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        } else {
            logger.error(ex.getMessage(), ex);
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }
}
