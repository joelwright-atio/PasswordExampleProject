package de.atio;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtil {
	
	private String hardCodedSecretKey	= "Cmlx+fdqO3fvWr+cOnKGv3nIlBlbmBHK/3sgULkISKc=";
	private String algorithm			= "AES/CBC/PKCS5Padding";
	private Cipher cipher				= null;
	private SecretKey secretKey			= null;
	
	public CryptoUtil() throws
			NoSuchAlgorithmException,
			NoSuchPaddingException {
		KeyGenerator keyGenerator =  KeyGenerator.getInstance("AES");
		keyGenerator.init(256);
		if (this.hardCodedSecretKey != null) {
			byte[] decodedKey = Base64.getDecoder().decode(hardCodedSecretKey.getBytes());
			this.secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
		} else {
			this.secretKey = keyGenerator.generateKey();
			byte[] rawData = secretKey.getEncoded();
			String encodedKey = Base64.getEncoder().encodeToString(rawData);		
			System.out.println("please set hardCodedSecretKey in source file to : " + encodedKey);
		}
		this.cipher = Cipher.getInstance(algorithm);
	}
	public String encrypt(String toEncryptString) throws
			NoSuchAlgorithmException,
			NoSuchPaddingException,
			InvalidKeyException,
			UnsupportedEncodingException,
			IllegalBlockSizeException,
			BadPaddingException,
			InvalidAlgorithmParameterException	{
		this.cipher.init(Cipher.ENCRYPT_MODE, secretKey,new IvParameterSpec(new byte[16]));
		byte[] utf8Bytes = toEncryptString.getBytes("UTF8");
		byte[] encryptedBytes = cipher.doFinal(utf8Bytes);
		return java.util.Base64.getEncoder().encodeToString(encryptedBytes);
	}
	public String decrypt(String toDecryptString) throws
			InvalidKeyException,
			IllegalBlockSizeException,
			BadPaddingException,
			UnsupportedEncodingException,
			InvalidAlgorithmParameterException{
		this.cipher.init(Cipher.DECRYPT_MODE, secretKey,new IvParameterSpec(new byte[16]));
		byte[] encryptedBytes = java.util.Base64.getDecoder().decode(toDecryptString);
		byte[] utf8Bytes = cipher.doFinal(encryptedBytes);
		return new String(utf8Bytes, "UTF8");
	}
}
