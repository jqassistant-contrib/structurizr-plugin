package org.jqassistant.contrib.plugin.structurizr.api.model;

import com.buschmais.jqassistant.plugin.common.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import org.jqassistant.contrib.plugin.structurizr.api.model.base.Description;
import org.jqassistant.contrib.plugin.structurizr.api.model.base.IdDescriptor;

/**
 * @author mh
 * @since 06.03.15
 */
@Label("Workspace")
public interface StructurizrWorkspace extends StructurizrDescriptor,IdDescriptor,NamedDescriptor,Description {

    @Relation("MODEL")
    StructurizrModel getModel();
    void setModel(StructurizrModel model);


}
