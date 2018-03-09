package com.xcgmym.jiajuwulian;

import java.net.ServerSocket;
import java.io.IOException;
import java.nio.channels.IllegalBlockingModeException;
import java.util.Map;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class FuWuQi implements YongHuJianTing
{
	private ServerSocket yongHuFuWu = null;
	private ServerSocket jieDianFuWu = null;
	private Timer timer = null;
	private Map<String, YongHu> yongHuJiHe = null;
	private Map<String, JieDian> jieDianJiHe = null;

	public FuWuQi()
	{
		yongHuJiHe = new HashMap<String, YongHu>();
		try
		{
			System.out.println("这是一个服务器程序，即将打开6000端口监听用户");
			yongHuFuWu = new ServerSocket(6000);
			System.out.println("打开6001端口，监听节点");
			jieDianFuWu = new ServerSocket(6001);
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
							new YongHu(yongHuFuWu.accept(), FuWuQi.this);
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
		if(yongHuFuWu != null)
		{
			try
			{
				yongHuFuWu.close();
			}catch(IOException ioe)
			{
				
			}
		}
	}

	public void yongHuMingLing(String arg)
	{
	}
	public void yongHuShangXian(String id)
	{
	}
	public void yongHuXiaXian(String id)
	{
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
