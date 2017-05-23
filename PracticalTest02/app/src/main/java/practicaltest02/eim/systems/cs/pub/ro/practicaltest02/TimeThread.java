package practicaltest02.eim.systems.cs.pub.ro.practicaltest02;

import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by student on 23.05.2017.
 */

public class TimeThread extends Thread{

    private boolean isRunning;

    private ServerSocket serverSocket;
    private int port = 9330;

    private ArrayList<String> data = new ArrayList<>();

    private EditText serverTextEditText;


    public TimeThread() {
    }

    public void startServer() {
        start();
        Log.v(Constants.TAG, "startServerTime() method was invoked");
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

    public void run() {
        Socket socket;
        try {
            socket = new Socket("utcnist.colorado.edu", 13);
            Log.d(Constants.TAG, socket + "");
            BufferedReader br = Utilities.getReader(socket);
            String queryStr = br.readLine().trim();
            if(queryStr != null) {
                String line;
                while ((line = br.readLine()) != null) {
                    Log.v(Constants.TAG, "A line has been received from the FTP server: " + line);
                }
            }
            Log.d(Constants.TAG, queryStr);
            socket.close();
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "An exception has occurred: " + ioException.getMessage());
            ioException.printStackTrace();

        }
    }
}
