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

import co.edu.uniandes.csw.artwork.ejbs.PurchaseLogic;
import co.edu.uniandes.csw.artwork.api.IPurchaseLogic;
import co.edu.uniandes.csw.artwork.entities.PurchaseEntity;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.persistence.PurchasePersistence;
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
public class PurchaseLogicTest {

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
    private IPurchaseLogic purchaseLogic;

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
    private List<PurchaseEntity> data = new ArrayList<PurchaseEntity>();
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
                .addPackage(PurchaseEntity.class.getPackage())
                .addPackage(PurchaseLogic.class.getPackage())
                .addPackage(IPurchaseLogic.class.getPackage())
                .addPackage(PurchasePersistence.class.getPackage())
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
        em.createQuery("delete from PurchaseEntity").executeUpdate();
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
            PurchaseEntity entity = factory.manufacturePojo(PurchaseEntity.class);
                entity.setClient(fatherEntity);
    

            em.persist(entity);
            data.add(entity);
        }
    }
   /**
     * Prueba para crear un Purchase
     *
     * @generated
     */
    @Test
    public void createPurchaseTest() {
        PurchaseEntity newEntity = factory.manufacturePojo(PurchaseEntity.class);
        PurchaseEntity result = purchaseLogic.createPurchase(fatherEntity.getId(), newEntity);
        Assert.assertNotNull(result);
        PurchaseEntity entity = em.find(PurchaseEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
    }

    /**
     * Prueba para consultar la lista de Purchases
     *
     * @generated
     */
    @Test
    public void getPurchasesTest() {
        List<PurchaseEntity> list = purchaseLogic.getPurchases(fatherEntity.getId());
        Assert.assertEquals(data.size(), list.size());
        for (PurchaseEntity entity : list) {
            boolean found = false;
            for (PurchaseEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    
    /**
     * Prueba para consultar un Purchase
     *
     * @generated
     */
    @Test
    public void getPurchaseTest() {
        PurchaseEntity entity = data.get(0);
        PurchaseEntity resultEntity = purchaseLogic.getPurchase(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getName(), resultEntity.getName());
    }

    /**
     * Prueba para eliminar un Purchase
     *
     * @generated
     */
    @Test
    public void deletePurchaseTest() {
        PurchaseEntity entity = data.get(0);
        purchaseLogic.deletePurchase(entity.getId());
        PurchaseEntity deleted = em.find(PurchaseEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Purchase
     *
     * @generated
     */
    @Test
    public void updatePurchaseTest() {
        PurchaseEntity entity = data.get(0);
        PurchaseEntity pojoEntity = factory.manufacturePojo(PurchaseEntity.class);

        pojoEntity.setId(entity.getId());

        purchaseLogic.updatePurchase(fatherEntity.getId(), pojoEntity);

        PurchaseEntity resp = em.find(PurchaseEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getName(), resp.getName());
    }
}

