package practicaltest02.eim.systems.cs.pub.ro.practicaltest02;

/**
 * Created by student on 23.05.2017.
 */

import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerThread extends Thread {

    private boolean isRunning;

    private ServerSocket serverSocket;
    private int port = 9330;

    private ArrayList<String> data = new ArrayList<>();

    private EditText serverTextEditText;

    private Socket socket;

    public ServerThread() {
    }

    public void startServer() {
        isRunning = true;
        start();
        Log.v(Constants.TAG, "startServer() method was invoked");
    }

    public void stopServer() {
        isRunning = false;
        try {
            serverSocket.close();
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "An exception has occurred: " + ioException.getMessage());
            ioException.printStackTrace();

        }
        Log.v(Constants.TAG, "stopServer() method was invoked");
    }

    public synchronized void setData (String time) {
        this.data.add(time);
    }

    public synchronized void removeData(String time) {
        if(this.data.contains(time)) {
            this.data.remove(time);
        }
    }

    public ServerSocket getServerSocket() {
        return this.serverSocket;
    }

    public synchronized Socket getSocket() {
        return this.socket;
    }

    public synchronized ArrayList<String> getData () {
        return this.data;
    }

    @Override
    public void run() {

        try {
            serverSocket = new ServerSocket(port);
            while (isRunning) {
                this.socket = serverSocket.accept();
                if (this.socket != null) {
                    //#################################################################
                    BufferedReader br = Utilities.getReader(socket);
                    String queryStr = br.readLine().trim();
                    if(queryStr.contains("set")){
                        Log.d(Constants.TAG, queryStr);
                        queryStr = queryStr.substring(3);
                        Log.d(Constants.TAG, queryStr);
                        setData(queryStr);
                        Log.d(Constants.TAG, "Array: " + getData().toString());
                    } else if(queryStr.contains("rst")) {
                        Log.d(Constants.TAG, queryStr);
                        queryStr = queryStr.substring(3);
                        Log.d(Constants.TAG, queryStr);
                        removeData(queryStr);
                        Log.d(Constants.TAG, "Array: " + getData().toString());
                    }


                    this.socket.close();
                } else {
                    Log.d(Constants.TAG, "Server is null");
                }
            }
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "An exception has occurred: " + ioException.getMessage());
            ioException.printStackTrace();

        }
    }

}