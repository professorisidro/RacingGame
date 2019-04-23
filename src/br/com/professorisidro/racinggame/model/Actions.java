package br.com.professorisidro.racinggame.model;

public class Actions {

	private float time;
	private int   action;
	
	public Actions(float time, int action) {
		this.time = time;
		this.action = action;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}
	
}
