package de.hdm.wim.sharedLib.helper;

import java.util.List;
import java.util.Random;

/**
 * Created by ben on 2/06/2017.
 */
public class Helper {

	public Helper(){}

	private final Random random	= new Random();

	public String getRandomStringFromList(List<String> list){
		int index = random.nextInt(list.size());

		return list.get(index);
	}
}
