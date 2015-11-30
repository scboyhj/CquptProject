package com.cqupt.http;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Encoder;

public class EncryptByEnTool {
	public static String getEncrypt(String str) {

		SecretKeySpec keySpec = new SecretKeySpec("cquptaaa".getBytes(), "des");

		try {
			Cipher cipher = Cipher.getInstance("des");

			cipher.init(Cipher.ENCRYPT_MODE, keySpec);

			byte[] bs = cipher.doFinal(str.getBytes("utf-8"));

			BASE64Encoder base64Encoder = new BASE64Encoder();

			return base64Encoder.encode(bs);

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
