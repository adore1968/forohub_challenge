package com.forohub.forohub.controller;

import com.forohub.forohub.domain.autores.Autor;
import com.forohub.forohub.domain.autores.DatosAutenticacionAutor;
import com.forohub.forohub.infra.security.DatosJWTtoken;
import com.forohub.forohub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionAutor datosAutenticacionAutor) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionAutor.login(), datosAutenticacionAutor.clave());
        var authentication = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarJWT((Autor) authentication.getPrincipal());
        return ResponseEntity.ok(new DatosJWTtoken(JWTtoken));
    }
}
