package hu.bme.mit.train.interfaces;

import java.time.LocalTime;

import com.google.common.collect.Table;

public interface TrainSensor {

	int getSpeedLimit();

	void overrideSpeedLimit(int speedLimit);
	
	void addValuesToTachoGraph();
	
	Table<LocalTime, Integer, Integer> getTachoGraph();
}
