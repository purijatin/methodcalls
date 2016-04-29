package com.jp.agent;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class MonitorInterceptor {

    static ThreadLocal<Integer> local = new ThreadLocal<>();

    @RuntimeType
    public static Object intercept(@Origin String method,
                     @SuperCall Callable<?> zuper) throws Exception {
        long start = System.nanoTime();
        Integer ger = local.get();
        if(ger==null){
            ger = 2;
        }
        local.set(ger+4);
        String space = IntStream.range(0, ger).mapToObj(x -> " ").collect(Collectors.joining());
        String n = method.split(" ")[2];
        String a[] = n.split("\\(");
        System.out.println();
        System.out.print(space+""+a[0]+" --> ");
        try {
            return zuper.call();
        } catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }finally {
            System.out.println(space+""+TimeUnit.MILLISECONDS.convert(System.nanoTime() - start, TimeUnit.NANOSECONDS)+"ms.");
            local.set(local.get()-4);
        }
    }


}

