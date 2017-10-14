package org.jqassistant.contrib.plugin.structurizr.impl;

import java.util.ArrayList;
import java.util.List;

import org.jqassistant.contrib.plugin.structurizr.api.model.*;

import com.buschmais.jqassistant.core.store.api.Store;
import com.structurizr.Workspace;
import com.structurizr.model.*;
import com.structurizr.view.ViewSet;

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
        structurizrWorkspace.setElementId(String.valueOf(workspace.getId()));
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
        sElement.setElementId(element.getId());
        sElement.setName(element.getName());
        sElement.setDescription(element.getDescription());
        sElement.setUrl(element.getUrl());
        sElement.setCanonicalName(element.getCanonicalName());
        List<TagDescriptor> tags = new ArrayList<>();
        for (String tagName : element.getTags().split(",\\s*")) {
            TagDescriptor tag = store.create(TagDescriptor.class);
            tag.setName(tagName.trim());
            tags.add(tag);
        }
        sElement.setTags(tags);
// todo relationships ??
//        sElement.setRelationships(convertRelationships(store, element));
//        sElement.set(element.getParent())
//        sElement.set(element.getModel())
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
        sSystem.setLocation(system.getLocation());
        sSystem.setContainers(convertContainers(system, sSystem));
        return sSystem;
    }

    private List<StructurizrContainer> convertContainers(SoftwareSystem system, StructurizrSoftwareSystem sSystem) {
        List<StructurizrContainer> containers = new ArrayList<>();
        for (Container container : system.getContainers()) {
            StructurizrContainer sContainer = createElement(container, StructurizrContainer.class);
            sContainer.setSystem(sSystem);
            sContainer.setTechnology(container.getTechnology());
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
        String type = component.getType();
        if (type != null) {
            sComponent.setType(type);
            // todo workaround
            if (type.indexOf('.') != -1) {
                // tries to instantiate the class
                // sComponent.setPackage(component.getPackage());
            }
        }
        sComponent.setTechnology(component.getTechnology());
        sComponent.setSize(component.getSize());

        for (CodeElement codeElement : component.getCode()) {
            StructurizrCodeElement sCodeElement = convertCodeElement(codeElement);
            sCodeElement.setComponent(sComponent);
        }
        return sComponent;
    }

    private StructurizrCodeElement convertCodeElement(CodeElement codeElement) {
        StructurizrCodeElement sElement = store.create(StructurizrCodeElement.class);
        sElement.setName(codeElement.getName());
        sElement.setDescription(codeElement.getDescription());
        sElement.setUrl(codeElement.getUrl());
        sElement.setSize(codeElement.getSize());
        sElement.setLanguage(codeElement.getLanguage());
        sElement.setRole(codeElement.getRole());
        sElement.setType(codeElement.getType());
        return sElement;
    }

    // todo replace with real relationships, map rel-name to rel-type or alternative target-entity-type-name
    private List<StructurizrRelationship> convertRelationships(Store store, Element element) {
        List<StructurizrRelationship> relationships = new ArrayList<>();
        for (Relationship relationship : element.getRelationships()) {
            StructurizrRelationship sRelationship = store.create(StructurizrRelationship.class);
            sRelationship.setElementId(relationship.getId());
            sRelationship.setDescription(relationship.getDescription());
            sRelationship.setFromElement(createElement(relationship.getSource(), StructurizrElement.class));
            sRelationship.setToElement(createElement(relationship.getDestination(), StructurizrElement.class));
            relationships.add(sRelationship);
        }
        return relationships;
    }
}
