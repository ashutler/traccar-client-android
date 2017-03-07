package org.traccar.client;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.traccar.client.model.Device;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;

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

        final MainApplication application = (MainApplication)getApplication();
        application.getServiceAsync(new MainApplication.GetServiceCallback() {
            @Override
            public void onServiceReady(OkHttpClient client, Retrofit retrofit, org.traccar.client.model.WebService service) {
                service.getDevices().enqueue(new WebServiceCallback<List<Device>>(DeviceListActivity.this) {
                    @Override
                    public void onSuccess(Response<List<Device>> response) {
                        mAdapter = new DeviceListAdapter(response.body());
                        mRecyclerView.setAdapter(mAdapter);
                    }
                });
            }

            @Override
            public boolean onFailure() {
                return false;
            }
        });

    }
}
