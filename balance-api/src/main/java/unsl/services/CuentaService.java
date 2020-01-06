package unsl.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unsl.entities.Cuenta;
import unsl.entities.Cuenta.Estado;
import unsl.repository.CuentaRepository;

@Service
public class CuentaService {
    @Autowired
    CuentaRepository CuentaRepository;

    public List<Cuenta> getAll() {
        return CuentaRepository.findAll();
    }

    public Cuenta getCuenta(Long cuentaId) {
        return CuentaRepository.findById(cuentaId).orElse(null);
    }

    public List<Cuenta> findByTitular(Integer titular) {
        return CuentaRepository.findByTitular(titular);
    }

    public Object saveCuenta(Cuenta cuenta) {
        cuenta.setEstado(Estado.ACTIVA);
        cuenta.setSaldo(0);
        return CuentaRepository.save(cuenta);
    }

    public Cuenta updateCuentaSaldo(Cuenta updateCuenta){
        Cuenta cuenta = CuentaRepository.findById(updateCuenta.getId()).orElse(null);;
        if (cuenta ==  null){
            return null;
        }
        cuenta.setSaldo(updateCuenta.getSaldo()+cuenta.getSaldo());
        return CuentaRepository.save(cuenta);
    }

    public Cuenta updateCuentaEstado(Long cuentaId) {
        Cuenta cuenta = CuentaRepository.findById(cuentaId).orElse(null);;
        if (cuenta ==  null){
            return null;
        }
        if(cuenta.getEstado()==Estado.ACTIVA){
            cuenta.setEstado(Cuenta.Estado.BAJA);
        }
        else{
            cuenta.setEstado(Cuenta.Estado.ACTIVA);
        }
        return CuentaRepository.save(cuenta);
    }
}
