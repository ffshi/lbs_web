package qselect_3_web;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.google.common.collect.Lists;
import com.innovate.cms.common.utils.DateUtils;
import com.innovate.cms.common.utils.HttpClientUtil;
import com.innovate.cms.common.utils.ListSortUtil;
import com.innovate.cms.modules.qs.entity.sns.RecommendUser;

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
//		List<RecommendUser> temp = Lists.newArrayList();
//		RecommendUser u1 = new RecommendUser(40, "fdsfdsfds", "1111122222");
//		RecommendUser u2 = new RecommendUser(30, "fdsfdsfds", "1111122222");
//		RecommendUser u3 = new RecommendUser(80, "fdsfdsfds", "1111122222");
//		temp.add(u1);
//		temp.add(u2);
//		temp.add(u3);
//
//		ListSortUtil<RecommendUser> sortList = new ListSortUtil<RecommendUser>();
//
//		sortList.sort(temp, "match", "desc");
//
//		System.out.println("");
		
//		System.out.println(DateUtils.getIntDate());
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

	private static int compareRes(String r1, String r2) {
		int count = 0;
		for (int i = 0; i < r1.length(); i++) {
			if (r1.charAt(i) == r2.charAt(i)) {
				count++;
			}
		}
		return 10 * count + new Random().nextInt(10);
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
