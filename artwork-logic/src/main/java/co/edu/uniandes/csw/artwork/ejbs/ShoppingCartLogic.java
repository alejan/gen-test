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

import co.edu.uniandes.csw.artwork.api.IShoppingCartLogic;
import co.edu.uniandes.csw.artwork.entities.ShoppingCartEntity;
import co.edu.uniandes.csw.artwork.persistence.ShoppingCartPersistence;
import co.edu.uniandes.csw.artwork.entities.ItemEntity;
import co.edu.uniandes.csw.artwork.api.IItemLogic;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

/**
 * @generated
 */
@Stateless
public class ShoppingCartLogic implements IShoppingCartLogic {

    @Inject private ShoppingCartPersistence persistence;


    @Inject private IItemLogic itemLogic;

    /**
     * Obtiene el número de registros de ShoppingCart.
     *
     * @return Número de registros de ShoppingCart.
     * @generated
     */
    public int countShoppingCarts() {
        return persistence.count();
    }

    /**
     * Obtiene la lista de los registros de ShoppingCart.
     *
     * @return Colección de objetos de ShoppingCartEntity.
     * @generated
     */
    @Override
    public List<ShoppingCartEntity> getShoppingCarts() {
        return persistence.findAll();
    }

    /**
     * Obtiene la lista de los registros de ShoppingCart indicando los datos para la paginación.
     *
     * @param page Número de página.
     * @param maxRecords Número de registros que se mostraran en cada página.
     * @return Colección de objetos de ShoppingCartEntity.
     * @generated
     */
    @Override
    public List<ShoppingCartEntity> getShoppingCarts(Integer page, Integer maxRecords) {
        return persistence.findAll(page, maxRecords);
    }

    /**
     * Obtiene los datos de una instancia de ShoppingCart a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de ShoppingCartEntity con los datos del ShoppingCart consultado.
     * @generated
     */
    public ShoppingCartEntity getShoppingCart(Long id) {
        return persistence.find(id);
    }

    /**
     * Se encarga de crear un ShoppingCart en la base de datos.
     *
     * @param entity Objeto de ShoppingCartEntity con los datos nuevos
     * @return Objeto de ShoppingCartEntity con los datos nuevos y su ID.
     * @generated
     */
    @Override
    public ShoppingCartEntity createShoppingCart(ShoppingCartEntity entity) {
        persistence.create(entity);
        return entity;
    }

    /**
     * Actualiza la información de una instancia de ShoppingCart.
     *
     * @param entity Instancia de ShoppingCartEntity con los nuevos datos.
     * @return Instancia de ShoppingCartEntity con los datos actualizados.
     * @generated
     */
    @Override
    public ShoppingCartEntity updateShoppingCart(ShoppingCartEntity entity) {
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de ShoppingCart de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @generated
     */
    @Override
    public void deleteShoppingCart(Long id) {
        persistence.delete(id);
    }
  

    /**
     * Obtiene una colección de instancias de ItemEntity asociadas a una
     * instancia de ShoppingCart
     *
     * @param shoppingCartId Identificador de la instancia de ShoppingCart
     * @return Colección de instancias de ItemEntity asociadas a la instancia de ShoppingCart
     * @generated
     */
    @Override
    public List<ItemEntity> listItem(Long shoppingCartId) {
        return getShoppingCart(shoppingCartId).getItem();
    }

    /**
     * Obtiene una instancia de ItemEntity asociada a una instancia de ShoppingCart
     *
     * @param shoppingCartId Identificador de la instancia de ShoppingCart
     * @param itemId Identificador de la instancia de Item
     * @generated
     */
    @Override
    public ItemEntity getItem(Long shoppingCartId, Long itemId) {
        List<ItemEntity> list = getShoppingCart(shoppingCartId).getItem();
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(itemId);
        int index = list.indexOf(itemEntity);
        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }

    /**
     * Asocia un Item existente a un ShoppingCart
     *
     * @param shoppingCartId Identificador de la instancia de ShoppingCart
     * @param itemId Identificador de la instancia de Item
     * @return Instancia de ItemEntity que fue asociada a ShoppingCart
     * @generated
     */
    @Override
    public ItemEntity addItem(Long shoppingCartId, Long itemId) {
        ShoppingCartEntity shoppingCartEntity = getShoppingCart(shoppingCartId);
        ItemEntity itemEntity = itemLogic.getItem(itemId);
        itemEntity.setShoppingCart(shoppingCartEntity);
        return itemEntity;
    }

    /**
     * Remplaza las instancias de Item asociadas a una instancia de ShoppingCart
     *
     * @param shoppingCartId Identificador de la instancia de ShoppingCart
     * @param list Colección de instancias de ItemEntity a asociar a instancia de ShoppingCart
     * @return Nueva colección de ItemEntity asociada a la instancia de ShoppingCart
     * @generated
     */
    @Override
    public List<ItemEntity> replaceItem(Long shoppingCartId, List<ItemEntity> list) {
        ShoppingCartEntity shoppingCartEntity = getShoppingCart(shoppingCartId);
        List<ItemEntity> itemList = itemLogic.getItems();
        for (ItemEntity item : itemList) {
            if (list.contains(item)) {
                item.setShoppingCart(shoppingCartEntity);
            } else {
                if (item.getShoppingCart() != null && item.getShoppingCart().equals(shoppingCartEntity)) {
                    item.setShoppingCart(null);
                }
            }
        }
        shoppingCartEntity.setItem(list);
        return shoppingCartEntity.getItem();
    }

    /**
     * Desasocia un Item existente de un ShoppingCart existente
     *
     * @param shoppingCartId Identificador de la instancia de ShoppingCart
     * @param itemId Identificador de la instancia de Item
     * @generated
     */
    @Override
    public void removeItem(Long shoppingCartId, Long itemId) {
        ItemEntity entity = itemLogic.getItem(itemId);
        entity.setShoppingCart(null);
    }
}
