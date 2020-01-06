package unsl.utils;


import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.http.HttpEntity;

import unsl.entities.Ping;
import unsl.entities.Cuenta;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RestService {
    private static Logger LOGGER = LoggerFactory.getLogger(RestService.class);
    
   
    /** 
     * @param url
     * @return
     * @throws Exception
     */
    public Ping getPing(String url) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        Ping ping;

        try {
            ping = restTemplate.getForObject(url, Ping.class);
        }  catch (Exception e){
            throw new Exception(buildMessageError(e));

        }

        return ping;
    }

    @Retryable( maxAttempts = 4, backoff = @Backoff(1000))
    public Cuenta getCuenta(String url) throws Exception {
        LOGGER.info(String.format("GET Cuenta :"+ url+"%d", LocalDateTime.now().getSecond()));
        Cuenta account;
        RestTemplate restTemplate=new RestTemplate();
        try {
            account = restTemplate.getForObject(url, Cuenta.class);
        }  catch (Exception e){
            throw new Exception( buildMessageError(e));
        }

        return account;
    }
   

    /**
     * @param url
     * @throws Exception
     */
    @Retryable( maxAttempts = 4, backoff = @Backoff(1000))
    public void putCuenta(String url, Cuenta cuenta) throws Exception {
        LOGGER.info("PUT Cuenta :"+ url);
        RestTemplate restTemplate=new RestTemplate();
        try {
            restTemplate.put(url, cuenta);
        }  catch (Exception e){
            throw new Exception( buildMessageError(e));
        }
    }
    
    private String buildMessageError(Exception e) {
        String msg = e.getMessage();
        if (e instanceof HttpClientErrorException) {
            msg = ((HttpClientErrorException) e).getResponseBodyAsString();
        } else if (e instanceof HttpServerErrorException) {
            msg =  ((HttpServerErrorException) e).getResponseBodyAsString();
        }
        return msg;
    }

}

