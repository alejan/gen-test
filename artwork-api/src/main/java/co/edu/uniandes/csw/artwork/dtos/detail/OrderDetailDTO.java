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
package co.edu.uniandes.csw.artwork.dtos.detail;

import co.edu.uniandes.csw.artwork.dtos.minimum.*;
import co.edu.uniandes.csw.artwork.entities.OrderEntity;
import javax.xml.bind.annotation.XmlRootElement;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * @generated
 */
@XmlRootElement
public class OrderDetailDTO extends OrderDTO{


    @PodamExclude
    private ShoppingCartDTO shoppingCart;

    /**
     * @generated
     */
    public OrderDetailDTO() {
        super();
    }

    /**
     * Crea un objeto OrderDetailDTO a partir de un objeto OrderEntity incluyendo los atributos de OrderDTO.
     *
     * @param entity Entidad OrderEntity desde la cual se va a crear el nuevo objeto.
     * @generated
     */
    public OrderDetailDTO(OrderEntity entity) {
        super(entity);
        if (entity.getShoppingCart()!=null){
        this.shoppingCart = new ShoppingCartDTO(entity.getShoppingCart());
        }
        
    }

    /**
     * Convierte un objeto OrderDetailDTO a OrderEntity incluyendo los atributos de OrderDTO.
     *
     * @return Nueva objeto OrderEntity.
     * @generated
     */
    @Override
    public OrderEntity toEntity() {
        OrderEntity entity = super.toEntity();
        if (this.getShoppingCart()!=null){
        entity.setShoppingCart(this.getShoppingCart().toEntity());
        }
        return entity;
    }

    /**
     * Obtiene el atributo shoppingCart.
     *
     * @return atributo shoppingCart.
     * @generated
     */
    public ShoppingCartDTO getShoppingCart() {
        return shoppingCart;
    }

    /**
     * Establece el valor del atributo shoppingCart.
     *
     * @param shoppingCart nuevo valor del atributo
     * @generated
     */
    public void setShoppingCart(ShoppingCartDTO shoppingcart) {
        this.shoppingCart = shoppingcart;
    }

}
