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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.dao.iClienteDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.service.IClienteService;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
	
	
	
	//@Qualifier("clienteDaoJPA")
	@Autowired
	private IClienteService clienteService;
	

	@RequestMapping(value="/listar",method = RequestMethod.GET)
	public String listar(Model model) {
		
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clienteService.findAll());
		
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
	public String editar(@PathVariable(value="id") long id, Map<String,Object> model,RedirectAttributes flash) {
		
		Cliente cliente=null;
		
		if(id>0) {
			cliente=clienteService.findOne(id);
			if(cliente==null) {
				flash.addFlashAttribute("error", "El ID del cliente no existe");
				return "redirect:/listar";
			}
		}else {
			flash.addFlashAttribute("error", "El ID del cliente no puede ser cero");
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo","Editar cliente");
		
		
		return "form";
	}
	
	
	@RequestMapping(value="/form",method = RequestMethod.POST)
	public String guardar (@Valid Cliente cliente,BindingResult result, Model model, RedirectAttributes flash ,SessionStatus status) {//añadimos la anotacion Valid, para que tengan efecto las validaciones que hemos incorporado en los atributos de la clase cliente
													//Añadimos el objeto result, del tipo BindingResult, para que al usuario le salga que errores ha cometido al introducir los datos
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de cliente");
			return "form";
		}
		
		String mensajeFlash=(cliente.getId() != null)? "Cliente editado con exito!" : "Cliente creado con exito!";
		
		clienteService.save(cliente);
		
		status.setComplete();//Elimina le objeto cliente d ela session
		
		flash.addFlashAttribute("success",mensajeFlash);
		return "redirect:listar";
	}
	
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") long id,RedirectAttributes flash) {
		
		
		if(id>0) {
			clienteService.deleteOne(id);
			flash.addFlashAttribute("success", "Cliente eliminado con exito");
		}
		
		return "redirect:/listar";
	}
	
}
