package misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONObject;

import listener.Notifier;

public class InputReader extends Thread {
	private BufferedReader in;
	private String s;
	
	public InputReader(){
		in = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public void run(){
		while (true){
			try {
				if ((s = in.readLine()) != null && s.length() != 0){
					JSONObject obj = new JSONObject();
//					obj.put("event", "console input");
					obj.put("event", "message");
					obj.put("service", "false");
					obj.put("text", s);
					JSONObject from = new JSONObject();
					from.put("username", "console");
					from.put("id", -631648677);
					from.put("first_name", "console");
					from.put("print_name", "console");
					from.put("type", "user");
					obj.put("from", from);
					NotifierManager.currentNotifier().onNotify(obj, true);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
