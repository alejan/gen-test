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

import co.edu.uniandes.csw.artwork.api.IPurchaseLogic;
import co.edu.uniandes.csw.artwork.entities.PurchaseEntity;
import co.edu.uniandes.csw.artwork.persistence.PurchasePersistence;
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
public class PurchaseLogic implements IPurchaseLogic {

    @Inject private PurchasePersistence persistence;

    @Inject
    private IClientLogic clientLogic;

    /**
     * Obtiene el número de registros de Purchase.
     *
     * @return Número de registros de Purchase.
     * @generated
     */
    public int countPurchases() {
        return persistence.count();
    }

    /**
     * Obtiene la lista de los registros de Purchase que pertenecen a un Client.
     *
     * @param clientid id del Client el cual es padre de los Purchases.
     * @return Colección de objetos de PurchaseEntity.
     * @generated
     */
    @Override
    public List<PurchaseEntity> getPurchases(Long clientid) {
        ClientEntity client = clientLogic.getClient(clientid);
        return client.getPurchase();
        
    }

    /**
     * Obtiene la lista de los registros de Purchase que pertenecen a un Client indicando los datos para la paginación.
     *
     * @param page Número de página.
     * @param maxRecords Número de registros que se mostraran en cada página.
     * @param clientid id del Client el cual es padre de los Purchases.
     * @return Colección de objetos de PurchaseEntity.
     * @generated
     */
    @Override
    public List<PurchaseEntity> getPurchases(Integer page, Integer maxRecords, Long clientid) {
        return persistence.findAll(page, maxRecords, clientid);
    }

    /**
     * Obtiene los datos de una instancia de Purchase a partir de su ID.
     *
     * @pre La existencia del elemento padre Client se debe garantizar.
     * @param purchaseid) Identificador del Purchase a consultar
     * @return Instancia de PurchaseEntity con los datos del Purchase consultado.
     * @generated
     */
    @Override
    public PurchaseEntity getPurchase(Long purchaseid) {
        try {
            return persistence.find(purchaseid);
        }catch(NoResultException e){
            throw new IllegalArgumentException("El Purchase no existe");
        }
    }

    /**
     * Se encarga de crear un Purchase en la base de datos.
     *
     * @param entity Objeto de PurchaseEntity con los datos nuevos
     * @param clientid id del Client el cual sera padre del nuevo Purchase.
     * @return Objeto de PurchaseEntity con los datos nuevos y su ID.
     * @generated
     */
    @Override
    public PurchaseEntity createPurchase(Long clientid, PurchaseEntity entity) {
        ClientEntity client = clientLogic.getClient(clientid);
        entity.setClient(client);
        entity = persistence.create(entity);
        return entity;
    }

    /**
     * Actualiza la información de una instancia de Purchase.
     *
     * @param entity Instancia de PurchaseEntity con los nuevos datos.
     * @param clientid id del Client el cual sera padre del Purchase actualizado.
     * @return Instancia de PurchaseEntity con los datos actualizados.
     * @generated
     */
    @Override
    public PurchaseEntity updatePurchase(Long clientid, PurchaseEntity entity) {
        ClientEntity client = clientLogic.getClient(clientid);
        entity.setClient(client);
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Purchase de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @param clientid id del Client el cual es padre del Purchase.
     * @generated
     */
    @Override
    public void deletePurchase(Long id) {
        PurchaseEntity old = getPurchase(id);
        persistence.delete(old.getId());
    }
  
}
