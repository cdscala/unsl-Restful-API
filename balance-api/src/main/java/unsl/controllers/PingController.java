package unsl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import unsl.entities.Ping;
import unsl.utils.RestService;

@RestController
public class PingController {

    @Autowired
    RestService restService;

    @GetMapping(value = "/ping")
    @ResponseBody
    public Object ping() {
    	Ping ping = new Ping();
    	ping.setPing("pong");
        return ping;
    }

    @GetMapping(value = "/transfers/ping")
    @ResponseBody
    public Object transfersPing() throws Exception {
        return restService.getPing("http://localhost:8080/ping");
    }


}