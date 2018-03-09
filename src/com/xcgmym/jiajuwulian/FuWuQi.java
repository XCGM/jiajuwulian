package com.xcgmym.jiajuwulian;

import java.net.ServerSocket;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.nio.channels.IllegalBlockingModeException;

public class FuWuQi implements YongHuJianTing, JieDianJianTing
{
	private ServerSocket yongHuFuWu = null;
	private ServerSocket jieDianFuWu = null;
	private Timer yongHuTimer = null;
	private Timer jieDianTimer = null;
	private Map<String, YongHu> yongHuJiHe = null;
	private Map<String, JieDian> jieDianJiHe = null;

	public FuWuQi()
	{
		yongHuJiHe = new HashMap<String, YongHu>();
		jieDianJiHe = new HashMap<String, JieDian>();

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
		
		yongHuTimer = new Timer("ServerSocket", true);
		jieDianTimer = new Timer("jieDianFuWu", true);
		yongHuTimer.schedule(new TimerTask()
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
		jieDianTimer.schedule(new TimerTask()
				{
					public void run()
					{
						try
						{
							new JieDian(jieDianFuWu.accept(), FuWuQi.this);
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
		if(yongHuTimer != null)
		{
			yongHuTimer.cancel();
		}
		if(jieDianTimer != null)
		{
			jieDianTimer.cancel();
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
	public void yongHuShangXian(String id, YongHu yh)
	{
		System.out.println("新用户上线，添加到map中:"+id);
		yongHuJiHe.put(id, yh);
	}
	public void yongHuXiaXian(String id)
	{
		System.out.println("用户下线，从map中移除:"+id);
		yongHuJiHe.remove(id);
	}

	public void jieDianMingLing(String arg)
	{
	}
	public void yongHuShangXian(String id, JieDian jd)
	{
		jieDianJiHe.put(id, jd);
	}
	public void jieDianXiaXian(String id)
	{
		jieDianJiHe.remove(id);
	}

	public static void main(String[] args)
	{
		FuWuQi fwq = new FuWuQi();
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
