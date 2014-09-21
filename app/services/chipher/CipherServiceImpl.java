package services.chipher;

import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.google.common.io.BaseEncoding;
import com.google.inject.Singleton;

/**
 * Utility to Encrypt/Decrypt Strings
 * @author Jerome Baudoux
 */
@Singleton
public class CipherServiceImpl implements CipherService {
	
	private String privateKey = "WAmhsmDHOBTDr7PY";
	private Charset charset = Charset.forName("UTF-8");

	@Override
	public String encrypt(String string) throws CipherException {
		try {
		    byte[] raw = this.privateKey.getBytes(this.charset);
		    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[16]));
		    byte [] enc = cipher.doFinal(string.getBytes(this.charset));
		    return BaseEncoding.base64().encode(enc);
		} catch (Throwable t) {
			throw new CipherException(t);
		}
	}

	@Override
	public String decrypt(String string) throws CipherException {
		try {
		    byte[] raw = this.privateKey.getBytes(this.charset);
		    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[16]));
		    byte [] enc = BaseEncoding.base64().decode(string);
			return new String(cipher.doFinal(enc), this.charset);
		} catch (Throwable t) {
			throw new CipherException(t);
		}
	}
}
