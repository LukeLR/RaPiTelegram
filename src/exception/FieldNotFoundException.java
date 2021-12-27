/**
 * This file is part of LukeUtils.
 *
 * LukeUtils is free software: you can redistribute it and/or modify
 * it under the terms of the cc-by-nc-sa (Creative Commons Attribution-
 * NonCommercial-ShareAlike) as released by the Creative Commons
 * organisation, version 3.0.
 *
 * LukeUtils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY.
 *
 * You should have received a copy of the cc-by-nc-sa-license along
 * with this LukeUtils. If not, see
 * <https://creativecommons.org/licenses/by-nc-sa/3.0/legalcode>.
 *
 * Copyright Lukas Rose 2013 - 2015
 */

<<<<<<< HEAD:src/misc/FieldNotFoundException.java
package misc;
=======
package exception;
>>>>>>> e521a27b19badded7b5509b16c76a9311c97d635:src/exception/FieldNotFoundException.java

public class FieldNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public FieldNotFoundException(){
		
	}
	
	public FieldNotFoundException(String message){
		super(message);
	}
}