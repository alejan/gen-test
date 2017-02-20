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
import co.edu.uniandes.csw.artwork.entities.ShoppingCartEntity;
import javax.xml.bind.annotation.XmlRootElement;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * @generated
 */
@XmlRootElement
public class ShoppingCartDetailDTO extends ShoppingCartDTO{


    @PodamExclude
    private ClientDTO client;

    /**
     * @generated
     */
    public ShoppingCartDetailDTO() {
        super();
    }

    /**
     * Crea un objeto ShoppingCartDetailDTO a partir de un objeto ShoppingCartEntity incluyendo los atributos de ShoppingCartDTO.
     *
     * @param entity Entidad ShoppingCartEntity desde la cual se va a crear el nuevo objeto.
     * @generated
     */
    public ShoppingCartDetailDTO(ShoppingCartEntity entity) {
        super(entity);
        if (entity.getClient()!=null){
        this.client = new ClientDTO(entity.getClient());
        }
        
    }

    /**
     * Convierte un objeto ShoppingCartDetailDTO a ShoppingCartEntity incluyendo los atributos de ShoppingCartDTO.
     *
     * @return Nueva objeto ShoppingCartEntity.
     * @generated
     */
    @Override
    public ShoppingCartEntity toEntity() {
        ShoppingCartEntity entity = super.toEntity();
        if (this.getClient()!=null){
        entity.setClient(this.getClient().toEntity());
        }
        return entity;
    }

    /**
     * Obtiene el atributo client.
     *
     * @return atributo client.
     * @generated
     */
    public ClientDTO getClient() {
        return client;
    }

    /**
     * Establece el valor del atributo client.
     *
     * @param client nuevo valor del atributo
     * @generated
     */
    public void setClient(ClientDTO client) {
        this.client = client;
    }

}
