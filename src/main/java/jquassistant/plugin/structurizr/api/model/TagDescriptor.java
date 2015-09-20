package jquassistant.plugin.structurizr.api.model;

import com.buschmais.jqassistant.core.store.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;

/**
 * @author mh
 * @since 06.03.15
 */
@Label("Tag")
public interface TagDescriptor extends StructurizrDescriptor, NamedDescriptor {
}
