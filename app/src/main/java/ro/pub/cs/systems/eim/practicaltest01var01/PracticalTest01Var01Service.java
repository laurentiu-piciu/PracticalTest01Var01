package ro.pub.cs.systems.eim.practicaltest01var01;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Date;
import java.util.Random;

public class PracticalTest01Var01Service extends Service {
    public PracticalTest01Var01Service() {

    }

    private ProcessingThread processingThread = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String instruction = intent.getStringExtra("instruction");

        Log.d(Constants.TAG,"Received in service " + instruction);
        processingThread = new ProcessingThread(this, instruction);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        processingThread.stopThread();
    }

}


class ProcessingThread extends Thread {
    private Context context = null;
    private boolean isRunning = true;
    String instruction = null;
    private Random random = new Random();


    public ProcessingThread(Context context, String instruction) {
        this.context = context;
        this.instruction = instruction;
    }

    @Override
    public void run() {
        Log.d(Constants.TAG, "Thread has started!");
        while (isRunning) {
            sendMessage();
            sleep();
        }
        Log.d(Constants.TAG, "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.actionTypes[random.nextInt(Constants.actionTypes.length)]);
        intent.putExtra("message", new Date(System.currentTimeMillis()) + " " + instruction);
        //Log.d("[ProcessingThread]", "Message Sent " + intent.getAction());
        context.sendBroadcast(intent);
    }


    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}