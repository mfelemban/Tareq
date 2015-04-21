import java.math.BigInteger; 
import java.util.Random;
import java.io.*;

public class RSA { 

	private BigInteger p; 
	private BigInteger q; 
	private BigInteger N; 
	private BigInteger phi; 
	private BigInteger e; 
	private BigInteger d; 
	// max block is 254
	// between 512 and 2048 bits
	private int bitLength; 

	private Random r; 
	public RSA(int bitLength) { 
		this.bitLength = bitLength;
		r = new Random(); 
		p = BigInteger.probablePrime(bitLength, r); 
		q = BigInteger.probablePrime(bitLength, r); 
		N = p.multiply(q); 

		phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)); 
		e = BigInteger.probablePrime(bitLength/2, r); 

		while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0 ) { 
			e.add(BigInteger.ONE); 
		} 
		d = e.modInverse(phi);  
	} 

	public RSA(BigInteger e, BigInteger d, BigInteger N) { 
		this.e = e; 
		this.d = d; 
		this.N = N; 
	} 

	public static void main (String[] args) throws IOException
	{ 
		RSA rsa = new RSA(1024); 
		DataInputStream in=new DataInputStream(System.in);  
		String teststring ;
		String fullText;
		String encText = "";
		String decText = "";

		System.out.println("Enter the plain text:");
		//        teststring=in.readLine();
		fullText = "FELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFELFEL";
		System.out.println("Encrypting String: " + fullText); 
		while(fullText.length()>254){
			teststring = fullText.substring(0, 245);
			fullText = fullText.substring(255);
			System.out.println("Byste size: " + teststring.getBytes().length);
			System.out.println("String in Bytes: " + bytesToString(teststring.getBytes())); 

			// encrypt 
			byte[] encrypted = rsa.encrypt(teststring.getBytes());                   
			System.out.println("Encrypted String in Bytes: " + bytesToString(encrypted));

			// decrypt 
			byte[] decrypted = rsa.decrypt(encrypted);       
			System.out.println("Decrypted String in Bytes: " +  bytesToString(decrypted));

			System.out.println("Decrypted String: " + new String(decrypted));
		}
		byte[] encrypted = rsa.encrypt(fullText.getBytes());                   
		System.out.println("Encrypted String in Bytes: " + bytesToString(encrypted));

		// decrypt 
		byte[] decrypted = rsa.decrypt(encrypted);       
		System.out.println("Decrypted String in Bytes: " +  bytesToString(decrypted));

		System.out.println("Decrypted String: " + new String(decText));


	} 

	private static String bytesToString(byte[] encrypted) { 
		String test = ""; 
		for (byte b : encrypted) { 
			test += Byte.toString(b); 
		} 
		return test; 
	} 

	//Encrypt message
	public byte[] encrypt(byte[] message) {      
		return (new BigInteger(message)).modPow(e, N).toByteArray(); 
	} 

	// Decrypt message
	public byte[] decrypt(byte[] message) { 
		return (new BigInteger(message)).modPow(d, N).toByteArray(); 
	}  
}
