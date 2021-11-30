package fr.diginamic.appspring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class MiscellaneousController {
	
	@GetMapping("login")
	public String toLogin(
			@RequestParam(value = "error", defaultValue = "false") boolean wrongAuthentication,
			Model model) {
		
		if(wrongAuthentication) {
			model.addAttribute("errorMessage", "CET UTILISATEUR N'EXISTE PAS !<br/>Username et/ou mot de passe incorrect(s)");
		}
		
		return "login";
	}
	
	@GetMapping("home")
	public String toHome() {
		return "home";
	}
	
	@GetMapping("access_denied")
	public String toAccessDenied() {
		return "access_denied";
	}

	@GetMapping("profile")
	public String getProfile(){
		return "profil/profil-info";
	}

	@GetMapping("ventes")
	public String toVente(){
		return "non-disponible";
	}

}
