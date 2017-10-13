package jquassistant.plugin.structurizr.api.model;

import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.api.annotation.Relation;

import java.util.List;

/**
 * @author mh
 * @since 06.03.15
 */
@Label("Model")
public interface StructurizrModel extends StructurizrDescriptor {

    @Relation("SYSTEM")
    List<StructurizrSoftwareSystem> getSystems();
    void setSystems(List<StructurizrSoftwareSystem> systems);

    @Relation("PERSON")
    List<StructurizrPerson> getPeople();
    void setPeople(List<StructurizrPerson> people);

}
