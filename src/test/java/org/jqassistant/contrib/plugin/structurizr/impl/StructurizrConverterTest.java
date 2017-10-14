package org.jqassistant.contrib.plugin.structurizr.impl;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.Arrays;
import java.util.Properties;

import org.jqassistant.contrib.plugin.structurizr.TechTribes;
import org.jqassistant.contrib.plugin.structurizr.api.model.*;
import org.jqassistant.contrib.plugin.structurizr.api.model.base.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.test.TestGraphDatabaseFactory;

import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.core.store.api.StoreConfiguration;
import com.buschmais.jqassistant.core.store.api.StoreFactory;
import com.structurizr.Workspace;
import com.structurizr.model.*;

/**
 * @author mh
 * @since 04.07.15
 */
public class StructurizrConverterTest {

    private GraphDatabaseService db;
    private Store store;

    @Before
    public void setUp() throws Exception {
        db = new TestGraphDatabaseFactory().newImpermanentDatabase();
        Properties props =new Properties();
        props.put("org.neo4j.graphdb.GraphDatabaseService",db);
        StoreConfiguration.StoreConfigurationBuilder config =
                StoreConfiguration.builder().uri(new URI("memory://local")).properties(props);
        this.store = StoreFactory.getStore(config.build());
        this.store.start(Arrays.<Class<?>>asList(
                StructurizrCodeElement.class,
                StructurizrComponent.class,
                StructurizrContainer.class,
                StructurizrWorkspace.class,
                StructurizrPerson.class,
//                StructurizrRelationship.class,
                StructurizrSoftwareSystem.class,
                StructurizrModel.class,
                StructurizrElement.class,
                StructurizrDescriptor.class,
                TaggableDescriptor.class,
                TagDescriptor.class,
                IdDescriptor.class,
                Located.class,
                Technology.class,
                CanonicalName.class,
                Description.class
        ));
    }

    @After
    public void tearDown() throws Exception {
        if (store!=null) store.stop();
        if (db!=null) db.shutdown();
    }

    @Test
    public void testConvertWorkspace() throws Exception {
        Workspace workspace = TechTribes.createTechTribesWorkspace();
        store.beginTransaction();
        StructurizrWorkspace sWorkspace = new StructurizrConverter(store).convertWorkspace(workspace);
        assertEquals(workspace.getDescription(), sWorkspace.getDescription());
        assertEquals(workspace.getName(), sWorkspace.getName());
        assertEquals(workspace.getId(), Long.parseLong(sWorkspace.getElementId().toString()));
        store.commitTransaction();
    }

    @Test
    public void testConvertPerson() throws Exception {
        Person person = new Model().addPerson(Location.Internal, "Simon", "Jersey");
        store.beginTransaction();
        StructurizrPerson sPerson = new StructurizrConverter(store).convertPerson(person);
        assetElement(person,sPerson);
        store.commitTransaction();
    }
    @Test
    public void testConvertSoftwaresystem() throws Exception {
        SoftwareSystem softwaresystem = new Model().addSoftwareSystem(Location.Internal, "TechTribes", "App");
        Container container = softwaresystem.addContainer("ContainerName", "ContainerDesc", "ContainerTech");
        store.beginTransaction();
        StructurizrSoftwareSystem sSoftwaresystem = new StructurizrConverter(store).convertSystem(softwaresystem);
        assetElement(softwaresystem,sSoftwaresystem);
        assertEquals(softwaresystem.getContainers().size(), sSoftwaresystem.getContainers().size());
        StructurizrContainer sContainer = sSoftwaresystem.getContainers().get(0);
        assertContainer(container, sContainer);
        store.commitTransaction();
    }

    private void assertContainer(Container container, StructurizrContainer sContainer) {
        assetElement(container, sContainer);
        assertEquals(container.getCanonicalName(), sContainer.getCanonicalName());
        assertEquals(container.getTechnology(), sContainer.getTechnology());
    }

    private void assetElement(Element element, StructurizrElement sElement) {
        assertEquals(element.getId(), sElement.getElementId());
        assertEquals(element.getName(), sElement.getName());
        assertEquals(element.getDescription(), sElement.getDescription());
    }

    @Test
    public void testConvertContainer() throws Exception {
        SoftwareSystem softwaresystem = new Model().addSoftwareSystem(Location.Internal, "TechTribes", "App");
        Container container = softwaresystem.addContainer("ContainerName", "ContainerDesc", "ContainerTech");
        Component component = container.addComponent("ComponentName", "ComponentDesc");
        component.setType("impl.ImplementationType");
        component.setUrl("http://host/path");
        component.setSize(1234);
        component.setTechnology("Technology");
        store.beginTransaction();
        StructurizrComponent sComponent = new StructurizrConverter(store).convertComponent(component);
        assetElement(component,sComponent);
        assertEquals(component.getTechnology(), sComponent.getTechnology());
        assertEquals(component.getType(), sComponent.getType());
//        assertEquals(component.getPackage(), sComponent.getPackage());
        assertEquals(component.getSize(), sComponent.getSize());
        assertEquals(component.getUrl(), sComponent.getUrl());
        store.commitTransaction();
    }
}
