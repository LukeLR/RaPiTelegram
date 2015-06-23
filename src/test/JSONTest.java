package test;

import logging.Logger;

import org.json.JSONObject;

public class JSONTest {
	public static void main(String[] args){
		JSONObject obj = new JSONObject(
				"{\"event\": \"message\", \"service\": false, \"flags\":"
				+ " 257, \"text\": \"hi\", \"id\": 4666, \"from\":"
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
	}
}
