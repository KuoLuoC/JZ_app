package com.wzq.jz_app.net;


public class SchedulerUtils<T> {

    public static <T> IoMainScheduler<T> ioToMain() {
        return new IoMainScheduler<T>();
    }
}
