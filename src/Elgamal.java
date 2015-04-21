import java.math.*;
import java.util.*;
import java.security.*;
import java.io.*;

public class Elgamal
{
	  private static String bytesToString(byte[] encrypted) { 
	        String test = ""; 
	        for (byte b : encrypted) { 
	            test += Byte.toString(b); 
	        } 
	        return test; 
	    } 
	     
	  
    public static void main(String[] args) throws IOException
    {
        BigInteger p, g, h, a;
        Random sc = new SecureRandom();
        a = new BigInteger("12345678901234567890");
        //
        // public key calculation
        //
        System.out.println("secretKey = " + a);
        p = BigInteger.probablePrime(64, sc);
        g = new BigInteger("3");
        h = g.modPow(a, p);
        System.out.println("p = " + p);
        System.out.println("g = " + g);
        System.out.println("h = " + h);
        //
        // Encryption
        //
        String s = "FELEMBAN";
        System.out.println("Plain text is:" + s);
        System.out.println("Plain text in Bytes is:" + bytesToString(s.getBytes()));
        
        BigInteger M = new BigInteger(s.getBytes());
        BigInteger b = new BigInteger(64, sc);
        BigInteger c2= M.multiply(h.modPow(b, p)).mod(p);
        BigInteger c1= g.modPow(b, p);
//        System.out.println("Plaintext = " + X);
        System.out.println("b = " + b);
        System.out.println("C2 = " + c2);
        System.out.println("g^b mod p = " + c1);
        //
        // Decryption
        //
        BigInteger crmodp = c1.modPow(a, p);
        BigInteger d = crmodp.modInverse(p);
        BigInteger ad = d.multiply(c2).mod(p);
        byte[] de = ad.toByteArray();
        System.out.println("\n\nc^r mod p = " + crmodp);
        System.out.println("d = " + d);
        System.out.println("Alice decodes in bytes: " + bytesToString(de));
        System.out.println("Alice decodes: " + new String(de));

    }
}