package com.immqy;

import com.immqy.bean.PropertiesProvider;
import com.immqy.utils.BluetoothUtils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

/**
 * 服务
 * @author KARL-Dujinyang
 * @author https://github.com/sheep0704
 */
public class OpenService extends Service {
    private final byte[] openLockBytes = {(byte) 0x55, (byte) 0x01, (byte) 0x05, (byte) 0xAA};
    public static Bluetooth bluetooth;
    private Thread thread;
    private static boolean isReceiver = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "自动开锁开始启动", Toast.LENGTH_SHORT).show();
        bluetooth = MainActivity.bluetooth;
        PropertiesProvider provider = new PropertiesProvider(getApplicationContext());
        final String path = provider.get("image").toString();
        final int time = Integer.valueOf(provider.get("time").toString());

        thread = new Thread(new Runnable() {


            @Override
            public void run() {
                Looper.prepare();
                while (true) {
                    try {
                        while (!isReceiver) {
                            bluetooth.comminute(openLockBytes, 2);
                            Thread.sleep(100);
                            bluetooth.sendKey(BluetoothUtils.createKey(path).getBytes());
                        }

                        Thread.sleep(time * 1000);
                        bluetooth.comminute(openLockBytes, 2);
                        Thread.sleep(100);
                        bluetooth.sendKey(BluetoothUtils.createKey(path).getBytes());
                        break;
                    } catch (Exception e) {
                        break;
                    }
                }
                Looper.loop();
            }


        }

        );
        thread.start();

        return super.

                onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        try {
            Toast.makeText(this, "自动开锁停止", Toast.LENGTH_LONG).show();
            thread.wait();
        } catch (Exception e) {

        }
    }
}
