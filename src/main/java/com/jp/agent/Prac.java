package com.jp.agent;

import java.util.ArrayList;

/**
 * Created by puri on 4/13/2016.
 */
public class Prac {
    @Monitor
    public Object go1() throws InterruptedException {
        Thread.sleep(1000);
        go2();
        ArrayList<String> strings = new ArrayList<>();
        strings.add("12");
        return new Object();
    }

    private void go2() throws InterruptedException {
        Thread.sleep(1000);
        go3();
    }

    @Monitor
    private void go3() throws InterruptedException {
        Thread.sleep(1000);

    }
}
