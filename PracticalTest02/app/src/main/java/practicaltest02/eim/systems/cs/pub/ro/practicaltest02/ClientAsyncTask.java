package practicaltest02.eim.systems.cs.pub.ro.practicaltest02;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by bobo on 19.05.2017.
 */

public class ClientAsyncTask extends AsyncTask<String, String, Void> {

    String serverAddr = "127.0.0.1";
    int port = 9330;
    String queryStr;
    Socket socket;

    public ClientAsyncTask(String queryStr, Socket socket) {
        this.queryStr = queryStr;
        this.socket = socket;
    }

    @Override
    protected Void doInBackground(String... params) {

        String response = "";
        int ok = 0;
        try {
            socket = new Socket(serverAddr, port);
            if(this.socket == null) {

                Log.d(Constants.TAG, "Socket null");
            } else {
                PrintWriter clientPr = Utilities.getWriter(socket);
                Log.d(Constants.TAG, queryStr);
                clientPr.write(queryStr+"\n");
                clientPr.flush();

                BufferedReader clientBr = Utilities.getReader(socket);
                //while (!socket.isClosed()) {
                response += clientBr.readLine();
                //}
                this.publishProgress(response);
            }
                socket.close();



        } catch (Exception e) {e.printStackTrace();}

        return null;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onProgressUpdate(String... progress) {
    }

    @Override
    protected void onPostExecute(Void result) {}

}
