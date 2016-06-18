public class State extends Functions {
    private byte[][] state;

    /**
     * Takes in an array of 16 bytes and forms a state array
     *
     * @param b bytes taken in
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
     * Applies the S-box to each index of the state
     */
    public void subBytes() {
        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 4; k++) {
                state[k][i] = s_box(state[k][i]);
            }
        }
    }

    /**
     * Applies the inverse S-box to each index of the state
     */
    public void invSubBytes() {
        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 4; k++) {
                state[k][i] = (byte) invS_Box[state[k][i] & 0xff];
            }
        }
    }

    /**
     * Shifts 3 of the rows
     */
    public void shiftRows() {
        state[1] = rotWord(state[1], 1);
        state[2] = rotWord(state[2], 2);
        state[3] = rotWord(state[3], 3);
    }

    /**
     * Shifts 3 of the rows back
     */
    public void invShiftRows() {
        state[1] = rotWord(state[1], 3);
        state[2] = rotWord(state[2], 2);
        state[3] = rotWord(state[3], 1);
    }

    /**
     * Each column is multiplied by a(x) = (03)x^3 + (01)x^2 +(01)^x + (02)
     */
    public void mixColumns() {
        byte[] tmp = new byte[4];
        for (int c = 0; c < 4; c++) {
            tmp[0] = (byte) ((mul((byte) 0x02, state[0][c])) ^ (mul((byte) 0x03, state[1][c])) ^ state[2][c] ^ state[3][c]);
            tmp[1] = (byte) (state[0][c] ^ (mul((byte) 0x02, state[1][c])) ^ (mul((byte) 0x03, state[2][c])) ^ state[3][c]);
            tmp[2] = (byte) (state[0][c] ^ state[1][c] ^ (mul((byte) 0x02, state[2][c])) ^ (mul((byte) 0x03, state[3][c])));
            tmp[3] = (byte) ((mul((byte) 0x03, state[0][c])) ^ state[1][c] ^ state[2][c] ^ (mul((byte) 0x02, state[3][c])));
            for (int i = 0; i < 4; i++) {
                state[i][c] = tmp[i];
            }
        }
    }

    /**
     * Each column is multiplied by a^-1(x) = {0b}x^3 + {0d}x^2 + {09}x + {0e}
     */
    public void invMixColumns() {
        byte[] tmp = new byte[4];
        for (int c = 0; c < 4; c++) {
            tmp[0] = (byte) ((mul((byte) 0x0e, state[0][c])) ^ (mul((byte) 0x0b, state[1][c])) ^ (mul((byte) 0x0d, state[2][c])) ^ (mul((byte) 0x09, state[3][c])));
            tmp[1] = (byte) ((mul((byte) 0x09, state[0][c])) ^ (mul((byte) 0x0e, state[1][c])) ^ (mul((byte) 0x0b, state[2][c])) ^ (mul((byte) 0x0d, state[3][c])));
            tmp[2] = (byte) ((mul((byte) 0x0d, state[0][c])) ^ (mul((byte) 0x09, state[1][c])) ^ (mul((byte) 0x0e, state[2][c])) ^ (mul((byte) 0x0b, state[3][c])));
            tmp[3] = (byte) ((mul((byte) 0x0b, state[0][c])) ^ (mul((byte) 0x0d, state[1][c])) ^ (mul((byte) 0x09, state[2][c])) ^ (mul((byte) 0x0e, state[3][c])));
            for (int i = 0; i < 4; i++) {
                state[i][c] = tmp[i];
            }
        }
    }

    /**
     * xor's each state index with it's corresponding key index. This is also it's own inverse
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

    /**
     * Multiplies 2 bytes modulo x^4 + 1
     *
     * @param a byte a
     * @param s byte s
     * @return (a)(s) (mod x^4 + 1)
     */
    protected byte mul(byte a, byte s) {
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

    /**
     * transforms 2D state into a 1D byte[]
     *
     * @return state as 1D byte array
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

    /**
     * prints the state in a pretty manner
     */
    public void prettyPrint() {
        System.out.println("________________");
        for (int i = 0; i < 4; i++) {
            System.out.print("| ");
            for (int k = 0; k < 4; k++) {
                System.out.print(String.format("%02X", state[i][k]) + " ");
            }
            System.out.println(" |");
        }
        System.out.println("|______________|");
    }

    public int[] invS_Box = { 0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb, 0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f,
            0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb, 0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3,
            0x4e, 0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25, 0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16,
            0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92, 0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84, 0x90,
            0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06, 0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf,
            0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b, 0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73, 0x96, 0xac, 0x74,
            0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e, 0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e,
            0xaa, 0x18, 0xbe, 0x1b, 0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4, 0x1f, 0xdd, 0xa8, 0x33, 0x88,
            0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f, 0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9,
            0x9c, 0xef, 0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61, 0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6,
            0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d };
}