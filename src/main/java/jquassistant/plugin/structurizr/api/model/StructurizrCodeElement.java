package jquassistant.plugin.structurizr.api.model;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import com.structurizr.model.CodeElementRole;
import jquassistant.plugin.structurizr.api.model.base.Technology;

/**
 * @author mh
 * @since 06.03.15
 */
@Label("CodeElement")
public interface StructurizrCodeElement extends StructurizrElement {

    @Relation("COMPONENT")
    @Relation.Outgoing
    StructurizrComponent getComponent();
    void setComponent(StructurizrComponent component);

    String getType();
    void setType(String interfaceType);

    void setLanguage(String language);
    String getLanguage();

    void setSize(long size);
    long getSize();

    void setRole(CodeElementRole role);
    CodeElementRole getRole();
}
