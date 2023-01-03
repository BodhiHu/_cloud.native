package BodhiTree.tree.lib;

import io.jsonwebtoken.Jwts;
import org.apache.commons.io.FileUtils;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

public class CryptoUtils {
    static private Logger logger = LoggerFactory.getLogger(CryptoUtils.class);

    static private PrivateKey privateKey = null;
    static private String rawPrivateKey = null;
    static public PublicKey publicKey = null;
    static public String rawPublicKey = null;

    static {
        try {
            loadRSAKeys();
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("errors while trying to load RSA keys: Cryptos will not work");
            e.printStackTrace();
        }
    }

    // FIXME: sha512 hash mismatch between NodeJS and Java code
    static public String hashText (String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(text.getBytes(UTF_8));
        byte[] hashBytes = md.digest();

        byte[] hex = new Hex().encode(hashBytes);
        return new String(hex, UTF_8).toUpperCase();

    }

    /** RSA asymmetric encryption / decryption *************************/
    static public String publicEncrypt(String data)
        throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException
    {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] crypted = cipher.doFinal(data.getBytes(UTF_8));
        return Base64.getEncoder().encodeToString(crypted);
    }

    public static String privateDecrypt(String crypted)
        throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException
    {
        byte[] cryptedData = Base64.getDecoder().decode(crypted);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(cryptedData));
    }


    /** Cipher / Decipher *************************************************/
    public static String encrypt(String secret, String data) throws Exception {
        MessageDigest dig = MessageDigest.getInstance("SHA-512");
        byte[] key = dig.digest(secret.getBytes(UTF_8));
        SecretKeySpec secKey = new SecretKeySpec(key, "AES");

        Cipher aesCipher = Cipher.getInstance("AES");
        byte[] byteText = data.getBytes(UTF_8);

        aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
        byte[] byteCipherText = aesCipher.doFinal(byteText);

        return new String(Base64.getEncoder().encode(byteCipherText), UTF_8);
    }

    public static String decrypt(String secret, String ciphertext) throws Exception {
        MessageDigest dig = MessageDigest.getInstance("SHA-512");
        byte[] key = dig.digest(secret.getBytes(UTF_8));
        SecretKeySpec secKey = new SecretKeySpec(key, "AES");

        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, secKey);
        byte[] cipherbytes = Base64.getDecoder().decode(ciphertext.getBytes());
        byte[] bytePlainText = aesCipher.doFinal(cipherbytes);

        return new String(bytePlainText, UTF_8);
    }

    /** JWT ************************************************************/
    static public String signJwtToken (String data) {
        String ret = null;
        if (!StringUtils.isEmpty(data)) {
            ret = Jwts.builder()
                .setSubject(data)
                .signWith(CryptoUtils.privateKey)
                .compact();
        }
        return ret;
    }
    static public String parseJwtToken (String jwtToken) {
        String ret = null;
        if (!StringUtils.isEmpty(jwtToken)) {
            ret = Jwts.parser()
                .setSigningKey(CryptoUtils.publicKey)
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
        }
        return ret;
    }

    // see https://gist.github.com/destan/b708d11bd4f403506d6d5bb5fe6a82c5
    static private void loadRSAKeys () throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        ClassLoader classLoader = CryptoUtils.class.getClassLoader();
        File file = new File(classLoader.getResource("ssh-keys/dev/private_key_pkcs8.pem").getFile());
        String rawPrivateKey = FileUtils.readFileToString(file, "UTF-8");
        file = new File(classLoader.getResource("ssh-keys/dev/public_key.pem").getFile());
        String rawPublicKey = FileUtils.readFileToString(file, "UTF-8");

        String privateKeyContent = rawPrivateKey.replaceAll("\\n", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
        String publicKeyContent = rawPublicKey.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");;

        KeyFactory kf = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
        PrivateKey privKey = kf.generatePrivate(keySpecPKCS8);

        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));
        RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);

        CryptoUtils.privateKey = privKey;
        CryptoUtils.rawPrivateKey = rawPrivateKey;
        CryptoUtils.publicKey = pubKey;
        CryptoUtils.rawPublicKey = rawPublicKey;
    }
}
