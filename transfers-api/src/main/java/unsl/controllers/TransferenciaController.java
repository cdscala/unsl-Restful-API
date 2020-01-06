package unsl.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import unsl.entities.Cuenta;
import unsl.entities.ResponseError;
import unsl.entities.Transferencia;
import unsl.services.TransferenciaService;
import unsl.utils.RestService;

@RestController
public class TransferenciaController {
    @Autowired
    TransferenciaService transferenciaService;
    final String uri = "http://127.0.0.1:8889/cuentas/";

    @GetMapping(value = "/transferencias")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Transferencia> getAll() {
        return transferenciaService.getAll();
    }

    @GetMapping(value = "/transferencias/{transferenciasId}")
    @ResponseBody
    public Object getTranferencia(@PathVariable("transferenciasId") Long transferenciaId) {
        Transferencia transferencia = transferenciaService.getTransferencia(transferenciaId);
        if (transferencia == null) {
            return new ResponseEntity(
                    new ResponseError(404, String.format("Cliente %d no encontrado", transferenciaId)),
                    HttpStatus.NOT_FOUND);
        }
        return transferencia;
    }

    @PostMapping(value = "/transferencias")
    @ResponseBody
    public Object createTransferencia(@RequestBody Transferencia transferencia) throws Exception {
        RestService rest = new RestService();

        Cuenta origen = rest.getCuenta(uri + String.valueOf(transferencia.getCuentaOrigen()));
        Cuenta destino = rest.getCuenta(uri + String.valueOf(transferencia.getCuentaDestino()));
        System.out.printf("origen: %d, %s ---- destino: %d,%s", origen.getId(), origen.getTipoMoneda(), destino.getId(),
                destino.getTipoMoneda());

        if (origen.getEstado().equals(Cuenta.Estado.BAJA) || destino.getEstado().equals(Cuenta.Estado.BAJA)) {
            return new ResponseEntity(
                    new ResponseError(404,
                            String.format("Una de las cuentas se encuentra dada de baja %d,%d",
                                    transferencia.getCuentaOrigen(), transferencia.getCuentaDestino())),
                    HttpStatus.NOT_FOUND);
        }
        if (origen.getSaldo() <= 0) {
            return new ResponseEntity(
                    new ResponseError(404,
                            String.format("Cuenta ID: %d no tiene fondos", transferencia.getCuentaOrigen())),
                    HttpStatus.NOT_FOUND);
        }
        if (origen.getTitular() == destino.getTitular()) {
            return new ResponseEntity(new ResponseError(404, "Cuenta origen igual a cuenta destino"),
                    HttpStatus.NOT_FOUND);
        }
        if (!origen.getTipoMoneda().equals(destino.getTipoMoneda())) {
            return new ResponseEntity(new ResponseError(404, "Cuenta origen distinta moneda a cuenta destino"),
                    HttpStatus.NOT_FOUND);
        }
        if (transferencia.getMonto() > origen.getSaldo()) {
            return new ResponseEntity(new ResponseError(404, "No hay suficientes fondos en cuenta origen"),
                    HttpStatus.NOT_FOUND);
        }

        // patch a cuenta de origen quitando el monto de la transferencia
        Cuenta cuenta = new Cuenta(origen.getId(), -transferencia.getMonto());
        rest.putCuenta(uri, cuenta);

        return transferenciaService.saveTransferencia(transferencia);
    }

    @PutMapping(value = "/transferencias")
    @ResponseBody
    public Object updateCuentaSaldo(@RequestBody Transferencia transferencia) {

        Transferencia transferenciaAux = transferenciaService.getTransferencia(transferencia.getId());
        if (transferenciaAux == null) {
            return new ResponseEntity(new ResponseError(404,
                    String.format("Transferencia ID: %d no encontrado, no se puede procesar", transferencia.getId())),
                    HttpStatus.NOT_FOUND);
        }
        Cuenta cuenta = new Cuenta(transferenciaAux.getCuentaDestino(), transferenciaAux.getMonto());
        RestService rest = new RestService();
        Transferencia res;
        try {
            rest.putCuenta(uri, cuenta);
            res = transferenciaService.updateEstadoTransferencia(transferencia);
            
        } catch (Exception e) {
            return new ResponseEntity(new ResponseError(404,
                    String.format("No se pudo depositar el dinero en la cuenta %d", transferencia.getCuentaDestino())),
                    HttpStatus.NOT_FOUND);
        }
        return res;
    }

    @DeleteMapping(value = "/transferencias/{id}")
    @ResponseBody
    public Object deleteTransferencia(@PathVariable Long id) {
        Transferencia res = transferenciaService.deleteTransferencia(id);
        if ( res == null) {
            return new ResponseEntity(new ResponseError(404, String.format("Usuario ID: %d no encontrado", id)), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(null,HttpStatus.NO_CONTENT);
    }

     // Moved Over from TestHelper for Blog Post
     /*public HttpEntity getPostRequestHeaders(String jsonPostBody) {
        List acceptTypes = new ArrayList();
        acceptTypes.add(MediaType.APPLICATION_JSON_UTF8);

        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        reqHeaders.setAccept(acceptTypes);

        return new HttpEntity(jsonPostBody, reqHeaders);
    }*/

}

