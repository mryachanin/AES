import org.junit.Assert;
import org.junit.Test;

public class AESTest {

    @Test
    public void testEncrypt() {
        byte[] key = new byte[] { (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09,
                (byte) 0x0a, (byte) 0x0b, (byte) 0x0c, (byte) 0x0d, (byte) 0x0e, (byte) 0x0f };

        byte[] plaintext = new byte[] { (byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33, (byte) 0x44, (byte) 0x55, (byte) 0x66, (byte) 0x77, (byte) 0x88,
                (byte) 0x99, (byte) 0xaa, (byte) 0xbb, (byte) 0xcc, (byte) 0xdd, (byte) 0xee, (byte) 0xff };

        byte[] expectedCiphertext = new byte[] { (byte) 0x69, (byte) 0xc4, (byte) 0xe0, (byte) 0xd8, (byte) 0x6a, (byte) 0x7b, (byte) 0x04, (byte) 0x30,
                (byte) 0xd8, (byte) 0xcd, (byte) 0xb7, (byte) 0x80, (byte) 0x70, (byte) 0xb4, (byte) 0xc5, (byte) 0x5a };

        byte[] ciphertext = AES.encrypt(plaintext, key);
        Assert.assertArrayEquals(expectedCiphertext, ciphertext);
    }

    @Test
    public void testDecrypt() {
        byte[] key = new byte[] { (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09,
                (byte) 0x0a, (byte) 0x0b, (byte) 0x0c, (byte) 0x0d, (byte) 0x0e, (byte) 0x0f };

        byte[] ciphertext = new byte[] { (byte) 0x69, (byte) 0xc4, (byte) 0xe0, (byte) 0xd8, (byte) 0x6a, (byte) 0x7b, (byte) 0x04, (byte) 0x30, (byte) 0xd8,
                (byte) 0xcd, (byte) 0xb7, (byte) 0x80, (byte) 0x70, (byte) 0xb4, (byte) 0xc5, (byte) 0x5a };

        byte[] expectedPlaintext = new byte[] { (byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33, (byte) 0x44, (byte) 0x55, (byte) 0x66, (byte) 0x77, (byte) 0x88,
                (byte) 0x99, (byte) 0xaa, (byte) 0xbb, (byte) 0xcc, (byte) 0xdd, (byte) 0xee, (byte) 0xff };

        byte[] plaintext = AES.decrypt(ciphertext, key);
        Assert.assertArrayEquals(expectedPlaintext, plaintext);
    }
}
