
/**
 * 
 *  Class to test AES functions and display their output
 *  in a human readable manner 
 * 
 */
public class AESPresentation {
	private State state;
	
	
	/**
	 *  Instantiates a state object with the byte array passed in
	 *  
	 *  @param b  -  byte[] holding some data
	 */
	public AESPresentation(byte[] b) {
		state = new State(b);
	}
	
	
	/**
	 *  Calls the subBytes function and prints the state before / after
	 */
	public void demoSubBytes() {
		System.out.println("#### Sub Bytes ####");
		state.prettyPrint();
		state.subBytes();
		state.prettyPrint();
	}
	
	
	/**
	 *  Calls the invSubBytes function and prints the state before / after
	 */
	public void demoInvSubBytes() {
		System.out.println("#### Invert Sub Bytes ####");
		state.prettyPrint();
		state.invSubBytes();
		state.prettyPrint();
	}
	
	
	/**
	 *  Calls the shiftRow function and prints the state before / after
	 */
	public void demoShiftRows() {
		System.out.println("#### Shift Rows ####");
		state.prettyPrint();
		state.shiftRows();
		state.prettyPrint();
	}
	
	
	/**
	 *  Calls the invShiftRow function and prints the state before / after
	 */
	public void demoInvShiftRows() {
		System.out.println("#### Invert Shift Rows ####");
		state.prettyPrint();
		state.invShiftRows();
		state.prettyPrint();
	}
	
	
	/**
	 *  Calls the mixColumns function and prints the state before / after
	 */
	public void demoMixColumns() {
		System.out.println("#### Mix Columns ####");
		state.prettyPrint();
		state.mixColumns();
		state.prettyPrint();
	}
	
	
	/**
	 *  Calls the invMixColumns function and prints the state before / after
	 */
	public void demoInvMixColumns() {
		System.out.println("#### Invert Mix Columns ####");
		state.prettyPrint();
		state.invMixColumns();
		state.prettyPrint();
	}
	
	
	/**
	 *  Calls the addRoundKey function and prints the state before / after
	 *  
	 *  @param key  -  key to use as the round key
	 */
	public void demoAddRoundKey(byte[] key) {
		System.out.println("#### Add Round Key ####");
		state.prettyPrint();
		state.addRoundKey(key);
		state.prettyPrint();
	}
	

	/**
	 *  Main method:
	 *  	Tests each of the core functions for AES and prints results.
	 *  
	 *  @param args  -  not used; This is simply for demonstrating the operations (state before / after)
	 *  				of the main functions used in AES
	 */
	public static void main(String[] args) {
		//byte[] testBytes = Functions.hexStringToByteArray("00112233445566778899aabbccddeeff");
		byte[] testBytes = Functions.hexStringToByteArray("00102030405060708090a0b0c0d0e0f0");
		//byte[] testKey = Functions.hexStringToByteArray("01010101010101010101010101010101");
		byte[] testKey = Functions.hexStringToByteArray("73576245245357634126541754748133");
		AESPresentation aes = new AESPresentation(testBytes);
		
		System.out.println("Initial Input: " + Functions.bytesToHex(testBytes));
		System.out.println("Initial Key:   " + Functions.bytesToHex(testKey));
		
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
