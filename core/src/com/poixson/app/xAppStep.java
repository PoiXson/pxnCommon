package com.poixson.app;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface xAppStep {


	public enum StepType {STARTUP, SHUTDOWN};

	StepType Type();
	int    StepValue() default 100;
	String Title()     default "";


}
