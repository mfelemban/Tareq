/*      name: Yaoguang Zhu 
 *      student number: 3505066 
 *      login name:yz753 
 */ 
/**********************this is algorithm of elgamal signature*************************/ 
//system use FileReader() to read the private key from prikey.txt 
//system use encryption() to encryption the signature 
//system use verify() to verify the message 
/*************************************************************************************/ 
import java.io.*; 
import java.math.*; 
import java.security.SecureRandom; 
//import java.util.*; 
public class ElgamaSignature
{//define the variables 
	BigInteger p;	//PrimeNumer 
	BigInteger g;	//Generator 
	BigInteger k;	//PrivateKey 
	BigInteger y;	//PublicKey 
	BigInteger M;	//Message 
	BigInteger r;	//Random Integer 
	BigInteger a;	//Commitment 
	BigInteger S;	//Signature 
	private SecureRandom rndnum = null; 
	 
	public ElgamaSignature() throws IOException{ 
		FileReader(); 
	} 
	 
 
	public void FileReader() throws IOException 
	{//get these variables from prikey.txt 
		File file = new File("prikey.txt"); 
		BufferedReader fileReader = new BufferedReader(new FileReader(file)); 
		String str = fileReader.readLine(); 
		str = str.substring(2); 
		k = new BigInteger(str); 
		String str1 = fileReader.readLine(); 
		str1 = str1.substring(2); 
		p = new BigInteger(str1); 
		String str2 = fileReader.readLine(); 
		str2 = str2.substring(2); 
		g = new BigInteger(str2); 
		String str3 = fileReader.readLine(); 
		str3 = str3.substring(2); 
		y = new BigInteger(str3); 
		//System.out.println("" + k); 
		 
	} 
	public void encryption(String md) 
	{//elgamal signature 
		byte [] tobyte = md.getBytes(); 
		BigInteger M = new BigInteger(tobyte); 
		while(true) 
		{ 
			rndnum = new SecureRandom();  
			r = new BigInteger(15, rndnum); 
			if(p.subtract(BigInteger.ONE).gcd(r).equals(BigInteger.ONE)&&r.compareTo(p) == -1) 
			{ 
			break; 
			} 
		} 
		//System.out.println(r.toString()); 
		a = g.modPow(r, p); 
		BigInteger temp1,temp2; 
		temp1 = M.subtract(a.multiply(k)).mod(p.subtract(BigInteger.ONE)); 
		temp2 = r.modInverse(p.subtract(BigInteger.ONE)); 
		S = temp1.multiply(temp2).mod(p.subtract(BigInteger.ONE)); 
	} 
	 
	public static boolean verify(String md, BigInteger a, BigInteger S, BigInteger p, BigInteger g, BigInteger y) 
	{//elgamal verify 
		byte [] tobyte = md.getBytes(); 
		BigInteger M = new BigInteger(tobyte); 
		BigInteger temp3,temp4; 
		temp3 = y.modPow(a, p).multiply(a.modPow(S, p)).mod(p); 
		temp4 = g.modPow(M, p); 
		//System.out.println(temp3.toString()); 
		//System.out.println(temp4.toString()); 
		 
		if(temp3.compareTo(temp4)==0) 
		{ 
			return true; 
			//System.out.println("verified"); 
		} 
		else 
		{ 
			return false; 
			//System.out.println("error"); 
		} 
	} 
	 
	/*public static void main(String[] args) throws IOException 
	{ 
		ElGamal get = new ElGamal(); 
		get.FileReader(); 
		get.M = new BigInteger("456"); 
		get.encryption(); 
		get.verify(); 
		 
		 
		 
		//System.out.println("r"); 
		System.out.println("p"); 
		System.out.println(get.p.toString()); 
		System.out.println("g"); 
		System.out.println(get.g.toString()); 
		System.out.println("k"); 
		System.out.println(get.k.toString()); 
		System.out.println("r"); 
		System.out.println(get.r.toString()); 
		System.out.println("S"); 
		System.out.println(get.S.toString()); 
		System.out.println("a"); 
		System.out.println(get.a.toString()); 
		System.out.println("y"); 
		System.out.println(get.y.toString()); 
	}*/ 
	 
} 
 