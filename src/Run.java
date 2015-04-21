import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class Run {
	
	public static void main(String[] args){
		byte[] file = readFile("src/test.txt");
//		System.out.println("File in bytes:\n" + bytesToString(file));
		
//		System.out.println("File content:\n" + new String(file));
		
//		String decString = testRSA1(file,1024);
//		System.out.println(decString);
		
		// bitLength in RSA beween 512 and 2048 
		int bitLength = 1024;
		testRSA2(file,bitLength);
		
		
		
		
		
	}
	
	
	public static void testRSA2(byte[] file,int bitLength){
		System.out.println("File size in bytes: " + file.length);
		System.out.println("Primes size: " + bitLength);
		RSA rsa = new RSA(bitLength);
		long start = System.currentTimeMillis();
		ArrayList<byte[]> encFile = RSAEncrypt(rsa,file);
		long elapsedTimeMillis = System.currentTimeMillis() - start;
		float elapsedTimeSec = elapsedTimeMillis/1000F;
		System.out.println("Encryption time: " + elapsedTimeSec + " seconds");
		
		start = System.currentTimeMillis();
		byte[] decFile = RSADecrypt(rsa,encFile);
		elapsedTimeMillis = System.currentTimeMillis() - start;
		elapsedTimeSec = elapsedTimeMillis/1000F;
		
		System.out.println("Decryption time: " + elapsedTimeSec+ " seconds");
//		System.out.println(new String(decFile));
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
