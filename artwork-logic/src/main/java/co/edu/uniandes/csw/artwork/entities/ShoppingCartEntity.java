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
package co.edu.uniandes.csw.artwork.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import co.edu.uniandes.csw.crud.spi.entity.BaseEntity;
import uk.co.jemos.podam.common.PodamExclude;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;


/**
 * @generated
 */
@Entity
public class ShoppingCartEntity extends BaseEntity implements Serializable {

    @PodamExclude
    @OneToOne(fetch=FetchType.LAZY)
    private ClientEntity client;

    @PodamExclude
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.REMOVE)
    private List<OrderEntity> order = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "shoppingCart")
    private List<ItemEntity> item = new ArrayList<>();

    /**
     * Obtiene el atributo client.
     *
     * @return atributo client.
     * @generated
     */
    public ClientEntity getClient() {
        return client;
    }

    /**
     * Establece el valor del atributo client.
     *
     * @param client nuevo valor del atributo
     * @generated
     */
    public void setClient(ClientEntity client) {
        this.client = client;
    }

    /**
     * Obtiene la colección de order.
     *
     * @return colección order.
     * @generated
     */
    public List<OrderEntity> getOrder() {
        return order;
    }

    /**
     * Establece el valor de la colección de order.
     *
     * @param order nuevo valor de la colección.
     * @generated
     */
    public void setOrder(List<OrderEntity> order) {
        this.order = order;
    }

    /**
     * Obtiene la colección de item.
     *
     * @return colección item.
     * @generated
     */
    public List<ItemEntity> getItem() {
        return item;
    }

    /**
     * Establece el valor de la colección de item.
     *
     * @param item nuevo valor de la colección.
     * @generated
     */
    public void setItem(List<ItemEntity> item) {
        this.item = item;
    }
}
