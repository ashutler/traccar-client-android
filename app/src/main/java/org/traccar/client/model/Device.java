package org.traccar.client.model;

import java.util.ArrayList;

/*
 * THis class maps to the information returned by the Traccar API GET /devices
 * https://www.traccar.org/api-reference/#definitions_Device
 */
public class Device {
    public String category;
    public String contact;
    public ArrayList<Geofence> geofenceIds;
    public String groupId;
    public String id;
    public String lastUpdate;
    public String model;
    public String name;
    public String phone;
    public String positionId;
    public String status;
    public String uniqueId;
}
