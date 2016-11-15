package handler.example.sde.sdu.edu.handlerdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by qianjin on 2016/11/15.
 */

public class SampleTaskDemo implements Runnable {

    Handler handler;
    //Step2:通过子线程的构造方法接收主线程的Handler
    public SampleTaskDemo(Handler handler) {
        super();
        this.handler = handler;
    }
    //Step3:完成一些任务，例如，下载，准备修改UI界面的数据
    public void run() {
        try {  // 模拟执行某项任务，下载等
            Thread.sleep(5000);
            //得到Message对象
            Message msMessage=prepareMessage("我是子线程！");
            //将Message放入消息队列，回调handleMessage()方法
            handler.sendMessage(msMessage);
            System.out.println("Thread----"+Thread.currentThread().getName());
        } catch (InterruptedException e) {
            System.out.println("出现异常");
        }

    }
    //Step4:完成任务的 方法，并创建Message，接收任务结束后的数据
    private Message prepareMessage(String str){
        Message msg=handler.obtainMessage();
        Bundle bundle=new Bundle();
        bundle.putString("message", str);
        msg.setData(bundle);
        return msg;

    }
}
