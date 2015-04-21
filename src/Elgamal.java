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
        BigInteger p, b, c, secretKey;
        Random sc = new SecureRandom();
        secretKey = new BigInteger("12345678901234567890");
        //
        // public key calculation
        //
        System.out.println("secretKey = " + secretKey);
        p = BigInteger.probablePrime(64, sc);
        b = new BigInteger("3");
        c = b.modPow(secretKey, p);
        System.out.println("p = " + p);
        System.out.println("b = " + b);
        System.out.println("c = " + c);
        //
        // Encryption
        //
        String s = "FELEMBAN";
        System.out.println("Plain text is:" + s);
        System.out.println("Plain text in Bytes is:" + bytesToString(s.getBytes()));
        BigInteger X = new BigInteger(s.getBytes());
        BigInteger r = new BigInteger(64, sc);
        BigInteger EC = X.multiply(c.modPow(r, p)).mod(p);
        BigInteger brmodp = b.modPow(r, p);
//        System.out.println("Plaintext = " + X);
        System.out.println("r = " + r);
        System.out.println("EC = " + EC);
        System.out.println("b^r mod p = " + brmodp);
        //
        // Decryption
        //
        BigInteger crmodp = brmodp.modPow(secretKey, p);
        BigInteger d = crmodp.modInverse(p);
        BigInteger ad = d.multiply(EC).mod(p);
        byte[] de = ad.toByteArray();
        System.out.println("\n\nc^r mod p = " + crmodp);
        System.out.println("d = " + d);
        System.out.println("Alice decodes in bytes: " + bytesToString(de));
        System.out.println("Alice decodes: " + new String(de));

    }
}