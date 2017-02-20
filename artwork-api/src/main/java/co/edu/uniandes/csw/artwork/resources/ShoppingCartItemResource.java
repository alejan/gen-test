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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import co.edu.uniandes.csw.artwork.api.IShoppingCartLogic;
import co.edu.uniandes.csw.artwork.dtos.detail.ItemDetailDTO;
import co.edu.uniandes.csw.artwork.entities.ItemEntity;
import java.util.ArrayList;
/**
 * URI: shoppingCarts/{shoppingCartsId: \\d+}/item
 * @generated
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ShoppingCartItemResource {

    @Inject private IShoppingCartLogic shoppingCartLogic;
    @Context private HttpServletResponse response;

    /**
     * Convierte una lista de ItemEntity a una lista de ItemDetailDTO.
     *
     * @param entityList Lista de ItemEntity a convertir.
     * @return Lista de ItemDetailDTO convertida.
     * @generated
     */
    private List<ItemDetailDTO> itemListEntity2DTO(List<ItemEntity> entityList){
        List<ItemDetailDTO> list = new ArrayList<>();
        for (ItemEntity entity : entityList) {
            list.add(new ItemDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de ItemDetailDTO a una lista de ItemEntity.
     *
     * @param dtos Lista de ItemDetailDTO a convertir.
     * @return Lista de ItemEntity convertida.
     * @generated
     */
    private List<ItemEntity> itemListDTO2Entity(List<ItemDetailDTO> dtos){
        List<ItemEntity> list = new ArrayList<>();
        for (ItemDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }

    /**
     * Obtiene una colección de instancias de ItemDetailDTO asociadas a una
     * instancia de ShoppingCart
     *
     * @param shoppingCartsId Identificador de la instancia de ShoppingCart
     * @return Colección de instancias de ItemDetailDTO asociadas a la instancia de ShoppingCart
     * @generated
     */
    @GET
    public List<ItemDetailDTO> listItem(@PathParam("shoppingCartsId") Long shoppingCartsId) {
        return itemListEntity2DTO(shoppingCartLogic.listItem(shoppingCartsId));
    }

    /**
     * Obtiene una instancia de Item asociada a una instancia de ShoppingCart
     *
     * @param shoppingCartsId Identificador de la instancia de ShoppingCart
     * @param itemId Identificador de la instancia de Item
     * @generated
     */
    @GET
    @Path("{itemId: \\d+}")
    public ItemDetailDTO getItem(@PathParam("shoppingCartsId") Long shoppingCartsId, @PathParam("itemId") Long itemId) {
        return new ItemDetailDTO(shoppingCartLogic.getItem(shoppingCartsId, itemId));
    }

    /**
     * Asocia un Item existente a un ShoppingCart
     *
     * @param shoppingCartsId Identificador de la instancia de ShoppingCart
     * @param itemId Identificador de la instancia de Item
     * @return Instancia de ItemDetailDTO que fue asociada a ShoppingCart
     * @generated
     */
    @POST
    @Path("{itemId: \\d+}")
    public ItemDetailDTO addItem(@PathParam("shoppingCartsId") Long shoppingCartsId, @PathParam("itemId") Long itemId) {
        return new ItemDetailDTO(shoppingCartLogic.addItem(shoppingCartsId, itemId));
    }

    /**
     * Remplaza las instancias de Item asociadas a una instancia de ShoppingCart
     *
     * @param shoppingCartsId Identificador de la instancia de ShoppingCart
     * @param items Colección de instancias de ItemDTO a asociar a instancia de ShoppingCart
     * @return Nueva colección de ItemDTO asociada a la instancia de ShoppingCart
     * @generated
     */
    @PUT
    public List<ItemDetailDTO> replaceItem(@PathParam("shoppingCartsId") Long shoppingCartsId, List<ItemDetailDTO> items) {
        return itemListEntity2DTO(shoppingCartLogic.replaceItem(shoppingCartsId, itemListDTO2Entity(items)));
    }

    /**
     * Desasocia un Item existente de un ShoppingCart existente
     *
     * @param shoppingCartsId Identificador de la instancia de ShoppingCart
     * @param itemId Identificador de la instancia de Item
     * @generated
     */
    @DELETE
    @Path("{itemId: \\d+}")
    public void removeItem(@PathParam("shoppingCartsId") Long shoppingCartsId, @PathParam("itemId") Long itemId) {
        shoppingCartLogic.removeItem(shoppingCartsId, itemId);
    }
}
