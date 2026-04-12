package de.unims.sopra25.model.logik;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Klasse, die gegebene Passwörter in HashCode umwandeln
 * 
 * @author Learnweb
 */
class PasswordHashing {
    private String currentAlgorithm = "PBKDF2WithHmacSHA512";
    private int currentRounds = 1 << 16;
    private int currentSaltBitLength = 128;
    private int currentKeyBitLength = 512;
    private final SecureRandom random;

    class CryptographicallyHashedPassword {
        public final byte[] hash;
        public final byte[] salt;
        public final String algorithm;
        public final int rounds;

        CryptographicallyHashedPassword(byte[] hash, byte[] salt, String algorithm, int rounds) {
            this.hash = hash;
            this.salt = salt;
            this.algorithm = algorithm;
            this.rounds = rounds;
        }

        CryptographicallyHashedPassword(String hashString) {
            String[] splitHashString = hashString.split("\\$");
            algorithm = splitHashString[0];
            rounds = Integer.parseInt(splitHashString[1]);
            Base64.Decoder dec = Base64.getDecoder();
            salt = dec.decode(splitHashString[2]);
            hash = dec.decode(splitHashString[3]);
        }

        int getKeyBitLength() {
            return hash.length * 8;
        }

        int getSaltBitLength() {
            return salt.length * 8;
        }

        public String toString() {
            Base64.Encoder enc = Base64.getEncoder();
            return algorithm + "$" + rounds + "$" + enc.encodeToString(salt) + "$" + enc.encodeToString(hash);
        }
    }

    PasswordHashing() {
        this.random = new SecureRandom();
    }

    // Hash the given password using the specified parameters, i.e.
    // deterministically transform it into a byte sequence from which the input data
    // cannot be (easily) recovered if parameters have been chosen accordingly.
    private static byte[] hash(char[] password, byte[] salt, String algorithm, int rounds, int keyLen)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password, salt, rounds, keyLen);
        SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
        return f.generateSecret(spec).getEncoded();
    }

    // Hash the given plaintext password using cryptographically secure methods so
    // that plaintext cannot be (easily) recovered from the hashed form.
    public CryptographicallyHashedPassword hashSecurely(char[] password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = new byte[currentSaltBitLength / 8];
        random.nextBytes(salt);
        String algorithm = currentAlgorithm;
        int rounds = currentRounds;
        byte[] hash = hash(password, salt, algorithm, rounds, currentKeyBitLength);
        return new CryptographicallyHashedPassword(hash, salt, algorithm, rounds);
    }

    // Validate the given char sequence against a an cryptographically hashed
    // password, i.e., check whether the currently entered (plaintext!) password
    // (the char sequence) matches the previously entered and cryptographically
    // hashed password.
    public boolean validate(CryptographicallyHashedPassword against, char[] to_check)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] hash_to_check = hash(to_check, against.salt, against.algorithm, against.rounds,
                against.getKeyBitLength());
        Base64.Encoder enc = Base64.getEncoder();
        // PasswordHashing.log("Current hash is: " + enc.encodeToString(hash_to_check));
        boolean all_bytes_equal = true;
        for (int i = 0; i < hash_to_check.length; ++i) {
            all_bytes_equal &= (hash_to_check[i] == against.hash[i]);
        }
        return all_bytes_equal;
    }
}
