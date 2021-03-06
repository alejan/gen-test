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
package co.edu.uniandes.csw.artwork.test.logic;

import co.edu.uniandes.csw.artwork.ejbs.AdressLogic;
import co.edu.uniandes.csw.artwork.api.IAdressLogic;
import co.edu.uniandes.csw.artwork.entities.AdressEntity;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.persistence.AdressPersistence;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class AdressLogicTest {

    /**
     * @generated
     */
    ClientEntity fatherEntity;

    /**
     * @generated
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * @generated
     */
    @Inject
    private IAdressLogic adressLogic;

    /**
     * @generated
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * @generated
     */
    @Inject
    private UserTransaction utx;

    /**
     * @generated
     */
    private List<AdressEntity> data = new ArrayList<AdressEntity>();
    /**
     * @generated
     */
    private List<ClientEntity> clientData = new ArrayList<>();

    /**
     * @generated
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(AdressEntity.class.getPackage())
                .addPackage(AdressLogic.class.getPackage())
                .addPackage(IAdressLogic.class.getPackage())
                .addPackage(AdressPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     *
     * @generated
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     *
     * @generated
     */
    private void clearData() {
        em.createQuery("delete from AdressEntity").executeUpdate();
        em.createQuery("delete from ClientEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * @generated
     */
    private void insertData() {
    
            fatherEntity = factory.manufacturePojo(ClientEntity.class);
            fatherEntity.setId(1L);
            em.persist(fatherEntity);
        for (int i = 0; i < 3; i++) {
            AdressEntity entity = factory.manufacturePojo(AdressEntity.class);
                entity.setClient(fatherEntity);
    

            em.persist(entity);
            data.add(entity);
        }
    }
   /**
     * Prueba para crear un Adress
     *
     * @generated
     */
    @Test
    public void createAdressTest() {
        AdressEntity newEntity = factory.manufacturePojo(AdressEntity.class);
        AdressEntity result = adressLogic.createAdress(fatherEntity.getId(), newEntity);
        Assert.assertNotNull(result);
        AdressEntity entity = em.find(AdressEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getCity(), entity.getCity());
        Assert.assertEquals(newEntity.getName(), entity.getName());
    }

    /**
     * Prueba para consultar la lista de Adresss
     *
     * @generated
     */
    @Test
    public void getAdresssTest() {
        List<AdressEntity> list = adressLogic.getAdresss(fatherEntity.getId());
        Assert.assertEquals(data.size(), list.size());
        for (AdressEntity entity : list) {
            boolean found = false;
            for (AdressEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    
    /**
     * Prueba para consultar un Adress
     *
     * @generated
     */
    @Test
    public void getAdressTest() {
        AdressEntity entity = data.get(0);
        AdressEntity resultEntity = adressLogic.getAdress(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getCity(), resultEntity.getCity());
        Assert.assertEquals(entity.getName(), resultEntity.getName());
    }

    /**
     * Prueba para eliminar un Adress
     *
     * @generated
     */
    @Test
    public void deleteAdressTest() {
        AdressEntity entity = data.get(0);
        adressLogic.deleteAdress(entity.getId());
        AdressEntity deleted = em.find(AdressEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Adress
     *
     * @generated
     */
    @Test
    public void updateAdressTest() {
        AdressEntity entity = data.get(0);
        AdressEntity pojoEntity = factory.manufacturePojo(AdressEntity.class);

        pojoEntity.setId(entity.getId());

        adressLogic.updateAdress(fatherEntity.getId(), pojoEntity);

        AdressEntity resp = em.find(AdressEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getCity(), resp.getCity());
        Assert.assertEquals(pojoEntity.getName(), resp.getName());
    }
}

