package org.traccar.client;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONException;
import org.traccar.client.model.Device;

import java.util.ArrayList;

public class DeviceListActivity extends Activity {
    private RecyclerView mRecyclerView;
    private DeviceListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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

        new GetDevices().execute();

    }
    private class GetDevices extends AsyncTask<Void, Void, ArrayList<Device>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(DeviceListActivity.this,
                    "Json Data is downloading",Toast.LENGTH_LONG).show();
        }
        @Override
        protected ArrayList<Device> doInBackground(Void... params) {
            //TODO: Get this from settings
            String serverUrl = "http://cydonian.homelinux.net:8082";
            String deviceAPIUrl = serverUrl + "/api/devices";
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(deviceAPIUrl);

            JsonParser parser = new JsonParser();
            ArrayList<Device> devices = null;
            try {
                devices = parser.ParseDevices(jsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // TODO: Make the array of devices from the JSON returned:
            // example: https://www.tutorialspoint.com/android/android_json_parser.htm
            return devices;
        }
        @Override
        protected void onPostExecute(ArrayList<Device> result) {
            super.onPostExecute(result);
            // TODO: Set the data in the d
            mAdapter.refill(result);
        }
    }
}
