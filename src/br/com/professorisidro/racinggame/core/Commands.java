package br.com.professorisidro.racinggame.core;

public class Commands {
	public static final int ACCELERATE = 0;
	public static final int BREAK      = 1;
	public static final int ROTATE_L   = 2;
	public static final int ROTATE_R   = 3;
	public static final int ZOOM       = 4;
	public static boolean set[] = {false, false, false, false, false};
	
	public static float globalTime=0.0f;
	
	public static void startLog() {
		globalTime = 0.0f;
	}
	
	public static boolean noMovment() {
		return (!set[ACCELERATE] && !set[BREAK]);
	}
	public static boolean noRotation() {
		return (!set[ROTATE_L] && !set[ROTATE_R]);
	}
	
	public static void log(String action) {
		System.out.println(String.format("%.3f",globalTime).replace(",", ".")+";"+action);
	}

}
