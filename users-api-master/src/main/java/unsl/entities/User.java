package unsl.entities;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;

@Entity
@Table(name = "clientes", uniqueConstraints={@UniqueConstraint(columnNames = {"dni"})})
public class User {
	public static enum Status {
        ACTIVO,
        BAJA
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private long dni;

	@JsonProperty("nombre")
	private String nombre;

	@JsonProperty("apellido")
	private String apellido;

	@Enumerated(EnumType.STRING)
    private Status estado;
	
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setDni(Long dni) {
		this.dni = dni;
	}

	public Long getDni() {
		return dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public Status getEstado() {
		return this.estado;
	}
	public void setEstado(Status estado) {
		this.estado = estado;
	}
}
