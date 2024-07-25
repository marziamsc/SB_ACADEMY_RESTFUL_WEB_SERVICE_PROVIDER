package com.sistemi.informativi.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;

@Entity
public class Academy implements Serializable {

	@Id
	@Column(length = 4)
	@Size(min = 4, max = 4)
	private String code;

	@Column(length = 50, nullable = false)
	private String title;

	@Column(name = "city_location", length = 50, nullable = false)
	private String cityLocation;

	@Column(name = "student_number", length = 50, nullable = false)
	private int studentNumber;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCityLocation() {
		return cityLocation;
	}

	public void setCityLocation(String cityLocation) {
		this.cityLocation = cityLocation;
	}

	public int getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(int studentNumber) {
		this.studentNumber = studentNumber;
	}

	protected Academy() {

	}

	public Academy(String code, String title, String cityLocation, int studentNumber) {

		this.code = code;
		this.title = title;
		this.cityLocation = cityLocation;
		this.studentNumber = studentNumber;
	}
}
