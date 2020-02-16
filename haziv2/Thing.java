package haziv2;

import java.awt.*;

public abstract class Thing {
	protected int x;
	protected int y;
	
	/**
	 * Thing konstruktor, minden Thingnek van egy x es y koordinataja
	 */
	
	public Thing(int a, int b) {
		x = a;
		y = b;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract void shoot();
	
	/**
	 * Getter
	 * @return Visszaadja a Thing x koordinatajat.
	 */
	
	public int getX() {
		return x;
	}
	
	/**
	 * Getter
	 * @return Visszaadja a Thing y koordinatajat.
	 */
	
	public int getY() {
		return y;
	}
	
	/**
	 * Beallitja a Thing x koordinatajat az adott ertekre.
	 * @param a adott ertek
	 */
	
	public void setX(int a) {
		x = a;
	}
	
	/**
	 * Beallitja a Thing y koordinatajat az adott ertekre.
	 * @param b adott ertek
	 */
	
	public void setY(int b) {
		y = b;
	}
}
