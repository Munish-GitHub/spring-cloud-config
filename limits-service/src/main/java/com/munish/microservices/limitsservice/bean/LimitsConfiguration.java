package com.munish.microservices.limitsservice.bean;

public class LimitsConfiguration {
	private int minimum;
	private int maximum;
	
	protected LimitsConfiguration(){
		super();
	}
	public LimitsConfiguration(int minimum, int maximum) {
		this();
		this.minimum = minimum;
		this.maximum = maximum;
	}
	

	public int getMinimum() {
		return minimum;
	}

	public int getMaximum() {
		return maximum;
	}
}
