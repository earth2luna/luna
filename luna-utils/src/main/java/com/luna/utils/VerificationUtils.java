package com.luna.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;

import com.luna.utils.enm.CharsetEnum;
import com.luna.utils.enm.VerificationEnum;

public class VerificationUtils {

	public static MessageDigest getMessageDigest(VerificationEnum verification) {
		try {
			return MessageDigest.getInstance(verification.getName());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("[Digest failed] - unknow exception");
		}
	}

	public static byte[] getMD5Encode(byte[] input) {
		MessageDigest digest = getMessageDigest(VerificationEnum.MD5);
		digest.update(input);
		return digest.digest();
	}

	public String getMD5Encode(String input, Charset charset) {
		if (StringUtils.isEmpty(input))
			return null;
		return bytes2HexString(getMD5Encode(input.getBytes(charset)));
	}

	public static String getMD5Encode(String input) {
		if (StringUtils.isEmpty(input))
			return null;
		return bytes2HexString(getMD5Encode(input.getBytes(Charset.forName(CharsetEnum.UTF8.getCharsetName()))));
	}

	private static String bytes2HexString(byte[] bs) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < bs.length; i++) {
			builder.append(Integer.toHexString(bs[i] & 0xff));
		}
		return builder.toString();
	}

}
