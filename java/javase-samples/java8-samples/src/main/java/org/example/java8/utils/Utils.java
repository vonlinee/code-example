package org.example.java8.utils;

import java.util.concurrent.TimeUnit;

public class Utils {
	
	// 秒
	public static void sleep(long milles) {
		try {
			TimeUnit.SECONDS.sleep(milles);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
