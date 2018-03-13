package com.xcgmym.jiajuwulian;

import java.net.Socket;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

public class MySocket {

    private String url = "www.xcgmym.com";
    private int port = 6000;
    private Socket socket = null;
    private Timer timer = null;

    public MySocket()
    {
        timer = new Timer("Socket", true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try
                {
                    socket = new Socket(url, port);
                }catch(UnknownHostException uhe)
                {

                }catch(IOException ioe)
                {

                }

                try
                {
                    Thread.sleep(1000);
                }catch(InterruptedException ie)
                {

                }
                close();
            }
        }, 1000);
    }

    public void close() {
        if(timer != null)
        {
            timer.cancel();
        }

        if (socket != null)
        {
            try
            {
                socket.close();
            }catch (IOException ioe)
            {

            }
        }
    }
}
