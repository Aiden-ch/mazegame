package com.mygdx.game;

import com.badlogic.gdx.InputAdapter;

public class Scroller extends InputAdapter {
	@Override
	public boolean scrolled (float amountX, float amountY) {
		System.out.println(amountY);
		AttackHandler.chooseCard(ItemHandler.checkHand(amountY));
		//System.out.println("checked");
		return false;
	}
}
