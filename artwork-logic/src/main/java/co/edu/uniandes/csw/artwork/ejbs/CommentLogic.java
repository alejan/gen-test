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
package co.edu.uniandes.csw.artwork.ejbs;

import co.edu.uniandes.csw.artwork.api.ICommentLogic;
import co.edu.uniandes.csw.artwork.entities.CommentEntity;
import co.edu.uniandes.csw.artwork.persistence.CommentPersistence;
import co.edu.uniandes.csw.artwork.api.IArtworkLogic;
import co.edu.uniandes.csw.artwork.entities.ArtworkEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

/**
 * @generated
 */
@Stateless
public class CommentLogic implements ICommentLogic {

    @Inject private CommentPersistence persistence;

    @Inject
    private IArtworkLogic artworkLogic;

    /**
     * Obtiene el número de registros de Comment.
     *
     * @return Número de registros de Comment.
     * @generated
     */
    public int countComments() {
        return persistence.count();
    }

    /**
     * Obtiene la lista de los registros de Comment que pertenecen a un Artwork.
     *
     * @param artworkid id del Artwork el cual es padre de los Comments.
     * @return Colección de objetos de CommentEntity.
     * @generated
     */
    @Override
    public List<CommentEntity> getComments(Long artworkid) {
        ArtworkEntity artwork = artworkLogic.getArtwork(artworkid);
        return artwork.getComment();
        
    }

    /**
     * Obtiene la lista de los registros de Comment que pertenecen a un Artwork indicando los datos para la paginación.
     *
     * @param page Número de página.
     * @param maxRecords Número de registros que se mostraran en cada página.
     * @param artworkid id del Artwork el cual es padre de los Comments.
     * @return Colección de objetos de CommentEntity.
     * @generated
     */
    @Override
    public List<CommentEntity> getComments(Integer page, Integer maxRecords, Long artworkid) {
        return persistence.findAll(page, maxRecords, artworkid);
    }

    /**
     * Obtiene los datos de una instancia de Comment a partir de su ID.
     *
     * @pre La existencia del elemento padre Artwork se debe garantizar.
     * @param commentid) Identificador del Comment a consultar
     * @return Instancia de CommentEntity con los datos del Comment consultado.
     * @generated
     */
    @Override
    public CommentEntity getComment(Long commentid) {
        try {
            return persistence.find(commentid);
        }catch(NoResultException e){
            throw new IllegalArgumentException("El Comment no existe");
        }
    }

    /**
     * Se encarga de crear un Comment en la base de datos.
     *
     * @param entity Objeto de CommentEntity con los datos nuevos
     * @param artworkid id del Artwork el cual sera padre del nuevo Comment.
     * @return Objeto de CommentEntity con los datos nuevos y su ID.
     * @generated
     */
    @Override
    public CommentEntity createComment(Long artworkid, CommentEntity entity) {
        ArtworkEntity artwork = artworkLogic.getArtwork(artworkid);
        entity.setArtwork(artwork);
        entity = persistence.create(entity);
        return entity;
    }

    /**
     * Actualiza la información de una instancia de Comment.
     *
     * @param entity Instancia de CommentEntity con los nuevos datos.
     * @param artworkid id del Artwork el cual sera padre del Comment actualizado.
     * @return Instancia de CommentEntity con los datos actualizados.
     * @generated
     */
    @Override
    public CommentEntity updateComment(Long artworkid, CommentEntity entity) {
        ArtworkEntity artwork = artworkLogic.getArtwork(artworkid);
        entity.setArtwork(artwork);
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Comment de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @param artworkid id del Artwork el cual es padre del Comment.
     * @generated
     */
    @Override
    public void deleteComment(Long id) {
        CommentEntity old = getComment(id);
        persistence.delete(old.getId());
    }
  
}
