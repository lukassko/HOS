package com.app.hos.utils.json;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy; 

@Retention(RetentionPolicy.RUNTIME)
public @interface JsonSerializer {
	Class serializer();
}
