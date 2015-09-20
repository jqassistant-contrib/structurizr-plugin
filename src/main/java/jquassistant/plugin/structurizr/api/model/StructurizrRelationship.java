package jquassistant.plugin.structurizr.api.model;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import jquassistant.plugin.structurizr.api.model.base.Description;
import jquassistant.plugin.structurizr.api.model.base.IdDescriptor;

import javax.validation.metadata.ElementDescriptor;

/**
 * @author mh
 * @since 06.03.15
 */
@Label("Relationship")
public interface StructurizrRelationship extends TaggableDescriptor, Description, IdDescriptor {
    @Relation("FROM")
    StructurizrElement getFromElement();
    void setFromElement(StructurizrElement from);

    @Relation("TO")
    StructurizrElement getToElement();
    void setToElement(StructurizrElement to);
}

/*
TODO no generics yet
public interface StructurizrRelationship<F extends StructurizrElement,T extends StructurizrElement> extends TaggableDescriptor, Description, IdDescriptor {
    @Relation("FROM")
    F getFromElement();
    void setFromElement(F from);

    @Relation("TO")
    T getToElement();
    void setToElement(T to);
}
 */
