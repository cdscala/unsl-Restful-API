package unsl.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import unsl.entities.ResponseError;
import unsl.entities.Cuenta;
import unsl.services.CuentaService;

@RestController
public class CuentaController {
    @Autowired
    CuentaService cuentaService;
    final String uri = "http://127.0.0.1:8888/clientes/";

    @GetMapping(value = "/cuentas/{cuentaId}")
    @ResponseBody
    public Object getUser(@PathVariable("cuentaId") Long cuentaId) {
        Cuenta cuenta = cuentaService.getCuenta(cuentaId);
        if ( cuenta == null) {
            return new ResponseEntity(new ResponseError(404, String.format("Cliente %d no existe", cuentaId)), HttpStatus.NOT_FOUND);
        }
        return cuenta;
    }

    @GetMapping(value ="/cuentas/busqueda")
    @ResponseBody
    public List<Cuenta> searchCuentas(@RequestParam("titular") Integer titular) {
        return cuentaService.findByTitular(titular);
    }

    @PostMapping(value = "/cuentas")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Object createCuenta(@RequestBody Cuenta cuenta) {
        for (Cuenta var : cuentaService.findByTitular(cuenta.getTitular())) {
            if(cuenta.getTitular()==var.getTitular() && cuenta.getTipoMoneda()==var.getTipoMoneda()){
                return new ResponseEntity(new ResponseError(404,"Cuenta ya existe, no se puede crear."), HttpStatus.NOT_FOUND);
            } 
        }
        RestTemplate restTemplate = new RestTemplate();
        String streamCuenta;
        try{
            streamCuenta = restTemplate.getForObject(uri+String.valueOf(cuenta.getTitular()), String.class);
        }
        catch(Exception e){
            return new ResponseEntity(new ResponseError(404, String.format("Cuenta ID: %d no existe", cuenta.getTitular())), HttpStatus.NOT_FOUND);
        }

        return cuentaService.saveCuenta(cuenta);
        
    }

    @PutMapping(value = "/cuentas")
    @ResponseBody
    public Object updateCuentaSaldo(@RequestBody Cuenta cuenta) {
        Cuenta res = cuentaService.updateCuentaSaldo(cuenta);
        if ( res == null) {
            return new ResponseEntity(new ResponseError(404, String.format("Cuenta ID: %d no encontrado, no se puede actualizar", cuenta.getId())), HttpStatus.NOT_FOUND);
        }
        return res;
    }

    @PutMapping(value = "/cuentas/{id}")
    @ResponseBody
    public Object updateCuentaEstado(@PathVariable Long id) {
        Cuenta res = cuentaService.updateCuentaEstado(id);
        if ( res == null) {
            return new ResponseEntity(new ResponseError(404, String.format("Usuario ID: %d no encontrado", id)), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(new ResponseError(200, String.format("Estado actualizado en cuenta ID: %d estado: %s", id,res.getEstado())),HttpStatus.ACCEPTED);
    }

}