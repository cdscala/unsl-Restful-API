package unsl.controllers;
import org.springframework.web.bind.annotation.*;
import unsl.entities.Ping;

@RestController
public class PingController {

    @GetMapping(value = "/ping")
    @ResponseBody
    public Object ping() {
        Ping ping = new Ping();
        ping.setPing("pong");
        return ping;
    }
}