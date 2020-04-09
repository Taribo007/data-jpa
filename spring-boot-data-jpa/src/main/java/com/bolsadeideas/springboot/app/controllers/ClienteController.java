package com.bolsadeideas.springboot.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.dao.iClienteDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.service.IClienteService;
import com.bolsadeideas.springboot.app.util.paginator.PageRender;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
	
	
	
	//@Qualifier("clienteDaoJPA")
	@Autowired
	private IClienteService clienteService;
	
	private final Logger log=LoggerFactory.getLogger(getClass());
	
	@GetMapping(value="/uploads/{filename:.+}")
	public ResponseEntity <Resource> verFoto(@PathVariable String filename){
		Path pathFoto=Paths.get("uploads").resolve(filename).toAbsolutePath();
		log.info("pathFoto: " + pathFoto);
		
		Resource recurso=null;
		
		try {
			recurso=new UrlResource(pathFoto.toUri());
			if (!recurso.exists() && !recurso.isReadable()) {
				throw new RuntimeException("Error: no se puede cargar la imagen " + pathFoto.toString());
				
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}
	
	
	@GetMapping(value="/ver/{id}")
	public String ver(@PathVariable(value="id") long id, Map<String,Object> model, RedirectAttributes flash) {
		
		
		Cliente cliente=clienteService.findOne(id);
		
		if (cliente==null) {
			flash.addFlashAttribute("Error",  "El cliente no existe en la BD");
			return "redirect:/listar";
		}
		
		model.put("cliente", cliente);
		model.put("titulo", "Detalle cliente: " + cliente.getNombre() + " " + cliente.getId());
		
		return "ver";
	}

	@RequestMapping(value="/listar",method = RequestMethod.GET)
	public String listar(@RequestParam(name="page", defaultValue="0") int page, Model model) {
		
		Pageable pageRequest= PageRequest.of(page,4);
		
		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		
		PageRender<Cliente> pageRender=new PageRender<>("/listar",clientes);
		
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
		
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
	public String guardar (@Valid Cliente cliente,BindingResult result, Model model, @RequestParam("file") MultipartFile foto, RedirectAttributes flash ,SessionStatus status) {//añadimos la anotacion Valid, para que tengan efecto las validaciones que hemos incorporado en los atributos de la clase cliente
													//Añadimos el objeto result, del tipo BindingResult, para que al usuario le salga que errores ha cometido al introducir los datos
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de cliente");
			return "form";
		}
		
		if (!foto.isEmpty()) {
			//Esta es la ruta cuando guardamos las imagenes en un directorio estaico, dentro de spring
			//Lo comentamos para hcaerlode otra forma
			//Path directorioRecursos=Paths.get("src//main//resources//static//uploads");
			
			//String rootPath=directorioRecursos.toFile().getAbsolutePath();
			
			//Esto es para una ruta relaitiva
			//String rootPath="C://Temp//Uploads";
			
			//Esto es para una ruta absoluta
			//Vamos a establecer el nombre del archivo de forma aleatoria cada vez que se seleccione suna imagen, le añadimos al nombre un valor aleatorio
			//paraq ue nunca se suban imagenes con igual nombre
			
			String uniqueFilename=UUID.randomUUID().toString() + "_" +foto.getOriginalFilename();
			
			
			Path rootPath=Paths.get("uploads").resolve(uniqueFilename);
			
			Path rootAbsolutPath=rootPath.toAbsolutePath();
			
			log.info("rootPath: " + rootPath);
			log.info("rootAbsolutPath: " + rootAbsolutPath);
			
			try {
				
				//Estas tres lineas son para la opcion de rutas relativas. Para copiar el archivo de uan ubicacion a otra
				//byte[] bytes=foto.getBytes();
				//Path rutaCompleta=Paths.get(rootPath+"//"+foto.getOriginalFilename());
				//Files.write(rutaCompleta, bytes);
				
				//Esta es otra opcion para copiar el archivo de uan ubicacion a otra
				Files.copy(foto.getInputStream(), rootAbsolutPath);
				
				flash.addFlashAttribute("info","Has subido la foto correctamente: "+uniqueFilename);
				cliente.setFoto(uniqueFilename);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
