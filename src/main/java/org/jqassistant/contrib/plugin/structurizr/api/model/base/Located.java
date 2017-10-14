package org.jqassistant.contrib.plugin.structurizr.api.model.base;

/**
 * @author mh
 * @since 07.03.15
 */
public interface Located {
    com.structurizr.model.Location getLocation();

    void setLocation(com.structurizr.model.Location location);
}
