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
import co.edu.uniandes.csw.artwork.api.ICreditCardLogic;
import co.edu.uniandes.csw.artwork.dtos.detail.CreditCardDetailDTO;
import co.edu.uniandes.csw.artwork.entities.CreditCardEntity;
import java.util.ArrayList;
import javax.ws.rs.WebApplicationException;

/**
 * URI: clients/{clientsId: \\d+}/creditCard
 * @generated
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CreditCardResource {

    @Inject private ICreditCardLogic creditCardLogic;
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("limit") private Integer maxRecords;
    @PathParam("clientsId") private Long clientsId;

   
    /**
     * Convierte una lista de CreditCardEntity a una lista de CreditCardDetailDTO
     *
     * @param entityList Lista de CreditCardEntity a convertir
     * @return Lista de CreditCardDetailDTO convertida
     * @generated
     */
    private List<CreditCardDetailDTO> listEntity2DTO(List<CreditCardEntity> entityList){
        List<CreditCardDetailDTO> list = new ArrayList<>();
        for (CreditCardEntity entity : entityList) {
            list.add(new CreditCardDetailDTO(entity));
        }
        return list;
    }


    /**
     * Obtiene la lista de los registros de CreditCard asociados a un Client
     *
     * @return Colección de objetos de CreditCardDetailDTO
     * @generated
     */
    @GET
    public List<CreditCardDetailDTO> getCreditCards() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", creditCardLogic.countCreditCards());
            return listEntity2DTO(creditCardLogic.getCreditCards(page, maxRecords, clientsId));
        }
        return listEntity2DTO(creditCardLogic.getCreditCards(clientsId));
    }

    /**
     * Obtiene los datos de una instancia de CreditCard a partir de su ID asociado a un Client
     *
     * @param creditCardId Identificador de la instancia a consultar
     * @return Instancia de CreditCardDetailDTO con los datos del CreditCard consultado
     * @generated
     */
    @GET
    @Path("{creditCardId: \\d+}")
    public CreditCardDetailDTO getCreditCard(@PathParam("creditCardId") Long creditCardId) {
        CreditCardEntity entity = creditCardLogic.getCreditCard(creditCardId);
        if (entity.getClient() != null && !clientsId.equals(entity.getClient().getId())) {
            throw new WebApplicationException(404);
        }
        return new CreditCardDetailDTO(entity);
    }

    /**
     * Asocia un CreditCard existente a un Client
     *
     * @param dto Objeto de CreditCardDetailDTO con los datos nuevos
     * @return Objeto de CreditCardDetailDTOcon los datos nuevos y su ID.
     * @generated
     */
    @POST
    @StatusCreated
    public CreditCardDetailDTO createCreditCard(CreditCardDetailDTO dto) {
        return new CreditCardDetailDTO(creditCardLogic.createCreditCard(clientsId, dto.toEntity()));
    }

    /**
     * Actualiza la información de una instancia de CreditCard.
     *
     * @param creditCardId Identificador de la instancia de CreditCard a modificar
     * @param dto Instancia de CreditCardDetailDTO con los nuevos datos.
     * @return Instancia de CreditCardDetailDTO con los datos actualizados.
     * @generated
     */
    @PUT
    @Path("{creditCardId: \\d+}")
    public CreditCardDetailDTO updateCreditCard(@PathParam("creditCardId") Long creditCardId, CreditCardDetailDTO dto) {
        CreditCardEntity entity = dto.toEntity();
        entity.setId(creditCardId);
        return new CreditCardDetailDTO(creditCardLogic.updateCreditCard(clientsId, entity));
    }

    /**
     * Elimina una instancia de CreditCard de la base de datos.
     *
     * @param creditCardId Identificador de la instancia a eliminar.
     * @generated
     */
    @DELETE
    @Path("creditCardId: \\d+}")
    public void deleteCreditCard(@PathParam("creditCardId") Long creditCardId) {
        creditCardLogic.deleteCreditCard(creditCardId);
    }
    
}
