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
import co.edu.uniandes.csw.artwork.api.IRatingLogic;
import co.edu.uniandes.csw.artwork.dtos.detail.RatingDetailDTO;
import co.edu.uniandes.csw.artwork.entities.RatingEntity;
import java.util.ArrayList;
import javax.ws.rs.WebApplicationException;

/**
 * URI: artists/{artistsId: \\d+}/rating
 * @generated
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RatingResource {

    @Inject private IRatingLogic ratingLogic;
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("limit") private Integer maxRecords;
    @PathParam("artistsId") private Long artistsId;

   
    /**
     * Convierte una lista de RatingEntity a una lista de RatingDetailDTO
     *
     * @param entityList Lista de RatingEntity a convertir
     * @return Lista de RatingDetailDTO convertida
     * @generated
     */
    private List<RatingDetailDTO> listEntity2DTO(List<RatingEntity> entityList){
        List<RatingDetailDTO> list = new ArrayList<>();
        for (RatingEntity entity : entityList) {
            list.add(new RatingDetailDTO(entity));
        }
        return list;
    }


    /**
     * Obtiene la lista de los registros de Rating asociados a un Artist
     *
     * @return Colección de objetos de RatingDetailDTO
     * @generated
     */
    @GET
    public List<RatingDetailDTO> getRatings() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", ratingLogic.countRatings());
            return listEntity2DTO(ratingLogic.getRatings(page, maxRecords, artistsId));
        }
        return listEntity2DTO(ratingLogic.getRatings(artistsId));
    }

    /**
     * Obtiene los datos de una instancia de Rating a partir de su ID asociado a un Artist
     *
     * @param ratingId Identificador de la instancia a consultar
     * @return Instancia de RatingDetailDTO con los datos del Rating consultado
     * @generated
     */
    @GET
    @Path("{ratingId: \\d+}")
    public RatingDetailDTO getRating(@PathParam("ratingId") Long ratingId) {
        RatingEntity entity = ratingLogic.getRating(ratingId);
        if (entity.getArtist() != null && !artistsId.equals(entity.getArtist().getId())) {
            throw new WebApplicationException(404);
        }
        return new RatingDetailDTO(entity);
    }

    /**
     * Asocia un Rating existente a un Artist
     *
     * @param dto Objeto de RatingDetailDTO con los datos nuevos
     * @return Objeto de RatingDetailDTOcon los datos nuevos y su ID.
     * @generated
     */
    @POST
    @StatusCreated
    public RatingDetailDTO createRating(RatingDetailDTO dto) {
        return new RatingDetailDTO(ratingLogic.createRating(artistsId, dto.toEntity()));
    }

    /**
     * Actualiza la información de una instancia de Rating.
     *
     * @param ratingId Identificador de la instancia de Rating a modificar
     * @param dto Instancia de RatingDetailDTO con los nuevos datos.
     * @return Instancia de RatingDetailDTO con los datos actualizados.
     * @generated
     */
    @PUT
    @Path("{ratingId: \\d+}")
    public RatingDetailDTO updateRating(@PathParam("ratingId") Long ratingId, RatingDetailDTO dto) {
        RatingEntity entity = dto.toEntity();
        entity.setId(ratingId);
        return new RatingDetailDTO(ratingLogic.updateRating(artistsId, entity));
    }

    /**
     * Elimina una instancia de Rating de la base de datos.
     *
     * @param ratingId Identificador de la instancia a eliminar.
     * @generated
     */
    @DELETE
    @Path("ratingId: \\d+}")
    public void deleteRating(@PathParam("ratingId") Long ratingId) {
        ratingLogic.deleteRating(ratingId);
    }
    
}
