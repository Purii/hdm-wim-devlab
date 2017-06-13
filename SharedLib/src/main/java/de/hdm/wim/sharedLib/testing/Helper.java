package de.hdm.wim.sharedLib.testing;

import java.util.List;
import java.util.Random;

/**
 * Created by ben on 2/06/2017.
 */
public class Helper {

	/**
	 * Instantiates a new Helper.
	 */
	public Helper(){}

	private final Random random	= new Random();

	/**
	 * Get random string from list of String.
	 *
	 * @param list
	 * @return the string
	 */
	public String getRandomStringFromList(List<String> list){
		int index = random.nextInt(list.size());

		return list.get(index);
	}
}
