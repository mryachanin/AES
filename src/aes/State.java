package aes;
import utils.Functions;
import utils.StringHelper;

/**
 * <p>
 * State is an implementation of the State outlined in FIPS-197 for the Advanced Encryption Standard.
 * </p>
 *
 * <p>
 * The State consists of 4 rows each containing 4 bytes, resulting in a block length of 128 bits.
 * </p>
 */
public class State {
    private byte[][] state;

    /**
     * Takes in an array of 16 bytes and constructs a state array.
     *
     * @param b The bytes to use to initialize this state.
     */
    public State(byte[] b) {
        this.state = new byte[4][4];

        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 4; k++) {
                state[k][i] = b[k + 4 * i];
            }
        }
    }

    /**
     * Returns this state.
     *
     * @return This state.
     */
    public byte[][] getState() {
        return this.state;
    }

    /**
     * Transforms this 2D state array into a 1D byte array.
     *
     * @return This state as a 1D byte array.
     */
    public byte[] getBytes() {
        byte[] bytes = new byte[16];
        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 4; k++) {
                bytes[k + 4 * i] = state[k][i];
            }
        }
        return bytes;
    }

    @Override
    public String toString() {
        return StringHelper.prettify2DArrayAsHex(this.state);
    }

    /**
     * Applies the S-box to each index of this state.
     */
    public void subBytes() {
        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 4; k++) {
                state[k][i] = SBox.apply(state[k][i]);
            }
        }
    }

    /**
     * Applies the inverse S-box to each index of this state.
     */
    public void invSubBytes() {
        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 4; k++) {
                state[k][i] = SBox.applyInverse(state[k][i]);
            }
        }
    }

    /**
     * Shifts 3 of the rows.
     */
    public void shiftRows() {
        state[1] = Functions.rotWord(state[1], 1);
        state[2] = Functions.rotWord(state[2], 2);
        state[3] = Functions.rotWord(state[3], 3);
    }

    /**
     * Shifts 3 of the rows back.
     */
    public void invShiftRows() {
        state[1] = Functions.rotWord(state[1], 3);
        state[2] = Functions.rotWord(state[2], 2);
        state[3] = Functions.rotWord(state[3], 1);
    }

    /**
     * Each column is multiplied by a(x) = (03)x^3 + (01)x^2 +(01)^x + (02).
     */
    public void mixColumns() {
        byte[] tmp = new byte[4];
        for (int c = 0; c < 4; c++) {
            tmp[0] = (byte) ((Functions.mul((byte) 0x02, state[0][c])) ^ (Functions.mul((byte) 0x03, state[1][c])) ^ state[2][c] ^ state[3][c]);
            tmp[1] = (byte) (state[0][c] ^ (Functions.mul((byte) 0x02, state[1][c])) ^ (Functions.mul((byte) 0x03, state[2][c])) ^ state[3][c]);
            tmp[2] = (byte) (state[0][c] ^ state[1][c] ^ (Functions.mul((byte) 0x02, state[2][c])) ^ (Functions.mul((byte) 0x03, state[3][c])));
            tmp[3] = (byte) ((Functions.mul((byte) 0x03, state[0][c])) ^ state[1][c] ^ state[2][c] ^ (Functions.mul((byte) 0x02, state[3][c])));
            for (int i = 0; i < 4; i++) {
                state[i][c] = tmp[i];
            }
        }
    }

    /**
     * Each column is multiplied by a^-1(x) = {0b}x^3 + {0d}x^2 + {09}x + {0e}.
     */
    public void invMixColumns() {
        byte[] tmp = new byte[4];
        for (int c = 0; c < 4; c++) {
            tmp[0] = (byte) ((Functions.mul((byte) 0x0e, state[0][c])) ^ (Functions.mul((byte) 0x0b, state[1][c])) ^ (Functions.mul((byte) 0x0d, state[2][c])) ^ (Functions
                    .mul((byte) 0x09, state[3][c])));
            tmp[1] = (byte) ((Functions.mul((byte) 0x09, state[0][c])) ^ (Functions.mul((byte) 0x0e, state[1][c])) ^ (Functions.mul((byte) 0x0b, state[2][c])) ^ (Functions
                    .mul((byte) 0x0d, state[3][c])));
            tmp[2] = (byte) ((Functions.mul((byte) 0x0d, state[0][c])) ^ (Functions.mul((byte) 0x09, state[1][c])) ^ (Functions.mul((byte) 0x0e, state[2][c])) ^ (Functions
                    .mul((byte) 0x0b, state[3][c])));
            tmp[3] = (byte) ((Functions.mul((byte) 0x0b, state[0][c])) ^ (Functions.mul((byte) 0x0d, state[1][c])) ^ (Functions.mul((byte) 0x09, state[2][c])) ^ (Functions
                    .mul((byte) 0x0e, state[3][c])));
            for (int i = 0; i < 4; i++) {
                state[i][c] = tmp[i];
            }
        }
    }

    /**
     * xor's each state index with it's corresponding key index. This is also it's own inverse.
     *
     * @param key 2D array representing the key.
     */
    public void addRoundKey(byte[] key) {
        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 4; k++) {
                state[k][i] = (byte) (state[k][i] ^ key[k + (4 * i)]);
            }
        }
    }
}