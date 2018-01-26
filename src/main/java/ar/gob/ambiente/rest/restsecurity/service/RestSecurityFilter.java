/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.gob.ambiente.rest.restsecurity.service;

//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.SignatureException;
//import io.jsonwebtoken.UnsupportedJwtException;
//import io.jsonwebtoken.impl.crypto.MacProvider;
//import java.io.IOException;
//import java.security.Key;
//import javax.annotation.Priority;
//import javax.ws.rs.Priorities;
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerRequestFilter;
//import javax.ws.rs.core.HttpHeaders;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.ext.Provider;

import ar.gob.ambiente.rest.restsecurity.annotation.Secured;
import java.io.IOException;
import java.security.Key;
import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.Priorities;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.crypto.MacProvider;

/**
 *
 * @author rincostante
 */
@Provider
@Secured
@Priority(Priorities.AUTHENTICATION)
public class RestSecurityFilter implements ContainerRequestFilter{
    
    public static final Key KEY = MacProvider.generateKey();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        
        // Recupera la cabecera HTTP Authorization de la petición
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        
        try{
            // Extrae el token de la cabecera
            String token = authorizationHeader.substring("Bearer".length()).trim();
            
            // Valida el token utilizando la cadena secreta
            Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
        }catch(Exception ex){
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
    
}
