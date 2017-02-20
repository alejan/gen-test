/*
The MIT License (MIT)

Copyright (c) 2015 Los Andes University

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package co.edu.uniandes.csw.artwork.resources;

import co.edu.uniandes.csw.auth.provider.StatusCreated;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import co.edu.uniandes.csw.artwork.api.IShoppingCartLogic;
import co.edu.uniandes.csw.artwork.dtos.detail.ShoppingCartDetailDTO;
import co.edu.uniandes.csw.artwork.entities.ShoppingCartEntity;
import java.util.ArrayList;
import javax.ws.rs.WebApplicationException;

/**
 * URI: shoppingCarts/
 * @generated
 */
@Path("/shoppingCarts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ShoppingCartResource {

    @Inject private IShoppingCartLogic shoppingCartLogic;
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("limit") private Integer maxRecords;

   
    /**
     * Convierte una lista de ShoppingCartEntity a una lista de ShoppingCartDetailDTO.
     *
     * @param entityList Lista de ShoppingCartEntity a convertir.
     * @return Lista de ShoppingCartDetailDTO convertida.
     * @generated
     */
    private List<ShoppingCartDetailDTO> listEntity2DTO(List<ShoppingCartEntity> entityList){
        List<ShoppingCartDetailDTO> list = new ArrayList<>();
        for (ShoppingCartEntity entity : entityList) {
            list.add(new ShoppingCartDetailDTO(entity));
        }
        return list;
    }


    /**
     * Obtiene la lista de los registros de ShoppingCart
     *
     * @return Colección de objetos de ShoppingCartDetailDTO
     * @generated
     */
    @GET
    public List<ShoppingCartDetailDTO> getShoppingCarts() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", shoppingCartLogic.countShoppingCarts());
            return listEntity2DTO(shoppingCartLogic.getShoppingCarts(page, maxRecords));
        }
        return listEntity2DTO(shoppingCartLogic.getShoppingCarts());
    }

    /**
     * Obtiene los datos de una instancia de ShoppingCart a partir de su ID
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de ShoppingCartDetailDTO con los datos del ShoppingCart consultado
     * @generated
     */
    @GET
    @Path("{id: \\d+}")
    public ShoppingCartDetailDTO getShoppingCart(@PathParam("id") Long id) {
        return new ShoppingCartDetailDTO(shoppingCartLogic.getShoppingCart(id));
    }

    /**
     * Se encarga de crear un ShoppingCart en la base de datos
     *
     * @param dto Objeto de ShoppingCartDetailDTO con los datos nuevos
     * @return Objeto de ShoppingCartDetailDTOcon los datos nuevos y su ID
     * @generated
     */
    @POST
    @StatusCreated
    public ShoppingCartDetailDTO createShoppingCart(ShoppingCartDetailDTO dto) {
        return new ShoppingCartDetailDTO(shoppingCartLogic.createShoppingCart(dto.toEntity()));
    }

    /**
     * Actualiza la información de una instancia de ShoppingCart
     *
     * @param id Identificador de la instancia de ShoppingCart a modificar
     * @param dto Instancia de ShoppingCartDetailDTO con los nuevos datos
     * @return Instancia de ShoppingCartDetailDTO con los datos actualizados
     * @generated
     */
    @PUT
    @Path("{id: \\d+}")
    public ShoppingCartDetailDTO updateShoppingCart(@PathParam("id") Long id, ShoppingCartDetailDTO dto) {
        ShoppingCartEntity entity = dto.toEntity();
        entity.setId(id);
        ShoppingCartEntity oldEntity = shoppingCartLogic.getShoppingCart(id);
        entity.setItem(oldEntity.getItem());
        return new ShoppingCartDetailDTO(shoppingCartLogic.updateShoppingCart(entity));
    }

    /**
     * Elimina una instancia de ShoppingCart de la base de datos
     *
     * @param id Identificador de la instancia a eliminar
     * @generated
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteShoppingCart(@PathParam("id") Long id) {
        shoppingCartLogic.deleteShoppingCart(id);
    }
    public void existsShoppingCart(Long shoppingCartsId){
        ShoppingCartDetailDTO shoppingCart = getShoppingCart(shoppingCartsId);
        if (shoppingCart== null) {
            throw new WebApplicationException(404);
        }
    }
    
    
    @Path("{shoppingCartsId: \\d+}/order")
    public Class<OrderResource> getOrderResource(@PathParam("shoppingCartsId") Long shoppingCartsId){
        existsShoppingCart(shoppingCartsId);
        return OrderResource.class;
    }
    
    @Path("{shoppingCartsId: \\d+}/item")
    public Class<ShoppingCartItemResource> getShoppingCartItemResource(@PathParam("shoppingCartsId") Long shoppingCartsId){
        existsShoppingCart(shoppingCartsId);
        return ShoppingCartItemResource.class;
    }
    
}
