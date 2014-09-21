package services.chipher;

/**
 * Utility to Encrypt/Decrypt Strings
 * @author Jerome Baudoux
 */
public interface CipherService {

	/**
	 * Encrypt a message
	 * @param string message
	 * @return encrypted message
	 */
	public String encrypt(final String string) throws CipherException;
	
	/**
	 * Tries to decrypt a message
	 * @param string message to decrypt
	 * @return decrypted message
	 * @throws DecryptException error
	 */
	public String decrypt(final String string) throws CipherException;
	
	/**
	 * Error during the decryption
	 * @author Jerome Baudoux
	 */
	public class CipherException extends Exception {
		private static final long serialVersionUID = 1L;
		public CipherException(Throwable t) {
			super("An error occurred during the cipher operation", t);
		}
	}
}
