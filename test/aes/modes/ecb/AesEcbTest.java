package aes.modes.ecb;
import org.junit.Assert;
import org.junit.Test;

import aes.AesFactory;
import aes.modes.AesMode;

/**
 * Integration tests for AES ECB encryption and decryption using keys of 128, 192, and 256 bits.
 */
public class AesEcbTest {

    /**
     * Test AES ECB encryption with a 128 bit key.
     */
    @Test
    public void test128BitEncrypt() {
        byte[] key = new byte[] { (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09,
                (byte) 0x0a, (byte) 0x0b, (byte) 0x0c, (byte) 0x0d, (byte) 0x0e, (byte) 0x0f };

        byte[] plaintext = new byte[] { (byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33, (byte) 0x44, (byte) 0x55, (byte) 0x66, (byte) 0x77, (byte) 0x88,
                (byte) 0x99, (byte) 0xaa, (byte) 0xbb, (byte) 0xcc, (byte) 0xdd, (byte) 0xee, (byte) 0xff };

        byte[] expectedCiphertext = new byte[] { (byte) 0x69, (byte) 0xc4, (byte) 0xe0, (byte) 0xd8, (byte) 0x6a, (byte) 0x7b, (byte) 0x04, (byte) 0x30,
                (byte) 0xd8, (byte) 0xcd, (byte) 0xb7, (byte) 0x80, (byte) 0x70, (byte) 0xb4, (byte) 0xc5, (byte) 0x5a };

        byte[] ciphertext = AesFactory.getCipher(AesMode.ECB).encrypt(plaintext, key);
        Assert.assertArrayEquals(expectedCiphertext, ciphertext);
    }

    /**
     * Test AES ECB encryption with a 192 bit key.
     */
    @Test
    public void test192BitEncrypt() {
        byte[] key = new byte[] { (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09,
                (byte) 0x0a, (byte) 0x0b, (byte) 0x0c, (byte) 0x0d, (byte) 0x0e, (byte) 0x0f, (byte) 0x10, (byte) 0x11, (byte) 0x12, (byte) 0x13, (byte) 0x14,
                (byte) 0x15, (byte) 0x16, (byte) 0x17 };

        byte[] plaintext = new byte[] { (byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33, (byte) 0x44, (byte) 0x55, (byte) 0x66, (byte) 0x77, (byte) 0x88,
                (byte) 0x99, (byte) 0xaa, (byte) 0xbb, (byte) 0xcc, (byte) 0xdd, (byte) 0xee, (byte) 0xff };

        byte[] expectedCiphertext = new byte[] { (byte) 0xdd, (byte) 0xa9, (byte) 0x7c, (byte) 0xa4, (byte) 0x86, (byte) 0x4c, (byte) 0xdf, (byte) 0xe0,
                (byte) 0x6e, (byte) 0xaf, (byte) 0x70, (byte) 0xa0, (byte) 0xec, (byte) 0x0d, (byte) 0x71, (byte) 0x91 };

        byte[] ciphertext = AesFactory.getCipher(AesMode.ECB).encrypt(plaintext, key);
        Assert.assertArrayEquals(expectedCiphertext, ciphertext);
    }

    /**
     * Test AES ECB encryption with a 256 bit key.
     */
    @Test
    public void test256BitEncrypt() {
        byte[] key = new byte[] { (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09,
                (byte) 0x0a, (byte) 0x0b, (byte) 0x0c, (byte) 0x0d, (byte) 0x0e, (byte) 0x0f, (byte) 0x10, (byte) 0x11, (byte) 0x12, (byte) 0x13, (byte) 0x14,
                (byte) 0x15, (byte) 0x16, (byte) 0x17, (byte) 0x18, (byte) 0x19, (byte) 0x1a, (byte) 0x1b, (byte) 0x1c, (byte) 0x1d, (byte) 0x1e, (byte) 0x1f };

        byte[] plaintext = new byte[] { (byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33, (byte) 0x44, (byte) 0x55, (byte) 0x66, (byte) 0x77, (byte) 0x88,
                (byte) 0x99, (byte) 0xaa, (byte) 0xbb, (byte) 0xcc, (byte) 0xdd, (byte) 0xee, (byte) 0xff };

        byte[] expectedCiphertext = new byte[] { (byte) 0x8e, (byte) 0xa2, (byte) 0xb7, (byte) 0xca, (byte) 0x51, (byte) 0x67, (byte) 0x45, (byte) 0xbf,
                (byte) 0xea, (byte) 0xfc, (byte) 0x49, (byte) 0x90, (byte) 0x4b, (byte) 0x49, (byte) 0x60, (byte) 0x89 };

        byte[] ciphertext = AesFactory.getCipher(AesMode.ECB).encrypt(plaintext, key);
        Assert.assertArrayEquals(expectedCiphertext, ciphertext);
    }

    /**
     * Test AES ECB decryption with a 128 bit key.
     */
    @Test
    public void test128BitDecrypt() {
        byte[] key = new byte[] { (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09,
                (byte) 0x0a, (byte) 0x0b, (byte) 0x0c, (byte) 0x0d, (byte) 0x0e, (byte) 0x0f };

        byte[] ciphertext = new byte[] { (byte) 0x69, (byte) 0xc4, (byte) 0xe0, (byte) 0xd8, (byte) 0x6a, (byte) 0x7b, (byte) 0x04, (byte) 0x30, (byte) 0xd8,
                (byte) 0xcd, (byte) 0xb7, (byte) 0x80, (byte) 0x70, (byte) 0xb4, (byte) 0xc5, (byte) 0x5a };

        byte[] expectedPlaintext = new byte[] { (byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33, (byte) 0x44, (byte) 0x55, (byte) 0x66, (byte) 0x77, (byte) 0x88,
                (byte) 0x99, (byte) 0xaa, (byte) 0xbb, (byte) 0xcc, (byte) 0xdd, (byte) 0xee, (byte) 0xff };

        byte[] plaintext = AesFactory.getCipher(AesMode.ECB).decrypt(ciphertext, key);
        Assert.assertArrayEquals(expectedPlaintext, plaintext);
    }

    /**
     * Test AES ECB decryption with a 192 bit key.
     */
    @Test
    public void test192BitDecrypt() {
        byte[] key = new byte[] { (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09,
                (byte) 0x0a, (byte) 0x0b, (byte) 0x0c, (byte) 0x0d, (byte) 0x0e, (byte) 0x0f, (byte) 0x10, (byte) 0x11, (byte) 0x12, (byte) 0x13, (byte) 0x14,
                (byte) 0x15, (byte) 0x16, (byte) 0x17 };

        byte[] ciphertext = new byte[] { (byte) 0xdd, (byte) 0xa9, (byte) 0x7c, (byte) 0xa4, (byte) 0x86, (byte) 0x4c, (byte) 0xdf, (byte) 0xe0, (byte) 0x6e,
                (byte) 0xaf, (byte) 0x70, (byte) 0xa0, (byte) 0xec, (byte) 0x0d, (byte) 0x71, (byte) 0x91 };

        byte[] expectedPlaintext = new byte[] { (byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33, (byte) 0x44, (byte) 0x55, (byte) 0x66, (byte) 0x77, (byte) 0x88,
                (byte) 0x99, (byte) 0xaa, (byte) 0xbb, (byte) 0xcc, (byte) 0xdd, (byte) 0xee, (byte) 0xff };

        byte[] plaintext = AesFactory.getCipher(AesMode.ECB).decrypt(ciphertext, key);
        Assert.assertArrayEquals(expectedPlaintext, plaintext);
    }

    /**
     * Test AES ECB decryption with a 256 bit key.
     */
    @Test
    public void test256BitDecrypt() {
        byte[] key = new byte[] { (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09,
                (byte) 0x0a, (byte) 0x0b, (byte) 0x0c, (byte) 0x0d, (byte) 0x0e, (byte) 0x0f, (byte) 0x10, (byte) 0x11, (byte) 0x12, (byte) 0x13, (byte) 0x14,
                (byte) 0x15, (byte) 0x16, (byte) 0x17, (byte) 0x18, (byte) 0x19, (byte) 0x1a, (byte) 0x1b, (byte) 0x1c, (byte) 0x1d, (byte) 0x1e, (byte) 0x1f };

        byte[] ciphertext = new byte[] { (byte) 0x8e, (byte) 0xa2, (byte) 0xb7, (byte) 0xca, (byte) 0x51, (byte) 0x67, (byte) 0x45, (byte) 0xbf, (byte) 0xea,
                (byte) 0xfc, (byte) 0x49, (byte) 0x90, (byte) 0x4b, (byte) 0x49, (byte) 0x60, (byte) 0x89 };

        byte[] expectedPlaintext = new byte[] { (byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33, (byte) 0x44, (byte) 0x55, (byte) 0x66, (byte) 0x77, (byte) 0x88,
                (byte) 0x99, (byte) 0xaa, (byte) 0xbb, (byte) 0xcc, (byte) 0xdd, (byte) 0xee, (byte) 0xff };

        byte[] plaintext = AesFactory.getCipher(AesMode.ECB).decrypt(ciphertext, key);
        Assert.assertArrayEquals(expectedPlaintext, plaintext);
    }
}
