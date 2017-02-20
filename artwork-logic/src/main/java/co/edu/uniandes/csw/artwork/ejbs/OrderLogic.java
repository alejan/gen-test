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

import co.edu.uniandes.csw.artwork.api.IOrderLogic;
import co.edu.uniandes.csw.artwork.entities.OrderEntity;
import co.edu.uniandes.csw.artwork.persistence.OrderPersistence;
import co.edu.uniandes.csw.artwork.api.IShoppingCartLogic;
import co.edu.uniandes.csw.artwork.entities.ShoppingCartEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

/**
 * @generated
 */
@Stateless
public class OrderLogic implements IOrderLogic {

    @Inject private OrderPersistence persistence;

    @Inject
    private IShoppingCartLogic shoppingCartLogic;

    /**
     * Obtiene el número de registros de Order.
     *
     * @return Número de registros de Order.
     * @generated
     */
    public int countOrders() {
        return persistence.count();
    }

    /**
     * Obtiene la lista de los registros de Order que pertenecen a un ShoppingCart.
     *
     * @param shoppingCartid id del ShoppingCart el cual es padre de los Orders.
     * @return Colección de objetos de OrderEntity.
     * @generated
     */
    @Override
    public List<OrderEntity> getOrders(Long shoppingCartid) {
        ShoppingCartEntity shoppingCart = shoppingCartLogic.getShoppingCart(shoppingCartid);
        return shoppingCart.getOrder();
        
    }

    /**
     * Obtiene la lista de los registros de Order que pertenecen a un ShoppingCart indicando los datos para la paginación.
     *
     * @param page Número de página.
     * @param maxRecords Número de registros que se mostraran en cada página.
     * @param shoppingCartid id del ShoppingCart el cual es padre de los Orders.
     * @return Colección de objetos de OrderEntity.
     * @generated
     */
    @Override
    public List<OrderEntity> getOrders(Integer page, Integer maxRecords, Long shoppingCartid) {
        return persistence.findAll(page, maxRecords, shoppingCartid);
    }

    /**
     * Obtiene los datos de una instancia de Order a partir de su ID.
     *
     * @pre La existencia del elemento padre ShoppingCart se debe garantizar.
     * @param orderid) Identificador del Order a consultar
     * @return Instancia de OrderEntity con los datos del Order consultado.
     * @generated
     */
    @Override
    public OrderEntity getOrder(Long orderid) {
        try {
            return persistence.find(orderid);
        }catch(NoResultException e){
            throw new IllegalArgumentException("El Order no existe");
        }
    }

    /**
     * Se encarga de crear un Order en la base de datos.
     *
     * @param entity Objeto de OrderEntity con los datos nuevos
     * @param shoppingCartid id del ShoppingCart el cual sera padre del nuevo Order.
     * @return Objeto de OrderEntity con los datos nuevos y su ID.
     * @generated
     */
    @Override
    public OrderEntity createOrder(Long shoppingCartid, OrderEntity entity) {
        ShoppingCartEntity shoppingCart = shoppingCartLogic.getShoppingCart(shoppingCartid);
        entity.setShoppingCart(shoppingCart);
        entity = persistence.create(entity);
        return entity;
    }

    /**
     * Actualiza la información de una instancia de Order.
     *
     * @param entity Instancia de OrderEntity con los nuevos datos.
     * @param shoppingCartid id del ShoppingCart el cual sera padre del Order actualizado.
     * @return Instancia de OrderEntity con los datos actualizados.
     * @generated
     */
    @Override
    public OrderEntity updateOrder(Long shoppingCartid, OrderEntity entity) {
        ShoppingCartEntity shoppingCart = shoppingCartLogic.getShoppingCart(shoppingCartid);
        entity.setShoppingCart(shoppingCart);
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Order de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @param shoppingCartid id del ShoppingCart el cual es padre del Order.
     * @generated
     */
    @Override
    public void deleteOrder(Long id) {
        OrderEntity old = getOrder(id);
        persistence.delete(old.getId());
    }
  
}
