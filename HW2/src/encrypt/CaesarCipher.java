package encrypt;

import java.util.Random;

public class CaesarCipher implements Encryptor {

	public CaesarCipher() {
		shift = getShift();
	}

	@Override
	public String encrypt(String s) {
		return encryptDecrypt(s, true);
	}

	@Override
	public String decrypt(String s) {
		return encryptDecrypt(s, false);
	}
	
	private static int getShift() {
		Random r = new Random();
		int low = 1;
		int high = OFFSET_MAX - OFFSET_MIN;
		return r.nextInt(high - low) + low;
	}

	private String encryptDecrypt(String s, boolean encrypt) throws IllegalArgumentException {
		StringBuilder sb = new StringBuilder();
		for (char c : s.toCharArray()) {
			int indx = c, cpos;
			if (!isPositionInRange(indx))
				throw new IllegalArgumentException("String to be encrypted has unrecognized character " + c);

			if (encrypt) {
				cpos = indx + shift;
				if (cpos > OFFSET_MAX)
					cpos = OFFSET_MIN + (cpos - OFFSET_MAX);
			} else {
				cpos = indx - shift;
				if (cpos < OFFSET_MIN)
					cpos = OFFSET_MAX - (OFFSET_MIN - cpos);	
			}
			sb.append((char)cpos);
		}
		return sb.toString();		
	}	
	
	private boolean isPositionInRange(int indx) {
		return indx >= OFFSET_MIN && indx <= OFFSET_MAX;
	}

	public String randomStringGen() {
		Random random = new Random();
		int number = random.nextInt(7);
		// chose a Character random from this String
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				+ "0123456789"
				+ "abcdefghijklmnopqrstuvxyz";

		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(11);

		for (int i = 0; i < 10; i++) {

			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index
					= (int)(AlphaNumericString.length()
					* Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaNumericString
					.charAt(index));
		}

		if(number == 1){
			sb.append("!");
		}
		else if(number == 2){
			sb.append("@");
		}
		else if(number == 3){
			sb.append("#");
		}
		else if(number == 3){
			sb.append("$");
		}
		else if(number == 4){
			sb.append("%");
		}
		else if(number == 5){
			sb.append("^");
		}
		else if(number == 6){
			sb.append("&");
		}
		return sb.toString();
	}

	private int shift;
    private static final int OFFSET_MIN = 32;
    private static final int OFFSET_MAX = 126;
}
