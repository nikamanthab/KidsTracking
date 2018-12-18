package com.example.nitinnikamanth.kidstracking;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import java.util.List;
import java.util.UUID;

public class DummyActivity extends Activity{

    private BeaconManager beaconManager;
    private BeaconRegion beaconRegion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        beaconManager= new BeaconManager(DummyActivity.this);
        beaconRegion = new BeaconRegion("myRegion" , UUID.fromString("b9407f30-f5f8-466e-aff9-25556b57fe6d") , 1 , 2);

        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion beaconRegion, List<Beacon> beacons) {
                Log.e("TAG" , "Beacon Size : "+ beacons.size());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(beaconRegion);
            }
        });
    }
}
