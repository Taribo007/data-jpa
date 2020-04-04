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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

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
	private Long id;

	// Se pueden establecer varias caracteristicas del campo, separando por comas,
	// dentro del parentesis de la etiqueta @Column()
	// @Column(name="NombreCliente")
	
	
	//@Size(min = 4,max = 12)//Para validar numero minimo y maximo de caracteres
	@NotEmpty //Anotacion para indicar que nombre es un campo requerido. Se uas para tipos de dato string, ya que comprunba que el campo no este vacio
	private String nombre;
	@NotEmpty
	private String apellido;
	@NotEmpty
	@Email //Valida que sea de tipo email
	private String email;

	@NotNull //Valida que el campo no sea nulo
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE) // Con esta eqtiqueta establecemos el formato de fecha que vamos a usar en el campo
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createAt;
	
	private String foto;
	
	// Constructores

	// Getter y Setter

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}


	// Metodos

	//@PrePersist
	//public void prePersist() {
	//	createAt= new Date();
	//}
}
