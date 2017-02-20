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

import co.edu.uniandes.csw.artwork.api.ISaleLogic;
import co.edu.uniandes.csw.artwork.entities.SaleEntity;
import co.edu.uniandes.csw.artwork.persistence.SalePersistence;
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
public class SaleLogic implements ISaleLogic {

    @Inject private SalePersistence persistence;

    @Inject
    private IArtistLogic artistLogic;

    /**
     * Obtiene el número de registros de Sale.
     *
     * @return Número de registros de Sale.
     * @generated
     */
    public int countSales() {
        return persistence.count();
    }

    /**
     * Obtiene la lista de los registros de Sale que pertenecen a un Artist.
     *
     * @param artistid id del Artist el cual es padre de los Sales.
     * @return Colección de objetos de SaleEntity.
     * @generated
     */
    @Override
    public List<SaleEntity> getSales(Long artistid) {
        ArtistEntity artist = artistLogic.getArtist(artistid);
        return artist.getSale();
        
    }

    /**
     * Obtiene la lista de los registros de Sale que pertenecen a un Artist indicando los datos para la paginación.
     *
     * @param page Número de página.
     * @param maxRecords Número de registros que se mostraran en cada página.
     * @param artistid id del Artist el cual es padre de los Sales.
     * @return Colección de objetos de SaleEntity.
     * @generated
     */
    @Override
    public List<SaleEntity> getSales(Integer page, Integer maxRecords, Long artistid) {
        return persistence.findAll(page, maxRecords, artistid);
    }

    /**
     * Obtiene los datos de una instancia de Sale a partir de su ID.
     *
     * @pre La existencia del elemento padre Artist se debe garantizar.
     * @param saleid) Identificador del Sale a consultar
     * @return Instancia de SaleEntity con los datos del Sale consultado.
     * @generated
     */
    @Override
    public SaleEntity getSale(Long saleid) {
        try {
            return persistence.find(saleid);
        }catch(NoResultException e){
            throw new IllegalArgumentException("El Sale no existe");
        }
    }

    /**
     * Se encarga de crear un Sale en la base de datos.
     *
     * @param entity Objeto de SaleEntity con los datos nuevos
     * @param artistid id del Artist el cual sera padre del nuevo Sale.
     * @return Objeto de SaleEntity con los datos nuevos y su ID.
     * @generated
     */
    @Override
    public SaleEntity createSale(Long artistid, SaleEntity entity) {
        ArtistEntity artist = artistLogic.getArtist(artistid);
        entity.setArtist(artist);
        entity = persistence.create(entity);
        return entity;
    }

    /**
     * Actualiza la información de una instancia de Sale.
     *
     * @param entity Instancia de SaleEntity con los nuevos datos.
     * @param artistid id del Artist el cual sera padre del Sale actualizado.
     * @return Instancia de SaleEntity con los datos actualizados.
     * @generated
     */
    @Override
    public SaleEntity updateSale(Long artistid, SaleEntity entity) {
        ArtistEntity artist = artistLogic.getArtist(artistid);
        entity.setArtist(artist);
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Sale de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @param artistid id del Artist el cual es padre del Sale.
     * @generated
     */
    @Override
    public void deleteSale(Long id) {
        SaleEntity old = getSale(id);
        persistence.delete(old.getId());
    }
  
}
