/**
 * @ Dattatray Bodhale
 * Jan 27, 2020
 */
package com.ZioSet.dto;

public class Status {

	
	private int code;
	private String message;
	private String resmessage;
	private int id;
	private Object object;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	public String getResmessage() {
		return resmessage;
	}
	public void setResmessage(String resmessage) {
		this.resmessage = resmessage;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	
	
}
