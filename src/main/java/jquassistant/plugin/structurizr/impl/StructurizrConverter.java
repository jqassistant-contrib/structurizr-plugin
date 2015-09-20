package jquassistant.plugin.structurizr.impl;

import com.buschmais.jqassistant.core.store.api.Store;
import com.structurizr.Workspace;
import com.structurizr.model.*;
import com.structurizr.view.ViewSet;
import jquassistant.plugin.structurizr.api.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mh
 * @since 04.07.15
 */
public class StructurizrConverter {
    private final Store store;

    public StructurizrConverter(Store store) {
        this.store = store;
    }

    public StructurizrWorkspace convertWorkspace(Workspace workspace) {
        StructurizrWorkspace structurizrWorkspace = store.create(StructurizrWorkspace.class);
        structurizrWorkspace.setId(String.valueOf(workspace.getId()));
        structurizrWorkspace.setDescription(workspace.getDescription());
        structurizrWorkspace.setName(workspace.getName());
        structurizrWorkspace.setModel(convertModel(workspace.getModel()));
        // todo handle views ?!?
        ViewSet views = workspace.getViews();
        return structurizrWorkspace;
    }

    private StructurizrModel convertModel(Model model) {
        StructurizrModel sModel = store.create(StructurizrModel.class);
        sModel.setPeople(convertPeople(model));
        sModel.setSystems(convertSystems(model));
        return sModel;
    }

    private List<StructurizrPerson> convertPeople(Model model) {
        List<StructurizrPerson> people = new ArrayList<>(model.getPeople().size());
        for (Person person : model.getPeople()) {
            people.add(convertPerson(person));
        }
        return people;
    }

    StructurizrPerson convertPerson(Person person) {
        StructurizrPerson structurizrPerson = createElement(person, StructurizrPerson.class);
        structurizrPerson.setLocation(person.getLocation());
        return structurizrPerson;
    }

    private <T extends StructurizrElement> T createElement(Element element, Class<T> type) {
        T sElement = store.create(type);
        sElement.setId(element.getId());
        sElement.setName(element.getName());
        sElement.setDescription(element.getDescription());
        List<TagDescriptor> tags = new ArrayList<>();
        for (String tagName : element.getTags().split(",")) {
            TagDescriptor tag = store.create(TagDescriptor.class);
            tag.setName(tagName);
            tags.add(tag);
        }
        sElement.setTags(tags);
// todo relationships ??
//        sElement.setRelationships(convertRelationships(store, element));
        return sElement;
    }

    private List<StructurizrSoftwareSystem> convertSystems(Model m) {
        List<StructurizrSoftwareSystem> systems = new ArrayList<>(m.getSoftwareSystems().size());
        for (SoftwareSystem system : m.getSoftwareSystems()) {
            systems.add(convertSystem(system));
        }
        return systems;
    }

    StructurizrSoftwareSystem convertSystem(SoftwareSystem system) {
        StructurizrSoftwareSystem sSystem = createElement(system, StructurizrSoftwareSystem.class);
        sSystem.setContainers(convertContainers(system, sSystem));
        return sSystem;
    }

    private List<StructurizrContainer> convertContainers(SoftwareSystem system, StructurizrSoftwareSystem sSystem) {
        List<StructurizrContainer> containers = new ArrayList<>();
        for (Container container : system.getContainers()) {
            StructurizrContainer sContainer = createElement(container, StructurizrContainer.class);
            sContainer.setSystem(sSystem);
            sContainer.setTechnology(container.getTechnology());
            sContainer.setCanonicalName(container.getCanonicalName());
            sContainer.setComponents(convertComponents(container, sContainer));
            containers.add(sContainer);
        }
        return containers;
    }

    private List<StructurizrComponent> convertComponents(Container container, StructurizrContainer sContainer) {
        List<StructurizrComponent> components = new ArrayList<>();
        for (Component component : container.getComponents()) {
            StructurizrComponent sComponent = convertComponent(component);
            sComponent.setContainer(sContainer);
            components.add(sComponent);
        }
        return components;
    }

    StructurizrComponent convertComponent(Component component) {
        StructurizrComponent sComponent = createElement(component, StructurizrComponent.class);

        // todo, these are fqn classes ??
        sComponent.setImplementationType(component.getImplementationType());
        sComponent.setInterfaceType(component.getInterfaceType());
        // todo workaround
        if (component.getInterfaceType().indexOf('.') != -1) {
            sComponent.setPackage(component.getPackage());
        }
        sComponent.setSourcePath(component.getSourcePath());
        sComponent.setTechnology(component.getTechnology());
        return sComponent;
    }

    // todo replace with real relationships, map rel-name to rel-type or alternative target-entity-type-name
    private List<StructurizrRelationship> convertRelationships(Store store, Element element) {
        List<StructurizrRelationship> relationships = new ArrayList<>();
        for (Relationship relationship : element.getRelationships()) {
            StructurizrRelationship sRelationship = store.create(StructurizrRelationship.class);
            sRelationship.setId(relationship.getId());
            sRelationship.setDescription(relationship.getDescription());
            sRelationship.setFromElement(createElement(relationship.getSource(), StructurizrElement.class));
            sRelationship.setToElement(createElement(relationship.getDestination(), StructurizrElement.class));
            relationships.add(sRelationship);
        }
        return relationships;
    }
}
