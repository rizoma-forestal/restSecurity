/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.gob.ambiente.rest.restsecurity.service;

import ar.gob.ambiente.rest.restsecurity.annotation.Secured;
import ar.gob.ambiente.rest.restsecurity.entities.Guia;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilderException;

/**
 *
 * @author rincostante
 */
@Stateless
@Path("guias")
public class GuiaFacadeREST extends AbstractFacade<Guia> {

    @PersistenceContext(unitName = "rsPU")
    private EntityManager em;

    public GuiaFacadeREST() {
        super(Guia.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Guia entity) {
        super.create(entity);
    }
    
    /**
     * 
     * @api {put} /guias/:id Actualiza una Guía existente
     * @apiExample {curl} Ejemplo de uso:
     *     curl -X PUT -d http://localhost:8080/restSecurity-1.0.0-SNAPSHOT/rest/guias/:id
     * @apiDescription Método para editar una Guia existente
     * Solo se editará el estado y la fecha correspondiente.
     * Obtiene la Guía según el id recibido con el método local find(Long id)
     * y posteriormente la edita con el método local edit(guia)
     * @apiVersion 0.1.0
     * @apiName PutGuia
     * @apiGroup Guias
     *
     * @apiParam {Long} id ID único que de la Guía a editar
     * @apiParam {ar.gob.ambiente.rest.restsecurity.entities.Guia} entity Guía a editar de la dependencia paqSecurityRest.
     *
     * @apiSuccess {String} Ok La guía se actualizó con éxito
     *
     * @apiError GuiaNoModificada La Guía no fue modificada.
     *
     * @apiErrorExample Respuesta de error:
     *     HTTP/1.1 304 Not Modified
     *     {
     *       "error": "Hubo un error procesado la actualización en el Componente de Trazabilidad"
     *     }
     */
    
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Long id, Guia entity) {
        try{
            // obtengo la guía según su id
            ar.gob.ambiente.rest.restsecurity.entities.Guia g = super.find(id);
            // actualizo la guía obtenida
            super.edit(g);
            // retorno respuesta existosa
            return Response.ok().entity("La guía se actualizó con éxito.").build();
        }catch(IllegalArgumentException | UriBuilderException ex){
            return Response.notModified().entity("Hubo un error procesado la actualización en el Componente de Trazabilidad. " + ex.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Secured
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Guia find(@PathParam("id") Long id) {
        return super.find(id);
    }

    /**
     * @api {get} /user/:id Obtiene la información de un usuario
     * @apiVersion 0.1.0
     * @apiName GetUser
     * @apiGroup User
     *
     * @apiParam {Number} id Users unique ID.
     *
     * @apiSuccess {Number} code  Código 0 conforme todo ha ido bien.
     * @apiSuccess {Bool} true/false  True o false dependiendo del resultado.
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "code": 0,
     *       "response": true
     *     }
     *
     * @apiError UserNotFound The id of the User was not found.
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 404 Not Found
     *     {
     *       "error": "UserNotFound"
     *     }
     */    
    @GET
    @Override
    @Secured
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Guia> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Guia> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
