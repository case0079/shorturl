package com.doodle;

/**
 * 整数 0-1073741823 转化成5位字符
 * 
 * @author gonghaifeng
 * @email 79100984@qq.com
 * @date 2015年3月5日
 */
public class ShortUrl {
  private static final byte[] URL_SAFE_ENCODE_TABLE = { 'A', 'B', 'C', 'D',
      'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
      'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
      'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
      'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
      '8', '9', '-', '_'                           };

  private static int s(int s) {
    return s & 0x3FFFFFFF;
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

  private static int intMix(int i) {
    i = s(i + s(~(s(i << 13))));
    i = i ^ (i >>> 9);
    i = s(i + (s(i << 3)));
    i = i ^ (i >>> 6);
    i = s(i + (s(i << 10)));
    i = i ^ (i >>> 14);
    return i;
  }

  public static String createToken(int i) {
    if (i > 0x3fffffff || i < 0) {
      throw new IllegalArgumentException("输入的数不能为负数，且不能大于0x3fffffff");
    }

    i = intMix(i);
    StringBuilder sb = new StringBuilder();
    byte[] bytes = intTo6bitByte(i);
    for (int j = 1; j < 6; j++) {
      sb.append((char) URL_SAFE_ENCODE_TABLE[bytes[j]]);
    }
    return sb.toString();
  }

  public static void main(String[] args) throws Exception {
    //5位字符支持的最大数 1073741823
    for (int i = 0; i < 100; i++) {
      System.out.println(createToken(i));
    }
  }
}
