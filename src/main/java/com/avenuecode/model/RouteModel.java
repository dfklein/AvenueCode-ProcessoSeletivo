package com.avenuecode.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper=false, of={"source", "target", "distance"})
@Entity
@Table(name="ROUTE")
@JsonInclude(Include.NON_NULL)
public class RouteModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_ROUTE")
	@JsonIgnore
	private Long id;
	
	@Column(name = "SOURCE")
	@NotNull
	private Character source;

	@Column(name = "TARGET")
	@NotNull
	private Character target;
	
	@Column(name = "DISTACE")
	@NotNull
	private Integer distance;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "ID_GRAPH")
	private GraphModel graphModel;
	
	public String toString() {
		return String.valueOf(source) + String.valueOf(target);
	}
	
}
