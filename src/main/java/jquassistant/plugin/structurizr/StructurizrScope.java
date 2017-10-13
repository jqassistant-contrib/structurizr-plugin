package jquassistant.plugin.structurizr;

import com.buschmais.jqassistant.core.scanner.api.ScannerContext;
import com.buschmais.jqassistant.core.scanner.api.Scope;

/**
 * @author mh
 * @since 07.03.15
 */
public enum StructurizrScope implements Scope {

    /**
     * Defines the scope of a connection to a database.
     */
    CONNECTION;

    @Override
    public String getPrefix() {
        return "structurizr";
    }

    @Override
    public String getName() {
        return name();
    }
}
