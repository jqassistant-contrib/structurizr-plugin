package org.jqassistant.contrib.plugin.structurizr.api.model;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import org.jqassistant.contrib.plugin.structurizr.api.model.base.CanonicalName;
import org.jqassistant.contrib.plugin.structurizr.api.model.base.Technology;

import java.util.List;

/**
 * @author mh
 * @since 06.03.15
 */
@Label("Container")
public interface StructurizrContainer extends StructurizrElement, Technology, CanonicalName {

    @Relation("SYSTEM")
    @Relation.Outgoing
    StructurizrSoftwareSystem getSystem();
    void setSystem(StructurizrSoftwareSystem system);

    @Relation
    @Relation.Incoming
    List<StructurizrComponent> getComponents();
    void setComponents(List<StructurizrComponent> components);
}
