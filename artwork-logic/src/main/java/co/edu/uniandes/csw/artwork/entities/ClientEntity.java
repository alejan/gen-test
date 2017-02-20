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
public class ClientEntity extends BaseEntity implements Serializable {

    @PodamExclude
    @OneToOne(fetch=FetchType.LAZY)
    private ShoppingCartEntity shoppingCart;

    @PodamExclude
    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
    private List<PurchaseEntity> purchase = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
    private List<AdressEntity> adresses = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
    private List<CreditCardEntity> creditCard = new ArrayList<>();

    /**
     * Obtiene el atributo shoppingCart.
     *
     * @return atributo shoppingCart.
     * @generated
     */
    public ShoppingCartEntity getShoppingCart() {
        return shoppingCart;
    }

    /**
     * Establece el valor del atributo shoppingCart.
     *
     * @param shoppingCart nuevo valor del atributo
     * @generated
     */
    public void setShoppingCart(ShoppingCartEntity shoppingcart) {
        this.shoppingCart = shoppingcart;
    }

    /**
     * Obtiene la colección de purchase.
     *
     * @return colección purchase.
     * @generated
     */
    public List<PurchaseEntity> getPurchase() {
        return purchase;
    }

    /**
     * Establece el valor de la colección de purchase.
     *
     * @param purchase nuevo valor de la colección.
     * @generated
     */
    public void setPurchase(List<PurchaseEntity> purchase) {
        this.purchase = purchase;
    }

    /**
     * Obtiene la colección de adresses.
     *
     * @return colección adresses.
     * @generated
     */
    public List<AdressEntity> getAdresses() {
        return adresses;
    }

    /**
     * Establece el valor de la colección de adresses.
     *
     * @param adresses nuevo valor de la colección.
     * @generated
     */
    public void setAdresses(List<AdressEntity> adresses) {
        this.adresses = adresses;
    }

    /**
     * Obtiene la colección de creditCard.
     *
     * @return colección creditCard.
     * @generated
     */
    public List<CreditCardEntity> getCreditCard() {
        return creditCard;
    }

    /**
     * Establece el valor de la colección de creditCard.
     *
     * @param creditCard nuevo valor de la colección.
     * @generated
     */
    public void setCreditCard(List<CreditCardEntity> creditcard) {
        this.creditCard = creditcard;
    }
}
