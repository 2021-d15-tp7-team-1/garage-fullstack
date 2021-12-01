package fr.diginamic.appspring.controllersRest;

import fr.diginamic.appspring.authentication.ApplicationUserService;
import fr.diginamic.appspring.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/")
public class UserRestController {
    @Autowired
    ApplicationUserService appUserService;


    @PostMapping("signin")
    public ResponseEntity<String> tryConnexion(@RequestParam String username, @RequestParam String password,
                                             HttpServletRequest request){
        System.out.println("we are in");
        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        UserDetails user = appUserService.loadUserByUsername(username);
        if(user != null){
            if(!user.getPassword().equals(password)){
                user = null;
            }
        }
        if(user == null){
            return (ResponseEntity<String>) ResponseEntity.notFound();
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("TOKEN", csrf.toString() );
        System.out.println(responseHeaders);
        System.out.println("csrf :" + csrf.toString());
        return (ResponseEntity<String>) ResponseEntity.ok()
                .headers(responseHeaders);
    }

}
