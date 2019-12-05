package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//Una clase que esta anotada con la etiqueta entity hace que se cree una tabla con los campos exactamente iguales que los atributos de la clase
//se mapea exactamente igual, a menos que anotemos el atributo con la etiqueta @column("NombreDelCampo")
@Entity

@Table(name = "clientes") // podemos omitir este paso, y entonces la tabla relacionada con la clase
							// Cliente se llamará igual que la clase
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	// Atributos
	// La etieuata @Id indica que este campo es clave
	// Con la etiqueta GeneratedValue, le indicamos como se va a generar (que
	// estrategia va a seguir) la clave. En este caso
	// con GenerationType.IDENTITY le indicamos que sea incremental (como un
	// autonumerico)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCliente;

	// Se pueden establecer varias caracteristicas del campo, separando por comas,
	// dentro del parentesis de la etiqueta @Column()
	// @Column(name="NombreCliente")
	private String nombre;
	private String apellido;
	private String email;

	@Column(name = "create_at")
	@Temporal(TemporalType.DATE) // Con esta eqtiqueta establecemos el formato de fecha que vamos a usar en el campo
	private Date createAt;
	
	// Constructores

	// Getter y Setter

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	// Metodos

}
