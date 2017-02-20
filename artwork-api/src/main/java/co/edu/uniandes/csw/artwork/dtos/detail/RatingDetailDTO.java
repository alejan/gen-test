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
import co.edu.uniandes.csw.artwork.entities.RatingEntity;
import javax.xml.bind.annotation.XmlRootElement;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * @generated
 */
@XmlRootElement
public class RatingDetailDTO extends RatingDTO{


    @PodamExclude
    private ArtistDTO artist;

    /**
     * @generated
     */
    public RatingDetailDTO() {
        super();
    }

    /**
     * Crea un objeto RatingDetailDTO a partir de un objeto RatingEntity incluyendo los atributos de RatingDTO.
     *
     * @param entity Entidad RatingEntity desde la cual se va a crear el nuevo objeto.
     * @generated
     */
    public RatingDetailDTO(RatingEntity entity) {
        super(entity);
        if (entity.getArtist()!=null){
        this.artist = new ArtistDTO(entity.getArtist());
        }
        
    }

    /**
     * Convierte un objeto RatingDetailDTO a RatingEntity incluyendo los atributos de RatingDTO.
     *
     * @return Nueva objeto RatingEntity.
     * @generated
     */
    @Override
    public RatingEntity toEntity() {
        RatingEntity entity = super.toEntity();
        if (this.getArtist()!=null){
        entity.setArtist(this.getArtist().toEntity());
        }
        return entity;
    }

    /**
     * Obtiene el atributo artist.
     *
     * @return atributo artist.
     * @generated
     */
    public ArtistDTO getArtist() {
        return artist;
    }

    /**
     * Establece el valor del atributo artist.
     *
     * @param artist nuevo valor del atributo
     * @generated
     */
    public void setArtist(ArtistDTO artist) {
        this.artist = artist;
    }

}
