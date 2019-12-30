package com.bolsadeideas.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.app.models.dao.iClienteDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;

@Service
public class ClienteServiceImpl implements IClienteService{

	@Autowired
	private iClienteDao clienteDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<Cliente> buscarTodos() {

		return clienteDao.buscarTodos();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteDao.save(cliente);
		
	}

	@Override
	@Transactional(readOnly=true)
	public Cliente findOne(long id) {
		
		return clienteDao.findOne(id);
	}

	@Override
	@Transactional
	public void deleteOne(long id) {
		clienteDao.deleteOne(id);
		
	}

}