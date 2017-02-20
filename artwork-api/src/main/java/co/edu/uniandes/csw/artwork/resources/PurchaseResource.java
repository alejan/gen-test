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
import co.edu.uniandes.csw.artwork.api.IPurchaseLogic;
import co.edu.uniandes.csw.artwork.dtos.detail.PurchaseDetailDTO;
import co.edu.uniandes.csw.artwork.entities.PurchaseEntity;
import java.util.ArrayList;
import javax.ws.rs.WebApplicationException;

/**
 * URI: clients/{clientsId: \\d+}/purchase
 * @generated
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PurchaseResource {

    @Inject private IPurchaseLogic purchaseLogic;
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("limit") private Integer maxRecords;
    @PathParam("clientsId") private Long clientsId;

   
    /**
     * Convierte una lista de PurchaseEntity a una lista de PurchaseDetailDTO
     *
     * @param entityList Lista de PurchaseEntity a convertir
     * @return Lista de PurchaseDetailDTO convertida
     * @generated
     */
    private List<PurchaseDetailDTO> listEntity2DTO(List<PurchaseEntity> entityList){
        List<PurchaseDetailDTO> list = new ArrayList<>();
        for (PurchaseEntity entity : entityList) {
            list.add(new PurchaseDetailDTO(entity));
        }
        return list;
    }


    /**
     * Obtiene la lista de los registros de Purchase asociados a un Client
     *
     * @return Colección de objetos de PurchaseDetailDTO
     * @generated
     */
    @GET
    public List<PurchaseDetailDTO> getPurchases() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", purchaseLogic.countPurchases());
            return listEntity2DTO(purchaseLogic.getPurchases(page, maxRecords, clientsId));
        }
        return listEntity2DTO(purchaseLogic.getPurchases(clientsId));
    }

    /**
     * Obtiene los datos de una instancia de Purchase a partir de su ID asociado a un Client
     *
     * @param purchaseId Identificador de la instancia a consultar
     * @return Instancia de PurchaseDetailDTO con los datos del Purchase consultado
     * @generated
     */
    @GET
    @Path("{purchaseId: \\d+}")
    public PurchaseDetailDTO getPurchase(@PathParam("purchaseId") Long purchaseId) {
        PurchaseEntity entity = purchaseLogic.getPurchase(purchaseId);
        if (entity.getClient() != null && !clientsId.equals(entity.getClient().getId())) {
            throw new WebApplicationException(404);
        }
        return new PurchaseDetailDTO(entity);
    }

    /**
     * Asocia un Purchase existente a un Client
     *
     * @param dto Objeto de PurchaseDetailDTO con los datos nuevos
     * @return Objeto de PurchaseDetailDTOcon los datos nuevos y su ID.
     * @generated
     */
    @POST
    @StatusCreated
    public PurchaseDetailDTO createPurchase(PurchaseDetailDTO dto) {
        return new PurchaseDetailDTO(purchaseLogic.createPurchase(clientsId, dto.toEntity()));
    }

    /**
     * Actualiza la información de una instancia de Purchase.
     *
     * @param purchaseId Identificador de la instancia de Purchase a modificar
     * @param dto Instancia de PurchaseDetailDTO con los nuevos datos.
     * @return Instancia de PurchaseDetailDTO con los datos actualizados.
     * @generated
     */
    @PUT
    @Path("{purchaseId: \\d+}")
    public PurchaseDetailDTO updatePurchase(@PathParam("purchaseId") Long purchaseId, PurchaseDetailDTO dto) {
        PurchaseEntity entity = dto.toEntity();
        entity.setId(purchaseId);
        return new PurchaseDetailDTO(purchaseLogic.updatePurchase(clientsId, entity));
    }

    /**
     * Elimina una instancia de Purchase de la base de datos.
     *
     * @param purchaseId Identificador de la instancia a eliminar.
     * @generated
     */
    @DELETE
    @Path("purchaseId: \\d+}")
    public void deletePurchase(@PathParam("purchaseId") Long purchaseId) {
        purchaseLogic.deletePurchase(purchaseId);
    }
    
}
