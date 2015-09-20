package jquassistant.plugin.structurizr.api.model.base;

import com.buschmais.xo.neo4j.api.annotation.Indexed;

/**
 * @author mh
 * @since 06.03.15
 */
public interface IdDescriptor {
    @Indexed(unique = true)
    String getId();
    void setId(String id);
}
