package com.xcgmym.jiajuwulian;

import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

public class KeHuDuan
{
	private Socket socket = null;
	private InputStream inputStream = null;
	private OutputStream outputStream = null;
	private Timer timer = null;
	private boolean isContinue = true;

	public KeHuDuan()
	{
		System.out.println("打开");
		try
		{
			socket = new Socket("www.xcgmym.com", 6000);
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
		}catch(UnknownHostException uhe)
		{
		}catch(IOException ioe)
		{
		}
		timer = new Timer("KeHuDuan", true);
		timer.schedule(new TimerTask(){
			public void run()
			{
				try
				{
					outputStream.write("{\"abc\":\"nihao\",\"state\":\"hello\"}".getBytes());
					outputStream.flush();
				}catch(IOException ioe)
				{
					System.out.println("FaSongShiBaituichu");
					close();
				}
			}
		},100, 10000);

	}

	public void close()
	{
		isContinue = false;
		if(timer != null)
		{
			timer.cancel();
		}
		try
		{
			if(inputStream != null)
			{
				inputStream.close();
			}
			if(outputStream != null)
			{
				outputStream.close();
			}
			if(socket != null)
			{
				socket.close();
			}
		}catch(IOException ioe)
		{
			System.out.println();
		}
	}

	public static void main(String[] arg)
	{
		KeHuDuan keHuDuan = new KeHuDuan();
		System.out.println(new String("欢迎测试".getBytes()));
	//	while(keHuDuan.isContinue)
		{
			try
			{
				Thread.sleep(5000);
			}catch(InterruptedException ie)
			{
			}
		}
		
	}
}
