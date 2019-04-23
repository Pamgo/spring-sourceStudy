package com.okay.thread.mycallback;


/**
 * Created by OKali on 2019/4/23.
 */
public class CallBackTask {

    private CallBackBody callBackBody;

    public CallBackTask(CallBackBody callBackBody) {
        this.callBackBody = callBackBody;
    }

    protected void start(final Object context) throws Exception {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    callBackBody.execute(context);
                } catch (Exception e) {
                    callBackBody.onFailure("失败："+context);
                    e.printStackTrace();
                }
                callBackBody.onSuccess("成功："+context);
            }
        });
        thread.start();
    }
}
