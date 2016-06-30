package aes.modes;

/**
 * AesMode represents a block cipher mode of operation for AES.
 */
public enum AesMode {
    /**
     * <p>
     * Electronic Codeblock (ECB) mode is the simplest method of encryption.
     * </p>
     *
     * <p>
     * A message is divided into blocks of equal size. Each block is encrypted separately using the vanilla
     * specification of AES.
     * </p>
     */
    ECB
}
