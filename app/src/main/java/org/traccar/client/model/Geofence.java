package org.traccar.client.model;

/**
 * THis class maps to the information returned by the Traccar API GET /devices
 * https://www.traccar.org/api-reference/#definitions_Geofence
 */
public class Geofence {
    String area;
    String attributes;
    String description;
    int id;
    String name;
}
