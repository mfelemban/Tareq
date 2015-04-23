import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;

import sun.misc.BASE64Encoder;


public class Run {

	public static void main(String[] args) throws Exception{
		byte[] file = readFile("src/test.txt");
		//		System.out.println("File in bytes:\n" + bytesToString(file));

		//		System.out.println("File content:\n" + new String(file));

		//		String decString = testRSA1(file,1024);
		//		System.out.println(decString);

		// bitLength in RSA beween 512 and 2048
		
		
		
		
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		// You can use any type of signature ... check documentation
		Signature sig = Signature.getInstance("SHA1WithRSA");
		long start = System.currentTimeMillis();
		kpg.initialize(2048);
		KeyPair keyPair = kpg.genKeyPair();
		long elapsedTimeMillis = System.currentTimeMillis() - start;
		float elapsedTimeSec = elapsedTimeMillis;
		System.out.println("Key generation time for signature: " + elapsedTimeSec + " milliseconds");
		byte[] signatureBytes = sign(file,keyPair,sig);


		int bitLength = 2048;
//		byte[] decFile = testRSA3(file,bitLength);
		byte[] decFile = testElgamal(file,bitLength);

		verify(decFile,keyPair,sig,signatureBytes);











	}
	
	public static byte[] sign(byte[] file,KeyPair keyPair,Signature sig) throws Exception{
	
		long start = System.currentTimeMillis();
		sig.initSign(keyPair.getPrivate());
		sig.update(file);
		byte[] signatureBytes = sig.sign();
		long elapsedTimeMillis = System.currentTimeMillis() - start;
		float elapsedTimeSec = elapsedTimeMillis;
		System.out.println("Siging time for signature: " + elapsedTimeSec + " milliseconds");
		System.out.println("Singature:" + new BASE64Encoder().encode(signatureBytes));
		
		return signatureBytes;
	}

	
	public static void verify(byte[] file,KeyPair keyPair, Signature sig,byte[] signatureBytes) throws Exception{
		sig.initVerify(keyPair.getPublic());
		sig.update(file);
		System.out.println(sig.verify(signatureBytes));
	}

	public static byte[] testElgamal(byte[] file,int size ){
		long start = System.currentTimeMillis();
		Elgamal e1 = new Elgamal(new BigInteger("134544543232323232"),size);
		long elapsedTimeMillis = System.currentTimeMillis() - start;
		float elapsedTimeSec = elapsedTimeMillis;
		System.out.println("Key generation time: " + elapsedTimeSec + " milliseconds");

		start = System.currentTimeMillis();
		BigInteger[] enc = e1.encrypt(file);
		elapsedTimeMillis = System.currentTimeMillis() - start;
		elapsedTimeSec = elapsedTimeMillis;
		System.out.println("Encryption time: " + elapsedTimeSec + " milliseconds");
		start = System.currentTimeMillis();
		byte[] de = e1.decrypt(enc);
		elapsedTimeMillis = System.currentTimeMillis() - start;
		elapsedTimeSec = elapsedTimeMillis;
		System.out.println("Decryption time: " + elapsedTimeSec+ " milliseconds");
		//		System.out.println("Alice decodes in bytes: " + bytesToString(de));
		//		System.out.println("Alice decodes: " + new String(de));
		return de;

	}


	public static byte[] testRSA2(byte[] file,int bitLength){
		System.out.println("File size in bytes: " + file.length);
		System.out.println("Primes size: " + bitLength);
		long start = System.currentTimeMillis();
		RSA rsa = new RSA(bitLength);
		long elapsedTimeMillis = System.currentTimeMillis() - start;
		float elapsedTimeSec = elapsedTimeMillis;
		System.out.println("Key generation time: " + elapsedTimeSec + " milliseconds");


		start = System.currentTimeMillis();
		ArrayList<byte[]> encFile = RSAEncrypt(rsa,file);
		elapsedTimeMillis = System.currentTimeMillis() - start;
		elapsedTimeSec = elapsedTimeMillis;
		System.out.println("Encryption time: " + elapsedTimeSec + " milliseconds");








		start = System.currentTimeMillis();
		byte[] decFile = RSADecrypt(rsa,encFile);
		elapsedTimeMillis = System.currentTimeMillis() - start;
		elapsedTimeSec = elapsedTimeMillis;
		System.out.println("Decryption time: " + elapsedTimeSec+ " milliseconds");
		System.out.println(new String(decFile));

		return decFile;
	}

	public static byte[] testRSA3(byte[] file,int bitLength){
		System.out.println("File size in bytes: " + file.length);
		System.out.println("Primes size: " + bitLength);
		long start = System.currentTimeMillis();
		RSA rsa = new RSA(bitLength);
		long elapsedTimeMillis = System.currentTimeMillis() - start;
		float elapsedTimeSec = elapsedTimeMillis;
		System.out.println("Key generation time: " + elapsedTimeSec + " milliseconds");


		start = System.currentTimeMillis();
		byte[] encFile = RSAEncrypt2(rsa,file);
		elapsedTimeMillis = System.currentTimeMillis() - start;
		elapsedTimeSec = elapsedTimeMillis;
		System.out.println("Encryption time: " + elapsedTimeSec + " milliseconds");


		start = System.currentTimeMillis();
		byte[] decFile = RSADecrypt2(rsa,encFile);
		elapsedTimeMillis = System.currentTimeMillis() - start;
		elapsedTimeSec = elapsedTimeMillis;
		System.out.println("Decryption time: " + elapsedTimeSec+ " milliseconds");
		System.out.println(new String(decFile));

		return decFile;
	}

	public static ArrayList<byte[]> RSAEncrypt(RSA rsa,byte[] file){
		ArrayList<byte[]> list = new ArrayList<byte[]>();
		int fileSize = file.length;
		int index = 0;
		while(fileSize > 0){
			byte[] block;
			if(fileSize >= 254){
				block = new byte[254];
				System.arraycopy(file, index, block, 0, 254);
			}
			else{
				block = new byte[fileSize];
				System.arraycopy(file, index, block, 0, fileSize);
			} 

			// encrypt 
			byte[] encrypted = rsa.encrypt(block);                   
			list.add(encrypted);
			index += 254;
			fileSize -= 254;
		}

		return list;
	}

	public static byte[] RSAEncrypt2(RSA rsa,byte[] file){
		byte[] encrypted = rsa.encrypt(file);
		return encrypted;

	}

	public static byte[] RSADecrypt2(RSA rsa, byte[] list){

		byte[] decrypted = rsa.decrypt(list);

		return decrypted;
	}

	public static byte[] RSADecrypt(RSA rsa, ArrayList<byte[]> list){
		String decString="";
		for(byte[] block:list){
			byte[] decrypted = rsa.decrypt(block);
			decString+= new String(decrypted);
		}

		return decString.getBytes();
	}

	public static String testRSA1(byte[] file, int bitLength){
		RSA rsa = new RSA(bitLength);
		int fileSize = file.length;
		int index = 0;
		String decString="";
		while(fileSize > 0){
			byte[] block;
			if(fileSize >= 254){
				block = new byte[254];
				System.arraycopy(file, index, block, 0, 254);
			}
			else{
				block = new byte[fileSize];
				System.arraycopy(file, index, block, 0, fileSize);
			}
			//			System.out.println("Byte size: " + block.length);
			//			System.out.println("String in Bytes: " + bytesToString(block)); 

			// encrypt 
			byte[] encrypted = rsa.encrypt(block);  
			//			System.out.println("Encrypted String in Bytes: " + bytesToString(encrypted));

			// decrypt 
			byte[] decrypted = rsa.decrypt(encrypted);       
			//			System.out.println("Decrypted String in Bytes: " +  bytesToString(decrypted));

			//			System.out.println("Decrypted String: " + new String(decrypted));
			decString+= new String(decrypted);
			index += 254;
			fileSize -= 254;
		}
		return decString;
	}


	public static byte[] readFile(String fileName){
		String fileString = "";
		try{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line = br.readLine();
			while(line!=null){
				fileString += line + "\n";
				line = br.readLine();
			}
		}
		catch(Exception e){}
		return fileString.getBytes();
	}



	private static String bytesToString(byte[] encrypted) { 
		String test = ""; 
		for (byte b : encrypted) { 
			test += Byte.toString(b); 
		} 
		return test; 
	}
}
