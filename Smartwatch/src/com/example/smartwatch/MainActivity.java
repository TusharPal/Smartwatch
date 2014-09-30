package com.example.smartwatch;

import java.io.IOException;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity
{
	private BluetoothAdapter bluetoothAdapter;
	private ThreadDataStream threadDataStream;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		startService(new Intent(this, ServiceNotifications.class));
	}

	protected void onPause()
	{
		super.onPause();
	}
	
	protected void onResume()
	{
		super.onResume();
	}
	
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()) 
	    {
	    	case R.id.action_main_connect:
	    	{
	    		try
	    		{
	    			connect();
	    		}
	    		catch(IOException ioe)
	    		{
	    			
	    		}
	    		
	    		break;
	    	}
	    	case R.id.action_main_disconnect:
	    	{
	    		if(threadDataStream!=null || !threadDataStream.isInterrupted())
	    		{
	    			disconnect();
	    		}
	    		
	    		break;
	    	}
	    	case R.id.action_main_settings:
	    	{
	    		break;
	    	}
	    }

	    return true;
	}
	
	private void connect()throws IOException 
	{
		bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
	    
	    if(bluetoothAdapter==null) 
	    {
	    	Toast.makeText(getApplicationContext(), "Bluetooth adapter not found!", Toast.LENGTH_SHORT).show(); 
	    }
	    else if(!bluetoothAdapter.isEnabled()) 
	    {
	    	startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 0);
	    	
	    	threadDataStream=new ThreadDataStream("BluetoothDataStream", bluetoothAdapter);
	    	threadDataStream.start();
	    }
	    else
	    {
	    	threadDataStream=new ThreadDataStream("BluetoothDataStream", bluetoothAdapter);
	    	threadDataStream.start();
	    }
	    
	    
	}
	
	private void disconnect()
	{
		stopService(new Intent(this, ServiceNotifications.class));
		threadDataStream.interrupt();
	}
	
}
