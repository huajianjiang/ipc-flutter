package com.test.ipc.ipc;

import android.content.Intent;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity implements MethodChannel.MethodCallHandler {

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        new MethodChannel(flutterEngine.getDartExecutor()
                .getBinaryMessenger(), "com.test.ipc/ipc")
                .setMethodCallHandler(this);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        if (call.method.equals("open")) {
            doOpen(call, result);
        } else {
            result.notImplemented();
        }
    }

    private void doOpen(@NonNull MethodCall call,@NonNull MethodChannel.Result result) {
        Map<Object, Object> args =  call.arguments();

        Intent sendIntent = new Intent("com.demo.icp.ACTION_IPC");
        JSONObject data = new JSONObject();
        try {
            data.put("field1", args.get("field1"));
            data.put("field2", args.get("field2"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sendIntent.putExtra("com.demo.icp.EXTRA_IPC_DATA", data.toString());
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(sendIntent);
    }

}
