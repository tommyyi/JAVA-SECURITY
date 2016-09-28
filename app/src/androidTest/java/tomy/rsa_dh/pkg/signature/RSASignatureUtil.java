package tomy.rsa_dh.pkg.signature;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;


public class RSASignatureUtil
{
    public static final String KEY_ALGORITHM = "RSA";
    public static final String RSA_PUBLIC_KEY = "RSA_PUBLIC_KEY";
    public static final String RSA_PRIVATE_KEY = "RSA_PRIVATE_KEY";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * generates RSA key pair or something else
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception
    {
        /*create key pair*/
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        /*box keys to map*/
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put(RSA_PUBLIC_KEY, publicKey);
        keyMap.put(RSA_PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * gets public key bytes from map
     * @param keyMap
     * @return
     */
    public static byte[] getPublicKey(Map<String, Object> keyMap)
    {
        RSAPublicKey key = (RSAPublicKey) keyMap.get(RSA_PUBLIC_KEY);
        return key.getEncoded();
    }

    /**
     * gets private key bytes
     * @param keyMap
     * @return
     */
    public static byte[] getPrivateKey(Map<String, Object> keyMap)
    {
        RSAPrivateKey key = (RSAPrivateKey) keyMap.get(RSA_PRIVATE_KEY);
        return key.getEncoded();
    }

    /**
     * using original data, private key bytes to sign. (we also using KeyFactory to restore key
     * @param data
     * @param privateKey
     * @return sign
     * @throws Exception
     */
    public static byte[] sign(byte[] data, byte[] privateKey) throws Exception
    {
        /*restore private key from private key bytes*/
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey priKey = keyFactory.generatePrivate(keySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);
        return signature.sign();
    }

    /**
     * @param data original information
     * @param publicKey
     * @param sign signing result
     * @return if data is from the right source and not modified
     * @throws Exception
     */
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
