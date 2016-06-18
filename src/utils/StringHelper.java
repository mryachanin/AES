package utils;

public class StringHelper {

    /**
     * Do not allow instantiation.
     */
    private StringHelper() {
    }

    /**
     * Converts a byte array to a string representation of the hex values.
     *
     * @param bytes Bytes to convert to hex representation.
     * @return Hex representation of the provided bytes.
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }

    /**
     * Converts a String to a byte array representation keeping the hex values integrity
     *
     * @param s Hex string to convert.
     * @return Byte array representation of the hex string.
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
