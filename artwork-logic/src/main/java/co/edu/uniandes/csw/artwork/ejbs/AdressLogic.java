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

import co.edu.uniandes.csw.artwork.api.IAdressLogic;
import co.edu.uniandes.csw.artwork.entities.AdressEntity;
import co.edu.uniandes.csw.artwork.persistence.AdressPersistence;
import co.edu.uniandes.csw.artwork.api.IClientLogic;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

/**
 * @generated
 */
@Stateless
public class AdressLogic implements IAdressLogic {

    @Inject private AdressPersistence persistence;

    @Inject
    private IClientLogic clientLogic;

    /**
     * Obtiene el número de registros de Adress.
     *
     * @return Número de registros de Adress.
     * @generated
     */
    public int countAdresss() {
        return persistence.count();
    }

    /**
     * Obtiene la lista de los registros de Adress que pertenecen a un Client.
     *
     * @param clientid id del Client el cual es padre de los Adresss.
     * @return Colección de objetos de AdressEntity.
     * @generated
     */
    @Override
    public List<AdressEntity> getAdresss(Long clientid) {
        ClientEntity client = clientLogic.getClient(clientid);
        return client.getAdresses();
        
    }

    /**
     * Obtiene la lista de los registros de Adress que pertenecen a un Client indicando los datos para la paginación.
     *
     * @param page Número de página.
     * @param maxRecords Número de registros que se mostraran en cada página.
     * @param clientid id del Client el cual es padre de los Adresss.
     * @return Colección de objetos de AdressEntity.
     * @generated
     */
    @Override
    public List<AdressEntity> getAdresss(Integer page, Integer maxRecords, Long clientid) {
        return persistence.findAll(page, maxRecords, clientid);
    }

    /**
     * Obtiene los datos de una instancia de Adress a partir de su ID.
     *
     * @pre La existencia del elemento padre Client se debe garantizar.
     * @param adressid) Identificador del Adress a consultar
     * @return Instancia de AdressEntity con los datos del Adress consultado.
     * @generated
     */
    @Override
    public AdressEntity getAdress(Long adressid) {
        try {
            return persistence.find(adressid);
        }catch(NoResultException e){
            throw new IllegalArgumentException("El Adress no existe");
        }
    }

    /**
     * Se encarga de crear un Adress en la base de datos.
     *
     * @param entity Objeto de AdressEntity con los datos nuevos
     * @param clientid id del Client el cual sera padre del nuevo Adress.
     * @return Objeto de AdressEntity con los datos nuevos y su ID.
     * @generated
     */
    @Override
    public AdressEntity createAdress(Long clientid, AdressEntity entity) {
        ClientEntity client = clientLogic.getClient(clientid);
        entity.setClient(client);
        entity = persistence.create(entity);
        return entity;
    }

    /**
     * Actualiza la información de una instancia de Adress.
     *
     * @param entity Instancia de AdressEntity con los nuevos datos.
     * @param clientid id del Client el cual sera padre del Adress actualizado.
     * @return Instancia de AdressEntity con los datos actualizados.
     * @generated
     */
    @Override
    public AdressEntity updateAdress(Long clientid, AdressEntity entity) {
        ClientEntity client = clientLogic.getClient(clientid);
        entity.setClient(client);
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Adress de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @param clientid id del Client el cual es padre del Adress.
     * @generated
     */
    @Override
    public void deleteAdress(Long id) {
        AdressEntity old = getAdress(id);
        persistence.delete(old.getId());
    }
  
}
