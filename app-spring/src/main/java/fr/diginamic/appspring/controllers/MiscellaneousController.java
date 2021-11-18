package fr.diginamic.appspring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MiscellaneousController {
	
	@GetMapping("login")
	public String toLogin() {
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

}
