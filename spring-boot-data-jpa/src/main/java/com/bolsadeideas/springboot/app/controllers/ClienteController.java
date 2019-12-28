package com.bolsadeideas.springboot.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsadeideas.springboot.app.models.dao.iClienteDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
	
	
	@Autowired
	@Qualifier("clienteDaoJPA")
	private iClienteDao clienteDao;
	

	@RequestMapping(value="/listar",method = RequestMethod.GET)
	public String listar(Model model) {
		
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clienteDao.buscarTodos());
		
		return "listar";
		
	}
	
	@RequestMapping(value="/form")
	public String crear(Map<String,Object> model) {
	
		Cliente cliente=new Cliente();
		
		model.put("cliente",cliente);
		model.put("titulo","Formulario de cliente");
		
		
		return "form";
	}
	
	@RequestMapping(value="/form/{id}")
	public String editar(@PathVariable(value="id") long id, Map<String,Object> model) {
		
		Cliente cliente=null;
		
		if(id>0) {
			cliente=clienteDao.findOne(id);
		}else {
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo","Editar cliente");
		
		
		return "form";
	}
	
	
	@RequestMapping(value="/form",method = RequestMethod.POST)
	public String guardar (@Valid Cliente cliente,BindingResult result, Model model, SessionStatus status) {//añadimos la anotacion Valid, para que tengan efecto las validaciones que hemos incorporado en los atributos de la clase cliente
													//Añadimos el objeto result, del tipo BindingResult, para que al usuario le salga que errores ha cometido al introducir los datos
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de cliente");
			return "form";
		}
		clienteDao.save(cliente);
		
		status.setComplete();//Elimina le objeto cliente d ela session
		return "redirect:listar";
	}
}
