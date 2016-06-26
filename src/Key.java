import java.util.Arrays;

import utils.Functions;

/**
 * This represents an expanded key for use with an AES implementation to encrypt or decrypt data.
 */
public class Key {
    // The number of columns (32-bit words) comprising a State
    public static final int Nb = 4;
    public final int Nr, Nk;

    private byte[] key;
    private int keyCount;

    /**
     * Do not allow public instantiation.
     *
     * @param key The bytes that represent the key.
     * @param Nr The number of rounds.
     * @param Nk The number of 32 bit words comprising the cipher key.
     */
    private Key(byte[] key, int Nr, int Nk) {
        this.key = new byte[4 * Nb * (Nr + 1)];
        this.keyCount = 0;
        this.Nr = Nr;
        this.Nk = Nk;

        keyExpansion(key);
    }

    /**
     * Constructs an expanded key to use for encrypting or decrypting a message.
     *
     * @param key The key to use. Supported key lengths are 16, 24, and 32 bytes (128, 192, 256 bits)
     * @return The expanded key object.
     */
    public static Key getKey(byte[] key) {
        int key_len = key.length;

        if (key_len != 16 && key_len != 24 && key_len != 32) {
            System.err.println("Invalid key size: " + key_len);
            System.exit(1);
        }

        int Nk = key_len / 4;
        int Nr = Nk + 6;
        return new Key(key, Nr, Nk);
    }

    /**
     * Expands an initial key for use in encrypting or decrypting a message.
     *
     * @param key The initial key in bytes.
     */
    private void keyExpansion(byte[] key) {
        byte[] temp = new byte[4];

        int i = 0;

        while (i < 4 * Nk) {
            this.key[i] = key[i];
            i++;
        }

        i = Nk;

        while (i < Nb * (Nr + 1)) {
            for (int tmp = 0; tmp < 4; tmp++) {
                temp[tmp] = this.key[((i - 1) * 4) + tmp];
            }

            if (i % Nk == 0) {
                temp = Functions.subWord(Functions.rotWord(temp, 1));
                temp = xorWords(temp, rCon(i / Nk));
            } else if (Nk > 6 && (i % Nk) == 4) {
                temp = Functions.subWord(temp);
            }
            for (int k = 4 * i; k < (4 * i) + 4; k++) {
                this.key[k] = (byte) (this.key[k - (4 * Nk)] ^ temp[k % (4 * i)]); // k % 4*i produces 0, 1, 2, 3
            }
            i++;
        }
    }

    /**
     * Returns the fully expanded key.
     *
     * @return The fully expanded key.
     */
    public byte[] getExpandedKey() {
        return this.key;
    }

    /**
     * Returns the next 16 bytes of the expanded key.
     *
     * @return The next 16 bytes of the expanded key.
     */
    public byte[] getEncryptKey() {
        byte[] keyPart = Arrays.copyOfRange(this.key, keyCount, keyCount + 16);
        keyCount += 16;
        return keyPart;
    }

    /**
     * Returns the previous 16 bytes of the expanded key.
     *
     * @return The previous 16 bites of the expanded key.
     */
    public byte[] getDecryptKey() {
        byte[] keyPart = Arrays.copyOfRange(this.key, keyCount - 16, keyCount);
        keyCount -= 16;
        return keyPart;
    }

    /**
     * Resets the index counter so the key can be used again to encrypt the next 16-bytes.
     */
    public void resetCounter() {
        keyCount = 0;
    }

    /**
     * Resets the index counter so the key can be used again to decrypt the next 16-bytes.
     */
    public void resetDecryptCounter() {
        keyCount = 4 * Nb * (Nr + 1);
    }

    /**
     * @param pow The power of x + 1.
     * @return A word representing {xPow}{00}{00}{00}.
     *
     */
    private byte[] rCon(int pow) {
        byte[] roundConstant = new byte[4];
        byte xPow = 0x01;
        for (int i = 0; i < (pow - 1); i++) {
            xPow = Functions.xtime(xPow);
        }
        roundConstant[0] = xPow;
        roundConstant[1] = 0x00;
        roundConstant[2] = 0x00;
        roundConstant[3] = 0x00;

        return roundConstant;
    }

    /**
     * Returns the xor of 2 words.
     *
     * @param word1 A word.
     * @param word2 A second word.
     * @return A new word with new[i] = word1[i] ^ word2[i].
     */
    private byte[] xorWords(byte[] word1, byte[] word2) {
        byte[] result = new byte[4];

        for (int i = 0; i < 4; i++) {
            result[i] = (byte) (word1[i] ^ word2[i]);
        }

        return result;
    }
}
