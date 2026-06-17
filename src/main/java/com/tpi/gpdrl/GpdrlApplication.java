package com.tpi.gpdrl;

import java.util.HashMap;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import java.util.Map;

@SpringBootApplication
@Controller
public class GpdrlApplication {

	
	@GetMapping("/")
	@PreAuthorize("hasAnyRole('MENU','ADMINISTRADOR','REGISTRO','DESPACHO')")
	public String home(Model model){
		model.addAttribute("titulo", "Inicio");
		return "index";
	}
	@GetMapping("/Ejemplo")
	@PreAuthorize("hasAnyRole('MENU','ADMINISTRADOR','REGISTRO','DESPACHO')")
	public String ejemplo(Model model){
		model.addAttribute("titulo", "Ejemplo");
		return "ejemploDeComoHeredar";
	}

	@GetMapping("/homeCliente")
	@PreAuthorize("hasAnyRole('CLIENTE','ADMINISTRADOR')")
	public String homeCliente(Model model){
		model.addAttribute("titulo", "Inicio");
		return "homeCliente";
	}


	@GetMapping("/session")
	public ResponseEntity<?> getDetailResponseEntity() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User userObject = null;

		if (authentication != null && authentication.getPrincipal() instanceof User) {
			userObject = (User) authentication.getPrincipal();
		}

		Map<String, Object> response = new HashMap<>();
		response.put("UsarioSesion", userObject);
		return ResponseEntity.ok(response);
	}


	public static void main(String[] args) {
		SpringApplication.run(GpdrlApplication.class, args);
	}

}
