package org.traccar.client;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.traccar.client.model.Device;

import java.util.ArrayList;

public class DeviceListActivity extends Activity {
    public static final String TAG = DeviceListActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private DeviceListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SharedPreferences preferences;
    private String address;
    private int port;
    private boolean secure;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Devices");

        mRecyclerView = (RecyclerView) findViewById(R.id.devices_list);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new DeviceListAdapter(new ArrayList<Device>());
        mRecyclerView.setAdapter(mAdapter);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        address = preferences.getString(MainActivity.KEY_ADDRESS, null);
        port = Integer.parseInt(preferences.getString(MainActivity.KEY_PORT, null));
        secure = preferences.getBoolean(MainActivity.KEY_SECURE, false);

        String request = ProtocolFormatter.formatDevicesRequest(address, port, secure);
        RequestManager.sendRequestAsync(request, new RequestManager.RequestHandler() {

            @Override
            public void onComplete(String response) {
                if (!response.isEmpty()) {
                    // TODO: Process the JSON here
                } else {
                    Log.e(TAG, "Responded with nothing!");
                }
            }
        });


    }
//    private class GetDevices extends AsyncTask<Void, Void, ArrayList<Device>> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            Toast.makeText(DeviceListActivity.this,
//                    "Json Data is downloading",Toast.LENGTH_LONG).show();
//        }
//        @Override
//        protected ArrayList<Device> doInBackground(Void... params) {
//            //TODO: Get this from settings
//            String serverUrl = "http://cydonian.homelinux.net:8082";
//            String deviceAPIUrl = serverUrl + "/api/devices";
//            HttpHandler sh = new HttpHandler();
//            String jsonStr = sh.makeServiceCall(deviceAPIUrl);
//
//            JsonParser parser = new JsonParser();
//            ArrayList<Device> devices = null;
//            try {
//                devices = parser.ParseDevices(jsonStr);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            // TODO: Make the array of devices from the JSON returned:
//            // example: https://www.tutorialspoint.com/android/android_json_parser.htm
//            return devices;
//        }
//        @Override
//        protected void onPostExecute(ArrayList<Device> result) {
//            super.onPostExecute(result);
//            // TODO: Set the data in the d
//            mAdapter.refill(result);
//        }
//    }
}
