package com.xcgmym.jiajuwulian;

import android.app.Application;

public class WuLianApplication extends Application
{
    private MySocket mySocket = null;

    public void onCreate()
    {
        super.onCreate();
        mySocket = new MySocket();
    }

    public MySocket getMySocket()
    {
        return mySocket;
    }
    public void onLowMemory()
    {
        super.onLowMemory();
        if(mySocket != null)
        {
            mySocket.close();
        }
    }
}
