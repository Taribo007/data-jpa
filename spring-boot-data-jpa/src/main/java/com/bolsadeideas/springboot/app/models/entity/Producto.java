package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//Una clase que esta anotada con la etiqueta entity hace que se cree una tabla con los campos exactamente iguales que los atributos de la clase
//se mapea exactamente igual, a menos que anotemos el atributo con la etiqueta @column("NombreDelCampo")
@Entity

@Table(name = "productos")
public class Producto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private Double precio;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE) // Con esta eqtiqueta establecemos el formato de fecha que vamos a usar en el campo
	private Date createAt;
	
	
	//Este merodo es para que se genere la fecha, ya que no es un dato que se vaya aintroducir
		@PrePersist
		public void prePersist() {
			createAt=new Date();
		}

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

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	// Para implementar serializable, tenemos que agregar este atributo estatico
	private static final long serialVersionUID = 1L;

}
