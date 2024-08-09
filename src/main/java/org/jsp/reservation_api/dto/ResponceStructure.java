package org.jsp.reservation_api.dto;

import lombok.Data;

@Data
public class ResponceStructure<T> {
	private String message;
	private T data;
	private int statusCode;
}
