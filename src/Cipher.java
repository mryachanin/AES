
public interface Cipher {
    public byte[] encrypt(byte[] plaintext, byte[] key);

    public byte[] decrypt(byte[] ciphertex, byte[] keyt);
}
