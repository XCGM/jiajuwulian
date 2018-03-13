package com.xcgmym.jiajuwulian;

import android.app.Application;

public class WuLianApplication extends Application
{
    public MySocket mySocket = null;

    public void onCreate()
    {
        super.onCreate();
        mySocket = new MySocket();
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
