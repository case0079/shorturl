package com.gonghf.open;

import io.seruco.encoding.base62.Base62;

/**
 * Program for short url generating.
 * Input an Integer/Long return another Integer/Long in the same range,
 * moreover, the result would never collision with other input number.
 *
 * @author gonghaifeng
 * @email 79100984@qq.com
 * @date 2015/03/05
 */

public class ShortUrl {

    /**
     * The max int value which has 30 bits (5 characters)
     */
    private static  int MAX_I=Integer.MAX_VALUE;

    /**
     * The max long value which has 60 bits (10 characters)
     */
    private static  long MAX_L=Long.MAX_VALUE;

    private static  Base62 base62= Base62.createInstance();

    private ShortUrl(int max){
        MAX_I=max;
    }

    private ShortUrl(long max){
        MAX_L=max;
    }

    /**
     * 设置最大值
     * @param max
     * @return
     */
    public static ShortUrl create(int max){
        return new ShortUrl(max);
    }

    /**
     * 设置最大值
     * @param max
     * @return
     */
    public static ShortUrl create(long max){
        return new ShortUrl(max);
    }

    private static byte[] intTo6bitByte(int i) {
        byte[] targets = new byte[6];

        targets[5] = (byte) (i & 0x3F);
        targets[4] = (byte) (i >> 6 & 0x3F);
        targets[3] = (byte) (i >> 12 & 0x3F);
        targets[2] = (byte) (i >> 18 & 0x3F);
        targets[1] = (byte) (i >> 24 & 0x3F);
        targets[0] = (byte) (i >> 30 & 0x3F);

        return targets;
    }

    private static byte[] longTo6bitByte(long i) {
        byte[] targets = new byte[11];

        targets[10] = (byte) (i & 0x3F);
        targets[9] = (byte) (i >> 6 & 0x3F);
        targets[8] = (byte) (i >> 12 & 0x3F);
        targets[7] = (byte) (i >> 18 & 0x3F);
        targets[6] = (byte) (i >> 24 & 0x3F);
        targets[5] = (byte) (i >> 30 & 0x3F);
        targets[4] = (byte) (i >> 36 & 0x3F);
        targets[3] = (byte) (i >> 42 & 0x3F);
        targets[2] = (byte) (i >> 48 & 0x3F);
        targets[1] = (byte) (i >> 54 & 0x3F);
        targets[0] = (byte) (i >> 60 & 0x3F);

        return targets;
    }


    /**
     * never collision!!
     * @param i
     * @return
     */
    private static int intMix(int i) {
        i = (i + (~(i << 13) & MAX_I)& MAX_I)& MAX_I;
        i = i ^ (i >>> 9);
        i = (i + ((i << 3)& MAX_I))& MAX_I;
        i = i ^ (i >>> 6);
        i = (i + ((i << 10)& MAX_I))& MAX_I;
        i = i ^ (i >>> 14);
        return i;
    }


    /**
     * never collision!!
     * @param i
     * @return
     */
    private static long longMix(long i) {
        i = (i + (~(i << 29) & MAX_L)& MAX_L)& MAX_L;
        i = i ^ (i >>> 25);
        i = (i + ((i << 11)& MAX_L))& MAX_L;
        i = i ^ (i >>> 15);
        i = (i + ((i << 19)& MAX_L))& MAX_L;
        i = i ^ (i >>> 21);
        i = (i + ((i << 5)& MAX_L))& MAX_L;
        i = i ^ (i >>> 6);
        return i;
    }

    public  String createToken(int i) {
        if(i<0||i>MAX_I){
            throw new IllegalArgumentException();
        }
        i = intMix(i);
        byte[] bytes = intTo6bitByte(i);
        return new String(base62.encode(bytes));
    }


    public  String createToken(long l) {
        if(l<0||l>MAX_L){
            throw new IllegalArgumentException();
        }
        l = longMix(l);
        byte[] bytes = longTo6bitByte(l);
        return new String(base62.encode(bytes));
    }

    public static void main(String[] args) throws Exception {

        for (int i = 1; i <10; i++) {
            System.out.println(ShortUrl.create(Integer.MAX_VALUE).createToken(i));
        }

//        for (int i = Integer.MAX_VALUE; i > Integer.MAX_VALUE-100; i--) {
//            System.out.println(ShortUrl.create(Integer.MAX_VALUE).createToken(i));
//        }


    }
}
