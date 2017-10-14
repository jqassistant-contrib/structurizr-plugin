package org.jqassistant.contrib.plugin.structurizr.api.model;

import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;

/**
 * @author mh
 * @since 06.03.15
 */
@Label("Tag")
public interface TagDescriptor extends StructurizrDescriptor, NamedDescriptor {
}
