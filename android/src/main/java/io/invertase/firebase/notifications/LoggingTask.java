package io.invertase.firebase.notifications;

import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Headers;

import io.invertase.firebase.messaging.BundleJSONConverter;

public class LoggingTask extends AsyncTask<Void, Void, Void> {
  private static final String TAG = "LoggingTask";
  private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
  private Bundle loggingRequest;

  LoggingTask(
    Bundle loggingRequest
  ) {
      Log.w(TAG, "Logging Task called");
    this.loggingRequest = loggingRequest;
  }

  @Override
  protected void onPostExecute(Void result) {
    loggingRequest = null;
  }

  @Override
  protected Void doInBackground(Void... voids) {
    // get url
    String url = this.loggingRequest.get("url").toString();

    // get type
    String type = this.loggingRequest.get("type").toString();

    // get headers
    Bundle headers = this.loggingRequest.getBundle("headers");
    Headers.Builder builder = new Headers.Builder();
    builder.add("User-Agent", "OkHttp Headers.java");
    if(headers != null){
      for (String key : headers.keySet()) {
          Object value = headers.get(key);
          builder.add(key, value.toString());
      }
    }
    Headers h = builder.build();

    // get body
    Bundle bodyBundle = this.loggingRequest.getBundle("body");
    try  {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, BundleJSONConverter.convertToJSON(bodyBundle).toString());
          Request request = new Request.Builder()
              .url(url)
              .headers(h)
              .post(body)
              .build();
        try (Response response = client.newCall(request).execute()) {
             Log.w(TAG, response.body().string());
          } catch (IOException exception) {

          }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
  }
}
