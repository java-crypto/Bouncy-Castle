package bc;

// source: https://github.com/bcgit/bc-java/blob/master/core/src/test/java/org/bouncycastle/crypto/test/Ed25519Test.java
// needs bouncy castle beta: bcprov-jdk15on-161b20.jar (version 1.605)
// tested with Java 8 Build 191 x64
// this is a full working example for generating, signing, verififying with ed25519 keys
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;
import java.io.UnsupportedEncodingException;
import javax.xml.bind.DatatypeConverter;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator;
import org.bouncycastle.crypto.params.Ed25519KeyGenerationParameters;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Ed25519Bc {

	public static void main(String[] args) throws DataLengthException, CryptoException, UnsupportedEncodingException {
		System.out.println("ED25519 with BC");
		Security.addProvider(new BouncyCastleProvider());
		Provider provider = Security.getProvider("BC");
		System.out.println("Provider          :" + provider.getName() + " Version: " + provider.getVersion());
		// generate ed25519 keys
		SecureRandom RANDOM = new SecureRandom();
		Ed25519KeyPairGenerator keyPairGenerator = new Ed25519KeyPairGenerator();
		keyPairGenerator.init(new Ed25519KeyGenerationParameters(RANDOM));
		AsymmetricCipherKeyPair asymmetricCipherKeyPair = keyPairGenerator.generateKeyPair();
		Ed25519PrivateKeyParameters privateKey = (Ed25519PrivateKeyParameters) asymmetricCipherKeyPair.getPrivate();
		Ed25519PublicKeyParameters publicKey = (Ed25519PublicKeyParameters) asymmetricCipherKeyPair.getPublic();
		// the message
		byte[] message = "Message to sign".getBytes("utf-8");
		// create the signature
		Signer signer = new Ed25519Signer();
		signer.init(true, privateKey);
		signer.update(message, 0, message.length);
		byte[] signature = signer.generateSignature();
		// verify the signature
		Signer verifier = new Ed25519Signer();
		verifier.init(false, publicKey);
		verifier.update(message, 0, message.length);
		boolean shouldVerify = verifier.verifySignature(signature);
		// output
		byte[] privateKeyEncoded = privateKey.getEncoded();
		byte[] publicKeyEncoded = publicKey.getEncoded();
		System.out.println("privateKey Length :" + privateKeyEncoded.length + " Data:"
				+ DatatypeConverter.printHexBinary(privateKeyEncoded));
		System.out.println("publicKey Length  :" + publicKeyEncoded.length + " Data:"
				+ DatatypeConverter.printHexBinary(publicKeyEncoded));
		System.out.println(
				"signature Length  :" + signature.length + " Data:" + DatatypeConverter.printHexBinary(signature));
		System.out.println("signature correct :" + shouldVerify);
		// rebuild the keys
		System.out.println("Rebuild the keys and verify the signature with rebuild public key");
		Ed25519PrivateKeyParameters privateKeyRebuild = new Ed25519PrivateKeyParameters(privateKeyEncoded, 0);
		Ed25519PublicKeyParameters publicKeyRebuild = new Ed25519PublicKeyParameters(publicKeyEncoded, 0);
		byte[] privateKeyRebuildEncoded = privateKeyRebuild.getEncoded();
		System.out.println("privateKey Length :" + privateKeyRebuild.getEncoded().length + " Data:"
				+ DatatypeConverter.printHexBinary(privateKeyRebuild.getEncoded()));
		byte[] publicKeyRebuildEncoded = publicKeyRebuild.getEncoded();
		System.out.println("publicKey Length  :" + publicKeyRebuild.getEncoded().length + " Data:"
				+ DatatypeConverter.printHexBinary(publicKeyRebuild.getEncoded()));
		// compare the keys
		System.out.println("private Keys Equal:" + Arrays.equals(privateKeyEncoded, privateKeyRebuildEncoded));
		System.out.println("public Keys Equal :" + Arrays.equals(publicKeyEncoded, publicKeyRebuildEncoded));
		// verify the signature with rebuild public key
		Signer verifierRebuild = new Ed25519Signer();
		verifierRebuild.init(false, publicKeyRebuild);
		verifierRebuild.update(message, 0, message.length);
		boolean shouldVerifyRebuild = verifierRebuild.verifySignature(signature);
		System.out.println("signature correct :" + shouldVerifyRebuild + " with rebuild public key");
	}
}
