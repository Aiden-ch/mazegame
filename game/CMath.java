package com.mygdx.game;

import java.lang.Math;

public class CMath {
	public static float changeAngles(float angle) {
		float newA = 0.0f;
		float newA2 = wrap(angle);
		if(angle < 0) {
			newA = newA2 + (float)Math.PI*2f;
		}
		newA *= 180/Math.PI;
		return newA;
	}
	
	public static float wrap(float angle) {
		return angle%(2f*(float)Math.PI);
	}
}
