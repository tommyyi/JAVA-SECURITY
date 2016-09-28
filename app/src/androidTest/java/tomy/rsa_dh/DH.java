package tomy.rsa_dh;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

/**
 * 用于协商对称加密的秘钥，解决对称加密的秘钥传输问题
 * 用对方公钥和自己的私钥生成对称加密秘钥
 *
 */
@RunWith(AndroidJUnit4.class)
public class DH
{
    private static final java.lang.String ALGORITHEM_NAME = "DH";

    @Test
    public void testDH() throws Exception
    {
        KeyPair keyPair = Tool.getKeyPair(ALGORITHEM_NAME);

        DHPublicKey publicKeyA = (DHPublicKey) keyPair.getPublic();
        DHPrivateKey privateKeyA = (DHPrivateKey) keyPair.getPrivate();

        KeyPair bKeyPair = getBKeyPair(publicKeyA);
        DHPublicKey publicKeyB = (DHPublicKey) bKeyPair.getPublic();
        DHPrivateKey privateKeyB = (DHPrivateKey) bKeyPair.getPrivate();

        KeyAgreement keyAgreement=KeyAgreement.getInstance(ALGORITHEM_NAME);
        keyAgreement.init(privateKeyB);
        keyAgreement.doPhase(publicKeyA,true);
        byte[] secretB = keyAgreement.generateSecret("DES").getEncoded();
        System.out.println(Tool.byteArray2HexString(secretB));

        keyAgreement.init(privateKeyA);
        keyAgreement.doPhase(publicKeyB,true);
        byte[] secretA = keyAgreement.generateSecret("DES").getEncoded();
        System.out.println(Tool.byteArray2HexString(secretA));
    }

    /**
     * initialize时要使用对方的公钥参数
     * @param publicKey
     * @return
     * @throws Exception
     */
    private KeyPair getBKeyPair(DHPublicKey publicKey)throws Exception
    {
        KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance(ALGORITHEM_NAME);
        DHParameterSpec params = publicKey.getParams();
        keyPairGenerator.initialize(params);

        return keyPairGenerator.generateKeyPair();
    }
}
