package com.xcgmym.jiajuwulian;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLTimeoutException;

import java.util.Timer;
import java.util.TimerTask;

public class ShuJuKu
{
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/jiajuwulian";

	private static final String USER = "jiajuwulian";
	private static final String PASS = "jiajuwulian";

	private Connection conn = null;
	private Timer timer = null;

	public ShuJuKu()
	{
		try
		{
			Class.forName(JDBC_DRIVER);
		}catch(ClassNotFoundException cnfe)
		{
			System.out.println("不能找到加载到数据库驱动类");
			System.out.println(cnfe.toString());
			return;
		}

		try
		{
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("链接到数据库");
		}catch(SQLException sqle)
		{
			System.out.println("连接数据库失败:"+sqle.toString());
			return;
		}

		/**
 		 * 心跳
 		 **/
		timer = new Timer("ShuJuKu", true);
		timer.schedule(new TimerTask()
		{
			public void run()
			{
				execute("select 1");
			}
		}, 10000, 3600*1000);
	}

	public void close()
	{
		if(timer != null)
		{
			timer.cancel();
		}
		if(conn != null)
		{
			try
			{
				conn.close();
			}catch(SQLException sqle)
			{
				System.out.println("关闭连接失败");
			}
		}
	}

	public boolean zhuCe(String name, String password)
	{
		String sql = "INSERT INTO yonghu (mingcheng, password) VALUES ('"+name+"','"+password+"')";
		return execute(sql);
	}

	public boolean execute(String sql)
	{
		System.out.println("执行语句："+sql);
		Statement statement = null;
		boolean res = false;
		try
		{
			statement = conn.createStatement();
			res = statement.execute(sql);
		}catch(SQLException sqle)
		{
			System.out.println("执行sql错误");
		}finally
		{
			try
			{
				if(statement != null)
					statement.close();
			}catch(SQLException sqle1)
			{
			}
		}
		return res;
	}

	public boolean login(String arg, String miMa)
	{
		String sql = "select bianhao from yonghu where mingcheng='"+arg+"' and password='"+miMa+"'";;
		System.out.println("执行语句:"+sql);
		Statement statement = null;
		boolean res = false;

		try
		{
			statement = conn.createStatement();
			res = statement.executeQuery(sql).first();
		}catch(SQLException sqle)
		{
			res = false;
			System.out.println("执行Sql失败："+sqle.toString());
		}finally
		{
			try
			{
				if(statement != null)
					statement.close();
			}catch(SQLException sqle1)
			{
			}
		}

		return res;
	}

}
