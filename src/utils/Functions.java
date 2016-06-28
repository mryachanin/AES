package utils;
public class Functions {

    /**
     * Do not allow instantiation.
     */
    private Functions() {
    }

    /**
     * Finds the highest bit set to 1 in a polynomial.
     *
     * @param algMatrix A polynomial to test.
     * @param n The number of bits to check.
     * @return The highest bit set.
     */
    public static int findHighestBitSet(int algMatrix, int n) {
        int highestBit = 0;
        for (int i = 0; i < n; i++) {
            if (isBitSet(algMatrix, i)) {
                highestBit = i;
            }
        }
        return highestBit;
    }

    /**
     * Checks if a certain bit of a polynomial is set (0 indexed).
     *
     * @param poly A polynomial to check.
     * @param bit The bit to check.
     * @return True if the bit is set; false otherwise.
     */
    public static boolean isBitSet(int poly, int bit) {
        return (poly & (0x01 << bit)) != 0;
    }

    /**
     * Returns the multiplicative inverse of polynomial b in the finite field 2^8; 0 maps to 0.
     *
     * @param poly A byte representing the polynomial to take the inverse of.
     * @return A byte representing the inverse of the polynomial passed in.
     */
    public static byte getMultInverse(int poly) {
        if (poly == 0) {
            return 0;
        }
        int[][] algMatrix = new int[2][3];

        algMatrix[0][0] = 0x011b;
        algMatrix[0][1] = 1;
        algMatrix[0][2] = 0;

        algMatrix[1][0] = poly;
        algMatrix[1][1] = 0;
        algMatrix[1][2] = 1;

        while (algMatrix[0][0] != 1 && algMatrix[1][0] != 1) {
            int highestBit0 = findHighestBitSet(algMatrix[0][0], 9);
            int highestBit1 = findHighestBitSet(algMatrix[1][0], 8);
            int diff = highestBit0 - highestBit1;

            if (diff > 0) {
                algMatrix[0][0] ^= xtimes(algMatrix[1][0], diff);
                algMatrix[0][1] ^= xtimes(algMatrix[1][1], diff);
                algMatrix[0][2] ^= xtimes(algMatrix[1][2], diff);
            } else if (diff < 0) {
                algMatrix[1][0] ^= xtimes(algMatrix[0][0], (diff * -1));
                algMatrix[1][1] ^= xtimes(algMatrix[0][1], (diff * -1));
                algMatrix[1][2] ^= xtimes(algMatrix[0][2], (diff * -1));
            } else { // diff == 0
                if (algMatrix[0][0] > algMatrix[1][0]) {
                    algMatrix[0][0] ^= algMatrix[1][0];
                    algMatrix[0][1] ^= algMatrix[1][1];
                    algMatrix[0][2] ^= algMatrix[1][2];
                } else {
                    algMatrix[1][0] ^= algMatrix[0][0];
                    algMatrix[1][1] ^= algMatrix[0][1];
                    algMatrix[1][2] ^= algMatrix[0][2];
                }
            }

            algMatrix[0][0] &= 0xff; // fixes java's impulsive nature to sign extend
            algMatrix[1][0] &= 0xff;
        }

        if (algMatrix[0][0] == 1) {
            return (byte) algMatrix[0][2];
        }

        return (byte) algMatrix[1][2];
    }

    /**
     * Takes in a 4-byte word and rotates each byte to the left n times.
     *
     * @param An array to rotate.
     * @param The number of times to rotate.
     * @return The rotated array.
     */
    public static byte[] rotWord(byte[] in, int n) {
        for (int i = 0; i < n; i++) {
            byte tmp = in[0];
            in[0] = in[1];
            in[1] = in[2];
            in[2] = in[3];
            in[3] = tmp;
        }

        return in;
    }

    /**
     * Takes a 4-byte input word and applies the S-box.
     *
     * @param in A 4 byte word.
     * @return The input passed through the S-box.
     */
    public static byte[] subWord(byte[] in) {
        for (int i = 0; i < 4; i++) {
            in[i] = sBox(in[i]);
        }
        return in;
    }

    /**
     * Takes a byte and applies the S-box.
     *
     * @param in A single byte.
     * @return The input passed through the S-box.
     */
    public static byte sBox(byte in) {
        byte inv = getMultInverse(in);
        byte val = inv;
        for (int i = 0; i < 8; i++) {
            for (int j = 4; j < 8; j++) {
                if (isBitSet(inv, (i + j) % 8)) {
                    val ^= (0x01 << i);
                }
            }
        }
        return (byte) (val ^ 0x63);
    }

    /**
     * Takes a byte and applies the inverse S-box.
     *
     * @param in A single byte.
     * @return The input passed through the inverse S-box.
     */
    public static byte inverseSBox(byte in) {
        byte val = 0x05;
        for (int i = 0; i < 8; i++) {
            if (isBitSet(in, (i + 2) % 8)) {
                val ^= (0x01 << i);
            }
            if (isBitSet(in, (i + 5) % 8)) {
                val ^= (0x01 << i);
            }
            if (isBitSet(in, (i + 7) % 8)) {
                val ^= (0x01 << i);
            }
        }
        return getMultInverse(val);
    }

    /**
     * Multiplies a polynomial by x in the finite field 2^8; {01}{1b} = 0.
     *
     * @param poly A byte representing the polynomial to multiply by 'x'.
     * @return A byte representing (poly)(x) in finite field 2^8.
     */
    public static byte xtime(byte poly) {
        byte b = (byte) (poly << 1);
        byte mask = 0x1b;
        if (isBitSet(poly, 7)) {
            b = (byte) (b ^ mask);
        }
        return b;
    }

    /**
     * Multiplies a polynomial by 'x' n times This is used for getting the multiplicative inverse and does not need to reduce with respect to the mod.
     *
     * @param poly A polynomial to multiply.
     * @param n The number of times to multiply the polynomial by 'x'.
     * @return The new polynomial.
     */
    public static int xtimes(int poly, int n) {
        return (poly << n);
    }

    /**
     * Multiplies 2 bytes modulo x^4 + 1.
     *
     * @param a A single byte.
     * @param s A single byte.
     * @return (a)(s) (mod x^4 + 1).
     */
    public static byte mul(byte a, byte s) {
        byte product = 0;
        byte temp;

        while (a != 0) {
            if ((a & 0x01) != 0) {
                product = (byte) (product ^ s);
            }
            temp = (byte) (s & 0x80);
            s = (byte) (s << 0x01);
            if (temp != 0) {
                s = (byte) (s ^ 0x1b);
            }
            a = (byte) ((a & 0xff) >> 1);
        }
        return product;
    }
}
