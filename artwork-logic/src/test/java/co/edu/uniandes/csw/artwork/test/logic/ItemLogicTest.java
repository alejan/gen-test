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

import co.edu.uniandes.csw.artwork.ejbs.ItemLogic;
import co.edu.uniandes.csw.artwork.api.IItemLogic;
import co.edu.uniandes.csw.artwork.entities.ItemEntity;
import co.edu.uniandes.csw.artwork.persistence.ItemPersistence;
import co.edu.uniandes.csw.artwork.entities.ShoppingCartEntity;
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
public class ItemLogicTest {

    /**
     * @generated
     */

    /**
     * @generated
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * @generated
     */
    @Inject
    private IItemLogic itemLogic;

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
    private List<ItemEntity> data = new ArrayList<ItemEntity>();
    /**
     * @generated
     */
    private List<ShoppingCartEntity> shoppingCartData = new ArrayList<>();

    /**
     * @generated
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ItemEntity.class.getPackage())
                .addPackage(ItemLogic.class.getPackage())
                .addPackage(IItemLogic.class.getPackage())
                .addPackage(ItemPersistence.class.getPackage())
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
        em.createQuery("delete from ItemEntity").executeUpdate();
        em.createQuery("delete from ShoppingCartEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * @generated
     */
    private void insertData() {
            for (int i = 0; i < 3; i++) {
                ShoppingCartEntity shoppingCart = factory.manufacturePojo(ShoppingCartEntity.class);
                em.persist(shoppingCart);
                shoppingCartData.add(shoppingCart);
            }
        for (int i = 0; i < 3; i++) {
            ItemEntity entity = factory.manufacturePojo(ItemEntity.class);
                entity.setShoppingCart(shoppingCartData.get(0));

            em.persist(entity);
            data.add(entity);
        }
    }
    /**
     * Prueba para crear un Item
     *
     * @generated
     */
    @Test
    public void createItemTest() {
        ItemEntity newEntity = factory.manufacturePojo(ItemEntity.class);
        ItemEntity result = itemLogic.createItem(newEntity);
        Assert.assertNotNull(result);
        ItemEntity entity = em.find(ItemEntity.class, result.getId());
    }

    /**
     * Prueba para consultar la lista de Items
     *
     * @generated
     */
    @Test
    public void getItemsTest() {
        List<ItemEntity> list = itemLogic.getItems();
        Assert.assertEquals(data.size(), list.size());
        for (ItemEntity entity : list) {
            boolean found = false;
            for (ItemEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    
    /**
     * Prueba para consultar un Item
     *
     * @generated
     */
    @Test
    public void getItemTest() {
        ItemEntity entity = data.get(0);
        ItemEntity resultEntity = itemLogic.getItem(entity.getId());
        Assert.assertNotNull(resultEntity);
    }

    /**
     * Prueba para eliminar un Item
     *
     * @generated
     */
    @Test
    public void deleteItemTest() {
        ItemEntity entity = data.get(0);
        itemLogic.deleteItem(entity.getId());
        ItemEntity deleted = em.find(ItemEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Item
     *
     * @generated
     */
    @Test
    public void updateItemTest() {
        ItemEntity entity = data.get(0);
        ItemEntity pojoEntity = factory.manufacturePojo(ItemEntity.class);

        pojoEntity.setId(entity.getId());

        itemLogic.updateItem(pojoEntity);

        ItemEntity resp = em.find(ItemEntity.class, entity.getId());

    }
}

