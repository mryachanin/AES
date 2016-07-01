# The Advanced Encryption Standard (AES)

An implementation of AES ECB that is extensible to other block cipher modes of operation.

## Usage
An instance of an AES cipher should be obtained through the static factory method AesFactory.getCipher(). That instance will be implemented with the specified block cipher mode. Using a factory allows new block cipher modes to be easilly added. Minimal code needs to be refactored to take advantage of them.

## Tests
Integration tests have been written for AES ECB encryption and decryption, as well as AES key generation. These tests cover the three key lengths supported by AES: 128, 192, and 256 bits.

## Future Plans
Eventually, I may implement AES with more block cipher modes. I may also implement other ciphers and create an abstract factory that takes a cipher-type and returns the corresponding factory. For example, AesFactory is the factory for cipher-type AES but there could be a BlowfishFactory.
