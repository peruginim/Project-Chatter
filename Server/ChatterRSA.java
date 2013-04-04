import java.math.BigInteger;
import java.io.*;
import java.security.*;
import java.security.spec.*;
import javax.crypto.*;

public class ChatterRSA {
	KeyPair kp;
	Key publicKey;
	Key privateKey;

	public ChatterRSA() {
		try {
			keyGen();
		}catch (Exception e) {
			System.err.print(e);
		}
	}


	/*
	 * This method is to generate the RSA keys
	 */
	public void keyGen() throws Exception{
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(2048);
		kp = kpg.genKeyPair();
		publicKey = kp.getPublic();
		privateKey = kp.getPrivate();

		KeyFactory fact = KeyFactory.getInstance("RSA");
		RSAPublicKeySpec pub = fact.getKeySpec(kp.getPublic(),
				RSAPublicKeySpec.class);
		RSAPrivateKeySpec priv = fact.getKeySpec(kp.getPrivate(),
				RSAPrivateKeySpec.class);

		saveToFile(".public.key", pub.getModulus(),
				pub.getPublicExponent());
		saveToFile(".private.key", priv.getModulus(),
				priv.getPrivateExponent());
	}	
	/*
	 *  Saves the Generated RSA keys to .hidden files
	 */
	public void saveToFile(String fileName, BigInteger mod, BigInteger exp) throws IOException {
		ObjectOutputStream oout = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream(fileName)));
		try {
			oout.writeObject(mod);
			oout.writeObject(exp);
		} catch (Exception e) {
			throw new IOException("Unexpected error", e);
		} finally {
			oout.close();
		}
	}

	/*
	 * Reads PRIVATE keys from .file to be returned as PrivateKey object
	 * This will be used for decryption
	 */
	PrivateKey readKeyFromFile(String keyFileName) throws IOException {
		InputStream in =
			ChatterRSA.class.getResourceAsStream(keyFileName);
		ObjectInputStream oin =
			new ObjectInputStream(new BufferedInputStream(in));
		try {
			BigInteger m = (BigInteger) oin.readObject();
			BigInteger e = (BigInteger) oin.readObject();
			RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(m, e);
			KeyFactory fact = KeyFactory.getInstance("RSA");
			PrivateKey priKey = fact.generatePrivate(keySpec);
			return priKey;
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
		PrivateKey priKey = readKeyFromFile("/.private.key");
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		byte[] cipherData = cipher.doFinal(data);
		return cipherData;
	}


	public static void main(String[] args) {
		ChatterRSA serverKeys = new ChatterRSA();
	}
}
