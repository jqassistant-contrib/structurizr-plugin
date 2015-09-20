package jquassistant.plugin.structurizr.api.model;

import com.buschmais.xo.neo4j.api.annotation.Relation;

import java.util.List;

/**
 * @author mh
 * @since 06.03.15
 */
public interface TaggableDescriptor extends StructurizrDescriptor {
    @Relation("TAG")
    List<TagDescriptor> getTags();
    void setTags(List<TagDescriptor> tags);
}
