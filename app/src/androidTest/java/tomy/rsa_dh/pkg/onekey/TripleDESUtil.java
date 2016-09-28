package tomy.rsa_dh.pkg.onekey;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class TripleDESUtil {
	
	/*
	 * ������Կ
	 */
	public static byte[] initKey() throws NoSuchAlgorithmException{
		KeyGenerator keyGen = KeyGenerator.getInstance("DESede");
		keyGen.init(168);  //112 168
		SecretKey secretKey = keyGen.generateKey();
		return secretKey.getEncoded();
	}

	
	/*
	 * 3DES ����
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception{
		SecretKey secretKey = new SecretKeySpec(key, "DESede");
		
		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] cipherBytes = cipher.doFinal(data);
		return cipherBytes;
	}
	
	/*
	 * 3DES ����
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception{
		SecretKey secretKey = new SecretKeySpec(key, "DESede");
		
		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] plainBytes = cipher.doFinal(data);
		return plainBytes;
	}
	
	
	
	
	
	
	
	

}
