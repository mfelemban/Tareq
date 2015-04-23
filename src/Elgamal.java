import java.math.*;
import java.util.*;
import java.security.*;
import java.io.*;

public class Elgamal
{
	BigInteger p, g, h, a;
	Random sc;
	int size;

	public Elgamal(BigInteger a,int size){
		this.size = size;
		this.a = a;
		this.sc = new SecureRandom();
		this.p = BigInteger.probablePrime(size, sc);
		this.g = new BigInteger("15"); 
		this.h = g.modPow(a, p);
//		System.out.println("p = " + p);
//		System.out.println("g = " + g);
//		System.out.println("h = " + h);
	}

	
	public void setA(BigInteger a){
		this.a = a;
	}
	
	public BigInteger[] encrypt(byte[] file){
		BigInteger[] enc = new BigInteger[2];
		BigInteger M = new BigInteger(file);
		BigInteger b = new BigInteger(size, sc);
		enc[0]= g.modPow(b, p);
		enc[1]= M.multiply(h.modPow(b, p)).mod(p);
		return enc;
	}
	
	public byte[] decrypt(BigInteger[] enc){
		BigInteger crmodp = enc[0].modPow(a, p);
		BigInteger d = crmodp.modInverse(p);
		BigInteger ad = d.multiply(enc[1]).mod(p);
		byte[] de = ad.toByteArray();
		return de;
	}

	public static void main(String[] args) throws IOException
	{
		

	}
	private static String bytesToString(byte[] encrypted) { 
		String test = ""; 
		for (byte b : encrypted) { 
			test += Byte.toString(b); 
		} 
		return test; 
	} 
}