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
import javax.persistence.OneToMany;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.CascadeType;


/**
 * @generated
 */
@Entity
public class ArtistEntity extends BaseEntity implements Serializable {

    @PodamExclude
    @OneToMany(mappedBy = "artist", cascade = CascadeType.REMOVE)
    private List<SaleEntity> sale = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "artist", cascade = CascadeType.REMOVE)
    private List<ArtworkEntity> artwork = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "artist", cascade = CascadeType.REMOVE)
    private List<RatingEntity> rating = new ArrayList<>();

    /**
     * Obtiene la colección de sale.
     *
     * @return colección sale.
     * @generated
     */
    public List<SaleEntity> getSale() {
        return sale;
    }

    /**
     * Establece el valor de la colección de sale.
     *
     * @param sale nuevo valor de la colección.
     * @generated
     */
    public void setSale(List<SaleEntity> sale) {
        this.sale = sale;
    }

    /**
     * Obtiene la colección de artwork.
     *
     * @return colección artwork.
     * @generated
     */
    public List<ArtworkEntity> getArtwork() {
        return artwork;
    }

    /**
     * Establece el valor de la colección de artwork.
     *
     * @param artwork nuevo valor de la colección.
     * @generated
     */
    public void setArtwork(List<ArtworkEntity> artwork) {
        this.artwork = artwork;
    }

    /**
     * Obtiene la colección de rating.
     *
     * @return colección rating.
     * @generated
     */
    public List<RatingEntity> getRating() {
        return rating;
    }

    /**
     * Establece el valor de la colección de rating.
     *
     * @param rating nuevo valor de la colección.
     * @generated
     */
    public void setRating(List<RatingEntity> rating) {
        this.rating = rating;
    }
}