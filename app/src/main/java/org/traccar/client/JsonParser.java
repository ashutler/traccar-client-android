package org.traccar.client;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.traccar.client.model.Device;

import java.util.ArrayList;


public class JsonParser {


    private static final String TAG = JsonParser.class.getSimpleName();

    public ArrayList<Device> ParseDevices(String jsonString) throws JSONException {
        ArrayList<Device> devices = new ArrayList<Device>();
        if (jsonString != null) {
            Log.d(TAG, "JSON returned = " + jsonString);
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int index = 0; index < jsonArray.length(); index++) {
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
        } else {
            Log.e(TAG, "JSON string is empty!");
        }
        return devices;
    }
}
