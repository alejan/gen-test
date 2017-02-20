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

import co.edu.uniandes.csw.artwork.api.IRatingLogic;
import co.edu.uniandes.csw.artwork.entities.RatingEntity;
import co.edu.uniandes.csw.artwork.persistence.RatingPersistence;
import co.edu.uniandes.csw.artwork.api.IArtistLogic;
import co.edu.uniandes.csw.artwork.entities.ArtistEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

/**
 * @generated
 */
@Stateless
public class RatingLogic implements IRatingLogic {

    @Inject private RatingPersistence persistence;

    @Inject
    private IArtistLogic artistLogic;

    /**
     * Obtiene el número de registros de Rating.
     *
     * @return Número de registros de Rating.
     * @generated
     */
    public int countRatings() {
        return persistence.count();
    }

    /**
     * Obtiene la lista de los registros de Rating que pertenecen a un Artist.
     *
     * @param artistid id del Artist el cual es padre de los Ratings.
     * @return Colección de objetos de RatingEntity.
     * @generated
     */
    @Override
    public List<RatingEntity> getRatings(Long artistid) {
        ArtistEntity artist = artistLogic.getArtist(artistid);
        return artist.getRating();
        
    }

    /**
     * Obtiene la lista de los registros de Rating que pertenecen a un Artist indicando los datos para la paginación.
     *
     * @param page Número de página.
     * @param maxRecords Número de registros que se mostraran en cada página.
     * @param artistid id del Artist el cual es padre de los Ratings.
     * @return Colección de objetos de RatingEntity.
     * @generated
     */
    @Override
    public List<RatingEntity> getRatings(Integer page, Integer maxRecords, Long artistid) {
        return persistence.findAll(page, maxRecords, artistid);
    }

    /**
     * Obtiene los datos de una instancia de Rating a partir de su ID.
     *
     * @pre La existencia del elemento padre Artist se debe garantizar.
     * @param ratingid) Identificador del Rating a consultar
     * @return Instancia de RatingEntity con los datos del Rating consultado.
     * @generated
     */
    @Override
    public RatingEntity getRating(Long ratingid) {
        try {
            return persistence.find(ratingid);
        }catch(NoResultException e){
            throw new IllegalArgumentException("El Rating no existe");
        }
    }

    /**
     * Se encarga de crear un Rating en la base de datos.
     *
     * @param entity Objeto de RatingEntity con los datos nuevos
     * @param artistid id del Artist el cual sera padre del nuevo Rating.
     * @return Objeto de RatingEntity con los datos nuevos y su ID.
     * @generated
     */
    @Override
    public RatingEntity createRating(Long artistid, RatingEntity entity) {
        ArtistEntity artist = artistLogic.getArtist(artistid);
        entity.setArtist(artist);
        entity = persistence.create(entity);
        return entity;
    }

    /**
     * Actualiza la información de una instancia de Rating.
     *
     * @param entity Instancia de RatingEntity con los nuevos datos.
     * @param artistid id del Artist el cual sera padre del Rating actualizado.
     * @return Instancia de RatingEntity con los datos actualizados.
     * @generated
     */
    @Override
    public RatingEntity updateRating(Long artistid, RatingEntity entity) {
        ArtistEntity artist = artistLogic.getArtist(artistid);
        entity.setArtist(artist);
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Rating de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @param artistid id del Artist el cual es padre del Rating.
     * @generated
     */
    @Override
    public void deleteRating(Long id) {
        RatingEntity old = getRating(id);
        persistence.delete(old.getId());
    }
  
}
