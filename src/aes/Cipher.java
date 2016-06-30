package aes;

/**
 * Cipher represents an algorithm that can encrypt and decrypt data.
 */
public interface Cipher {

    /**
     * Takes a message and returns the resulting ciphertext.
     *
     * @param plaintext A message to encrypt.
     * @param key The symmetric key to use.
     * @return The resulting ciphertext.
     */
    public byte[] encrypt(byte[] plaintext, byte[] key);

    /**
     * Takes ciphertext and returns the resulting plaintext.
     *
     * @param ciphertext The ciphertext to decrypt.
     * @param key The symmetric key to use.
     * @return The resulting plaintext.
     */
    public byte[] decrypt(byte[] ciphertext, byte[] key);
}
