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

import co.edu.uniandes.csw.artwork.api.IAwardLogic;
import co.edu.uniandes.csw.artwork.entities.AwardEntity;
import co.edu.uniandes.csw.artwork.persistence.AwardPersistence;
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
public class AwardLogic implements IAwardLogic {

    @Inject private AwardPersistence persistence;

    @Inject
    private IArtworkLogic artworkLogic;

    /**
     * Obtiene el número de registros de Award.
     *
     * @return Número de registros de Award.
     * @generated
     */
    public int countAwards() {
        return persistence.count();
    }

    /**
     * Obtiene la lista de los registros de Award que pertenecen a un Artwork.
     *
     * @param artworkid id del Artwork el cual es padre de los Awards.
     * @return Colección de objetos de AwardEntity.
     * @generated
     */
    @Override
    public List<AwardEntity> getAwards(Long artworkid) {
        ArtworkEntity artwork = artworkLogic.getArtwork(artworkid);
        return artwork.getAward();
        
    }

    /**
     * Obtiene la lista de los registros de Award que pertenecen a un Artwork indicando los datos para la paginación.
     *
     * @param page Número de página.
     * @param maxRecords Número de registros que se mostraran en cada página.
     * @param artworkid id del Artwork el cual es padre de los Awards.
     * @return Colección de objetos de AwardEntity.
     * @generated
     */
    @Override
    public List<AwardEntity> getAwards(Integer page, Integer maxRecords, Long artworkid) {
        return persistence.findAll(page, maxRecords, artworkid);
    }

    /**
     * Obtiene los datos de una instancia de Award a partir de su ID.
     *
     * @pre La existencia del elemento padre Artwork se debe garantizar.
     * @param awardid) Identificador del Award a consultar
     * @return Instancia de AwardEntity con los datos del Award consultado.
     * @generated
     */
    @Override
    public AwardEntity getAward(Long awardid) {
        try {
            return persistence.find(awardid);
        }catch(NoResultException e){
            throw new IllegalArgumentException("El Award no existe");
        }
    }

    /**
     * Se encarga de crear un Award en la base de datos.
     *
     * @param entity Objeto de AwardEntity con los datos nuevos
     * @param artworkid id del Artwork el cual sera padre del nuevo Award.
     * @return Objeto de AwardEntity con los datos nuevos y su ID.
     * @generated
     */
    @Override
    public AwardEntity createAward(Long artworkid, AwardEntity entity) {
        ArtworkEntity artwork = artworkLogic.getArtwork(artworkid);
        entity.setArtwork(artwork);
        entity = persistence.create(entity);
        return entity;
    }

    /**
     * Actualiza la información de una instancia de Award.
     *
     * @param entity Instancia de AwardEntity con los nuevos datos.
     * @param artworkid id del Artwork el cual sera padre del Award actualizado.
     * @return Instancia de AwardEntity con los datos actualizados.
     * @generated
     */
    @Override
    public AwardEntity updateAward(Long artworkid, AwardEntity entity) {
        ArtworkEntity artwork = artworkLogic.getArtwork(artworkid);
        entity.setArtwork(artwork);
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Award de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @param artworkid id del Artwork el cual es padre del Award.
     * @generated
     */
    @Override
    public void deleteAward(Long id) {
        AwardEntity old = getAward(id);
        persistence.delete(old.getId());
    }
  
}
