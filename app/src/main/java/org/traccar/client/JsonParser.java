package org.traccar.client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.traccar.client.model.Device;

import java.util.ArrayList;


public class JsonParser {



    public ArrayList<Device> ParseDevices(String jsonString) throws JSONException {

        ArrayList<Device> devices = new ArrayList<Device>();
        JSONArray jsonArray = new JSONArray(jsonString);
        for(int index = 0; index < jsonArray.length(); index++) {
            JSONObject jsonObject = jsonArray.getJSONObject(index);
            devices.add(new Device(jsonObject.getString("id"),
                                   jsonObject.getString("name"),
                                   jsonObject.getString("uniqueId"),
                                   jsonObject.getString("status"),
                                   jsonObject.getString("lastUpdate"),
                                   jsonObject.getString("positionId"),
                                   jsonObject.getString("groupId"),
                                   jsonObject.getJSONArray("geoFenceIds").toString(),
                                   jsonObject.getString("phone"),
                                   jsonObject.getString("model"),
                                   jsonObject.getString("contact"),
                                   jsonObject.getString("category")
            ));
        }

        return devices;
    }
}
