package handler.example.sde.sdu.edu.handlerdemo.looper;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class LooperHandlerTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		setContentView(R.layout.handlermain);
        //打印了当前线程的ID
        System.out.println("Activity-->" + Thread.currentThread().getId());
        System.out.println("Activity--->" + Thread.currentThread().getName());
        //生成一个Thread对象，实现了使用Looper来处理消息队列的功能
        myThread handlerThread = new myThread();
        //调用该Thread对象的start();
        handlerThread.start();
    }

    class MyHandler extends Handler {
        public MyHandler(){

        }
        public MyHandler(Looper looper){
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            Bundle b = msg.getData();
            int age = b.getInt("age");
            String name = b.getString("name");
            System.out.println("age is " + age + ", name is" + name);
            System.out.println("Handler--->" + Thread.currentThread().getId());
            System.out.println("handlername--->" + Thread.currentThread().getName());
//			System.out.println("handlerMessage");
        }
    }
    class myThread extends Thread{
        Looper looper;
        MyHandler mainHandler;
        public void run() {
            Looper.prepare();
            looper=Looper.myLooper();
            if(looper==null){System.out.println("空");}
            mainHandler=new MyHandler(looper);
            //实例化Message对象
            Message msg = mainHandler.obtainMessage();
            //将msg发送到目标对象，所谓的目标对象，就是生成该msg对象的handler对象
            Bundle b = new Bundle();
            b.putInt("age", 20);
            b.putString("name", "Jhon");
            msg.setData(b);
            msg.sendToTarget();
//			System.out.println("++++++++++++++++++++");
            try {
                //设置当前显示睡眠1秒
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread--->" + Thread.currentThread().getId());
            System.out.println("Thread--->" + Thread.currentThread().getName());
            Looper.loop();

        }
    }
}
