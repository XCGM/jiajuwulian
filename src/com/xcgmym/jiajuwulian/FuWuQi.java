package com.xcgmym.jiajuwulian;

import java.net.ServerSocket;
import java.utils.Timer;
import java.utils.TimerTask;

public class FuWuQi
{
	private ServerSocket serverSocket = null;
	private Timer timer = null;

	public FuWuQi()
	{
		serverSocket = new ServerSocket(6000);
		
		timer = new Timer("ServerSocket", true);
		timer.schedule(new TimerTask()
				{
					public void run()
					{
						
					}
				}, 100, 100);
	}
}
