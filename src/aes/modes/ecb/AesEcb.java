package aes.modes.ecb;
import java.util.Arrays;

import aes.Cipher;
import aes.Key;
import aes.State;

/**
 * An implementation of AES ECB mode.
 *
 * NOTE: if the message is not a multiple of 16-bytes, this will zero extend the end of the message to a multiple of 16 bytes. For instance, if the message is
 * 00112233, it will be decrypted as 0011223300000000. This is not a problem with printing ASCII as '00' represents the null character and signifies the end of a
 * string anyways.
 */
public class AesEcb implements Cipher {

    /**
     * Takes a message and returns the resulting ciphertext.
     *
     * @param plaintext A message to encrypt.
     * @param key A 128, 296, or 256-bit key to use as the symmetric key.
     * @return The encrypted plaintext.
     */
    @Override
    public byte[] encrypt(byte[] plaintext, byte[] key) {
        int encryptedLength;
        if ((plaintext.length % 16) == 0) {
            encryptedLength = plaintext.length;
        } else {
            encryptedLength = plaintext.length + 16 - (plaintext.length % 16);
        }
        byte[] encrypted = new byte[encryptedLength];

        Key m_key = Key.getKey(key);

        // break up the byte array into 16-byte 2d arrays
        for (int i = 0; i < plaintext.length; i += 16) {
            State state = new State(Arrays.copyOfRange(plaintext, i, i + 16));

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
        state.addRoundKey(key.getEncryptKey());
        for (int k = 0; k < (key.Nr - 1); k++) {
            state.subBytes();
            state.shiftRows();
            state.mixColumns();
            state.addRoundKey(key.getEncryptKey());
        }

        // final round does not include mixColumns()
        state.subBytes();
        state.shiftRows();
        state.addRoundKey(key.getEncryptKey());
        return state;
    }

    /**
     * Takes ciphertext and returns the resulting plaintext.
     *
     * @param ciphertext The cipher to decrypt.
     * @param key A 128, 296, or 256-bit key to use as the symmetric key.
     * @return The decrypted ciphertext.
     */
    @Override
    public byte[] decrypt(byte[] ciphertext, byte[] key) {
        int decryptedLength = ciphertext.length;
        byte[] decrypted = new byte[decryptedLength];

        Key m_key = Key.getKey(key);
        m_key.resetDecryptCounter();

        // break up the byte array into 16-byte 2d arrays
        for (int i = 0; i < ciphertext.length; i += 16) {
            State state = new State(Arrays.copyOfRange(ciphertext, i, i + 16));

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
}
