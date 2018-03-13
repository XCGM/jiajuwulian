package com.xcgmym.jiajuwulian;

import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONObject;
import org.json.JSONException;

public class JieDian
{
	private Socket socket = null;
	private Timer timer = null;
	private JieDianJianTing jieDianJianTing = null;
	private InputStream inputStream = null;
	private OutputStream outputStream = null;
	private String id;
	private boolean isDengLu = false;

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
								chuLi(res);
							}
						}catch(IOException ioe)
						{
							System.out.println("读取失败，退出用户");
							close();
						}
					}
				}, 10*1000, 100);
		timer.schedule(new TimerTask()
			{
				public void run()
				{
					JSONObject json = new JSONObject();
					try
					{
						json.put("HuiFu","XinTiao");
						faSong(json.toString());
					}catch(JSONException jsone)
					{
						System.out.println("发送心跳包失败，"+id+"掉线");
						close();
					}
				}
			}, 10*1000, 10*60*1000);
	}

	private void chuLi(byte[] arg)
	{
		JSONObject json = new JSONObject(new String(arg));
		String qingQiu = "";

		try
		{
			qingQiu = json.getString("QingQiu");
		}catch(JSONException je)
		{
			return;
		}

		if(qingQiu.equals("DengLu"))
		{
			try
			{
				id = json.getString("WoShi");
			}catch(JSONException je)
			{
				System.out.println("获取节点ID失败");
				return;
			}

			if(id == null)
			{
				JSONObject jsonRes = new JSONObject();
				jsonRes.put("HuiFu", "IDWeiKong");
				return;
			}
			if(jieDianJianTing != null)
			{
				jieDianJianTing.jieDianShangXian(id, this);
				isDengLu = true;
				return;
			}
		}

		if(isDengLu == false)
		{
			JSONObject jsonRes = new JSONObject();
			jsonRes.put("HuiFu", "QingDengLu");
			faSong(jsonRes.toString());
			return;
		}

		if(qingQiu.equals("DengChu"))
		{
			if(jieDianJianTing != null)
			{
				jieDianJianTing.jieDianXiaXian(id);
				isDengLu = false;
				close();
				return;
			}
		}

		if(qingQiu.equals("XinTiao"))
		{
			return;
		}
	}

	public void faSong(String arg)
	{
		try
		{
			outputStream.write(arg.getBytes());
			outputStream.flush();
		}catch(IOException ioe)
		{
			System.out.println("节点"+id+"异常掉线，退出");
			close();
		}
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
			jieDianJianTing.jieDianXiaXian(id);
		}
	}
}
