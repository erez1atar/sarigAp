package newsarig.com.example.erez.newsarig;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import newsarig.com.example.erez.newsarig.Connection.WebSocket;
import newsarig.com.example.erez.newsarig.Connection.WebSocketConnection;
import newsarig.com.example.erez.newsarig.Connection.WebSocketException;
import newsarig.com.example.erez.newsarig.Connection.WebSocketConnectionHandler;

public class MainActivity extends AppCompatActivity {

    private Button sendButton;
    private EditText userId;
    private TextView statusText;
    private TextView actionText;
    private ListView listView;
    private ParamAdapter adapter;
    private final WebSocketConnection mConnection = new WebSocketConnection();

    private void start() {

        final String wsuri = "ws://web1.yaad.net:4000";

        try {
            mConnection.connect(wsuri, new WebSocketConnectionHandler() {
                @Override
                public void onOpen() {
                    mConnection.isConnected();
                    statusText.setText("Connected");
                    Log.d("mConnection", "Connect");

                }

                @Override
                public void onTextMessage(String payload)
                {
                    Log.d("mConnection", "msg " + payload);
                    MSGData msgData = MSGParser.parse(payload);
                    sendButton.setEnabled(false);
                    userId.setEnabled(false);
                    if(msgData.getType().compareTo("OK") == 0)
                    {
                        return;
                    }
                    if(msgData.getType().compareTo("ERR") == 0)
                    {
                        new AlertDialog.Builder(MainActivity.this).
                                setTitle("Connection Error").
                                setMessage(msgData.getAction())
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();
                        return;
                    }
                    Toast.makeText(MainActivity.this, "New Message",
                            Toast.LENGTH_SHORT).show();
                    actionText.setText(msgData.getAction());
                    if(msgData.getType().compareTo("DO") == 0) {
                        printList(msgData.getParams());
                        adapter.setParams(msgData.getParams());
                        adapter.notifyDataSetChanged();
                    }

                }

                @Override
                public void onClose(int code, String reason) {
                    Log.d("mConnection", "close" + reason);
                    statusText.setText("Disconnected");
                }
            });
        } catch (WebSocketException e) {

            Log.d("", "WebSocketException");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        statusText = (TextView)findViewById(R.id.status);
        actionText = (TextView)findViewById(R.id.action);
        sendButton = (Button)findViewById(R.id.button);
        listView = (ListView)findViewById(R.id.list);
        userId = (EditText)findViewById(R.id.user_id);
        adapter = new ParamAdapter(this,R.layout.param_layout, new ArrayList<Param>());
        listView.setAdapter(adapter);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConnection.sendTextMessage("REG|" + userId.getText() + "|99999");
            }
        });
        start();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mConnection.isConnected()) {
            mConnection.disconnect();
        }
    }

    private void printList(ArrayList<Param> params)
    {
        int size = params.size();
        Log.d("size", "" + size);
        for(int i = 0 ; i < size ; i++)
        {
            Log.d("param", params.get(i).getName() + " " + params.get(i).getValue());
        }
    }
}
