package aes;
import aes.modes.AesMode;
import aes.modes.ecb.AesEcb;

/**
 * AesFactory abstracts the instantiation of a {@link Cipher} implemented with a given {@link AesMode} that uses the
 * Advanced Encryption Standard (AES).
 */
public class AesFactory {

    /**
     * A static factory method that returns a {@link Cipher} implemented with a given {@link AesMode} that uses AES.
     *
     * @param mode The block cipher mode of operation to use.
     * @return A {@link Cipher} implemented with a given {@link AesMode} that uses AES.
     */
    public static Cipher getCipher(AesMode mode) {
        if (mode == AesMode.ECB)
            return new AesEcb();
        return null;
    }
}
