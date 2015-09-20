package jquassistant.plugin.structurizr.api.model;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import jquassistant.plugin.structurizr.api.model.base.CanonicalName;
import jquassistant.plugin.structurizr.api.model.base.Located;
import jquassistant.plugin.structurizr.api.model.base.Technology;

/**
 * @author mh
 * @since 06.03.15
 */
@Label("Person")
public interface StructurizrPerson extends StructurizrElement, Located, CanonicalName {

    @Relation("CONTAINER")
    @Relation.Outgoing
    StructurizrContainer getContainer();
    void setContainer(StructurizrContainer container);

    String getInterfaceType();
    void setInterfaceType(String interfaceType);

    String getImplementationType();
    void setImplementationType(String implementationType);
}
