package unsl.entities;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;

@Entity
@Table(name = "cuentas", uniqueConstraints={@UniqueConstraint(columnNames = {"id"})})
public class Cuenta {
	public static enum TipoMoneda {
        PESO_AR,
        DOLAR,
        EURO
	}
	public static enum Estado {
        ACTIVA,
        BAJA
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@JsonProperty("titular")
	private int titular;

	@JsonProperty("saldo")
	private int saldo;

	@Enumerated(EnumType.STRING)
	private TipoMoneda tipoMoneda;
	
	@Enumerated(EnumType.STRING)
	private Estado estado;
	
	public Cuenta(){
	}

	public Cuenta(long id, int saldo){
		this.id=id;
		this.saldo=saldo;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public int getTitular() {
		return titular;
	}

	public void setTitular(int titular) {
		this.titular = titular;
	}

	public int getSaldo() {
		return saldo;
	}

	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}
	
	public Estado getEstado() {
		return this.estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public TipoMoneda getTipoMoneda() {
		return this.tipoMoneda;
	}
	public void setTipoMoneda(TipoMoneda tipoMoneda) {
		this.tipoMoneda = tipoMoneda;
	}
}
