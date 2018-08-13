package com.avenuecode.vo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This is a view object used to represent distance service responses as they were requested (models didn't really fit for that).  
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class DistanceVO {
	
	public DistanceVO(String stringPath) {
		path = new ArrayList<>();
		for(int i=0; i<stringPath.length(); i++) {
			path.add(stringPath.charAt(i));
		}
	}
	
	private Integer distance;
	
	private List<Character> path;

}
