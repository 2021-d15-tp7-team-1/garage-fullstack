package fr.diginamic.appspring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/magasinier")
public class MagasinierController {
	
	@GetMapping("/test")
	public String toMagasinierTestPage() {
		return "/magasinier/magasinier_test";
	}
}
