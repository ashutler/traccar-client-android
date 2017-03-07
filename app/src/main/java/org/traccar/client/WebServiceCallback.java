package org.traccar.client;

import android.content.Context;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class WebServiceCallback<T> implements Callback<T> {

    private Context context;

    public WebServiceCallback(Context context) {
        this.context = context;
    }

    public abstract void onSuccess(Response<T> response);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onSuccess(response);
        } else {
            onFailure(call, new ServiceException(response.message()));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        String text;
        if (t instanceof ServiceException) {
            text = context.getString(R.string.error_general);
        } else {
            text = context.getString(R.string.error_connection);
        }
        Toast.makeText(context, text + ": " + t.getMessage(), Toast.LENGTH_LONG).show();
    }

}
