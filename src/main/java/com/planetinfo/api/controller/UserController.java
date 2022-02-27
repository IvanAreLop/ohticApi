package com.planetinfo.api.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.planetinfo.api.entity.User;
import com.planetinfo.api.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.OPTIONS})
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * Creación de usuario
	 * @param user Datos del usuario a crear
	 * @return Usuario creado
	 */
	@PostMapping("/signup")
	public ResponseEntity<?> create (@RequestBody User user) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
	}
	
	/**
	 * Login
	 * @param user Nombre de usuario y contraseña
	 * @return 
	 */
	@PostMapping("/signin")
	public ResponseEntity<Object> login(@RequestBody User user) {
		
		Optional<User> existingUser = userService.login(user.getUsername(), user.getPassword());
		
		if(!existingUser.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		// Se crea y setea el token
		String token = getToken(user.getUsername());
		existingUser.get().setToken(token);
		
		return ResponseEntity.ok(existingUser);
	}
	
	/**
	 * Guarda voto de usuario
	 * @param userName Nombre de usuario
	 * @param vote Planeta votado
	 */
	@GetMapping("/vote")
	public void saveVote(@RequestParam String userName, @RequestParam Integer vote) {
		userService.saveVote(userName, vote);
	}
	
	/**
	 * Obtener votos
	 * @return Listado de votos
	 */
	@GetMapping("/votes")
	public ResponseEntity<Object> getAllVotes() {
		return ResponseEntity.ok(userService.getAllVotes());
	}

	
	/**
	 * Crea token
	 * @param userName Nombre de usuario
	 * @return token
	 */
	private String getToken(String userName) {
		String token = Jwts.builder().setIssuedAt(new Date())
				.setSubject(userName)
				.setExpiration(new Date(System.currentTimeMillis() + 3_600_000))
				.signWith(SignatureAlgorithm.HS512, "prrytasdfkqwenfagksjtyiurteovbaprrytasdfkqwenfagksjtyiurteovbasdlpojgwaqgheqvvbnxagqwetrrwewqrqsdlpojgwaqgheqvvbnxagqwetrrwewqrq").compact();
		return "Bearer " + token;
	}
}
