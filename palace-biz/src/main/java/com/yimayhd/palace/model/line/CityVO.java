package com.yimayhd.palace.model.line;

import com.yimayhd.user.client.dto.CityDTO;

/**
 * 
 * 城市VO
 * 
 * @author yebin
 *
 */
public class CityVO extends TagDTO {
	private CityDTO city;

	public CityVO() {
		super();
	}

	public CityVO(long id, String name, CityDTO city) {
		super(id, name);
		this.city = city;
	}

	public CityDTO getCity() {
		return city;
	}

	public void setCity(CityDTO city) {
		this.city = city;
	}
}
