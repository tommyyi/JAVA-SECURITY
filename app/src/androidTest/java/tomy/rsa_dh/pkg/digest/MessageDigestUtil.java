package tomy.rsa_dh.pkg.digest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.DigestInputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import tomy.rsa_dh.pkg.BytesToHex;


public class MessageDigestUtil
{
    /**
     * get md5 based on byte[]
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String encryptMD5(byte[] data) throws NoSuchAlgorithmException
    {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);
        byte[] resultBytes = md5.digest();

        String resultString = BytesToHex.fromBytesToHex(resultBytes);
        return resultString;
    }

    /**
     * get md5 based on a file
     * @param path
     * @return
     * @throws Exception
     */
    public static String getMD5OfFile(String path) throws Exception
    {
        FileInputStream fileInputStream = new FileInputStream(new File(path));
        DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, MessageDigest.getInstance("MD5"));
        byte[] buffer = new byte[1024];
        int read = digestInputStream.read(buffer, 0, 1024);
        while (read != -1)
        {
            read = digestInputStream.read(buffer, 0, 1024);
        }

        MessageDigest messageDigest = digestInputStream.getMessageDigest();
        byte[] resultBytes = messageDigest.digest();
        String resultString = BytesToHex.fromBytesToHex(resultBytes);
        return resultString;
    }

    public static String encryptSHA(byte[] data) throws NoSuchAlgorithmException
    {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        messageDigest.update(data);
        byte[] resultBytes = messageDigest.digest();

        String resultString = BytesToHex.fromBytesToHex(resultBytes);
        return resultString;
    }

    public static byte[] initHmacKey() throws Exception
    {
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA512");
        SecretKey secretKey = keyGen.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * 验证时也是这个过程
     * @param data
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static String calculateHmac(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException
    {
        SecretKey secretKey = new SecretKeySpec(key, "HmacSHA512");
        Mac mac = Mac.getInstance("HmacSHA512");
        mac.init(secretKey);
        byte[] resultBytes = mac.doFinal(data);

        String resultString = BytesToHex.fromBytesToHex(resultBytes);
        return resultString;
    }


}
