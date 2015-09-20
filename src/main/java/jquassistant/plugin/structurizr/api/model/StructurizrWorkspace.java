package jquassistant.plugin.structurizr.api.model;

import com.buschmais.jqassistant.core.store.api.model.NamedDescriptor;
import com.buschmais.xo.neo4j.api.annotation.Relation;
import jquassistant.plugin.structurizr.api.model.base.Description;
import jquassistant.plugin.structurizr.api.model.base.IdDescriptor;

import java.util.List;

/**
 * @author mh
 * @since 06.03.15
 */
public interface StructurizrWorkspace extends StructurizrDescriptor,IdDescriptor,NamedDescriptor,Description {

    @Relation("MODEL")
    StructurizrModel getModel();
    void setModel(StructurizrModel model);


}
