package haziv2;

import java.awt.event.*;

public class MyKeyAdapter extends KeyAdapter{
	
	private Player player;
	
	/**
	 * Konstruktor
	 * @param p Az epp jatszo jatekos.
	 */
	
	public MyKeyAdapter(Player p) {
		this.player = p;
	}
	
	/**
	 * keyPressed override - Jobbra-balra gombbal iranyitjuk a jatekot, a space gombbal tudunk loni a jatek soran.
	 */
	
	public void keyPressed (KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT) {
			if(player.getX() > 10)  player.setX(player.getX()-15);
		}
		if (key == KeyEvent.VK_RIGHT) {
			if (player.getX() < 480) player.setX(player.getX()+15);
		}
		if (key == KeyEvent.VK_SPACE) {
			player.shoot();
		}
		
	}
	/*
	public void keyReleased(KeyEvent e) {
		
	}*/

	
}

