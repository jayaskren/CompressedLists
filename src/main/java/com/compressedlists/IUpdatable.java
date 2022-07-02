package com.compressedlists;

public interface IUpdatable {

	public void updateProgress(long currentValue, String currentMessage);
	public void setMax(double max);
	public void finish();
	
}
