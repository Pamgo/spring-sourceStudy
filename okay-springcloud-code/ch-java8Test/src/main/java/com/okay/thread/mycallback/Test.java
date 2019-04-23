package com.okay.thread.mycallback;

import javax.sound.midi.Soundbank;

/**
 * Created by OKali on 2019/4/23.
 */
public class Test {

    public static void main(String[] args) throws Exception {
        System.out.println("====准备执行====");
        final Object context = "okali 上下文";

        new CallBackTask(new CallBackBody() {
                @Override
                void execute(Object context) throws Exception {
                    System.out.println("执行耗时操作》》》》");
                    System.out.println(context);
                    Thread.sleep(2000);
                    System.out.println("执行完成》》》》》");
                }

                @Override
                void onFailure(Object context) {
                    System.out.println("失败回调");
                    System.out.println(context);
                }

                @Override
                void onSuccess(Object context) {
                    System.out.println("成功");
                    System.out.println(context);
                }
        }).start(context);

        System.out.println("=============异步任务以及开始，请等待===============");
    }
}
