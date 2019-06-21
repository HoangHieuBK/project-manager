package com.example.demo.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartViewData {
	Map<Object, Object> map = new HashMap<Object,Object>();
	List<Map<Object, Object>> datapoints = new ArrayList<Map<Object, Object>>();
	List<List<Map<Object, Object>>> list = new ArrayList<List<Map<Object, Object>>>();
	
	public Map<Object, Object> getMap() {
		return map;
	}

	public void setMap(Map<Object, Object> map) {
		this.map = map;
	}

	public List<Map<Object, Object>> getDatapoints() {
		return datapoints;
	}

	public void setDatapoints(List<Map<Object, Object>> datapoints) {
		this.datapoints = datapoints;
	}

	public List<List<Map<Object, Object>>> getList() {
		return list;
	}

	public void setList(List<List<Map<Object, Object>>> list) {
		this.list = list;
	}

}
