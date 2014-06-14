package com.matigakis.flightbot.tests.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.matigakis.flightbot.util.PID;

@RunWith(JUnit4.class)
public class TestPID {
	private PID pid;
	
	@Before
	public void setup(){
		pid = new PID(1.0, 0.5, 0.1, 0.01, 100.0);
	}
	
	@Test
	public void testPIDControllerUpdate(){
		double error = 32.6;
		
		double output = pid.update(error);
		
		double expected = 358.763;
		
		assertEquals(expected, output, 0.0);
		
		error = 20.1;
		
		output = pid.update(error);
		
		expected = -104.6365;
		
		assertEquals(expected, output, 0.0);
	}
}
