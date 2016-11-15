package handler.example.sde.sdu.edu.handlerdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private TextView textview;
    //Step1:创建一个主线程的Handler,该Handler绑定主线程的Looper
    Handler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textview = (TextView) findViewById(R.id.mytv);
        myHandler=new MyHandler();
        //Step6:创建子线程对象，传递主线程中的Handler到子线程中
        Thread myThread=new Thread(new SampleTaskDemo(myHandler));
        //Step7:启动子线程
        myThread.start();
        System.out.println("Activity----"+Thread.currentThread().getName());
    }

    class MyHandler extends Handler{
        //Step5:重写hanleMessage方法，接收子线程来的数据，更改UI界面
        public void handleMessage(Message msg) {
            String result=msg.getData().getString("message");
            //把数据放入UI界面
            appendText(result);
            super.handleMessage(msg);
        }
    }

    public void appendText(String str){
        textview.setText(str);
    }
}
