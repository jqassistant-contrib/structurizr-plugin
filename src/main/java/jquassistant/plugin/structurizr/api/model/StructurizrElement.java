package jquassistant.plugin.structurizr.api.model;

import com.buschmais.jqassistant.core.store.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Indexed;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import jquassistant.plugin.structurizr.api.model.base.Description;
import jquassistant.plugin.structurizr.api.model.base.IdDescriptor;

import java.util.List;

/**
 * @author mh
 * @since 06.03.15
 */
public interface StructurizrElement extends StructurizrDescriptor,TaggableDescriptor,NamedDescriptor,IdDescriptor,Description {

    @Indexed
    @Override
    String getName();

    @Relation()
//    @Relation.Incoming
    @Relation.Outgoing
    List<StructurizrRelationship> getRelationships();
    void setRelationships(List<StructurizrRelationship> relationships);
}
