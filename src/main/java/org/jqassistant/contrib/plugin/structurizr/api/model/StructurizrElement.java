package org.jqassistant.contrib.plugin.structurizr.api.model;

import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Indexed;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import org.jqassistant.contrib.plugin.structurizr.api.model.base.Description;
import org.jqassistant.contrib.plugin.structurizr.api.model.base.IdDescriptor;

import java.util.List;

/**
 * @author mh
 * @since 06.03.15
 */
@Label("Element")  // todo not needed, just for this error:  use the same set of discriminators [Structurizr]
public interface StructurizrElement extends StructurizrDescriptor,TaggableDescriptor,NamedDescriptor,IdDescriptor,Description {

    @Indexed
    @Override
    String getName();

    @Relation()
//    @Relation.Incoming
    @Relation.Outgoing
    List<StructurizrRelationship> getRelationships();
    void setRelationships(List<StructurizrRelationship> relationships);

    String getCanonicalName();
    void setCanonicalName(String canonicalName);

    void setUrl(String url);
    String getUrl();
}
