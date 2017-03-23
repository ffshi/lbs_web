package qselect_3_web;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.innovate.cms.common.utils.HttpClientUtil;

public class Test {

	public static void main(String[] args) {
		// System.out.println(!StrUtil.isNum("4021"));
		// System.out.println(testFor());
		// System.out.println(Runtime.getRuntime().availableProcessors());
		// System.out.println(Integer.MAX_VALUE);
		// System.out.println(Global.getSycnAliIM());
		// System.out.println(DateUtils.getDate("yyyyMMdd"));
//		System.out.println(compareRes("1111122222", "1111111221"));

		// for (int i=0;i<100;i++) {
		// System.out.println(new Random().nextInt(10));
		// }
		System.out.println(HttpClientUtil.doGet("http://m.image.so.com/j?ie=utf-8&pn=60&sn=60&src=hao_360so&q=4"));
	}

	/**
	 * DES算法密钥
	 */
	private static final byte[] DES_KEY = { 21, 1, -110, 82, -32, -85, -128, -65 };

	public static String decryptBasedDes(String cryptData) {
		String decryptedData = null;
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(DES_KEY);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey);
			// 解密对象
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key, sr);
			// 把字符串解码为字节数组，并解密
			decryptedData = new String(cipher.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(cryptData)));
		} catch (Exception e) {
			// log.error("解密错误，错误信息：", e);
			throw new RuntimeException("解密错误，错误信息：", e);
		}
		return decryptedData;
	}


	public static int testFor() {
		int i = 0;
		for (;;) {
			i++;
			if (i == 10) {
				return i;
			}
		}

	}
}
