package com.vn.hcmute.team.cortana.mymoney.utils;

import static com.vn.hcmute.team.cortana.mymoney.BuildConfig.AES_KEY;

import android.util.Base64;
import java.security.Key;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
/**
 * Created by infamouSs on 8/25/17.
 */

public class SecurityUtil {
    private static final String ALGORITHM = "AES";
    private static final byte[] KEY_AES = AES_KEY.getBytes();
    
    public static String getRandomUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    public static String encrypt(String valueToEnc)   {
        try{
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encValue = c.doFinal(valueToEnc.getBytes());
            String encryptedValue = Base64.encodeToString(encValue,Base64.NO_WRAP);
            return encryptedValue;
        }catch (Exception e){
            throw new UnknownError();
        }
      
    }

    public static String decrypt(String encryptedValue)  {
        try{
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decordedValue = Base64.decode(encryptedValue,Base64.NO_WRAP);
            byte[] decValue = c.doFinal(decordedValue);
            return new String(decValue);
        }catch (Exception e){
            throw new UnknownError();
        }
       
    }

    private static Key generateKey() throws Exception {
        return new SecretKeySpec(KEY_AES, ALGORITHM);
    }
    
}
