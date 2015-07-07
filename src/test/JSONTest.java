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

package test;

import logging.Logger;

import org.json.JSONObject;

public class JSONTest {
	public static void main(String[] args){
		JSONObject obj = new JSONObject(
				"{\"event\": \"message\", \"service\": false, \"flags\":"
				+ " 257, \"text\": \"hi 123 56es\", \"id\": 4666, \"from\":"
				+ " {\"username\": \"my_username\", \"flags\": 259,"
				+ " \"id\": 12345678, \"first_name\": \"Ich\", \"phone\":"
				+ " \"4915781234567\", \"print_name\": \"Ich\", \"last_name\":"
				+ " \"\", \"type\": \"user\"}, \"to\": {\"username\":"
				+ " \"RPi_username\", \"flags\": 267, \"id\": 87654321,"
				+ " \"first_name\": \"Raspberry\", \"phone\": \"4912345678901\","
				+ " \"print_name\": \"Raspberry_Pi\", \"last_name\": \"Pi\","
				+ " \"type\": \"user\"}, \"date\": 1435076540, \"out\": false,"
				+ " \"unread\": true}");
		Logger.logMessage('I', new JSONTest(), "event is: " + obj.getString("event"));
		Logger.logMessage('I', new JSONTest(), "message text is: " + obj.getString("text"));
		
		String messageString = obj.getString("text");
		String[] contents = messageString.trim().split("\\s");
		for (int i = 0; i < contents.length; i++){
			Logger.logMessage('I', new JSONTest(), "message contents [" + String.valueOf(i) + "]: " + contents[i]);
		}
	}
}
