package org.jqassistant.contrib.plugin.structurizr.impl;

import java.io.IOException;
import java.net.URI;

import org.jqassistant.contrib.plugin.structurizr.api.model.StructurizrWorkspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.core.scanner.api.Scope;
import com.buschmais.jqassistant.core.store.api.Store;
import com.buschmais.jqassistant.plugin.common.api.scanner.AbstractScannerPlugin;
import com.structurizr.Workspace;
import com.structurizr.api.StructurizrClient;

/**
 * @author mh
 * @since 06.03.15
 */
public abstract class StructurizrScannerPlugin extends AbstractScannerPlugin<URI, StructurizrWorkspace> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StructurizrScannerPlugin.class);

    @Override
    public boolean accepts(URI uri, String s, Scope scope) throws IOException {
        return uri.getHost().equals("api.structurizr.com");
    }

    @Override
    public StructurizrWorkspace scan(URI uri, String s, Scope scope, Scanner scanner) throws IOException {
        Workspace workspace = loadWorkspace(uri);
        Store store = scanner.getContext().getStore();
        return new StructurizrConverter(store).convertWorkspace(workspace);
    }

    private Workspace loadWorkspace(URI uri) throws IOException {
        StructurizrClient client = createStructurizrClient(uri);
        long workSpaceId = Integer.parseInt(uri.getQuery().split("=")[1]);
        Workspace workspace;
        try {
            workspace = client.getWorkspace(workSpaceId);
        } catch (Exception e) {
            throw new IOException("Error loading workspace from "+uri,e);
        }
        if (workspace.getId() != workSpaceId)
            throw new IOException("Workspace id " + workspace.getId() + " differs from requested id " + workSpaceId);
        return workspace;
    }

    private StructurizrClient createStructurizrClient(URI uri) throws IOException {
        String apiKey = getStringProperty("structurizr.api-key", null);
        if (apiKey == null) throw new IOException("Structurizr config missing: api-key");
        String apiSecret = getStringProperty("structurizr.api-secret", null);
        if (apiSecret == null) throw new IOException("Structurizr config missing: api-key");
        return new StructurizrClient(uri.toASCIIString(), apiKey, apiSecret);
    }
}
