package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


//Una clase que esta anotada con la etiqueta entity hace que se cree una tabla con los campos exactamente iguales que los atributos de la clase
	//se mapea exactamente igual, a menos que anotemos el atributo con la etiqueta @column("NombreDelCampo")
	@Entity

	@Table(name = "facturas_items")

public class ItemFactura implements Serializable {

	// Atributos
	// La etieuata @Id indica que este campo es clave
	// Con la etiqueta GeneratedValue, le indicamos como se va a generar (que
	// estrategia va a seguir) la clave. En este caso
	// con GenerationType.IDENTITY le indicamos que sea incremental (como un
	// autonumerico)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private Integer cantidad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="producto-id")
	private Producto producto;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	public Double calcularImporte() {
		return cantidad.doubleValue() * producto.getPrecio();
	}

	// Para implementar serializable, tenemos que agregar este atributo estatico
	private static final long serialVersionUID = 1L;

}
