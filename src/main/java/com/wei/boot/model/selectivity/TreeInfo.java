package com.wei.boot.model.selectivity;

import java.util.ArrayList;
import java.util.List;

public class TreeInfo {

	private Integer id;
	
	private String text;
	
	private Integer level;

    private Integer parentId;
	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	private List<TreeInfo> children = new ArrayList<TreeInfo>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<TreeInfo> getChildren() {
		return children;
	}

	public void setChildren(List<TreeInfo> children) {
		this.children = children;
	}
}
