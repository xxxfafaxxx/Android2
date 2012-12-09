package com.example.zpi;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.zpi.communication.Connect;
import com.example.zpi.communication.NoInternetException;
import com.example.zpi.communication.ServerErrorException;
import com.google.android.gcm.GCMBaseIntentService;


public class GCMIntentService extends GCMBaseIntentService {
	
	final private static String SENDER_ID = "303941619301";

	
	public GCMIntentService(){
		super(SENDER_ID);

	}

	@Override
	protected void onError(Context arg0, String arg1) {
		Toast.makeText(arg0, "Error: "+arg1, Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onMessage(Context arg0, Intent arg1) {
		// TODO What's up tutaj
		throw new UnsupportedOperationException();
	}

	@Override
	protected void onRegistered(Context arg0, String regId) {
        Connect c = new Connect(arg0);
        try {
            c.requestRegister(regId);
        } catch (ServerErrorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoInternetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

	@Override
	protected void onUnregistered(Context arg0, String regId) {
		// TODO unregister do serwera tutaj
		throw new UnsupportedOperationException();
	}

}
