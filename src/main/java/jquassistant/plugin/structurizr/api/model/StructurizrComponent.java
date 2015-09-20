package jquassistant.plugin.structurizr.api.model;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import jquassistant.plugin.structurizr.api.model.base.Technology;

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

    // todo, these are fqn classes ??
    String getInterfaceType();
    void setInterfaceType(String interfaceType);

    // todo, these are fqn classes ??
    String getImplementationType();
    void setImplementationType(String implementationType);

    void setPackage(String aPackage);
    String getPackage();

    void setSourcePath(String sourcePath);
    String getSourcePath();
}
