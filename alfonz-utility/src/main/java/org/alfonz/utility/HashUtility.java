package org.alfonz.utility;

import androidx.annotation.NonNull;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public final class HashUtility {
	private HashUtility() {}

	public static String getMd5(@NonNull byte[] data) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(data);
			StringBuilder stringBuilder = new StringBuilder();
			for (byte b : bytes) {
				stringBuilder.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
			}
			return stringBuilder.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getMd5(@NonNull String data) {
		return getMd5(data.getBytes(StandardCharsets.UTF_8));
	}

	public static String getSha1(@NonNull byte[] data) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.reset();
			md.update(data);
			return byteToHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getSha1(@NonNull String data) {
		return getSha1(data.getBytes(StandardCharsets.UTF_8));
	}

	private static String byteToHex(@NonNull final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
}
