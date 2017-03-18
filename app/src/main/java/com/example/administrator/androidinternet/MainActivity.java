package com.example.administrator.androidinternet;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity  {

    private Button mBtnGet;
    private TextView mTvShow;
    private Handler handler;
    private Context ctx;

    private static final int SHOW = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnGet = (Button)findViewById(R.id.get);
        mTvShow = (TextView)findViewById(R.id.show);

        handler=new Handler(){
            public void handleMessage(Message msg){
                switch (msg.what){
                    case SHOW:
                        mTvShow.setText((CharSequence) msg.obj);
                }
                                   }
        };
        mBtnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connection();

                           }
        });
    }
    private void Connection(){
        new Thread(){
            public void run() {
                try {
                    URL url = new URL("http://www.baidu.com");
                    final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                        InputStream inputstream = connection.getInputStream();
                        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));

                    Message message = Message.obtain();
                    message.what = SHOW;
                    message.obj=reader.readLine();
                    handler.sendMessage(message);
                       /* runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                   Toast.makeText(MainActivity.this,reader.readLine(),Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });*/


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
    public Handler getHandler(){
        return this.handler;
    }
}
