package doodle;

/**
 * Program for short url generating.
 * Input an Integer/Long then return another Integer/Long in the same range,
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
    private static int MAX_I=0x3fffffff;

    /**
     * The max long value which has 60 bits (10 characters)
     */
    private static long MAX_L=0xfffffffffffffffL;

    private static final byte[] URL_SAFE_ENCODE_TABLE = { 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', '-', '_'                           };


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

    public static String createToken(int i) {
        if ( i < 0) {
            throw new IllegalArgumentException();
        }

        i = intMix(i);
        StringBuilder sb = new StringBuilder();
        byte[] bytes = intTo6bitByte(i);
        for (int j = 1; j < 6; j++) {
            sb.append((char) URL_SAFE_ENCODE_TABLE[bytes[j]]);
        }
        return sb.toString();
    }


    public static String createToken(long i) {
        if ( i < 0) {
            throw new IllegalArgumentException();
        }

        i = longMix(i);
        StringBuilder sb = new StringBuilder();
        byte[] bytes = longTo6bitByte(i);
        for (int j = 1; j < 11; j++) {
            sb.append((char) URL_SAFE_ENCODE_TABLE[bytes[j]]);
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {

        for (int i = 1; i < 100; i++) {
            System.out.println(createToken(i));
        }


        for (int i = 9540123; i < 9540223; i++) {
            System.out.println(createToken(i));
        }

        for (long i = 1; i < 100; i++) {
            System.out.println(createToken(i));
        }

        for (long i = 87572396021L; i < 87572396121L; i++) {
            System.out.println(createToken(i));
        }

    }
}
