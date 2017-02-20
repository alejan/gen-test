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
import co.edu.uniandes.csw.artwork.api.IOrderLogic;
import co.edu.uniandes.csw.artwork.dtos.detail.OrderDetailDTO;
import co.edu.uniandes.csw.artwork.entities.OrderEntity;
import java.util.ArrayList;
import javax.ws.rs.WebApplicationException;

/**
 * URI: shoppingCarts/{shoppingCartsId: \\d+}/order
 * @generated
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject private IOrderLogic orderLogic;
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("limit") private Integer maxRecords;
    @PathParam("shoppingCartsId") private Long shoppingCartsId;

   
    /**
     * Convierte una lista de OrderEntity a una lista de OrderDetailDTO
     *
     * @param entityList Lista de OrderEntity a convertir
     * @return Lista de OrderDetailDTO convertida
     * @generated
     */
    private List<OrderDetailDTO> listEntity2DTO(List<OrderEntity> entityList){
        List<OrderDetailDTO> list = new ArrayList<>();
        for (OrderEntity entity : entityList) {
            list.add(new OrderDetailDTO(entity));
        }
        return list;
    }


    /**
     * Obtiene la lista de los registros de Order asociados a un ShoppingCart
     *
     * @return Colección de objetos de OrderDetailDTO
     * @generated
     */
    @GET
    public List<OrderDetailDTO> getOrders() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", orderLogic.countOrders());
            return listEntity2DTO(orderLogic.getOrders(page, maxRecords, shoppingCartsId));
        }
        return listEntity2DTO(orderLogic.getOrders(shoppingCartsId));
    }

    /**
     * Obtiene los datos de una instancia de Order a partir de su ID asociado a un ShoppingCart
     *
     * @param orderId Identificador de la instancia a consultar
     * @return Instancia de OrderDetailDTO con los datos del Order consultado
     * @generated
     */
    @GET
    @Path("{orderId: \\d+}")
    public OrderDetailDTO getOrder(@PathParam("orderId") Long orderId) {
        OrderEntity entity = orderLogic.getOrder(orderId);
        if (entity.getShoppingCart() != null && !shoppingCartsId.equals(entity.getShoppingCart().getId())) {
            throw new WebApplicationException(404);
        }
        return new OrderDetailDTO(entity);
    }

    /**
     * Asocia un Order existente a un ShoppingCart
     *
     * @param dto Objeto de OrderDetailDTO con los datos nuevos
     * @return Objeto de OrderDetailDTOcon los datos nuevos y su ID.
     * @generated
     */
    @POST
    @StatusCreated
    public OrderDetailDTO createOrder(OrderDetailDTO dto) {
        return new OrderDetailDTO(orderLogic.createOrder(shoppingCartsId, dto.toEntity()));
    }

    /**
     * Actualiza la información de una instancia de Order.
     *
     * @param orderId Identificador de la instancia de Order a modificar
     * @param dto Instancia de OrderDetailDTO con los nuevos datos.
     * @return Instancia de OrderDetailDTO con los datos actualizados.
     * @generated
     */
    @PUT
    @Path("{orderId: \\d+}")
    public OrderDetailDTO updateOrder(@PathParam("orderId") Long orderId, OrderDetailDTO dto) {
        OrderEntity entity = dto.toEntity();
        entity.setId(orderId);
        return new OrderDetailDTO(orderLogic.updateOrder(shoppingCartsId, entity));
    }

    /**
     * Elimina una instancia de Order de la base de datos.
     *
     * @param orderId Identificador de la instancia a eliminar.
     * @generated
     */
    @DELETE
    @Path("orderId: \\d+}")
    public void deleteOrder(@PathParam("orderId") Long orderId) {
        orderLogic.deleteOrder(orderId);
    }
    
}
