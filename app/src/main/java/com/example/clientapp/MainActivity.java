package com.example.clientapp;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {
    EditText etIP, etPort;
    TextView tvMessages;
    EditText etMessage;
    String SERVER_IP;
    int SERVER_PORT;
    private Socket client;
    private PrintWriter printwriter;
    private Button Onbutton, Offbutton, btnConnect, btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etIP = findViewById(R.id.etIP);
        etPort = findViewById(R.id.etPort);
        tvMessages = findViewById(R.id.tvMessages);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);
        Onbutton = findViewById(R.id.Onbutton);
        Offbutton = findViewById(R.id.Offbutton);
        btnConnect = findViewById(R.id.btnConnect);
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvMessages.setText("");
                SERVER_IP = etIP.getText().toString().trim();
                SERVER_PORT = Integer.parseInt(etPort.getText().toString().trim());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvMessages.setText("Server details have been saved!");
                    }
                });
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = etMessage.getText().toString();
                if (!message.isEmpty()) {
                    new Thread(new Thread1(message)).start();
                }
            }
        });
        Onbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Thread1("101")).start();
            }
        });
        Offbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Thread1("100")).start();
            }
        });
    }
    class Thread1 implements Runnable {
        private String message;
        Thread1(String message) {
            this.message = message;
        }
        @Override
        public void run() {
            try {
                client = new Socket(SERVER_IP, SERVER_PORT);  // connect to server
                printwriter = new PrintWriter(client.getOutputStream(), true);
                printwriter.write(message);  // write the message to output stream
                printwriter.flush();
                printwriter.close();
                // closing the connection
                client.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}