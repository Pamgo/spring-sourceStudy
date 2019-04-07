package com.okay.thread.guardSuspension;

/**
 * Created by OKali on 2019/4/7.
 */
public class Test {

    public static void main(String[] args) {
        RequestQueue requestQueue = new RequestQueue();
        for (int i = 0; i < 10; i++)
            new ServerThread(requestQueue,"serverThread"+i).start();

        for (int i = 0; i < 10; i++)
            new ClientThread(requestQueue, "clientThread"+i).start();
    }
}
