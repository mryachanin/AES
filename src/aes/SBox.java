package aes;

import utils.Functions;

public class SBox {
    /**
     * Takes a byte and applies the S-box.
     *
     * @param in A single byte.
     * @return The input passed through the S-box.
     */
    public static byte apply(byte in) {
        byte inv = Functions.getMultInverse(in);
        byte val = inv;
        for (int i = 0; i < 8; i++) {
            for (int j = 4; j < 8; j++) {
                if (Functions.isBitSet(inv, (i + j) % 8)) {
                    val ^= (0x01 << i);
                }
            }
        }
        return (byte) (val ^ 0x63);
    }

    /**
     * Takes an array of bytes and applies the S-box to each index.
     *
     * @param in An array of bytes.
     * @return The input passed through the S-box.
     */
    public static byte[] apply(byte[] in) {
        for (int i = 0; i < in.length; i++) {
            in[i] = SBox.apply(in[i]);
        }
        return in;
    }

    /**
     * Takes a byte and applies the inverse S-box.
     *
     * @param in A single byte.
     * @return The input passed through the inverse S-box.
     */
    public static byte applyInverse(byte in) {
        byte val = 0x05;
        for (int i = 0; i < 8; i++) {
            if (Functions.isBitSet(in, (i + 2) % 8)) {
                val ^= (0x01 << i);
            }
            if (Functions.isBitSet(in, (i + 5) % 8)) {
                val ^= (0x01 << i);
            }
            if (Functions.isBitSet(in, (i + 7) % 8)) {
                val ^= (0x01 << i);
            }
        }
        return Functions.getMultInverse(val);
    }
}
