package tomy.rsa_dh.pkg.signature;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;


public class DSASignatureUtil
{
    public static final String KEY_ALGORITHM = "DSA";
    public static final int KEY_SIZE = 1024;
    public static final String DSA_PUBLIC_KEY = "DSA_PUBLIC_KEY";
    public static final String DSA_PRIVATE_KEY = "DSA_PRIVATE_KEY";
    public static final String SIGNATURE_ALGORITHM = "SHA1withDSA";

    public static Map<String, Object> initKey() throws Exception
    {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        DSAPublicKey publicKey = (DSAPublicKey) keyPair.getPublic();
        DSAPrivateKey privateKey = (DSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>();
        keyMap.put(DSA_PUBLIC_KEY, publicKey);
        keyMap.put(DSA_PRIVATE_KEY, privateKey);
        return keyMap;
    }

    public static byte[] getPublicKey(Map<String, Object> keyMap)
    {
        DSAPublicKey key = (DSAPublicKey) keyMap.get(DSA_PUBLIC_KEY);
        return key.getEncoded();
    }

    public static byte[] getPrivateKey(Map<String, Object> keyMap)
    {
        DSAPrivateKey key = (DSAPrivateKey) keyMap.get(DSA_PRIVATE_KEY);
        return key.getEncoded();
    }

    public static byte[] sign(byte[] data, byte[] privateKey) throws Exception
    {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey priKey = keyFactory.generatePrivate(keySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);
        return signature.sign();
    }

    public static boolean verify(byte[] data, byte[] publicKey, byte[] sign) throws Exception
    {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);
        boolean isValid = signature.verify(sign);
        return isValid;
    }
}
