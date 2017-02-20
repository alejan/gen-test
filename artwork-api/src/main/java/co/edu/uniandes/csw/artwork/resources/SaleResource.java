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
import co.edu.uniandes.csw.artwork.api.ISaleLogic;
import co.edu.uniandes.csw.artwork.dtos.detail.SaleDetailDTO;
import co.edu.uniandes.csw.artwork.entities.SaleEntity;
import java.util.ArrayList;
import javax.ws.rs.WebApplicationException;

/**
 * URI: artists/{artistsId: \\d+}/sale
 * @generated
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SaleResource {

    @Inject private ISaleLogic saleLogic;
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("limit") private Integer maxRecords;
    @PathParam("artistsId") private Long artistsId;

   
    /**
     * Convierte una lista de SaleEntity a una lista de SaleDetailDTO
     *
     * @param entityList Lista de SaleEntity a convertir
     * @return Lista de SaleDetailDTO convertida
     * @generated
     */
    private List<SaleDetailDTO> listEntity2DTO(List<SaleEntity> entityList){
        List<SaleDetailDTO> list = new ArrayList<>();
        for (SaleEntity entity : entityList) {
            list.add(new SaleDetailDTO(entity));
        }
        return list;
    }


    /**
     * Obtiene la lista de los registros de Sale asociados a un Artist
     *
     * @return Colección de objetos de SaleDetailDTO
     * @generated
     */
    @GET
    public List<SaleDetailDTO> getSales() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", saleLogic.countSales());
            return listEntity2DTO(saleLogic.getSales(page, maxRecords, artistsId));
        }
        return listEntity2DTO(saleLogic.getSales(artistsId));
    }

    /**
     * Obtiene los datos de una instancia de Sale a partir de su ID asociado a un Artist
     *
     * @param saleId Identificador de la instancia a consultar
     * @return Instancia de SaleDetailDTO con los datos del Sale consultado
     * @generated
     */
    @GET
    @Path("{saleId: \\d+}")
    public SaleDetailDTO getSale(@PathParam("saleId") Long saleId) {
        SaleEntity entity = saleLogic.getSale(saleId);
        if (entity.getArtist() != null && !artistsId.equals(entity.getArtist().getId())) {
            throw new WebApplicationException(404);
        }
        return new SaleDetailDTO(entity);
    }

    /**
     * Asocia un Sale existente a un Artist
     *
     * @param dto Objeto de SaleDetailDTO con los datos nuevos
     * @return Objeto de SaleDetailDTOcon los datos nuevos y su ID.
     * @generated
     */
    @POST
    @StatusCreated
    public SaleDetailDTO createSale(SaleDetailDTO dto) {
        return new SaleDetailDTO(saleLogic.createSale(artistsId, dto.toEntity()));
    }

    /**
     * Actualiza la información de una instancia de Sale.
     *
     * @param saleId Identificador de la instancia de Sale a modificar
     * @param dto Instancia de SaleDetailDTO con los nuevos datos.
     * @return Instancia de SaleDetailDTO con los datos actualizados.
     * @generated
     */
    @PUT
    @Path("{saleId: \\d+}")
    public SaleDetailDTO updateSale(@PathParam("saleId") Long saleId, SaleDetailDTO dto) {
        SaleEntity entity = dto.toEntity();
        entity.setId(saleId);
        return new SaleDetailDTO(saleLogic.updateSale(artistsId, entity));
    }

    /**
     * Elimina una instancia de Sale de la base de datos.
     *
     * @param saleId Identificador de la instancia a eliminar.
     * @generated
     */
    @DELETE
    @Path("saleId: \\d+}")
    public void deleteSale(@PathParam("saleId") Long saleId) {
        saleLogic.deleteSale(saleId);
    }
    
}
