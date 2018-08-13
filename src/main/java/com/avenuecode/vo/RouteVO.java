package com.avenuecode.vo;

import com.avenuecode.model.RouteModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This is a view object used to represent route service responses as they were requested (models didn't really fit for that).  
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper=false, of={"route", "stops"})
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class RouteVO {

	private String route;
	
	private Integer stops;
	
}
