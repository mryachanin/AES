import utils.StringHelper;

/**
 *
 * Class to test AES functions and display their output in a human readable manner
 *
 */
public class AESPresentation {
    private State state;

    /**
     * Instantiates a state object with the byte array passed in
     *
     * @param b - byte[] holding some data
     */
    public AESPresentation(byte[] b) {
        state = new State(b);
    }

    /**
     * Calls the subBytes function and prints the state before / after
     */
    public void demoSubBytes() {
        System.out.println("#### Sub Bytes ####");
        System.out.println(state);
        state.subBytes();
        System.out.println(state);
    }

    /**
     * Calls the invSubBytes function and prints the state before / after
     */
    public void demoInvSubBytes() {
        System.out.println("#### Invert Sub Bytes ####");
        System.out.println(state);
        state.invSubBytes();
        System.out.println(state);
    }

    /**
     * Calls the shiftRow function and prints the state before / after
     */
    public void demoShiftRows() {
        System.out.println("#### Shift Rows ####");
        System.out.println(state);
        state.shiftRows();
        System.out.println(state);
    }

    /**
     * Calls the invShiftRow function and prints the state before / after
     */
    public void demoInvShiftRows() {
        System.out.println("#### Invert Shift Rows ####");
        System.out.println(state);
        state.invShiftRows();
        System.out.println(state);
    }

    /**
     * Calls the mixColumns function and prints the state before / after
     */
    public void demoMixColumns() {
        System.out.println("#### Mix Columns ####");
        System.out.println(state);
        state.mixColumns();
        System.out.println(state);
    }

    /**
     * Calls the invMixColumns function and prints the state before / after
     */
    public void demoInvMixColumns() {
        System.out.println("#### Invert Mix Columns ####");
        System.out.println(state);
        state.invMixColumns();
        System.out.println(state);
    }

    /**
     * Calls the addRoundKey function and prints the state before / after
     *
     * @param key - key to use as the round key
     */
    public void demoAddRoundKey(byte[] key) {
        System.out.println("#### Add Round Key ####");
        System.out.println(state);
        state.addRoundKey(key);
        System.out.println(state);
    }

    /**
     * Main method: Tests each of the core functions for AES and prints results.
     *
     * @param args - not used; This is simply for demonstrating the operations (state before / after) of the main functions used in AES
     */
    public static void main(String[] args) {
        // byte[] testBytes = StringHelper.hexStringToByteArray("00112233445566778899aabbccddeeff");
        byte[] testBytes = StringHelper.hexStringToByteArray("00102030405060708090a0b0c0d0e0f0");
        // byte[] testKey = StringHelper.hexStringToByteArray("01010101010101010101010101010101");
        byte[] testKey = StringHelper.hexStringToByteArray("73576245245357634126541754748133");
        AESPresentation aes = new AESPresentation(testBytes);

        System.out.println("Initial Input: " + StringHelper.bytesToHex(testBytes));
        System.out.println("Initial Key:   " + StringHelper.bytesToHex(testKey));

        System.out.println("\n\n");
        aes.demoSubBytes();

        System.out.println("\n\n");
        aes.demoShiftRows();

        System.out.println("\n\n");
        aes.demoMixColumns();

        System.out.println("\n\n");
        aes.demoAddRoundKey(testKey);

        // reverse

        System.out.println("\n\n");
        aes.demoAddRoundKey(testKey);

        System.out.println("\n\n");
        aes.demoInvMixColumns();

        System.out.println("\n\n");
        aes.demoInvShiftRows();

        System.out.println("\n\n");
        aes.demoInvSubBytes();
    }
}
