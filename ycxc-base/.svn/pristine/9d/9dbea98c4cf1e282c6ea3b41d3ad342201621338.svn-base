/*
 * Copyright erong software, Inc. All rights reserved.
 * SHENZHEN ERONG SOFTWARE CO.,LTD. WWW.ERONGSOFT.COM
 */

package com.ycxc.base.util;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author joshuaxu
 * 
 */
public class BeanUtil {
	private static WebApplicationContext wac;

	public static Object lookup(final String name)
	{
		if (wac == null) {
			wac = ContextLoader.getCurrentWebApplicationContext();
		}

		return wac.getBean(name);
	}

	public static <T> T lookup(final String name, final Class<T> clazz) {
		if (wac == null) {
			wac = ContextLoader.getCurrentWebApplicationContext();
		}
		return wac.getBean(name, clazz);
	}

}
