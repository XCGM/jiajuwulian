package com.xcgmym.jiajuwulian;

import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ZiFuWu
{
	private Socket socket = null;
	private Timer timer = null;
	private ZiFuWuJianTing ziFuWuJianTing = null;
	private InputStream inputStream = null;
	private OutputStream outputStream = null;

	public ZiFuWu(Socket arg, ZiFuWuJianTing zfwjt)
	{
		socket = arg;
		ziFuWuJianTing = zfwjt;
		System.out.println("获取一个新连接");
		try
		{
			inputStream = socket.getInputStream();
		}catch(IOException ioe)
		{
		}

		try
		{
			outputStream = socket.getOutputStream();
		}catch(IOException ioe)
		{
		}
		
		timer = new Timer("ZiFuWu", true);
		timer.schedule(new TimerTask(){
			int count = 0;
			int tmp = 0;
			public void run()
			{
				try
				{
					count = inputStream.available();
					if(count > 0)
					{
						byte[] res = new byte[count];
						for(int i=0; i<count; i++)
						{
							tmp = inputStream.read();
							if(tmp == '\n')
							{
								if(ziFuWuJianTing != null)
								{
									ziFuWuJianTing.huoQuDao(res);
								}
							}else
							{
								res[i] = (byte)tmp;
							}
						}
						count = inputStream.available();
						//尝试读一个字节确定是否断线
						if(count == 0)
						{
							tmp = inputStream.read();
						}
					}
				}catch(IOException ioe)
				{
					System.out.println("ZiFuwu:读取错误,退出zifuwu");
					close();
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
		if(inputStream != null)
		{
			try
			{
				inputStream.close();
			}catch(IOException ioe)
			{
			}
		}

		if(outputStream != null)
		{
			try
			{
				outputStream.close();
			}catch(IOException ioe)
			{
			}
		}

		if(socket != null)
		{
			try
			{
				socket.close();
			}catch(IOException ioe)
			{
			}
		}
	}
}
