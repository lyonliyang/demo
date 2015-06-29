/*
 * Copyright 2008-2010 erong software, Inc. All rights reserved.
 * SHENZHEN ERONG SOFTWARE CO.,LTD. WWW.ERONGSOFT.COM
 */

package com.ycxc.base.exception;


//import javax.ejb.ApplicationException;

/**
 * Handy class for wrapping checked <code>Exceptions</code> with a root cause.
 * 
 * <p>
 * This class is basic Application Business Exception. <code>getMessage</code>
 * will include nested exception information; <code>printStackTrace</code> and
 * other like methods will delegate to the wrapped exception, if any.
 * 
 * <p>
 * The similarity between this class and the {@link NestedRuntimeException}
 * class is unavoidable, as Java forces these two classes to have different
 * superclasses (ah, the inflexibility of concrete inheritance!).
 * 
 * @author Joshua Xu
 * @see #getMessage
 * @see #printStackTrace
 * @see NestedRuntimeException
 */
@SuppressWarnings("serial")
//@ApplicationException(rollback = true)
public class AppBusinessException extends NestedCheckedException {
/*
 protected AppBusinessException() {
	}
*/
	private int retCode;
	private String retCodeString;
	/**
	 * Constructor for AppBusinessException.
	 * 
	 * @param msg
	 *            the detail message
	 */
	public AppBusinessException(String msg) {
		super(msg);
	}
	
	public AppBusinessException(int retCode, String msg) {
		super(msg);
		this.retCode=retCode;
	}
	
	public AppBusinessException(String retCodeString, String msg) {
		super(msg);
		this.retCodeString=retCodeString;
	}
	
	public int getCode() {
		return this.retCode;
	}
	
	public String getCodeString() {
		return this.retCodeString;
	}


	/**
	 * Constructor for AppBusinessException.
	 * 
	 * @param msg
	 *            the detail message
	 * @param cause
	 *            the root cause ()
	 */
	public AppBusinessException(String msg, Throwable cause) {
		super(msg, cause);
	}
}