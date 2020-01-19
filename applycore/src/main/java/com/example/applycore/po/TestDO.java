package com.example.applycore.po;



import com.example.basecore.entity.BaseDO;

import java.io.Serializable;

/**
 * @function 功能 :enterprise 实体
 * @author   创建人:sleetdream
 * @date     创建日期:2020-01-19 15:31:09
 */
public class TestDO extends BaseDO {


	private Integer id;

	private String value;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}

