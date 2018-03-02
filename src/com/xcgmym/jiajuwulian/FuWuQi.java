package com.xcgmym.jiajuwulian;

import java.net.ServerSocket;
import java.util.Timer;
import java.util.TimerTask;
import java.io.IOException;
import java.nio.channels.IllegalBlockingModeException;

public class FuWuQi implements ZiFuWuJianTing
{
	private ServerSocket serverSocket = null;
	private Timer timer = null;

	public FuWuQi()
	{
		try
		{
			System.out.println("这是一个服务器程序，即将打开6001端口监听");
			serverSocket = new ServerSocket(6001);
		}catch(IOException ioe)
		{
			System.out.println("打开输入输出失败:"+ioe.toString());
		}catch(SecurityException see)
		{
			System.out.println("security:"+see.toString());
		}catch(IllegalArgumentException iae)
		{
			System.out.println("iae:"+iae.toString());
		}
		
		timer = new Timer("ServerSocket", true);
		timer.schedule(new TimerTask()
				{
					public void run()
					{
						try
						{
							new ZiFuWu(serverSocket.accept(), FuWuQi.this);
						}catch(IOException ioe)
						{
							System.out.println("打开输入输出失败:"+ioe.toString());
						}catch(SecurityException see)
						{
							System.out.println("security:"+see.toString());
						}catch(IllegalArgumentException iae)
						{
							System.out.println("iae:"+iae.toString());
						}					
					}
				}, 100, 100);
	}

	public void close()
	{
		if(timer != null)
		{
			timer.cancel();
		}
		if(serverSocket != null)
		{
			try
			{
				serverSocket.close();
			}catch(IOException ioe)
			{
				
			}
		}
	}
	public void huoQuDao(byte[] arg)
	{
		System.out.println("获取到一个包");
		System.out.println("arg:"+arg.toString());
	}

	public static void main(String[] args)
	{
		new FuWuQi();
		while(true)
		{
			try
			{
				Thread.sleep(1000);
			}catch(InterruptedException ie)
			{
			}
		}
	}
}
