package com.example.smartwatch;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class ThreadDataStream extends Thread
{
	
	private BluetoothAdapter bluetoothAdapter;
	private BluetoothDevice bluetoothDevice;
	private BluetoothSocket bluetoothSocket;
	private OutputStream outputStream;
	
	public ThreadDataStream(String threadName, BluetoothAdapter bluetoothAdapter)
	{
		super(threadName);
		this.bluetoothAdapter=bluetoothAdapter;
	}
	
	public void run()
	{
		while(!bluetoothAdapter.isEnabled())
		{
			try
			{
				Thread.sleep(10);
			}
			catch(InterruptedException ie)
			{
				
			}
		}
		
		try
		{
			bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
			bluetoothDevice=bluetoothAdapter.getRemoteDevice("20:13:09:30:12:47");
	 
			UUID uuid=UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
			bluetoothSocket=bluetoothDevice.createRfcommSocketToServiceRecord(uuid);    
			bluetoothSocket.connect();
			outputStream=bluetoothSocket.getOutputStream();
		}
		catch(IOException ioe)
		{
			
		}
		
		while(true)
		{
			try
			{
				DateFormat dateFormat=new SimpleDateFormat("HH:mm");
				Date date=new Date();
				sendData(dateFormat.format(date));
			
				Thread.sleep(1000);
			}
			catch(IOException ioe)
			{
				
			}
			catch(InterruptedException ie)
			{
				try
				{
					outputStream.close();
					bluetoothSocket.close();
					break;
				}
				catch(IOException ioe)
				{
					
				}
			}
		}
	}

	void sendData(String s)throws IOException 
	{
		if(outputStream!=null)
		{
			char c[]=s.toCharArray();
			
			for(int i=0;i<s.length();i++)
			{
				outputStream.write(c[i]);
			}
		}
	}
}
