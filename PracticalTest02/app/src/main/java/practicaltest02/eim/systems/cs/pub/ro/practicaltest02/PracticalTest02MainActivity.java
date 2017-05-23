package practicaltest02.eim.systems.cs.pub.ro.practicaltest02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class PracticalTest02MainActivity extends AppCompatActivity {

    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    EditText queryTxt;
    Button startServer;
    Button setButton;
    Button resetButton;
    Button pollButton;
    EditText okTxt;
    String serverQuery;
    ServerThread serverThread;
    TimeThread timeThread;

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (startServer.equals((Button)view)) {
                Log.d(Constants.TAG, "apas buton");

                if(serverThread == null) {
                    serverThread = new ServerThread();
                    serverThread.startServer();
                } else {
                    Log.d(Constants.TAG,"Server stopped");
                    serverThread.stopServer();
                }
            }

            if (setButton.equals((Button)view)) {

                serverQuery = queryTxt.getText().toString();
                Log.d(Constants.TAG, "String: " + serverQuery);
                Log.d(Constants.TAG, serverThread.getSocket() + "");
                new ClientAsyncTask(serverQuery, serverThread.getSocket()).execute();

            }
            if (resetButton.equals((Button)view)) {
                serverThread.stopServer();
            }

            if(pollButton.equals((Button)view)) {
                timeThread = new TimeThread();
                timeThread.startServer();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        this.queryTxt = (EditText) findViewById(R.id.clockText);

        this.startServer = (Button) findViewById(R.id.startButton);
        this.setButton = (Button) findViewById(R.id.setButton);
        this.resetButton = (Button)findViewById(R.id.resetButton);
        this.pollButton = (Button)findViewById(R.id.pollButton);

        startServer.setOnClickListener(buttonClickListener);
        setButton.setOnClickListener(buttonClickListener);
        resetButton.setOnClickListener(buttonClickListener);
        pollButton.setOnClickListener(buttonClickListener);
    }
}
