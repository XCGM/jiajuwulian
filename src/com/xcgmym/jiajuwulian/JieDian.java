package com.xcgmym.jiajuwulian;

import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class JieDian
{
	private Socket socket = null;
	private Timer timer = null;
	private JieDianJianTing jieDianJianTing = null;
	private InputStream inputStream = null;
	private OutputStream outputStream = null;
	private String id;

	public JieDian(Socket arg, JieDianJianTing jdjt)
	{
		socket = arg;
		jieDianJianTing = jdjt;

		System.out.println("得到一个节点连接");

		try
		{
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
		}catch(IOException ioe)
		{
			System.out.println("获取输入输出流失败");
			close();
			return;
		}

		timer = new Timer("YongHu", true);
		timer.schedule(new TimerTask()
				{
					int count = 0;
					int tmp = 0;

					public void run()
					{
						try
						{
							count = inputStream.available();
							if(count>0)
							{
								byte[] res = new byte[count];
								for(int i=0; i<count; i++)
								{
									tmp = inputStream.read();
									res[i] = (byte)tmp;
								}
								mingLing(res);
								count = inputStream.available();
								if(count == 0)
								{
									tmp = inputStream.read();
									if(tmp == -1)
									{
										System.out.println("设备断线，退出当前");
										close();
									}
								}
							}
						}catch(IOException ioe)
						{
							System.out.println("读取失败，退出用户");
							close();
						}
					}
				}, 100, 100);
	}

	private void mingLing(byte[] arg)
	{
	}

	public void close()
	{
		if(timer != null)
		{
			timer.cancel();
		}
		if(inputStream != null)
		{
			try
			{
				inputStream.close();
			}catch(IOException ioe)
			{
				System.out.println("失败");
			}
		}
		if(outputStream != null)
		{
			try
			{
				outputStream.close();
			}catch(IOException ioe)
			{
				System.out.println("失败");
			}
		}

		if(jieDianJianTing!=null)
		{
			jieDianJianTing.yongHuXiaXian(id);
		}
	}
}
