package handler.example.sde.sdu.edu.handlerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HandlerMain extends Activity implements View.OnClickListener {

    Button btn = null;
    TextView tv = null;
    Thread thread = null;
    MyHandler mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_main);
        btn = (Button) findViewById(R.id.startButton);
        btn.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.starttv);
    }

    class MyHandler extends Handler {

        public MyHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            System.out.println("Thread--->" + Thread.currentThread().getName());
            tv.setText(msg.obj.toString());
        }
    }

    class MyThread extends Thread {

        Looper curLooper;
        Looper mainLooper;

        public void run() {
            //Looper.prepare();
            curLooper = Looper.myLooper();
            mainLooper = Looper.getMainLooper();
            String msg;
            if (curLooper == null) {
                mHandler = new MyHandler(mainLooper);
                msg = "curLooper is null";
            } else {
                mHandler = new MyHandler(curLooper);
                msg = "this is curLooper";
            }
            mHandler.removeMessages(0);
            Message message = mHandler.obtainMessage(1, 1, 1, msg);
            mHandler.sendMessage(message);
            System.out.println("Thread--->" + Thread.currentThread().getName());
            //Looper.loop();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startButton:
                thread = new MyThread();
                thread.start();
                break;

            default:
                break;
        }

    }

}
