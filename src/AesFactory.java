
public class AesFactory {

    public static Cipher getCipher(AesMode mode) {
        if (mode == AesMode.ECB)
            return new AesEcb();
        return null;
    }
}
