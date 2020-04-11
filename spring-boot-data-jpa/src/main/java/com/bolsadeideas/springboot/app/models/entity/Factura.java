package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//Una clase que esta anotada con la etiqueta entity hace que se cree una tabla con los campos exactamente iguales que los atributos de la clase
//se mapea exactamente igual, a menos que anotemos el atributo con la etiqueta @column("NombreDelCampo")
@Entity

@Table(name = "facturas") // podemos omitir este paso, y entonces la tabla relacionada con la clase
							// Factura se llamará igual que la clase
public class Factura implements Serializable{

	// Atributos
	// La etieuata @Id indica que este campo es clave
	// Con la etiqueta GeneratedValue, le indicamos como se va a generar (que
	// estrategia va a seguir) la clave. En este caso
	// con GenerationType.IDENTITY le indicamos que sea incremental (como un
	// autonumerico)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descripcion;
	private String observacion;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE) // Con esta eqtiqueta establecemos el formato de fecha que vamos a usar en el campo
	private Date createAt;
	
	//esta anotacion indica la realcion entre la clase facturas y la clase cliente. Many to one,
	//muchas facturas puede tener un cliente
	//fetch=FetchType.LAZY-> indica el tipod e carga de datos. Es carga perezosa, solo carga la factura que necesita. es mas recomendable, menos costosa
	@ManyToOne(fetch=FetchType.LAZY)
	private Cliente cliente;
	
	//esta anotacion indica la realcion entre la clase cliente y la clase factura. One to many,
	//Un cliente puede tener muchas facturas
	//cascade=CascadeType.ALL-> actualizaciones en cascada
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
		
	//La relacion con la tabla factura es en un solo sentido, por eso teenmos que indicar la llave foranea
	//Se lo indicamos con esta anotacion.
	//Es decir, vamos a tener una llave foranea en la tabla factura_items, llamada factura_id
	@JoinColumn(name="factura_id")
	private List<ItemFactura> items;
	
	
	
	public Factura() {
		this.items=new ArrayList<ItemFactura>();
	}

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
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	public List<ItemFactura> getItems() {
		return items;
	}

	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}

	public void addItemFactura(ItemFactura item) {
		this.items.add(item);
	}

	public Double getTotal() {
		Double total=0.0;
		
		for (int i = 0; i < items.size(); i++) {
			total +=items.get(i).calcularImporte();
		}
		return total;
	}
	
	

	//Para implementar serializable, tenemos que agregar este atributo estatico
	private static final long serialVersionUID = 1L;
	
}
