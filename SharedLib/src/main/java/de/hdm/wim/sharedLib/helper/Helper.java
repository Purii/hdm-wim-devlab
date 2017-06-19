package de.hdm.wim.sharedLib.helper;

import com.google.common.io.BaseEncoding;
import java.util.List;
import java.util.Random;

/**
 * Created by ben on 2/06/2017.
 */
public class Helper {

	/**
	 * Instantiates a new Helper.
	 */
	public Helper() {
	}

	/**
	 * Get random string from list of String.
	 *
	 * @param list the list
	 * @return the string
	 */
	public String getRandomStringFromList(List<String> list) {
		Random random = new Random();
		int index = random.nextInt(list.size());
		return list.get(index);
	}

	/**
	 * Encode base 64 string.
	 *
	 * @param data the data
	 * @return the string
	 * @throws Exception the exception
	 */
	public String encodeBase64(String data) throws Exception {
		byte[] byteString = data.getBytes(data);
		return new String(BaseEncoding.base64().encode(byteString));
	}

	/**
	 * Decode base 64 string.
	 *
	 * @param data the data
	 * @return the string
	 */
	public String decodeBase64(String data) {
		return new String(BaseEncoding.base64().decode(data));
	}
}
