package com.mygdx.game;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class Scroller extends InputAdapter {
	@Override
	public boolean scrolled (float amountX, float amountY) {
		//System.out.println(amountY);
		AttackHandler.chooseCard(ItemHandler.checkHand(amountY));
		//System.out.println("checked");
		return false;
	}
	
	@Override
	public boolean keyDown (int keycode) {
		switch (keycode) {
		case Keys.Q:
			AttackHandler.chooseCard(ItemHandler.checkHand(-1f));
			break;
		case Keys.E:
			AttackHandler.chooseCard(ItemHandler.checkHand(1f));
			break;
		}
		return false;
	}
}
