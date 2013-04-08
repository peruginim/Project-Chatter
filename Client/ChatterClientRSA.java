import java.io.*;
import java.security.*;
import java.security.spec.*;
import javax.crypto.*;
import java.math.BigInteger;

public class ChatterClientRSA {
	/*
	 * Reads PRIVATE keys from .file to be returned as PrivateKey object
	 * This will be used for decryption
	 */
	PublicKey readKeyFromFile(String keyFileName) throws IOException {
		InputStream in =
			ChatterClientRSA.class.getResourceAsStream(keyFileName);
		ObjectInputStream oin =
			new ObjectInputStream(new BufferedInputStream(in));
		try {
			BigInteger m = (BigInteger) oin.readObject();
			BigInteger e = (BigInteger) oin.readObject();
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
			KeyFactory fact = KeyFactory.getInstance("DES");
			PublicKey pubKey = fact.generatePublic(keySpec);
			return pubKey;
		} catch (Exception e) {
			throw new RuntimeException("Spurious serialisation error", e);
		} finally {
			oin.close();
		}
	}
	/*
	 * Decrypt data
	 */
	public byte[] rsaDecrypt(byte[] data) throws Exception{
		PublicKey pubKey = readKeyFromFile("/.public.key");
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		byte[] cipherData = cipher.doFinal(data);
		return cipherData;
	}
}
