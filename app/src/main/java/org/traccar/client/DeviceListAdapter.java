package org.traccar.client;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.traccar.client.model.Device;
import org.traccar.client.model.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.traccar.client.R.id.speed;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.ViewHolder>  {

    ArrayList<Device> mDevices;
    HashMap<String, List<Position>> mPositions;

    // Provide a suitable constructor (depends on the kind of dataset)
    public DeviceListAdapter(ArrayList<Device> myDataset,
                             HashMap<String, List<Position>> positions) {
        mDevices = myDataset;
        mPositions = positions;
    }

    public DeviceListAdapter() {
    }
    public void refill(ArrayList<Device> devices, HashMap<String, List<Position>> positions) {
        mPositions.clear();
        mPositions = positions;
        mDevices.clear();
        mDevices = devices;
        notifyDataSetChanged();
    }

    @Override
    public DeviceListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(item);
        return vh;
    }

    @Override
    public void onBindViewHolder(DeviceListAdapter.ViewHolder holder, int position) {
        Device device = mDevices.get(position);

        if (device != null) {
            holder.deviceName.setText(device.getName());
            holder.devicePosition.setText(mPositions.get(device.getId()).get(0).getLatitude() + " / " + mPositions.get(device.getId()).get(0).getLongitude());
            holder.deviceSpeed.setText(String.valueOf(mPositions.get(device.getId()).get(0).getSpeed()));
            //holder.mPosition.setText(device.getLastUpdate().toString());
            //holder.mSpeed.setText(device.getId());
        }
    }

    @Override
    public int getItemCount() {
        return mDevices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View deviceListItem;
        public TextView deviceName;
        public TextView devicePosition;
        public TextView deviceSpeed;

        public ViewHolder(View item) {
            super(item);
            deviceListItem = item;
            deviceName = (TextView)item.findViewById(R.id.device_name);
            devicePosition = (TextView)item.findViewById(R.id.position);
            deviceSpeed = (TextView)item.findViewById(speed);
        }
    }
}
