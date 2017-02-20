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
package co.edu.uniandes.csw.artwork.test.persistence;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.entities.AdressEntity;
import co.edu.uniandes.csw.artwork.persistence.AdressPersistence;
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
public class AdressPersistenceTest {

    /**
     * @generated
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(AdressEntity.class.getPackage())
                .addPackage(AdressPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * @generated
     */
    ClientEntity fatherEntity;

    /**
     * @generated
     */
    @Inject
    private AdressPersistence adressPersistence;

    /**
     * @generated
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * @generated
     */
    @Inject
    UserTransaction utx;

    /**
     * Configuración inicial de la prueba.
     *
     * @generated
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
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
     * @generated
     */
    private List<AdressEntity> data = new ArrayList<AdressEntity>();

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * @generated
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
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
     * Prueba para crear un Adress.
     *
     * @generated
     */
    @Test
    public void createAdressTest() {
		PodamFactory factory = new PodamFactoryImpl();
        AdressEntity newEntity = factory.manufacturePojo(AdressEntity.class);
        newEntity.setClient(fatherEntity);
        AdressEntity result = adressPersistence.create(newEntity);

        Assert.assertNotNull(result);

        AdressEntity entity = em.find(AdressEntity.class, result.getId());

        Assert.assertEquals(newEntity.getCity(), entity.getCity());
        Assert.assertEquals(newEntity.getName(), entity.getName());
    }

    /**
     * Prueba para consultar la lista de Adresss.
     *
     * @generated
     */
    @Test
    public void getAdresssTest() {
        List<AdressEntity> list = adressPersistence.findAll(null, null, fatherEntity.getId());
        Assert.assertEquals(data.size(), list.size());
        for (AdressEntity ent : list) {
            boolean found = false;
            for (AdressEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Adress.
     *
     * @generated
     */
    @Test
    public void getAdressTest() {
        AdressEntity entity = data.get(0);
        AdressEntity newEntity = adressPersistence.find(entity.getClient().getId(), entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getCity(), newEntity.getCity());
        Assert.assertEquals(entity.getName(), newEntity.getName());
    }

    /**
     * Prueba para eliminar un Adress.
     *
     * @generated
     */
    @Test
    public void deleteAdressTest() {
        AdressEntity entity = data.get(0);
        adressPersistence.delete(entity.getId());
        AdressEntity deleted = em.find(AdressEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Adress.
     *
     * @generated
     */
    @Test
    public void updateAdressTest() {
        AdressEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        AdressEntity newEntity = factory.manufacturePojo(AdressEntity.class);

        newEntity.setId(entity.getId());

        adressPersistence.update(newEntity);

        AdressEntity resp = em.find(AdressEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getCity(), resp.getCity());
        Assert.assertEquals(newEntity.getName(), resp.getName());
    }
}
