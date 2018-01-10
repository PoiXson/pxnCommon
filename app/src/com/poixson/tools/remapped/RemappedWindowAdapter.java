package com.poixson.tools.remapped;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.utils.Utils;


public class RemappedWindowAdapter extends WindowAdapter {
	private static final String LOG_NAME = "GUI";

	protected final Object obj;
	protected final Method method;



	public static RemappedWindowAdapter getNew(final Object listenerClass, final String methodName) {
		try {
			return new RemappedWindowAdapter(listenerClass, methodName);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}
	public RemappedWindowAdapter(final Object listenerClass, final String methodStr)
			throws NoSuchMethodException {
		if (listenerClass == null)    throw RequiredArgumentException.getNew("listenerClass");
		if (Utils.isEmpty(methodStr)) throw RequiredArgumentException.getNew("methodName");
		this.obj = listenerClass;
		final Class<?> clss = listenerClass.getClass();
		this.method = clss.getMethod(methodStr);
		if (this.method == null) {
			log().severe(
				"Method: {}() in class: {}",
				methodStr,
				listenerClass.getClass().getName()
			);
			throw new NoSuchMethodException();
		}
		log().finest(
			"New WindowAdapter created for: {}::{}()",
			clss.getName(),
			methodStr
		);
	}



	@Override
	public void windowClosing(final WindowEvent event) {
		try {
			this.method.invoke(this.obj);
		} catch (IllegalAccessException e) {
			log().trace(e);
		} catch (IllegalArgumentException e) {
			log().trace(e);
		} catch (InvocationTargetException e) {
			log().trace(e);
		} catch (Exception e) {
			log().trace(e);
		}
	}



	// logger
	public static xLog log() {
		return xLog.getRoot()
				.get(LOG_NAME);
	}



}