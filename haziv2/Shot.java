package haziv2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Shot{
	private int x;
	private int y;
	private boolean playerShot;

	/**
	 * Lovedek konstruktora, jatekos altal lott lovedeket hoz letre.
	 * @param p Az eppen jatszo jatekos.
	 */
	
	public Shot(Player p) {
		this.x = p.getX() + 25;
		this.y = p.getY()-3;
		playerShot = true;
	}
	
	/**
	 * Lovedek konstruktora, alien altal lott lovedeket hoz letre
	 * @param a A lovedeket kilovo alien.
	 */
	
	public Shot(Alien a) {
		this.x = a.getX()+20;
		this.y = a.getY()+20;
		playerShot = false;
	}
	
	/**
	 * Getter
	 * @return A lovedek x koordinatajat adja vissza.
	 */
	
	public int getX() {
		return x;
	}
	
	/**
	 * Getter
	 * @return A lovedek y koordinatajat adja vissza.
	 */
	
	public int getY() {
		return y;
	}
	
	/**
	 * A lovedek hatarai egy teglalap segitsegevel vannak megadva, ezt adja vissza.
	 * @return Visszaadja a teglalapot.
	 */
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, 2, 5);
	}
	
	/**
	 * tick metodus, ha Jatekos altal lott lovedekrol van szo, felfele megy tovabb, ha alien altalirol, akkor lefele.
	 */

	public void tick() {
		if (playerShot) y -= 5;
		if (!playerShot) y += 3;
	}
	
	/**
	 * render metodus, kirajzolja a lovedeket a koordinatai alapjan
	 * @param g
	 */

	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.RED);
		g.drawRect(x, y, 2, 5);
		g.fillRect(x, y, 2, 5);
	}
}
