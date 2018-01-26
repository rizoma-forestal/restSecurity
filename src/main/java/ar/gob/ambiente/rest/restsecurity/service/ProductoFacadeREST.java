
package ar.gob.ambiente.rest.restsecurity.service;

import ar.gob.ambiente.rest.restsecurity.annotation.Secured;
import ar.gob.ambiente.rest.restsecurity.entities.Producto;
import java.net.URI;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriBuilderException;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author rincostante
 */
@Stateless
@Path("productos")
public class ProductoFacadeREST extends AbstractFacade<Producto> {

    @PersistenceContext(unitName = "rsPU")
    private EntityManager em;
    
    @Context
    UriInfo uriInfo;      

    public ProductoFacadeREST() {
        super(Producto.class);
    }

    /**
     * @api {post} /productos Crea un nuevo Producto
     * @apiExample {curl} Ejemplo de uso:
     *     curl -d @data.json -H "Content-Type: application/json" -X POST -d http://localhost:8080/restSecurity-1.0.0-SNAPSHOT/rest/productos
     * @apiDescription Método para insertar un Producto nuevo
     * Instancia un objeto Producto (prod) y setea los valores de la entidad recibida como parámetro en el Producto instanciado.
     * Posteriormente lo crea con el método local create(prod).
     * Finalmente, construye la respuesta según la operación haya sido exitosa o no.
     * En caso de ser exitosa, agrega a la respuesta la uri de acceso al Producto creado.
     * @apiVersion 0.1.0
     * @apiName PostProducto
     * @apiGroup Productos
     *
     * @apiParam {ar.gob.ambiente.rest.paqsecurityrest.Producto} entity clase de la dependencia paqSecurityRest que emula al Producto a crear.
     *
     * @apiSuccess {String} uri Uri de acceso mediante GET al Producto creado.
     *
     * @apiError ProductoNoCreado El Producto no fue creado.
     *
     * @apiErrorExample Respuesta de error:
     *     HTTP/1.1 400 Bad Request
     *     {
     *       "error": "Hubo un error procesado la inserción del producto en el servidor."
     *     }
     */
    @POST
    @Secured
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(ar.gob.ambiente.rest.paqsecurityrest.Producto entity) {
        try{
            // instancio el producto a persistir
            Producto prod = new Producto();
            // seteo el producto con los valores de la entidad recibida
            prod.setClase(entity.getClase());
            prod.setNombreCientifico(entity.getNombreCientifico());
            prod.setNombreVulgar(entity.getNombreVulgar());
            prod.setTotal(entity.getTotal());
            prod.setUnidad(entity.getUnidad());
            // persisto el producto
            this.create(prod);
            // armo la respuesta exitosa
            UriBuilder uriBuilder = uriInfo.getRequestUriBuilder();
            URI uri = uriBuilder.path(prod.getId().toString()).build();
            return Response.created(uri).build();
            
        }catch(IllegalArgumentException | UriBuilderException ex){
            // armo la respuesta de error
            return Response.status(400).entity("Hubo un error procesado la inserción del producto. " + ex.getMessage()).build();
        }
    }

    /**
     * @api {put} /productos/:id Actualiza un Producto existente
     * @apiExample {curl} Ejemplo de uso:
     *     curl -d @data.json -H "Content-Type: application/json" -X PUT -d http://localhost:8080/restSecurity-1.0.0-SNAPSHOT/rest/productos/:id
     * @apiDescription Método para editar un Producto existente
     * Se pueden editar todos los campos del Producto.
     * Obtiene el Producto (prod) según el id recibido con el método local find(Long id),
     * setea los valores de la entidad recibida como parámetro en el Producto obtenido
     * y posteriormente lo edita con el método local edit(prod).
     * Finalmente, construye la respuesta según la operación haya sido exitosa o no.
     * @apiVersion 0.1.0
     * @apiName PutProducto
     * @apiGroup Productos
     *
     * @apiParam {Long} id ID único que del Producto a editar
     * @apiParam {ar.gob.ambiente.rest.paqsecurityrest.Producto} entity clase de la dependencia paqSecurityRest que emula al Producto a editar.
     *
     * @apiSuccess {String} Ok El Productos se actualizó con éxito
     *
     * @apiError ProductoNoModificado El Producto no fue modificado.
     * 
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "uri": "http://localhost:8080/restSecurity-1.0.0-SNAPSHOT/rest/productos/:id"
     *     }
     *
     * @apiErrorExample Respuesta de error:
     *     HTTP/1.1 304 Not Modified
     *     {
     *       "error": "Hubo un error procesado la actualización del Producto en el servidor."
     *     }
     */
    @PUT
    @Path("{id}")
    @Secured
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Long id, ar.gob.ambiente.rest.paqsecurityrest.Producto entity) {
        try{
            // obtengo el producto a actualizar
            Producto prod = this.find(id);
            // actualizo el producto con los datos recibidos en la entity
            prod.setClase(entity.getClase());
            prod.setNombreCientifico(entity.getNombreCientifico());
            prod.setNombreVulgar(entity.getNombreVulgar());
            prod.setTotal(entity.getTotal());
            prod.setUnidad(entity.getUnidad());
            // actualizo el producto
            this.edit(prod);
            // armo la respuesta exitosa
            return Response.ok().build();
        }catch(IllegalArgumentException | UriBuilderException ex){
            // armo la respuesta de error
            return Response.notModified().entity("Hubo un error procesado la actualización en el Componente de Trazabilidad: " + ex.getMessage()).build();
        }
    }

    /**
     * @api {get} /productos/:id Obtiene un Producto con la id recibida
     * @apiExample {curl} Ejemplo de uso:
     *     curl -i -H "Content-Type: application/json" -X GET -d http://localhost:8080/restSecurity-1.0.0-SNAPSHOT/rest/productos/1
     * @apiVersion 0.1.0
     * @apiName GetProducto
     * @apiGroup Productos
     *
     * @apiDescription Método para obtener un Producto existente
     * Obtiene los productos mediante el método local find(id)
     * 
     * @apiSuccess {ar.gob.ambiente.rest.paqsecurityrest.Producto} Producto  Detalle del producto registrado.
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       {
     *          "clase": "ROLLO",
     *          "id": "1",
     *          "nombreCientifico": "Cordia trichotoma",
     *          "nombreVulgar": "PETIRIBI",
     *          "total": "123.0",
     *          "unidad": "Tn",
     *       }
     *     }
     *
     * @apiError ProductoNotFound No existe producto registrado con ese id.
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 400 Not Found
     *     {
     *       "error": "No hay producto registrado con el id recibido"
     *     }
     */     
    @GET
    @Path("{id}")
    @Secured
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Producto find(@PathParam("id") Long id) {
        return super.find(id);
    }

    /**
     * @api {get} /productos Obtiene un listado con todos los Productos registrados
     * @apiExample {curl} Ejemplo de uso:
     *     curl -i -H "Content-Type: application/json" -X GET -d http://localhost:8080/restSecurity-1.0.0-SNAPSHOT/rest/productos
     * @apiVersion 0.1.0
     * @apiName GetProductos
     * @apiGroup Productos
     *
     * @apiDescription Método para obtener un listado de los Productos existentes
     * Obtiene los productos mediante el método local findAll()
     * 
     * @apiSuccess {ar.gob.ambiente.rest.paqsecurityrest.Producto} Productos Listado con todos los Productos registrados.
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "productos": [
     *          {"clase": "ROLLO",
     *              "id": "1",
     *              "nombreCientifico": "Cordia trichotoma",
     *              "nombreVulgar": "PETIRIBI",
     *              "total": "123.0",
     *              "unidad": "Tn"},
     *          {"clase": "ROLLO",
     *              "id": "2",
     *              "nombreCientifico": "Phyllostylon rhamnoides",
     *              "nombreVulgar": "RAMONES",
     *              "total": "521.0",
     *              "unidad": "Tn"}
     *          ]
     *     }
     *
     * @apiError ProductosNotFound No existen producto registrados.
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 400 Not Found
     *     {
     *       "error": "No hay Productos registrados"
     *     }
     */        
    @GET
    @Override
    @Secured
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Producto> findAll() {
        return super.findAll();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
