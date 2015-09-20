package jquassistant.plugin.structurizr.impl;

import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.core.store.impl.GraphDbStore;
import com.structurizr.Workspace;
import com.structurizr.model.*;
import jquassistant.plugin.structurizr.TechTribes;
import jquassistant.plugin.structurizr.api.model.*;
import jquassistant.plugin.structurizr.api.model.base.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.test.TestGraphDatabaseFactory;

import java.util.Arrays;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

/**
 * @author mh
 * @since 04.07.15
 */
public class StructurizrConverterTest {

    private GraphDatabaseService db;
    private GraphDbStore store;

    @Before
    public void setUp() throws Exception {
        db = new TestGraphDatabaseFactory().newImpermanentDatabase();
        store = new GraphDbStore(db);
        store.start(Arrays.<Class<?>>asList(
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
        store.stop();
        if (db!=null) db.shutdown();
    }

    @Test
    public void testConvertWorkspace() throws Exception {
        Workspace workspace = TechTribes.createTechTribesWorkspace();
        store.beginTransaction();
        StructurizrWorkspace sWorkspace = new StructurizrConverter(store).convertWorkspace(workspace);
        assertEquals(workspace.getDescription(), sWorkspace.getDescription());
        assertEquals(workspace.getName(), sWorkspace.getName());
        assertEquals(workspace.getId(), Long.parseLong(sWorkspace.getId()));
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
        assertEquals(element.getId(), sElement.getId());
        assertEquals(element.getName(), sElement.getName());
        assertEquals(element.getDescription(), sElement.getDescription());
    }

    @Test
    public void testConvertContainer() throws Exception {
        Component component = new Component();
        component.setImplementationType("impl.ImplementationType");
        component.setInterfaceType("api.InterfaceType");
        component.setSourcePath("SourcePath");
        component.setTechnology("Technology");
        component.setDescription("ComponentDesc");
        component.setName("ComponentName");
        store.beginTransaction();
        StructurizrComponent sComponent = new StructurizrConverter(store).convertComponent(component);
        assetElement(component,sComponent);
        assertEquals(component.getTechnology(), sComponent.getTechnology());
        assertEquals(component.getInterfaceType(), sComponent.getInterfaceType());
        assertEquals(component.getPackage(), sComponent.getPackage());
        assertEquals(component.getImplementationType(), sComponent.getImplementationType());
        assertEquals(component.getSourcePath(), sComponent.getSourcePath());
        store.commitTransaction();
    }
}
