package haziv2;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Alien extends Thing{
	public static boolean bound = true;
	private Image img;
	private ArrayList<Shot> fired = new ArrayList<Shot>();
	private boolean dead;
	
	/**
	 * Az Alien konstruktora
	 * @param x Megadjuk az Alien x koordinatajat
	 * @param y Es az y koordinatat is.
	 */
	
	public Alien(int x, int y) {
		super(x,y);
		dead = false;
		try {
			img  = ImageIO.read(new File("alien.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Egy Alienre ha meghivjuk, meghal, a dead attributum igaz lesz.
	 */

	public void die() {
		dead = true;
	}
	
	/**
	 * Letrehoz egy uj lovest, amit hozzaad a listahoz amibe taroljuk ezeket.
	 */
	
	public void shoot() {
		Shot s = new Shot(this);
		fired.add(s);
	}
	
	/**
	 * Visszaadja azt a listat, ami a loveseket tarolja.
	 * @return Leadott lovesek listaja.
	 */
	
	public ArrayList<Shot> getFired(){
		return fired;
	}
	
	/**
	 * Az alien hatarai egy teglalap segitsegevel vannak megadva.
	 * @return Visszaadja az alien kep hatarait.
	 */
	
	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), 48, 32);
	}
	
	/**
	 * A tick metodus override-ja, ket static attributum szerint mozgatja jobbra illetve balra az
	 * alieneket, ha elernek egy bizonyos szelso koordinatat, akkor ezeket a statik attributumokat megvaltoztatjak
	 * es az ellenkezo iranyba mennek.
	 * Minden lovesuknek meghivjak a tick metodusat.
	 */
	
	@Override
	public void tick() {
		if (x >= 480) {bound = false;  }
		if (x <= 15) {bound = true;  }
		if (bound) { x+=1;}
		else { x-=1;}
		for(int i = 0; i < fired.size(); i++) {
			fired.get(i).tick();
		}
	}
		
	/**
	 * Render ovverride, kirajzolja az alien kepet, majd meghivja a lovesek render metodusat
	 */

	@Override
	public void render(Graphics g) {
			g.drawImage(img, x, y, null);
			for(int i = 0; i < fired.size(); i++) {
				fired.get(i).render(g);
			}
	}
	
	/**
	 *
	 * @return Visszaadja egy adott alienrol, hogy halott-e vagy sem.
	 */

	public boolean isDead() {
		return dead;
	}

	

}
