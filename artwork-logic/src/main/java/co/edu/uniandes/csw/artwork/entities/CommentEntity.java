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
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;


/**
 * @generated
 */
@Entity
public class CommentEntity extends BaseEntity implements Serializable {

    private String text;

    private String email;

    @PodamExclude
    @ManyToOne
    private ArtworkEntity artwork;

    /**
     * Obtiene el atributo text.
     *
     * @return atributo text.
     * @generated
     */
    public String getText(){
        return text;
    }

    /**
     * Establece el valor del atributo text.
     *
     * @param text nuevo valor del atributo
     * @generated
     */
    public void setText(String text){
        this.text = text;
    }

    /**
     * Obtiene el atributo email.
     *
     * @return atributo email.
     * @generated
     */
    public String getEmail(){
        return email;
    }

    /**
     * Establece el valor del atributo email.
     *
     * @param email nuevo valor del atributo
     * @generated
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * Obtiene el atributo artwork.
     *
     * @return atributo artwork.
     * @generated
     */
    public ArtworkEntity getArtwork() {
        return artwork;
    }

    /**
     * Establece el valor del atributo artwork.
     *
     * @param artwork nuevo valor del atributo
     * @generated
     */
    public void setArtwork(ArtworkEntity artwork) {
        this.artwork = artwork;
    }
}
