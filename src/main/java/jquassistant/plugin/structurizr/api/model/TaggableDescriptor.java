package jquassistant.plugin.structurizr.api.model;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

import java.util.List;

/**
 * @author mh
 * @since 06.03.15
 */
@Label("Tagged") // todo not needed, just for this error:  use the same set of discriminators [Structurizr]
public interface TaggableDescriptor extends StructurizrDescriptor {
    @Relation("TAG")
    List<TagDescriptor> getTags();
    void setTags(List<TagDescriptor> tags);
}
