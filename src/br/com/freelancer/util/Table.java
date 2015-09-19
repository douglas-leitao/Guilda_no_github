package br.com.freelancer.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Table {

	public String tableName();
	public String alias() default "";
}