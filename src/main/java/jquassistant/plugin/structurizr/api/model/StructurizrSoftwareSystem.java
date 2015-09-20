package jquassistant.plugin.structurizr.api.model;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import jquassistant.plugin.structurizr.api.model.base.Located;

import java.util.List;

/**
 * @author mh
 * @since 06.03.15
 */
@Label("SoftwareSystem")
public interface StructurizrSoftwareSystem extends StructurizrElement, Located {

    @Relation
    @Relation.Incoming
    List<StructurizrContainer> getContainers();
    void setContainers(List<StructurizrContainer> container);
}
