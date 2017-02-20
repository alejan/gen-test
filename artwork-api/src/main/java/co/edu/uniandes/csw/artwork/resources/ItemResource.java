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
import co.edu.uniandes.csw.artwork.api.IItemLogic;
import co.edu.uniandes.csw.artwork.dtos.detail.ItemDetailDTO;
import co.edu.uniandes.csw.artwork.entities.ItemEntity;
import java.util.ArrayList;
import javax.ws.rs.WebApplicationException;

/**
 * URI: items/
 * @generated
 */
@Path("/items")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ItemResource {

    @Inject private IItemLogic itemLogic;
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("limit") private Integer maxRecords;

   
    /**
     * Convierte una lista de ItemEntity a una lista de ItemDetailDTO.
     *
     * @param entityList Lista de ItemEntity a convertir.
     * @return Lista de ItemDetailDTO convertida.
     * @generated
     */
    private List<ItemDetailDTO> listEntity2DTO(List<ItemEntity> entityList){
        List<ItemDetailDTO> list = new ArrayList<>();
        for (ItemEntity entity : entityList) {
            list.add(new ItemDetailDTO(entity));
        }
        return list;
    }


    /**
     * Obtiene la lista de los registros de Item
     *
     * @return Colección de objetos de ItemDetailDTO
     * @generated
     */
    @GET
    public List<ItemDetailDTO> getItems() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", itemLogic.countItems());
            return listEntity2DTO(itemLogic.getItems(page, maxRecords));
        }
        return listEntity2DTO(itemLogic.getItems());
    }

    /**
     * Obtiene los datos de una instancia de Item a partir de su ID
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de ItemDetailDTO con los datos del Item consultado
     * @generated
     */
    @GET
    @Path("{id: \\d+}")
    public ItemDetailDTO getItem(@PathParam("id") Long id) {
        return new ItemDetailDTO(itemLogic.getItem(id));
    }

    /**
     * Se encarga de crear un Item en la base de datos
     *
     * @param dto Objeto de ItemDetailDTO con los datos nuevos
     * @return Objeto de ItemDetailDTOcon los datos nuevos y su ID
     * @generated
     */
    @POST
    @StatusCreated
    public ItemDetailDTO createItem(ItemDetailDTO dto) {
        return new ItemDetailDTO(itemLogic.createItem(dto.toEntity()));
    }

    /**
     * Actualiza la información de una instancia de Item
     *
     * @param id Identificador de la instancia de Item a modificar
     * @param dto Instancia de ItemDetailDTO con los nuevos datos
     * @return Instancia de ItemDetailDTO con los datos actualizados
     * @generated
     */
    @PUT
    @Path("{id: \\d+}")
    public ItemDetailDTO updateItem(@PathParam("id") Long id, ItemDetailDTO dto) {
        ItemEntity entity = dto.toEntity();
        entity.setId(id);
        return new ItemDetailDTO(itemLogic.updateItem(entity));
    }

    /**
     * Elimina una instancia de Item de la base de datos
     *
     * @param id Identificador de la instancia a eliminar
     * @generated
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteItem(@PathParam("id") Long id) {
        itemLogic.deleteItem(id);
    }
    
}
