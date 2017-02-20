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

import co.edu.uniandes.csw.artwork.ejbs.ShoppingCartLogic;
import co.edu.uniandes.csw.artwork.api.IShoppingCartLogic;
import co.edu.uniandes.csw.artwork.entities.ShoppingCartEntity;
import co.edu.uniandes.csw.artwork.persistence.ShoppingCartPersistence;
import co.edu.uniandes.csw.artwork.entities.ClientEntity;
import co.edu.uniandes.csw.artwork.entities.OrderEntity;
import co.edu.uniandes.csw.artwork.entities.ItemEntity;
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
public class ShoppingCartLogicTest {

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
    private IShoppingCartLogic shoppingCartLogic;

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
    private List<ShoppingCartEntity> data = new ArrayList<ShoppingCartEntity>();
    /**
     * @generated
     */
    private List<ClientEntity> clientData = new ArrayList<>();
    /**
     * @generated
     */
    private List<ItemEntity> itemData = new ArrayList<>();

    /**
     * @generated
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ShoppingCartEntity.class.getPackage())
                .addPackage(ShoppingCartLogic.class.getPackage())
                .addPackage(IShoppingCartLogic.class.getPackage())
                .addPackage(ShoppingCartPersistence.class.getPackage())
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
        em.createQuery("delete from OrderEntity").executeUpdate();
        em.createQuery("delete from ItemEntity").executeUpdate();
        em.createQuery("delete from ShoppingCartEntity").executeUpdate();
        em.createQuery("delete from ClientEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * @generated
     */
    private void insertData() {
            for (int i = 0; i < 3; i++) {
                ClientEntity client = factory.manufacturePojo(ClientEntity.class);
                em.persist(client);
                clientData.add(client);
            }
            for (int i = 0; i < 3; i++) {
                ItemEntity item = factory.manufacturePojo(ItemEntity.class);
                em.persist(item);
                itemData.add(item);
            }
        for (int i = 0; i < 3; i++) {
            ShoppingCartEntity entity = factory.manufacturePojo(ShoppingCartEntity.class);
                entity.setClient(clientData.get(0));

            em.persist(entity);
            data.add(entity);

            if (i == 0) {
                itemData.get(i).setShoppingCart(entity);
            }
        }
    }
    /**
     * Prueba para crear un ShoppingCart
     *
     * @generated
     */
    @Test
    public void createShoppingCartTest() {
        ShoppingCartEntity newEntity = factory.manufacturePojo(ShoppingCartEntity.class);
        ShoppingCartEntity result = shoppingCartLogic.createShoppingCart(newEntity);
        Assert.assertNotNull(result);
        ShoppingCartEntity entity = em.find(ShoppingCartEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
    }

    /**
     * Prueba para consultar la lista de ShoppingCarts
     *
     * @generated
     */
    @Test
    public void getShoppingCartsTest() {
        List<ShoppingCartEntity> list = shoppingCartLogic.getShoppingCarts();
        Assert.assertEquals(data.size(), list.size());
        for (ShoppingCartEntity entity : list) {
            boolean found = false;
            for (ShoppingCartEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    
    /**
     * Prueba para consultar un ShoppingCart
     *
     * @generated
     */
    @Test
    public void getShoppingCartTest() {
        ShoppingCartEntity entity = data.get(0);
        ShoppingCartEntity resultEntity = shoppingCartLogic.getShoppingCart(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getName(), resultEntity.getName());
    }

    /**
     * Prueba para eliminar un ShoppingCart
     *
     * @generated
     */
    @Test
    public void deleteShoppingCartTest() {
        ShoppingCartEntity entity = data.get(0);
        shoppingCartLogic.removeItem(entity.getId(), itemData.get(0).getId());
        shoppingCartLogic.deleteShoppingCart(entity.getId());
        ShoppingCartEntity deleted = em.find(ShoppingCartEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un ShoppingCart
     *
     * @generated
     */
    @Test
    public void updateShoppingCartTest() {
        ShoppingCartEntity entity = data.get(0);
        ShoppingCartEntity pojoEntity = factory.manufacturePojo(ShoppingCartEntity.class);

        pojoEntity.setId(entity.getId());

        shoppingCartLogic.updateShoppingCart(pojoEntity);

        ShoppingCartEntity resp = em.find(ShoppingCartEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getName(), resp.getName());
    }

    /**
     * Prueba para obtener una instancia de Item asociada a una instancia ShoppingCart
     *
     * @generated
     */
    @Test
    public void getItemTest() {
        ShoppingCartEntity entity = data.get(0);
        ItemEntity itemEntity = itemData.get(0);
        ItemEntity response = shoppingCartLogic.getItem(entity.getId(), itemEntity.getId());

    }

    /**
     * Prueba para obtener una colección de instancias de Item asociadas a una instancia ShoppingCart
     *
     * @generated
     */
    @Test
    public void listItemTest() {
        List<ItemEntity> list = shoppingCartLogic.listItem(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }

    /**
     *Prueba para asociar un Item existente a un ShoppingCart
     *
     * @generated
     */
    @Test
    public void addItemTest() {
        ShoppingCartEntity entity = data.get(0);
        ItemEntity itemEntity = itemData.get(1);
        ItemEntity response = shoppingCartLogic.addItem(entity.getId(), itemEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(itemEntity.getId(), response.getId());
    }

    /**
     * Prueba para remplazar las instancias de Item asociadas a una instancia de ShoppingCart
     *
     * @generated
     */
    @Test
    public void replaceItemTest() {
        ShoppingCartEntity entity = data.get(0);
        List<ItemEntity> list = itemData.subList(1, 3);
        shoppingCartLogic.replaceItem(entity.getId(), list);

        entity = shoppingCartLogic.getShoppingCart(entity.getId());
        Assert.assertFalse(entity.getItem().contains(itemData.get(0)));
        Assert.assertTrue(entity.getItem().contains(itemData.get(1)));
        Assert.assertTrue(entity.getItem().contains(itemData.get(2)));
    }

    /**
     * Prueba para desasociar un Item existente de un ShoppingCart existente
     *
     * @generated
     */
    @Test
    public void removeItemTest() {
        shoppingCartLogic.removeItem(data.get(0).getId(), itemData.get(0).getId());
        ItemEntity response = shoppingCartLogic.getItem(data.get(0).getId(), itemData.get(0).getId());
        Assert.assertNull(response);
    }
}

