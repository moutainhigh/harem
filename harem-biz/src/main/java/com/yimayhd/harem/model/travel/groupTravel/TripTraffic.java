package com.yimayhd.harem.model.travel.groupTravel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.yimayhd.ic.client.model.enums.RouteItemType;

/**
 * 交通方式
 * 
 * @author yebin
 *
 */
public class TripTraffic {
	private static final Map<Integer, String> WAYS = new TreeMap<Integer, String>();

	static {
		WAYS.put(RouteItemType.PLANE.getType(), RouteItemType.PLANE.getDesc());
		WAYS.put(RouteItemType.TRAIN.getType(), RouteItemType.TRAIN.getDesc());
		WAYS.put(RouteItemType.BUS.getType(), RouteItemType.BUS.getDesc());
		WAYS.put(RouteItemType.BOAT.getType(), RouteItemType.BOAT.getDesc());
	}

	private String from;
	private String to;
	private int way;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public int getWay() {
		return way;
	}

	public void setWay(int way) {
		this.way = way;
	}

	public String wayName() {
		return WAYS.get(way);
	}

	public static List<Entry<Integer, String>> ways() {
		return new ArrayList<Entry<Integer, String>>(WAYS.entrySet());
	}
}
