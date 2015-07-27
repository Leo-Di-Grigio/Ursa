package main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public final class Config {

	private static boolean debug;
	private static boolean fullscreen;
	private static int frameHight;
	private static int frameWidth;
	
	public Config() {
		try {
			File file = new File("config.cfg");
		
			if(!file.exists()){
				file.createNewFile();
				PrintWriter out = new PrintWriter(file);
				
				out.println("# display settings");
				out.println("frame-witdh: " + 1366);
				out.println("frame-hight: " + 768);
				out.println("fullscreen: false");
				out.println();
				out.println("debug: false");
				
				out.flush();
				out.close();
			}
			else{
				Scanner in = new Scanner(file);
				
				while(in.hasNextLine()){
					String line = in.nextLine();
					
					if(!line.startsWith("#")){
						String [] arr = line.split(":");
						
						if(arr.length == 2){
							arr[1] = arr[1].replaceAll("\\s+","");
						
							switch(arr[0]){
								case "frame-witdh":
									frameWidth = Integer.parseInt(arr[1]);
									break;
								
								case "frame-hight":
									frameHight = Integer.parseInt(arr[1]);
									break;
								
								case "fullscreen":
									fullscreen = Boolean.parseBoolean(arr[1]);
									break;
									
								case "debug":
									debug = Boolean.parseBoolean(arr[1]);
									break;
							}
						}
					}
				}
				
				in.close();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int width() {
		return frameWidth;
	}

	public static int height() {
		return frameHight;
	}

	public static boolean fullscreen() {
		return fullscreen;
	}

	public static boolean resizable() {
		return false;
	}
	
	public static boolean debug(){
		return debug;
	}
}
