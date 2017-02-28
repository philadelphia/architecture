package com.delta.smt.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;

import com.delta.smt.R;

/**
 * Created by Shaoqiang.Zhang on 2017/2/15.
 */

public class VibratorAndVoiceUtils {

    private static Vibrator vibrator;

    private static MediaPlayer music;// 播放器引用

    public static void correctVibrator(Context context) {
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {30, 150, 30, 150}; // 停止 开启 停止 开启
        vibrator.vibrate(pattern, -1); //重复两次上面的pattern 如果只想震动一次，index设为-1
    }

    public static void wrongVibrator(Context context) {
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {30, 400}; // 停止 开启 停止 开启
        vibrator.vibrate(pattern, -1); //重复两次上面的pattern 如果只想震动一次，index设为-1
    }

    //自定义时常的震动模式
    public static void myStyleVibrator(Context context, long[] pattern) {

        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, -1); //重复两次上面的pattern 如果只想震动一次，index设为-1

    }

    public static void correctVoice(Context context) {

        music = MediaPlayer.create(context, R.raw.scanfailed);
        music.start();
        music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                music = null;
            }
        });

    }

    public static void wrongVoice(Context context) {

        music = MediaPlayer.create(context, R.raw.scansuccess);
        music.start();
        music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                music = null;
            }
        });

    }
}
