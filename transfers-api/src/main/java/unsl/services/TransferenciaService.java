package unsl.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unsl.entities.Transferencia;
import unsl.entities.Transferencia.Status;
import unsl.repository.TransferenciaRepository;
import unsl.utils.RestService;

@Service
public class TransferenciaService {
    @Autowired
    TransferenciaRepository transferenciaRepository;

    public List<Transferencia> getAll() {
        return transferenciaRepository.findAll();
    }

    public Transferencia getTransferencia(Long transferenciaId) {
        return transferenciaRepository.findById(transferenciaId).orElse(null);
    }

    public Transferencia saveTransferencia(Transferencia transferencia) {
        transferencia.setEstado(Status.PENDIENTE);
        return transferenciaRepository.save(transferencia);
    }

    public Transferencia updateTransferencia(Transferencia updatedTransferencia){
        Transferencia transferencia = transferenciaRepository.findById(updatedTransferencia.getId()).orElse(null);;
        if (transferencia ==  null){
            return null;
        }
        transferencia.setCuentaOrigen(updatedTransferencia.getCuentaOrigen());
        transferencia.setCuentaDestino(updatedTransferencia.getCuentaDestino());
        return transferenciaRepository.save(transferencia);
    }

    public Transferencia updateEstadoTransferencia(Transferencia updatedTransferencia){
        Transferencia transferencia = transferenciaRepository.findById(updatedTransferencia.getId()).orElse(null);;
        if (transferencia ==  null){
            return null;
        }
        transferencia.setEstado(Status.PROCESADA);
        return transferenciaRepository.save(transferencia);
    }

    public Transferencia deleteTransferencia(Long transferenciaId) {
        Transferencia transferencia = transferenciaRepository.findById(transferenciaId).orElse(null);;
        if (transferencia ==  null){
            return null;
        }
        transferencia.setEstado(Transferencia.Status.CANCELADA);
        return transferenciaRepository.save(transferencia);
    }
}
