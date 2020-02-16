package haziv2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;

import haziv2.Ingame.STATE;

public class Menu extends MouseAdapter{
	
	Ingame game;
	Handler handler;
	Player player;
    ArrayList<Score> scores;
    
    /**
     * Menu konstruktora.
     * @param g A jatekban hasznalt Ingame peldanya adodik at.
     * @param h A jatekban hasznalt handler.
     * @param p Az epp jatszo player.
     * @param s A lista aminek segitsegevel szerializaljuk az eredmenyeket.
     */
	
	public Menu(Ingame g, Handler h, Player p, ArrayList<Score> s) {
		this.game = g;
		this.handler = h;
		this.player = p;
		this.scores = s;
	}
	
	/**
	 * mousePressed override, ellenorzi hova kattintottunk a menuben, es ennek fuggvenyeben hivja meg a tovabbi fuggvenyeket,
	 * illetve valtoztatja a jatek allapotat(STATE). 
	 */
	
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
	//	Start Game button
		if (mouseinRect(x, y, 170, 370, 200, 260)) {
			handler.fill();
			game.newPlayer(handler, game, scores);
			game.GameState = STATE.Ingame;
		}
		
		if(mouseinRect(x, y, 170, 370, 260, 320)) {
			game.GameState = STATE.Highscores;
			loadScores();
		}
		
		if(mouseinRect(x, y, 70, 170, 415, 455) && game.GameState == STATE.Highscores) {
			game.GameState = STATE.Menu;
		}
		
		if (mouseinRect(x, y, 170, 370, 320, 380) && game.GameState == STATE.Menu) {
			System.exit(1);
		}
	}
	
	/**
	 * mouseReleased override
	 */
	public void mouseReleased(MouseEvent e) {
		
	}
	
	/**
	 * Szerializacio : beolvassa a fajlbol az eredmenyeket tartalmazo listat.
	 */
	
	@SuppressWarnings("unchecked")
	public void loadScores() {
		ObjectInputStream objectinputstream = null;
		try {
		    FileInputStream streamIn = new FileInputStream("scores.txt");
		    objectinputstream  = new ObjectInputStream(streamIn);
		    scores = (ArrayList<Score>) objectinputstream.readObject();
		    Collections.sort(scores, Score.getCompByPoints());
		} catch (Exception e) {
		    e.printStackTrace();
		} 
	}
	
	/**
	 * Ellenorzi, hogy az eger a megadott koordinatakon(teglalapot hataroznak meg) belul helyezkedik vagy sem.
	 * @param x A vizsgalt x koordinata.
	 * @param y A vizsgalt y koordinata.
	 * @param x1 A teglalap baloldali pontja
 	 * @param x2 A teglalap jobboldali pontja.
	 * @param y1 A teglalap felso koordinataja.
 	 * @param y2 A teglalap also koordinataja.
	 * @return Visszaad egy boolean erteket, ha true benne van, ha false akkor nincs.
	 */
	
	public boolean mouseinRect(int x, int y, int x1, int x2, int y1, int y2) {
		if (x > x1 && x < x2)
			if (y > y1 && y < y2) return true;
		return false;
	}
	
	/*public void tick() {
		
	}*/
	
	/**
	 * Ha a menu gamestate aktiv a jatekban, akkor a menut es a harom gombot rajzolja ki: Start, Highscores, Quit
	 * Ha pedig a Highscores STATE igaz, akkor listazza a kepernyore a highscoreokat.
	 * @param g
	 */
	
	public void render(Graphics g) {
		
		if (game.GameState == STATE.Menu) {
		
		g.fillRect(195, 50, 150, 60);
		g.setColor(Color.RED);
		g.setFont(new Font("Comic Sans MS", Font.ITALIC, 50));
		g.drawString("Menu", 205, 95);
		
		g.setFont(new Font("Comic Sans MS", Font.ITALIC, 30));
		g.setColor(Color.GRAY);
		g.fillRect(170, 200, 200, 60);
		g.setColor(Color.RED);
		g.drawRect(170, 200, 200, 60);
		g.setColor(Color.RED);
		g.drawString("Start Game", 190, 240);
		
		g.setColor(Color.GRAY);
		g.fillRect(170, 260, 200, 60);
		g.setColor(Color.RED);
		g.drawRect(170, 260, 200, 60);
		g.setColor(Color.RED);
		g.drawString("Highscores", 190, 300);
		
		g.setColor(Color.GRAY);
		g.fillRect(170, 320, 200, 60);
		g.setColor(Color.RED);
		g.drawRect(170, 320, 200, 60);
		g.setColor(Color.RED);
		g.drawString("Quit", 230, 360);
		}
		
		if(game.GameState == STATE.Highscores) {
			g.setColor(Color.GRAY);
			g.fillRect(90, 415, 100, 40);
			g.fillRect(50, 80, 440, 310);
			g.setColor(Color.RED);
			g.drawRect(90, 415, 100, 40);
			g.drawRect(50, 80, 440, 310);
			g.setFont(new Font("Comic Sans MS", Font.ITALIC, 35));
			g.drawString("Highscores", 180, 50);					//fejlec
			g.setFont(new Font("Comic Sans MS", Font.BOLD, 25));    
			g.drawString("Back", 110, 445);	//back
			g.setFont(new Font("Comic Sans MS", Font.CENTER_BASELINE, 20));
			for(int i = 0, vert = 0; i < scores.size() && i < 7; i++, vert += 40) {
				String data = scores.get(i).getDate();
				String points = Integer.toString(scores.get(i).getPoints());
				g.drawString(data + "                " + points, 70, 110+vert);
			}
		}
	}
}
