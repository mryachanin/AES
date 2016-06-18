import java.util.Arrays;

/**
 * An implementation of AES ECB mode.
 *
 * NOTE: if the message is not a multiple of 16-bytes, this will zero extend the end of the message to a multiple of 16 bytes. For instance, if the message is
 * 00112233, it will be decrypted as 0011223300000000. This is not a problem with printing ASCII as '00' represents the null character and signifies the end of a
 * string anyways.
 */
public class AES {

    /**
     * Do not allow instantiation.
     */
    private AES() {
    }

    /**
     * Takes a message and returns the resulting ciphertext.
     *
     * @param m A message to encrypt.
     * @param key A 128, 296, or 256-bit key to use as the symmetric key.
     * @return The encrypted plaintext.
     */
    public static byte[] encrypt(byte[] message, byte[] key) {
        int encryptedLength;
        if ((message.length % 16) == 0) {
            encryptedLength = message.length;
        } else {
            encryptedLength = message.length + 16 - (message.length % 16);
        }
        byte[] encrypted = new byte[encryptedLength];

        Key m_key = getKey(key);

        // break up the byte array into 16-byte 2d arrays
        for (int i = 0; i < message.length; i += 16) {
            State state = new State(Arrays.copyOfRange(message, i, i + 16));

            byte[] stateBytes = encryptState(state, m_key).getBytes();
            for (int n = 0; n < 16; n++) {
                encrypted[i + n] = stateBytes[n];
            }
            if ((i + 16) != encryptedLength) {
                m_key.resetCounter();
            }
        }

        return encrypted;
    }

    private static State encryptState(State state, Key key) {
        state.addRoundKey(key.getKey());
        for (int k = 0; k < (key.Nr - 1); k++) {
            state.subBytes();
            state.shiftRows();
            state.mixColumns();
            state.addRoundKey(key.getKey());
        }

        // final round does not include mixColumns()
        state.subBytes();
        state.shiftRows();
        state.addRoundKey(key.getKey());
        return state;
    }

    /**
     * Takes a cipher and returns the resulting plaintext.
     *
     * @param cipher The cipher to decrypt.
     * @param key A 128, 296, or 256-bit key to use as the symmetric key.
     * @return The decrypted ciphertext.
     */
    public static byte[] decrypt(byte[] cipher, byte[] key) {
        int decryptedLength = cipher.length;
        byte[] decrypted = new byte[decryptedLength];

        Key m_key = getKey(key);
        m_key.resetDecryptCounter();

        // break up the byte array into 16-byte 2d arrays
        for (int i = 0; i < cipher.length; i += 16) {
            State state = new State(Arrays.copyOfRange(cipher, i, i + 16));

            byte[] stateBytes = decryptState(state, m_key).getBytes();
            for (int n = 0; n < 16; n++) {
                decrypted[i + n] = stateBytes[n];
            }
            if ((i + 16) != decryptedLength) {
                m_key.resetDecryptCounter();
            }
        }

        return decrypted;
    }

    private static State decryptState(State state, Key key) {
        state.addRoundKey(key.getDecryptKey());
        for (int k = 0; k < (key.Nr - 1); k++) {
            state.invShiftRows();
            state.invSubBytes();
            state.addRoundKey(key.getDecryptKey());
            state.invMixColumns();
        }

        // final round does not include mixColumns()
        state.invShiftRows();
        state.invSubBytes();
        state.addRoundKey(key.getDecryptKey());
        return state;
    }

    private static Key getKey(byte[] key) {
        int key_len = key.length;

        if (key_len != 16 && key_len != 24 && key_len != 32) {
            System.err.println("Invalid key size: " + key_len);
            System.exit(1);
        }

        int Nk = key_len / 4;
        int Nr = Nk + 6;
        return new Key(key, Nr, Nk);
    }
}
