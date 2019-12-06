package com.bolsadeideas.springboot.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.app.models.entity.Cliente;


//Marcamos la clase como componente de persistencia, de acceso a datos
//En realidad es un tipo de la etiqueta @component, por tanto lo enmarca dentro del contenedor de spring
@Repository("clienteDaoJPA")
public class ClienteDaoImpl implements iClienteDao {

	//se encarga de manejar als clases de entidades. Todas la s operaciones a la BD, pero a nivel de objeto, a traves d ela sclase enptyti
	//Las cosultas son consulta de JPA, que van a la clase entiy, no a la tabla. Pero la tabla esta mapeada a la clase
	
	//esta etiqueta conitnee la unidad de persistencia
	//de forma automatica, le va  ainyectar el em segun la configuracion de la unidad de persietencia que contiene el datasource,
	//que contiene el proveedor JPA. Si no ocnfuguramos ninguna BD en el Aplication.properties, va a usar el H2, que añadimos la dependecia al principio
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<Cliente> buscarTodos() {

		return em.createQuery("from Cliente").getResultList();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		em.persist(cliente);
		
	}

}
