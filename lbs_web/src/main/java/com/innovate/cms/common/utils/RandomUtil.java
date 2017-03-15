package com.innovate.cms.common.utils;

import com.innovate.cms.modules.qs.entity.user.RandomUser;

public class RandomUtil {

	public static void main(String[] args) {

		for (int i = 0; i < 100; i++) {
			RandomUser rUser = randomUser("m");
			System.out.println("man:" + rUser.getMan() + "\twoman:" + rUser.getWoman());
		}
		for(int i = 0; i < 1000; i++) {
			System.out.println(getRandomNumByRate(0, 1, 33));
		}
		
	}


	/**
	 *  sex=f: 女性随机率70
	 *  sex=m: 男性随机率70
	 * @param sex
	 * @return
	 */
	public static RandomUser randomUser(String sex) {
		RandomUser rUser = new RandomUser();
		for (int i = 0; i < 3; i++) {
			int ran = getRandomRange(100);
			if (ran < 70) {
				if (sex.equals("m")) {
					rUser.setMan(rUser.getMan() + 1);
				} else {
					rUser.setWoman(rUser.getWoman() + 1);
				}
			} else {
				if (sex.equals("m")) {
					rUser.setWoman(rUser.getWoman() + 1);
				} else {
					rUser.setMan(rUser.getMan() + 1);
				}
			}
		}
		return rUser;
	}
	
	/**
	 * 指定概率获取随机数
	 * @param numA 数字A
	 * @param numB 数字B
	 * @param rate 数字A的概率
	 * @return
	 */
	public static int getRandomNumByRate(int numA, int numB, int rate) {
		int ran = getRandomRange(100);
		if (ran < rate) {
			return numA;
		} else {
			return numB;
		}
	}

	/**
	 * java产生一个0到n的随机数 [0,n]
	 * 
	 * @param n
	 * @return
	 */
	public static int getRandomRange(int n) {
		return (int) Math.rint(Math.random() * n);
	}

}

