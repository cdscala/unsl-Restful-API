package unsl.utils;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import unsl.entities.Ping;

@Service
public class RestService {

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

