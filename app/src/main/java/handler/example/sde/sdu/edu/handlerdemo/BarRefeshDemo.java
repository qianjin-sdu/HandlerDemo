package handler.example.sde.sdu.edu.handlerdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class BarRefeshDemo extends AppCompatActivity {

    //声明控件变量
    ProgressBar bar = null;
    Button startButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_refesh_demo);
        //根据控件的ID得到代表控件的对象,并为按钮设置监听器
        bar = (ProgressBar)findViewById(R.id.bar);
        startButton = (Button)findViewById(R.id.startButton);
        //Step1:开始按钮绑定监听
        startButton.setOnClickListener(new ButtonListener());
    }

    //Step2:当点击startButton按钮时，就会执行ButtonListener的onClick方法
    class ButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            //将进度条设置为可见状态
            bar.setVisibility(View.VISIBLE);
            //将线程对象压入到线程队列中
            updateBarHandler.post(updateThread);
        }
    }

    //使用匿名内部类来复写Handler当中的handleMessage方法
    //Step5:异步执行handleMessage()方法，sendMessage()方法执行后继续执行其后代码，
    //与此同时，handleMessage()方法也在执行
    Handler updateBarHandler = new Handler(){
        public void handleMessage(Message msg) {
            bar.setProgress(msg.arg1);
            //线程再次被加入到线程队列中，则再次执行Step3
            updateBarHandler.post(updateThread);
        };
    };

    //Step3:线程类，该类使用匿名内部类的方式进行声明，执行线程对象中的run方法
    Runnable updateThread = new Runnable(){
        int i = 0 ;
        public void run() {
            System.out.println("Begin Thread" + i);
            i = i + 10 ;
            //得到一个Message消息对象
            Message msg=updateBarHandler.obtainMessage();
            //arg1,arg2这两个成员变量传递int型数据，优点：系统性能消耗较少
            //将msg对象的arg1参数设置为i
            msg.arg1=i;
            try {
                //设置当前显示睡眠1秒
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if( i > 100){
                //如果当i的值为100时，就将线程对象从handler当中移除
                updateBarHandler.removeCallbacks(updateThread);
                System.out.println(">>>>>>");
            }else{
                //Step4:将msg消息对象压入到消息队列中，回调Handler的handleMessage()方法
                updateBarHandler.sendMessage(msg);
                System.out.println("<<<<<<");
            }
        }
    };
}
