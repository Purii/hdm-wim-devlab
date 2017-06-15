package de.hdm.wim.sharedLib.events;

import java.util.Hashtable;

/**
 * Created by ben on 13/06/2017.
 */
public interface IEvent {
	String data				= "";
	String id 				= "";
	String publishTime 		= "";
	Hashtable attributes 	= new Hashtable();
}
