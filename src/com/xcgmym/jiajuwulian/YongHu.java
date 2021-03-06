package com.xcgmym.jiajuwulian;

import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONObject;
import org.json.JSONException;

public class YongHu
{
	private Socket socket = null;
	private Timer timer = null;
	private YongHuJianTing yongHuJianTing = null;
	private InputStream inputStream = null;
	private OutputStream outputStream = null;

	private String id;
	private boolean isDengLu = false;
	private ShuJuKu shuJuKu = null;

	public YongHu(Socket arg, YongHuJianTing yhjt)
	{
		socket = arg;
		yongHuJianTing = yhjt;
		shuJuKu = new ShuJuKu();

		System.out.println("得到一个用户连接");

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
		/**
 		 * 接收 
 		 **/
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
			}, 10*1000, 60*1000);
	}

	private void chuLi(byte[] arg)
	{
		System.out.println("用户"+id+"收到信息"+new String(arg));
		JSONObject json = new JSONObject(new String(arg));
		String qingQiu = "";

		try
		{
			qingQiu = json.getString("QingQiu");
		}catch(JSONException je)
		{
			System.out.println("获取Json的请求值错误");
			return;
	       	}

		System.out.println("用户"+id+"的请求是"+qingQiu);
		//登录
		if(qingQiu.equals("DengLu"))
		{
			if(isDengLu)
				return;
			String miMa = "";
			try
			{
				id = json.getString("MingCheng");
				miMa = json.getString("MiMa");
			}catch(JSONException je)
			{
				System.out.println("获取Id的请求值错误");
				return;
			}

			if(id == null)
			{
				JSONObject jsonRes = new JSONObject();
				jsonRes.put("HuiFu", "IDWeiKong");
				return;
			}
			
			if(shuJuKu.login(id, miMa))
			{
				JSONObject jsonRes = new JSONObject();
				jsonRes.put("HuiFu", "DengLu");
				jsonRes.put("NeiRong","ChengGong");
				faSong(jsonRes.toString());
				if(yongHuJianTing != null)
				{
					yongHuJianTing.yongHuShangXian(id, this);
					isDengLu = true;
				}
			}else
			{
				JSONObject jsonRes = new JSONObject();
				jsonRes.put("HuiFu", "DengLu");
				jsonRes.put("NeiRong","ShiBai");
				faSong(jsonRes.toString());
			}
			return;
		}
		//心跳
		if(qingQiu.equals("XinTiao"))
		{
			return;
		}
		//注册
		if(qingQiu.equals("ZhuCe"))
		{
			if(isDengLu)
				return;
			String miMa = "";
	
			try
			{
				id = json.getString("MingCheng");
				miMa = json.getString("MiMa");
			}catch(JSONException jse)
			{
				System.out.println("注册数据有问题");
				return;
			}

			if(shuJuKu.zhuCe(id, miMa))
			{
				if(yongHuJianTing != null)
				{
					yongHuJianTing.yongHuShangXian(id, this);
					isDengLu = true;
					return;
				}
				JSONObject jsonRes = new JSONObject();
				try
				{
					jsonRes.put("HuiFu", "ZhuCe");
					jsonRes.put("NeiRong", "ChengGong");
					faSong(jsonRes.toString());
				}catch(JSONException jsone)
				{
				}
			}else
			{
				JSONObject jsonRes = new JSONObject();
				try
				{
					jsonRes.put("HuiFu", "ZhuCe");
					jsonRes.put("NeiRong", "ShiBai");
					faSong(jsonRes.toString());
				}catch(JSONException jsone)
				{
				}
			}

			return;
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
			if(yongHuJianTing != null)
			{
				yongHuJianTing.yongHuXiaXian(id);
				isDengLu = false;
				close();
				return;
			}
		}

		if(qingQiu.equals("FaSong"))
		{
			String faSongDao = "";
			String mingCheng = "";
			Object obj = null;
			try
			{
				faSongDao = json.getString("FaSongDao");
				mingCheng = json.getString("MingCheng");
			}catch(JSONException jse)
			{
			}
			if(yongHuJianTing != null)
			{
				yongHuJianTing.yongHuMingLing(faSongDao, mingCheng, json);
				return;
			}
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
			System.out.println("设备"+id+"异常掉线，退出");
			close();
		}
	}

	public void close()
	{
		System.out.println("用户退出");
		if(shuJuKu != null)
		{
			shuJuKu.close();
		}
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

		if(yongHuJianTing!=null)
		{
			yongHuJianTing.yongHuXiaXian(id);
		}
	}
}
