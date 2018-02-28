package com.xcgmym.jiajuwulian;

import java.net.ServerSocket;
import java.util.Timer;
import java.util.TimerTask;
import java.io.IOException;
import java.nio.channels.IllegalBlockingModeException;

public class FuWuQi
{
	private ServerSocket serverSocket = null;
	private Timer timer = null;

	public FuWuQi()
	{
		try
		{
			serverSocket = new ServerSocket(6000);
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
							new ZiFuWu(serverSocket.accept());
						}catch(IOException ioe)
						{
							System.out.println("打开输入输出失败:"+ioe.toString());
						}catch(SecurityException see)
						{
							System.out.println("security:"+see.toString());
						}catch(IllegalArgumentException iae)
						{
							System.out.println("iae:"+iae.toString());
						}					}
				}, 100, 100);
	}
}
