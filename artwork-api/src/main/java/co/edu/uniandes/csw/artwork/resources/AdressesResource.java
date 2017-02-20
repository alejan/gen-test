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
import co.edu.uniandes.csw.artwork.api.IAdressLogic;
import co.edu.uniandes.csw.artwork.dtos.detail.AdressDetailDTO;
import co.edu.uniandes.csw.artwork.entities.AdressEntity;
import java.util.ArrayList;
import javax.ws.rs.WebApplicationException;

/**
 * URI: clients/{clientsId: \\d+}/adresses
 * @generated
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AdressesResource {

    @Inject private IAdressLogic adressLogic;
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("limit") private Integer maxRecords;
    @PathParam("clientsId") private Long clientsId;

   
    /**
     * Convierte una lista de AdressEntity a una lista de AdressDetailDTO
     *
     * @param entityList Lista de AdressEntity a convertir
     * @return Lista de AdressDetailDTO convertida
     * @generated
     */
    private List<AdressDetailDTO> listEntity2DTO(List<AdressEntity> entityList){
        List<AdressDetailDTO> list = new ArrayList<>();
        for (AdressEntity entity : entityList) {
            list.add(new AdressDetailDTO(entity));
        }
        return list;
    }


    /**
     * Obtiene la lista de los registros de Adress asociados a un Client
     *
     * @return Colección de objetos de AdressDetailDTO
     * @generated
     */
    @GET
    public List<AdressDetailDTO> getAdressess() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", adressLogic.countAdresss());
            return listEntity2DTO(adressLogic.getAdresss(page, maxRecords, clientsId));
        }
        return listEntity2DTO(adressLogic.getAdresss(clientsId));
    }

    /**
     * Obtiene los datos de una instancia de Adress a partir de su ID asociado a un Client
     *
     * @param adressesId Identificador de la instancia a consultar
     * @return Instancia de AdressDetailDTO con los datos del Adress consultado
     * @generated
     */
    @GET
    @Path("{adressesId: \\d+}")
    public AdressDetailDTO getAdresses(@PathParam("adressesId") Long adressesId) {
        AdressEntity entity = adressLogic.getAdress(adressesId);
        if (entity.getClient() != null && !clientsId.equals(entity.getClient().getId())) {
            throw new WebApplicationException(404);
        }
        return new AdressDetailDTO(entity);
    }

    /**
     * Asocia un Adress existente a un Client
     *
     * @param dto Objeto de AdressDetailDTO con los datos nuevos
     * @return Objeto de AdressDetailDTOcon los datos nuevos y su ID.
     * @generated
     */
    @POST
    @StatusCreated
    public AdressDetailDTO createAdresses(AdressDetailDTO dto) {
        return new AdressDetailDTO(adressLogic.createAdress(clientsId, dto.toEntity()));
    }

    /**
     * Actualiza la información de una instancia de Adress.
     *
     * @param adressesId Identificador de la instancia de Adress a modificar
     * @param dto Instancia de AdressDetailDTO con los nuevos datos.
     * @return Instancia de AdressDetailDTO con los datos actualizados.
     * @generated
     */
    @PUT
    @Path("{adressesId: \\d+}")
    public AdressDetailDTO updateAdresses(@PathParam("adressesId") Long adressesId, AdressDetailDTO dto) {
        AdressEntity entity = dto.toEntity();
        entity.setId(adressesId);
        return new AdressDetailDTO(adressLogic.updateAdress(clientsId, entity));
    }

    /**
     * Elimina una instancia de Adress de la base de datos.
     *
     * @param adressId Identificador de la instancia a eliminar.
     * @generated
     */
    @DELETE
    @Path("adressesId: \\d+}")
    public void deleteAdresses(@PathParam("adressesId") Long adressesId) {
        adressLogic.deleteAdress(adressesId);
    }
    
}
