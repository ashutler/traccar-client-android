package org.traccar.client;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import junit.framework.Assert;

import org.traccar.client.model.Device;
import org.traccar.client.model.Position;
import org.traccar.client.model.User;
import org.traccar.client.model.WebService;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class DeviceListActivity extends Activity {
    private RecyclerView mRecyclerView;
    private DeviceListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Devices");
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.devices_list);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new DeviceListAdapter(new ArrayList<Device>(),
                new HashMap<String, List<Position>>());
        mRecyclerView.setAdapter(mAdapter);

        new GetDevices().execute();

    }
    private class GetDevices extends AsyncTask<Void, Void, Void> {

        private List<User> users = null;
        private List<Device> devices = null;
        private List<Position> positions = null;
        private HashMap<String, List<Position>> deviceMap = new HashMap<String, List<Position>>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(DeviceListActivity.this,
                    "Json Data is downloading",Toast.LENGTH_LONG).show();
        }
        @Override
        protected Void doInBackground(Void... params) {
            //TODO: Get this from settings
            //preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE); "caixI8tLJk4x";

            String username = preferences.getString(getString(R.string.key_username), "user");
            String password = preferences.getString(getString(R.string.key_password), "password");
            String domain = preferences.getString(getString(R.string.key_address), getString(R.string.key_address_default));
            String port = preferences.getString(getString(R.string.key_port), getString(R.string.key_port_default));
            String serverURL = "http://" + domain + ":" + port;

            Log.d("SETTINGS DATA: ", "Domain: " + domain + "\n" +
                                     "Port: " + port + "\n" +
                                     "Server URL: " + serverURL + "\n");

            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            OkHttpClient client = new OkHttpClient.Builder()
                    .cookieJar(new JavaNetCookieJar(cookieManager)).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(serverURL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            WebService service = retrofit.create(WebService.class);

            try {
                User user = service.addSession(username, password).execute().body();
                users = service.getUsers().execute().body();
                devices = service.getDevices().execute().body();
                // Get the positions for each device
                // TODO: pass these separately into the adapter
                for(Device device: devices) {
                    Log.d("RESONSE BODY --> ", service.getPositions(device.getId()).execute().body().toString());
                    positions = service.getPositions(device.getId()).execute().body();
                    if (positions != null && device.getId() != null) {
                        deviceMap.put(device.getId(), positions);
                    }
                }
                Assert.assertNotNull(user);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            // TODO: Set the data in the d
            mAdapter.refill((ArrayList<Device>) devices, deviceMap);
        }

    }
}
