package tomy.rsa_dh;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * RSA生成公私钥，公钥加密私钥解密（加密），或者私钥加密公钥解密（可用于数字签名）
 */
@RunWith(AndroidJUnit4.class)
public class RSA
{
    private static final java.lang.String ALGORITHEM_NAME = "RSA";

    @Test
    public void testRSA() throws Exception
    {
        KeyPair keyPair = Tool.getKeyPair(ALGORITHEM_NAME);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        String publicKeyStr = Tool.byteArray2HexString(publicKey.getEncoded());
        System.out.println(publicKeyStr);
        String privateKeyStr = Tool.byteArray2HexString(privateKey.getEncoded());
        System.out.println(privateKeyStr);

        Cipher cipher=Cipher.getInstance(ALGORITHEM_NAME);
        cipher.init(Cipher.ENCRYPT_MODE,getPrivateKey(privateKey.getEncoded(), ALGORITHEM_NAME));
        byte[] secretData = cipher.doFinal(Tool.DATA.getBytes());

        cipher.init(Cipher.DECRYPT_MODE,getPublicKey(publicKey.getEncoded(), ALGORITHEM_NAME));
        byte[] plaintBytes = cipher.doFinal(secretData);

        System.out.println("");
        System.out.println("");

        System.out.println(new String(plaintBytes));

        System.out.println("");
        System.out.println("");

    }

    private PublicKey getPublicKey(byte[] key, String algorithemName) throws Exception
    {
        X509EncodedKeySpec x509EncodedKeySpec=new X509EncodedKeySpec(key);
        KeyFactory keyFactory=KeyFactory.getInstance(algorithemName);
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        return publicKey;
    }

    private PrivateKey getPrivateKey(byte[] key, String algorithemName) throws Exception
    {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec=new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory=KeyFactory.getInstance(algorithemName);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        return privateKey;
    }

}
