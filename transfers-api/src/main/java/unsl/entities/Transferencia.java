package unsl.entities;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;

@Entity
@Table(name = "transferencias", uniqueConstraints={@UniqueConstraint(columnNames = {"id"})})
public class Transferencia {
	public static enum Status {
		PENDIENTE,
		PROCESADA,
        CANCELADA
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@JsonProperty("cuenta_origen")
	private int cuenta_origen;

	@JsonProperty("cuenta_destino")
	private int cuenta_destino;

	@JsonProperty("monto")
	private int monto;

	@Enumerated(EnumType.STRING)
    private Status estado;
	
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public int getCuentaOrigen() {
		return cuenta_origen;
	}

	public void setCuentaOrigen(int cuenta_origen) {
		this.cuenta_origen = cuenta_origen;
	}

	public int getCuentaDestino() {
		return cuenta_destino;
	}

	public void setCuentaDestino(int cuenta_destino) {
		this.cuenta_destino = cuenta_destino;
	}
	
	public int getMonto() {
		return this.monto;
	}
	public void setMonto(int monto) {
		this.monto = monto;
	}

	public Status getEstado() {
		return this.estado;
	}
	public void setEstado(Status estado) {
		this.estado = estado;
	}
}
