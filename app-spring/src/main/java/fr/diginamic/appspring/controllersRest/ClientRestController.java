package fr.diginamic.appspring.controllersRest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.appspring.entities.Client;
import fr.diginamic.appspring.repository.CrudClientRepository;

@RestController
@RequestMapping("rest/clients")
public class ClientRestController {

	@Autowired
	CrudClientRepository cr;
	
	@GetMapping("/all")
	public List<Client> findAll() {
		return (List<Client>) cr.findAll();
	}
	
}