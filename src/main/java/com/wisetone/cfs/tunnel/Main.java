package com.wisetone.cfs.tunnel;

/**
 * Created by zhiyedan on 5/5/17.
 */
public class Main {
    public static void main(String[] args) {
//        System.out.println(B.getX());
//        Function.getTopo();
        Function.addTunnelPort("ovsdb://mini2/bridge/s1","192.168.0.109");
    }
}
