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

import co.edu.uniandes.csw.artwork.api.ICreditCardLogic;
import co.edu.uniandes.csw.artwork.entities.CreditCardEntity;
import co.edu.uniandes.csw.artwork.persistence.CreditCardPersistence;
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
public class CreditCardLogic implements ICreditCardLogic {

    @Inject private CreditCardPersistence persistence;

    @Inject
    private IClientLogic clientLogic;

    /**
     * Obtiene el número de registros de CreditCard.
     *
     * @return Número de registros de CreditCard.
     * @generated
     */
    public int countCreditCards() {
        return persistence.count();
    }

    /**
     * Obtiene la lista de los registros de CreditCard que pertenecen a un Client.
     *
     * @param clientid id del Client el cual es padre de los CreditCards.
     * @return Colección de objetos de CreditCardEntity.
     * @generated
     */
    @Override
    public List<CreditCardEntity> getCreditCards(Long clientid) {
        ClientEntity client = clientLogic.getClient(clientid);
        return client.getCreditCard();
        
    }

    /**
     * Obtiene la lista de los registros de CreditCard que pertenecen a un Client indicando los datos para la paginación.
     *
     * @param page Número de página.
     * @param maxRecords Número de registros que se mostraran en cada página.
     * @param clientid id del Client el cual es padre de los CreditCards.
     * @return Colección de objetos de CreditCardEntity.
     * @generated
     */
    @Override
    public List<CreditCardEntity> getCreditCards(Integer page, Integer maxRecords, Long clientid) {
        return persistence.findAll(page, maxRecords, clientid);
    }

    /**
     * Obtiene los datos de una instancia de CreditCard a partir de su ID.
     *
     * @pre La existencia del elemento padre Client se debe garantizar.
     * @param creditCardid) Identificador del CreditCard a consultar
     * @return Instancia de CreditCardEntity con los datos del CreditCard consultado.
     * @generated
     */
    @Override
    public CreditCardEntity getCreditCard(Long creditCardid) {
        try {
            return persistence.find(creditCardid);
        }catch(NoResultException e){
            throw new IllegalArgumentException("El CreditCard no existe");
        }
    }

    /**
     * Se encarga de crear un CreditCard en la base de datos.
     *
     * @param entity Objeto de CreditCardEntity con los datos nuevos
     * @param clientid id del Client el cual sera padre del nuevo CreditCard.
     * @return Objeto de CreditCardEntity con los datos nuevos y su ID.
     * @generated
     */
    @Override
    public CreditCardEntity createCreditCard(Long clientid, CreditCardEntity entity) {
        ClientEntity client = clientLogic.getClient(clientid);
        entity.setClient(client);
        entity = persistence.create(entity);
        return entity;
    }

    /**
     * Actualiza la información de una instancia de CreditCard.
     *
     * @param entity Instancia de CreditCardEntity con los nuevos datos.
     * @param clientid id del Client el cual sera padre del CreditCard actualizado.
     * @return Instancia de CreditCardEntity con los datos actualizados.
     * @generated
     */
    @Override
    public CreditCardEntity updateCreditCard(Long clientid, CreditCardEntity entity) {
        ClientEntity client = clientLogic.getClient(clientid);
        entity.setClient(client);
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de CreditCard de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @param clientid id del Client el cual es padre del CreditCard.
     * @generated
     */
    @Override
    public void deleteCreditCard(Long id) {
        CreditCardEntity old = getCreditCard(id);
        persistence.delete(old.getId());
    }
  
}
