package com.itheima.util;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 此类实现MP3音乐的播放
 * 播放音乐：new Music("路径").start();
 * 结束音乐：Music Music = new Music("xxx.mp3");
 * Music.start();  //开启
 */
public class Music extends Thread {
    private String music;
    public static Music m;

    public Music getM() {
        return m;
    }

    public void setM(Music m) {
        Music.m = m;
    }

    public Music(String file) {
        this.music = file;
    }

    @Override
    public void run() {
        try {
            BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(music));
            Player player = new Player(buffer);
            player.play();
        } catch (FileNotFoundException | JavaLayerException e) {
            e.printStackTrace();
        }
    }
}