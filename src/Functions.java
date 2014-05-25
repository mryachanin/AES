
public abstract class Functions {
	
	/**
	 *  Finds the highest bit set to 1 in a polynomial
	 *  
	 *  @param algMatrix  polynomial to test
	 *  @param n		  number of bits to check
	 *  @return           highest bit set
	 */
	protected int findHighestBitSet(int algMatrix, int n) {
		int highestBit = 0;
		for (int i = 0; i < n; i++) {
			if (isBitSet(algMatrix, i)) {
				highestBit = i;
			}
		}
		return highestBit;
	}
	
	
	/**
	 *  Converts a byte array to a string representation of the hex values
	 *  
	 *  @param bytes  -  byte[] containing some hex values
	 *  @return       -  String representing the byte[]
	 */
	public static String bytesToHex(byte[] bytes) {
		StringBuffer result = new StringBuffer();
		for (byte b: bytes) {
		    result.append(String.format("%02X", b));
		}
		return result.toString();

	}
	
	
	/**
	 *  Checks if a certain bit of a polynomial is set (0 indexed)
	 *  
	 *  @param poly  polynomial to check
	 *  @param bit   bit to check
	 *  @return      true if bit is set; false otherwise
	 */
	protected boolean isBitSet(int poly, int bit) {
		return (poly & (0x01 << bit)) != 0;
	}
	
	
	/**
	 *  Returns the multiplicative inverse of polynomial b in the finite field 2^8
	 *  0 maps to 0
	 *  
	 *  @param poly  byte representing the polynomial to take the inverse of
	 *  @return      byte representing the inverse of the polynomial passed in
	 */
	protected byte getMultInverse(int poly) {
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
			int highestBit0 = findHighestBitSet(algMatrix[0][0],9);	
			int highestBit1 = findHighestBitSet(algMatrix[1][0],8);
			int diff = highestBit0 - highestBit1;
			
			if (diff > 0) {
				algMatrix[0][0] ^= xtimes(algMatrix[1][0], diff);
				algMatrix[0][1] ^= xtimes(algMatrix[1][1], diff);
				algMatrix[0][2] ^= xtimes(algMatrix[1][2], diff);
			}
			else if (diff < 0) {
				algMatrix[1][0] ^= xtimes(algMatrix[0][0], (diff * -1));
				algMatrix[1][1] ^= xtimes(algMatrix[0][1], (diff * -1));
				algMatrix[1][2] ^= xtimes(algMatrix[0][2], (diff * -1));
			}
			else {														// diff == 0
				if (algMatrix[0][0] >  algMatrix[1][0]) {
					algMatrix[0][0] ^= algMatrix[1][0];
					algMatrix[0][1] ^= algMatrix[1][1];
					algMatrix[0][2] ^= algMatrix[1][2];
				}
				else {
					algMatrix[1][0] ^= algMatrix[0][0];
					algMatrix[1][1] ^= algMatrix[0][1];
					algMatrix[1][2] ^= algMatrix[0][2];
				}
			}
			
			algMatrix[0][0] &= 0xff;									// fixes java's impulsive nature to sign extend
			algMatrix[1][0] &= 0xff;
		}
		
		if (algMatrix[0][0] == 1) {
			return (byte)algMatrix[0][2];
		}

		return (byte)algMatrix[1][2];
	}
	
	
	/**
	 *  Converts a String to a byte array representation keeping the hex values integrity
	 *  
	 *  @param s  -  String to convert
	 *  @return   -  byte[] representation of the String
	 */
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	
	/**
	 *  Takes in a 4-byte word and rotates each byte to the left n times
	 *  
	 *  @param   array to rotate
	 *  @param   number of times to rotate
	 *  @return  rotated array
	 */
	protected byte[] rotWord(byte[] in, int n) {
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
	 *  Takes a 4-byte input word and applies the S-box
	 *  
	 *  @param in  4 byte word
	 *  @return    input passed through S-box
	 */
	protected byte subWord(byte in) {
		byte c = 0x63;
		byte inv = getMultInverse(in);
		byte val = inv;
		
		for (int i = 0; i < 8; i++) {
			if (isBitSet(inv, (i + 4) % 8)) {
				val ^= (0x01 << i);
			}
			if (isBitSet(inv, (i + 5) % 8)) {
				val ^= (0x01 << i);
			}
			if (isBitSet(inv, (i + 6) % 8)) {
				val ^= (0x01 << i);
			}
			if (isBitSet(inv, (i + 7) % 8)) {
				val ^= (0x01 << i);
			}
			if (isBitSet(c, i)) {
				val ^= (0x01 << i);
			}
		}
		return val;
	}
	
	
	/**
	 *  Multiplies a polynomial by x in the finite field 2^8
	 *  {01}{1b} = 0
	 *  
	 *  @param poly  byte representing the polynomial to multiply by 'x'
	 *  @return      byte representing (poly)(x) in finite field 2^8
	 */
	protected byte xtime(byte poly) {
		byte b = (byte)(poly << 1);
		byte mask = 0x1b;
		if (isBitSet(poly, 7)) {
			b = (byte)(b ^ mask);
		}
		return b;
	}
	
	
	/**
	 *  Multiplies a polynomial by 'x' n times
	 *  This is used for getting the multiplicative inverse and 
	 *  does not need to reduce with respect to the mod.
	 *  
	 *  @param poly  polynomial to multiply
	 *  @param n     number of times to multiply polynomial by 'x'
	 *  @return      new polynomial
	 */
	protected int xtimes(int poly, int n) {
		return (poly << n);
	}
}
