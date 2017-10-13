package jquassistant.plugin.structurizr.api.model.base;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;
import com.buschmais.xo.api.CompositeObject;
import com.buschmais.xo.neo4j.api.annotation.Indexed;

/**
 * @author mh
 * @since 06.03.15
 */
public interface IdDescriptor extends Descriptor, CompositeObject {
    @Indexed(unique = true)
    String getElementId();
    void setElementId(String id);
}
