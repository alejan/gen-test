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
import co.edu.uniandes.csw.artwork.api.IAwardLogic;
import co.edu.uniandes.csw.artwork.dtos.detail.AwardDetailDTO;
import co.edu.uniandes.csw.artwork.entities.AwardEntity;
import java.util.ArrayList;
import javax.ws.rs.WebApplicationException;

/**
 * URI: artists/{artistsId: \\d+}/artwork/{artworkId: \\d+}/award
 * @generated
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AwardResource {

    @Inject private IAwardLogic awardLogic;
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("limit") private Integer maxRecords;
    @PathParam("artworkId") private Long artworkId;

   
    /**
     * Convierte una lista de AwardEntity a una lista de AwardDetailDTO
     *
     * @param entityList Lista de AwardEntity a convertir
     * @return Lista de AwardDetailDTO convertida
     * @generated
     */
    private List<AwardDetailDTO> listEntity2DTO(List<AwardEntity> entityList){
        List<AwardDetailDTO> list = new ArrayList<>();
        for (AwardEntity entity : entityList) {
            list.add(new AwardDetailDTO(entity));
        }
        return list;
    }


    /**
     * Obtiene la lista de los registros de Award asociados a un Artwork
     *
     * @return Colección de objetos de AwardDetailDTO
     * @generated
     */
    @GET
    public List<AwardDetailDTO> getAwards() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", awardLogic.countAwards());
            return listEntity2DTO(awardLogic.getAwards(page, maxRecords, artworkId));
        }
        return listEntity2DTO(awardLogic.getAwards(artworkId));
    }

    /**
     * Obtiene los datos de una instancia de Award a partir de su ID asociado a un Artwork
     *
     * @param awardId Identificador de la instancia a consultar
     * @return Instancia de AwardDetailDTO con los datos del Award consultado
     * @generated
     */
    @GET
    @Path("{awardId: \\d+}")
    public AwardDetailDTO getAward(@PathParam("awardId") Long awardId) {
        AwardEntity entity = awardLogic.getAward(awardId);
        if (entity.getArtwork() != null && !artworkId.equals(entity.getArtwork().getId())) {
            throw new WebApplicationException(404);
        }
        return new AwardDetailDTO(entity);
    }

    /**
     * Asocia un Award existente a un Artwork
     *
     * @param dto Objeto de AwardDetailDTO con los datos nuevos
     * @return Objeto de AwardDetailDTOcon los datos nuevos y su ID.
     * @generated
     */
    @POST
    @StatusCreated
    public AwardDetailDTO createAward(AwardDetailDTO dto) {
        return new AwardDetailDTO(awardLogic.createAward(artworkId, dto.toEntity()));
    }

    /**
     * Actualiza la información de una instancia de Award.
     *
     * @param awardId Identificador de la instancia de Award a modificar
     * @param dto Instancia de AwardDetailDTO con los nuevos datos.
     * @return Instancia de AwardDetailDTO con los datos actualizados.
     * @generated
     */
    @PUT
    @Path("{awardId: \\d+}")
    public AwardDetailDTO updateAward(@PathParam("awardId") Long awardId, AwardDetailDTO dto) {
        AwardEntity entity = dto.toEntity();
        entity.setId(awardId);
        return new AwardDetailDTO(awardLogic.updateAward(artworkId, entity));
    }

    /**
     * Elimina una instancia de Award de la base de datos.
     *
     * @param awardId Identificador de la instancia a eliminar.
     * @generated
     */
    @DELETE
    @Path("awardId: \\d+}")
    public void deleteAward(@PathParam("awardId") Long awardId) {
        awardLogic.deleteAward(awardId);
    }
    
}
