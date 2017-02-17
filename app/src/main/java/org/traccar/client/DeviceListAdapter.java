package org.traccar.client;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.traccar.client.model.Device;

import java.util.ArrayList;
import java.util.HashMap;

import static org.traccar.client.R.id.speed;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.ViewHolder>  {

    ArrayList<Device> mDevices;
    HashMap<String, Position> mPositions;

    // Provide a suitable constructor (depends on the kind of dataset)
    public DeviceListAdapter(ArrayList<Device> myDataset) {
        mDevices = myDataset;
    }

    public DeviceListAdapter() {
    }
    public void refill(ArrayList<Device> devices) {
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
            holder.mName.setText(device.getName());
            holder.mPosition.setText(device.getLastUpdate().toString());
            holder.mSpeed.setText(device.getId());
        }
    }

    @Override
    public int getItemCount() {
        return mDevices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mDeviceListItem;
        public TextView mName;
        public TextView mPosition;
        public TextView mSpeed;

        public ViewHolder(View item) {
            super(item);
            mDeviceListItem = item;
            mName = (TextView)item.findViewById(R.id.device_name);
            mPosition = (TextView)item.findViewById(R.id.position);
            mSpeed = (TextView)item.findViewById(speed);
        }
    }
}
