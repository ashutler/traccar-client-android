package org.traccar.client.model;

/**
 * THis class maps to the information returned by the Traccar API GET /devices
 * https://www.traccar.org/api-reference/#definitions_Device
 */

public class Position {
    public String address;
    public float altitude;
    public String attributes;
    public float course;
    public int deviceId;
    public String deviceTime;
    public String fixTime;
    public int id;
    public double latitude;
    public double longitude;
    public boolean outdated;
    public String protocol;
    public String serverTime;
    public float speed;
    public boolean valid;
}
