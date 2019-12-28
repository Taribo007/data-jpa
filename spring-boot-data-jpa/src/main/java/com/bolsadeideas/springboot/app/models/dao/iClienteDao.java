package com.bolsadeideas.springboot.app.models.dao;

import java.util.List;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

public interface iClienteDao {

	public List<Cliente> buscarTodos();
	
	public void save (Cliente cliente);
	public Cliente findOne(long id);
	public void deleteOne(long id);
}
