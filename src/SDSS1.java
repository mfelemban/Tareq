
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class SDSS1 {

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException{

		BigInteger p = new BigInteger("467");
		BigInteger q = new BigInteger("233");
		BigInteger g = new BigInteger("21");
		BigInteger x = new BigInteger("213");
		BigInteger xa = new BigInteger("127");
		BigInteger ya = new BigInteger("48");
		BigInteger xb = new BigInteger("123");
		BigInteger yb = new BigInteger("141");
		BigInteger k = new BigInteger("25");

		BigInteger gxmodp = pow(g,x).mod(p);

		BigInteger m = new BigInteger("77");

		SecretKeySpec key = new SecretKeySpec(gxmodp.toByteArray(), "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(key);
		byte[] hash= mac.doFinal(m.toByteArray());
		System.out.println(hash.length);
		BigInteger r = new BigInteger(1,hash);
		System.out.println(r);
		BigInteger s = x.divide(r.add(xa)).mod(q);
		
		k = pow(pow(g,r).multiply(ya),s).mod(p);
		
		key = new SecretKeySpec(k.toByteArray(), "HmacSHA1");
		mac = Mac.getInstance("HmacSHA1");
		mac.init(key);
		hash= mac.doFinal(m.toByteArray());
		
		BigInteger rr = new BigInteger(1,hash);
		System.out.println(rr);
	}
	
	private static String bytesToString(byte[] encrypted) { 
		String test = ""; 
		for (byte b : encrypted) { 
			test += Byte.toString(b); 
		} 
		return test; 
	} 

	public static BigInteger sqrt(BigInteger x) {
		BigInteger div = BigInteger.ZERO.setBit(x.bitLength()/2);
		BigInteger div2 = div;
		// Loop until we hit the same value twice in a row, or wind
		// up alternating.
		for(;;) {
			BigInteger y = div.add(x.divide(div)).shiftRight(1);
			if (y.equals(div) || y.equals(div2))
				return y;
			div2 = div;
			div = y;
		}
	}

	public static BigInteger pow(BigInteger base, BigInteger exponent) {
		BigInteger result = BigInteger.ONE;
		while (exponent.signum() > 0) {
			if (exponent.testBit(0)) result = result.multiply(base);
			base = base.multiply(base);
			exponent = exponent.shiftRight(1);
		}
		return result;
	}


	public BigInteger largestPrimeFactor(BigInteger p){
		BigInteger q = new BigInteger("0");
		BigInteger i;
		BigInteger unit = BigInteger.ONE;
		BigInteger zero = BigInteger.ZERO;
		for(i = new BigInteger(p.divide(new BigInteger("2")).toByteArray()); i.compareTo(p) < 0 ; i=i.add(unit)){
			BigInteger l;
			for(l = BigInteger.ONE; l.compareTo(sqrt(i))<=0; l=l.add(unit)){
				if(l.mod(i).compareTo(zero)==0){
					break;
				} else{
					while(p.mod(i).compareTo(zero)==0){
						p = p.divide(i);
						q =i;
					}
				}
			}
		}
		return q;
	}
}
