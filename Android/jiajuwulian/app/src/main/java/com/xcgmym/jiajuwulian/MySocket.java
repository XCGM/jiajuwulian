package com.xcgmym.jiajuwulian;

import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

public class MySocket {

    private String url = "www.xcgmym.com";
    private int port = 6000;
    private Socket socket = null;
    private Timer timer = null;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;

    public MySocket()
    {
        timer = new Timer("Socket", true);
        connect();
    }

    public void connect()
    {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try
                {
                    socket = new Socket(url, port);
                    inputStream = socket.getInputStream();
                    outputStream = socket.getOutputStream();
                }catch(UnknownHostException uhe)
                {
                    close();
                }catch(IOException ioe)
                {
                    close();
                }

//                faSong("{\"QingQiu\":\"DengLu\",\"WoShi\":\"Hello world\"}");
            }
        }, 500);
        timer.schedule(new TimerTask() {
            int count = 0;
            int tmp = 0;

            @Override
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
                            res[i] = (byte)tmp;
                        }
                        chuLi(res);
                    }
                }catch(IOException ioe)
                {
                    reConnect();
                }
            }
        }, 2000, 100);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                xinTiao();
            }
        }, 10000, 30000);
    }

    public void xinTiao()
    {
        JSONObject json = new JSONObject();
        try
        {
            json.put("QingQiu","XinTiao");
            faSong(json.toString());
        }catch (JSONException jsone)
        {

        }
    }

    public void chuLi(byte[] arg)
    {
        Log.v("MySocket", new String(arg));
    }

    public void faSong(String arg)
    {
        Log.v("socket", arg);
        try
        {
            outputStream.write(arg.getBytes());
            outputStream.flush();
        }catch(IOException ioe)
        {
            reConnect();
        }
    }

    public void reConnect()
    {
        Log.v("MySocket", "断线重连");
        close();
        try
        {
            Thread.sleep(1000);
        }catch(InterruptedException ie)
        {

        }
        timer = new Timer("Socket", true);
        connect();
    }
    public void close() {
        if(timer != null)
        {
            timer.cancel();
        }

        try
        {
            if(inputStream != null)
            {
                inputStream.close();
            }
            if(outputStream != null)
            {
                outputStream.close();
            }
            if (socket != null)
            {
                socket.close();
            }
        }catch(IOException ioe)
        {

        }
    }
}
