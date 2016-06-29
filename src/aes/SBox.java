package aes;

import utils.Functions;

/**
 * <p>
 * SBox is an implementation of the substitution box outlined in FIPS-197 for the Advanced Encryption Standard.
 * </p>
 *
 * <p>
 * The s-box and inverse s-box transformations in this implementation do not use look-up tables. Instead, they use the
 * mathematical definitions of the s-box and inverse s-box to dynamically derive the correct output.
 * </p>
 */
public class SBox {
    /**
     * <p>
     * Takes a byte and applies the S-box.
     * </p>
     *
     * <p>
     * The AES S-box works by taking the multiplicative inverse of a polynomial in GF(2)[x]/(x^8 + x^4 + x^3 + x + 1)
     * and then applying the following affine transformation:
     * </p>
     *
     * <pre>
     * |10001111|   |inv[0]|   |1|
     * |11000111|   |inv[1]|   |1|
     * |11100011|   |inv[2]|   |0|
     * |11110001| * |inv[3]| + |0|
     * |11111000|   |inv[4]|   |0|
     * |01111100|   |inv[5]|   |1|
     * |00111110|   |inv[6]|   |1|
     * |00011111|   |inv[7]|   |0|
     * </pre>
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
     * Takes an array of bytes and applies the S-box to each byte.
     *
     * @param in An array of bytes.
     * @return The input passed through the S-box.
     */
    public static byte[] apply(byte[] in) {
        for (int i = 0; i < in.length; i++) {
            in[i] = apply(in[i]);
        }
        return in;
    }

    /**
     * <p>
     * Takes a byte and applies the inverse S-box.
     * </p>
     *
     * <p>
     * The AES inverse S-box works by applying the following affine transformation and then taking the multiplicative
     * inverse of the polynomial in GF(2)[x]/(x^8 + x^4 + x^3 + x + 1)
     * </p>
     *
     * <pre>
     * |00100101|   |in[0]|   |1|
     * |10010010|   |in[1]|   |0|
     * |01001001|   |in[2]|   |1|
     * |10100100| * |in[3]| + |0|
     * |01010010|   |in[4]|   |0|
     * |00101001|   |in[5]|   |0|
     * |10010100|   |in[6]|   |0|
     * |01001010|   |in[7]|   |0|
     * </pre>
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
