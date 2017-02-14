package org.traccar.client.model;

/*
 * THis class maps to the information returned by the Traccar API GET /devices
 * https://www.traccar.org/api-reference/#definitions_Device
 */
public class Device {
    public String category;
    public String contact;
    //public ArrayList<Geofence> geofenceIds;
    public String geofenceIds;
    public String groupId;
    public String id;
    public String lastUpdate;
    public String model;
    public String name;
    public String phone;
    public String positionId;
    public String status;
    public String uniqueId;


    public Device(String id,
                  String name,
                  String uniqueId,
                  String status,
                  String lastUpdate,
                  String positionId,
                  String groupId,
                  String geofenceIds,
                  String phone,
                  String model,
                  String contact,
                  String category) {

        this.id = id;
        this.name = name;
        this.uniqueId = uniqueId;
        this.status = status;
        this.lastUpdate = lastUpdate;
        this.positionId = positionId;
        this.groupId = groupId;
        this.geofenceIds = geofenceIds;
        this.phone = phone;
        this.model = model;
        this.contact = contact;
        this.category = category;

    }




}
