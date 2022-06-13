package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;

@Controller
public class LoginController {

	@Autowired HttpSession sesion;
	@Autowired UsuarioService serviceUsu;
	
	/**
	* Nos muestra el login al acceder a la aplicacion
	* @param model
	* @return nos devuelve al login
	*/
	@GetMapping({"/login", "/"})
	public String login(Model model) {
		
		model.addAttribute("usuario", new Usuario());
		return "login";
		
	}
	
	/**
	 * Comprueba que el usuario que se ha introducido en el login es correcto
	 * @param usuario
	 * @param model
	 * @return Si el usuario existe pasara al inicio de usuario, si no, le devolvera al login y dara
	 * un mensaje de error
	 */
	@PostMapping("/login/submit")
	public String inicioSesion(@ModelAttribute("usuario") Usuario usuario, Model model) {
		
		if(!serviceUsu.loginUsuario(usuario.getNick(), usuario.getContra())) {
			
			String wrongLogin = "El usuario o contraseña introducidos no son validos";
			model.addAttribute("wrongLogin", wrongLogin);
			return "login";
			
		} else {
			
			Usuario usu = serviceUsu.findById(usuario.getNick());
			sesion.setAttribute("usuario", usu);
			model.addAttribute("usuario", usu);
			
			return "inicio";
			
		}
		
	}
	
	/**
	 * Invalida la sesion actual
	 * @return Nos devuelve al login
	 */
	@GetMapping({"/terminate"})
	public String terminate(Model model) {
		
		sesion.invalidate();
		//String wrongLogin = "El usuario o contraseña introducidos no son validos";
		//model.addAttribute("wrongLogin", wrongLogin);
		return "redirect:/login";

		
		
	}
	
	/**
	 * Pagina de error personalizada que nos devolvera al login
	 * @return
	 */
	@GetMapping("/error")
	public String error() {
		
		return "redirect:/error";
		
	}
	
}

