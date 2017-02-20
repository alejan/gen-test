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
package co.edu.uniandes.csw.artwork.tests.rest;

import co.edu.uniandes.csw.auth.model.UserDTO;
import co.edu.uniandes.csw.auth.security.JWT;
import co.edu.uniandes.csw.artwork.entities.CommentEntity;
import co.edu.uniandes.csw.artwork.entities.ArtworkEntity;
import co.edu.uniandes.csw.artwork.entities.ArtistEntity;
import co.edu.uniandes.csw.artwork.dtos.detail.CommentDetailDTO;
import co.edu.uniandes.csw.artwork.resources.CommentResource;
import co.edu.uniandes.csw.artwork.tests.Utils;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.UserTransaction;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/*
 * Testing URI: artists/{commentId: \\d+}/artwork/{commentId: \\d+}/comment/
 */
@RunWith(Arquillian.class)
public class CommentTest {

    private WebTarget target;
    private final String apiPath = Utils.apiPath;
    private final String username = Utils.username;
    private final String password = Utils.password;
    PodamFactory factory = new PodamFactoryImpl();

    private final int Ok = Status.OK.getStatusCode();
    private final int Created = Status.CREATED.getStatusCode();
    private final int OkWithoutContent = Status.NO_CONTENT.getStatusCode();

    private final static List<CommentEntity> oraculo = new ArrayList<>();

    private final String artistPath = "artists";
    private final String artworkPath = "artwork";
    private final String commentPath = "comment";

    ArtistEntity fatherArtistEntity;
    ArtworkEntity fatherArtworkEntity;

    @ArquillianResource
    private URL deploymentURL;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                // Se agrega las dependencias
                .addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml")
                        .importRuntimeDependencies().resolve()
                        .withTransitivity().asFile())
                // Se agregan los compilados de los paquetes de servicios
                .addPackage(CommentResource.class.getPackage())
                // El archivo que contiene la configuracion a la base de datos.
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                // El archivo beans.xml es necesario para injeccion de dependencias.
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"))
                // El archivo shiro.ini es necesario para injeccion de dependencias
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/shiro.ini"))
                // El archivo web.xml es necesario para el despliegue de los servlets
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"));
    }

    private WebTarget createWebTarget() {
        return ClientBuilder.newClient().target(deploymentURL.toString()).path(apiPath);
    }

    @PersistenceContext(unitName = "ArtworkPU")
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private void clearData() {
        em.createQuery("delete from CommentEntity").executeUpdate();
        em.createQuery("delete from ArtworkEntity").executeUpdate();
        em.createQuery("delete from ArtistEntity").executeUpdate();
        oraculo.clear();
    }

   /**
     * Datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * @generated
     */
    public void insertData() {
        fatherArtistEntity = factory.manufacturePojo(ArtistEntity.class);
        fatherArtistEntity.setId(1L);
        em.persist(fatherArtistEntity);

        fatherArtworkEntity = factory.manufacturePojo(ArtworkEntity.class);
        fatherArtworkEntity.setId(1L);
        fatherArtworkEntity.setArtist(fatherArtistEntity);
        em.persist(fatherArtworkEntity);

        for (int i = 0; i < 3; i++) {            
            CommentEntity comment = factory.manufacturePojo(CommentEntity.class);
            comment.setId(i + 1L);
            comment.setArtwork(fatherArtworkEntity);
            em.persist(comment);
            oraculo.add(comment);
        }
    }

    /**
     * Configuración inicial de la prueba.
     *
     * @generated
     */
    @Before
    public void setUpTest() {
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
        target = createWebTarget()
                .path(artistPath)
                .path(fatherArtistEntity.getId().toString())
                .path(artworkPath)
                .path(fatherArtworkEntity.getId().toString())
                .path(commentPath);
    }

    /**
     * Login para poder consultar los diferentes servicios
     *
     * @param username Nombre de usuario
     * @param password Clave del usuario
     * @return Cookie con información de la sesión del usuario
     * @generated
     */
    public Cookie login(String username, String password) {
        UserDTO user = new UserDTO();
        user.setUserName(username);
        user.setPassword(password);
        user.setRememberMe(true);
        Response response = createWebTarget().path("users").path("login").request()
                .post(Entity.entity(user, MediaType.APPLICATION_JSON));
        if (response.getStatus() == Ok) {
            return response.getCookies().get(JWT.cookieName);
        } else {
            return null;
        }
    }

    /**
     * Prueba para crear un Comment
     *
     * @generated
     */
    @Test
    public void createCommentTest() throws IOException {
        CommentDetailDTO comment = factory.manufacturePojo(CommentDetailDTO.class);
        Cookie cookieSessionId = login(username, password);

        Response response = target
            .request().cookie(cookieSessionId)
            .post(Entity.entity(comment, MediaType.APPLICATION_JSON));

        CommentDetailDTO  commentTest = (CommentDetailDTO) response.readEntity(CommentDetailDTO.class);

        Assert.assertEquals(Created, response.getStatus());

        Assert.assertEquals(comment.getText(), commentTest.getText());
        Assert.assertEquals(comment.getEmail(), commentTest.getEmail());
        Assert.assertEquals(comment.getName(), commentTest.getName());

        CommentEntity entity = em.find(CommentEntity.class, commentTest.getId());
        Assert.assertNotNull(entity);
    }

    /**
     * Prueba para consultar un Comment
     *
     * @generated
     */
    @Test
    public void getCommentByIdTest() {
        Cookie cookieSessionId = login(username, password);

        CommentDetailDTO commentTest = target
            .path(oraculo.get(0).getId().toString())
            .request().cookie(cookieSessionId).get(CommentDetailDTO.class);
        
        Assert.assertEquals(commentTest.getId(), oraculo.get(0).getId());
        Assert.assertEquals(commentTest.getText(), oraculo.get(0).getText());
        Assert.assertEquals(commentTest.getEmail(), oraculo.get(0).getEmail());
        Assert.assertEquals(commentTest.getName(), oraculo.get(0).getName());
    }

    /**
     * Prueba para consultar la lista de Comments
     *
     * @generated
     */
    @Test
    public void listCommentTest() throws IOException {
        Cookie cookieSessionId = login(username, password);

        Response response = target
            .request().cookie(cookieSessionId).get();

        String listComment = response.readEntity(String.class);
        List<CommentDetailDTO> listCommentTest = new ObjectMapper().readValue(listComment, List.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(oraculo.size(), listCommentTest.size());
    }

    /**
     * Prueba para actualizar un Comment
     *
     * @generated
     */
    @Test
    public void updateCommentTest() throws IOException {
        Cookie cookieSessionId = login(username, password);
        CommentDetailDTO comment = new CommentDetailDTO(oraculo.get(0));

        CommentDetailDTO commentChanged = factory.manufacturePojo(CommentDetailDTO.class);

        comment.setText(commentChanged.getText());
        comment.setEmail(commentChanged.getEmail());
        comment.setName(commentChanged.getName());

        Response response = target
            .path(comment.getId().toString())
            .request().cookie(cookieSessionId)
            .put(Entity.entity(comment, MediaType.APPLICATION_JSON));

        CommentDetailDTO commentTest = (CommentDetailDTO) response.readEntity(CommentDetailDTO.class);

        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(comment.getText(), commentTest.getText());
        Assert.assertEquals(comment.getEmail(), commentTest.getEmail());
        Assert.assertEquals(comment.getName(), commentTest.getName());
    }

    /**
     * Prueba para eliminar un Comment
     *
     * @generated
     */
    @Test
    public void deleteCommentTest() {
        Cookie cookieSessionId = login(username, password);
        CommentDetailDTO comment = new CommentDetailDTO(oraculo.get(0));
        Response response = target
            .path(comment.getId().toString())
            .request().cookie(cookieSessionId).delete();

        Assert.assertEquals(OkWithoutContent, response.getStatus());
    }
}
