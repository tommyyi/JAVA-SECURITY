package tomy.rsa_dh;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * Created by Administrator on 2016/9/27.
 */
public class Tool
{
    static final String DATA = "tomy";

    public static String byteArray2HexString(byte[] digest)
    {
        StringBuilder stringBuilder=new StringBuilder();
        for (byte aDigest : digest)
        {
            String character = Integer.toHexString(0xFF & aDigest);
            if (character.length() == 1)
            {
                stringBuilder.append("0").append(character);
            }
            else
            {
                stringBuilder.append(character);
            }
        }
        return stringBuilder.toString();
    }

    public static KeyPair getKeyPair(String algorithemName) throws Exception
    {
        KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance(algorithemName);
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }
}
