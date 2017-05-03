/**
 * COPYRIGHT@LAULYL
 */
package com.luna.web.security;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import com.luna.utils.IOUtils;

/**
 * @author laulyl
 * @date 2017年5月2日 下午3:03:16
 * @description RSA 编码解码
 */
public class RSACoder {

	// RSA最大加密明文大小
	private static final int MAX_ENCRYPT_BLOCK = 117;

	// RSA最大解密密文大小
	private static final int MAX_DECRYPT_BLOCK = 128;

	// 获取钥匙
	public static Key getKeyBase64(String derPath, boolean isPublic) {

		Key key = null;
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(SecurityCusor.KEY_ALGORITHM);
			byte[] bs = IOUtils.readBytes(derPath);
			byte[] decodeBytes = Base64.decodeBase64(bs);
			if (isPublic) {
				X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodeBytes);
				key = keyFactory.generatePublic(keySpec);
			} else {
				PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodeBytes);
				key = keyFactory.generatePrivate(keySpec);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return key;
	}

	public static String decryptByPrivateKey(String base64String, Key privateKey) throws Exception {
		return StringUtils.newStringUtf8(decryptByPrivateKey(Base64.decodeBase64(base64String), privateKey));
	}

	public static String encryptBase64String(String source, Key publicKey) throws Exception {
		return Base64.encodeBase64String(encryptByPublicKey(source.getBytes(), publicKey));
	}

	// 公钥加密
	public static byte[] encryptByPublicKey(byte[] data, Key publicKey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance(SecurityCusor.KEY_ALGORITHM);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		return encryptedData;
	}

	public static byte[] decryptByPrivateKey(byte[] encryptedData, Key privateKey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance(SecurityCusor.KEY_ALGORITHM);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		return decryptedData;
	}

	public static KeyPair generateKeyPair() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(SecurityCusor.KEY_ALGORITHM);
		keyPairGenerator.initialize(1024);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		return keyPair;
	}

}
