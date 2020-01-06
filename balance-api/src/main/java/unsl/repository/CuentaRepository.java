package unsl.repository;
import java.util.List;
import unsl.entities.Cuenta;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
	List<Cuenta> findByTitular(@Param("titular") Integer titular);
}
