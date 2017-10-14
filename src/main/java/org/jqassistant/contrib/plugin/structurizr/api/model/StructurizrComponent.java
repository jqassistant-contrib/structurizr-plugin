package org.jqassistant.contrib.plugin.structurizr.api.model;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import org.jqassistant.contrib.plugin.structurizr.api.model.base.Technology;

/**
 * @author mh
 * @since 06.03.15
 */
@Label("Component")
public interface StructurizrComponent extends StructurizrElement, Technology {

    @Relation("CONTAINER")
    @Relation.Outgoing
    StructurizrContainer getContainer();
    void setContainer(StructurizrContainer container);

    String getType();
    void setType(String interfaceType);

// tries to instantiate the class
//    void setPackage(String aPackage);
//    String getPackage();

    void setSize(long size);
    long getSize();
}
