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
import co.edu.uniandes.csw.artwork.api.ICommentLogic;
import co.edu.uniandes.csw.artwork.dtos.detail.CommentDetailDTO;
import co.edu.uniandes.csw.artwork.entities.CommentEntity;
import java.util.ArrayList;
import javax.ws.rs.WebApplicationException;

/**
 * URI: artists/{artistsId: \\d+}/artwork/{artworkId: \\d+}/comment
 * @generated
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CommentResource {

    @Inject private ICommentLogic commentLogic;
    @Context private HttpServletResponse response;
    @QueryParam("page") private Integer page;
    @QueryParam("limit") private Integer maxRecords;
    @PathParam("artworkId") private Long artworkId;

   
    /**
     * Convierte una lista de CommentEntity a una lista de CommentDetailDTO
     *
     * @param entityList Lista de CommentEntity a convertir
     * @return Lista de CommentDetailDTO convertida
     * @generated
     */
    private List<CommentDetailDTO> listEntity2DTO(List<CommentEntity> entityList){
        List<CommentDetailDTO> list = new ArrayList<>();
        for (CommentEntity entity : entityList) {
            list.add(new CommentDetailDTO(entity));
        }
        return list;
    }


    /**
     * Obtiene la lista de los registros de Comment asociados a un Artwork
     *
     * @return Colección de objetos de CommentDetailDTO
     * @generated
     */
    @GET
    public List<CommentDetailDTO> getComments() {
        if (page != null && maxRecords != null) {
            this.response.setIntHeader("X-Total-Count", commentLogic.countComments());
            return listEntity2DTO(commentLogic.getComments(page, maxRecords, artworkId));
        }
        return listEntity2DTO(commentLogic.getComments(artworkId));
    }

    /**
     * Obtiene los datos de una instancia de Comment a partir de su ID asociado a un Artwork
     *
     * @param commentId Identificador de la instancia a consultar
     * @return Instancia de CommentDetailDTO con los datos del Comment consultado
     * @generated
     */
    @GET
    @Path("{commentId: \\d+}")
    public CommentDetailDTO getComment(@PathParam("commentId") Long commentId) {
        CommentEntity entity = commentLogic.getComment(commentId);
        if (entity.getArtwork() != null && !artworkId.equals(entity.getArtwork().getId())) {
            throw new WebApplicationException(404);
        }
        return new CommentDetailDTO(entity);
    }

    /**
     * Asocia un Comment existente a un Artwork
     *
     * @param dto Objeto de CommentDetailDTO con los datos nuevos
     * @return Objeto de CommentDetailDTOcon los datos nuevos y su ID.
     * @generated
     */
    @POST
    @StatusCreated
    public CommentDetailDTO createComment(CommentDetailDTO dto) {
        return new CommentDetailDTO(commentLogic.createComment(artworkId, dto.toEntity()));
    }

    /**
     * Actualiza la información de una instancia de Comment.
     *
     * @param commentId Identificador de la instancia de Comment a modificar
     * @param dto Instancia de CommentDetailDTO con los nuevos datos.
     * @return Instancia de CommentDetailDTO con los datos actualizados.
     * @generated
     */
    @PUT
    @Path("{commentId: \\d+}")
    public CommentDetailDTO updateComment(@PathParam("commentId") Long commentId, CommentDetailDTO dto) {
        CommentEntity entity = dto.toEntity();
        entity.setId(commentId);
        return new CommentDetailDTO(commentLogic.updateComment(artworkId, entity));
    }

    /**
     * Elimina una instancia de Comment de la base de datos.
     *
     * @param commentId Identificador de la instancia a eliminar.
     * @generated
     */
    @DELETE
    @Path("commentId: \\d+}")
    public void deleteComment(@PathParam("commentId") Long commentId) {
        commentLogic.deleteComment(commentId);
    }
    
}
