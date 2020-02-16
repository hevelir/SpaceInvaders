package haziv2;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.imageio.ImageIO;

import haziv2.Ingame.STATE;

public class Player extends Thing {
	
	
	private Image img;
	private ArrayList<Shot> fired = new ArrayList<Shot>();
	private Handler handler;
	private Ingame game;
	private ArrayList<Score> scores;
	private int points;
	private int lives;
	private int addedpoints;
	
	/**
	 * Konstruktor
	 * @param h A jatek handlerje
	 * @param g A jatek Ingame peldanya
	 * @param s A szerializaciohoz szukseges lista
	 */

	public Player(Handler h, Ingame g, ArrayList<Score> s) {
		super(270,480);
		points = 0; 
		lives = 3;
		addedpoints = 20;
		try {
			img  = ImageIO.read(new File("lovo1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		handler = h;
		game = g;
		scores = s;
	}
	
	/**
	 * Getter
	 * @return Visszaadja a jatekos pontjait.
	 */
	
	public int getPoints() {
		return points;
	}
	
	/**
	 * Getter
	 * @return Visszaadja a jatekos eleteinek szamat.
	 */
	
	public int getLives() {
		return lives;
	}
	
	/**
	 * Getter
	 * @return Visszaadja a Jatekos kirajzolasahoz szukseges kepet
	 */
	
	public Image getImage() {
		return img;
	}
	
	/**
	 * A jatekos hatarait egy teglalap segitsegevel adjuk meg.
	 * @return Visszaadja ezt a teglalapot.
	 */
	
	public Rectangle getBounds() {
		return new Rectangle(getX()+2, getY(), 48, 20);
	}
	
	/**
	 * Getter
	 * @return Visszaadja a leadott lovesek listajat.
	 */
	
	public ArrayList<Shot> getFired(){
		return fired;
	}
	
	/**
	 * Letrehoz egy uj lovest, es hozzaadja a listat.
	 */
	
	public void shoot() {
		Shot s = new Shot(this);
		if (fired.size() < 3) fired.add(s);
	}
	
	/**
	 * Ellenorzi,  hogy a leadott lovesek eltalaltak-e alient, ha igen akkor megoli azt, eltunik a loves,
	 * es pontot kapunk erte.
	 */
	
	public void collision() {
		for (int i = 0; i < 6; i++) {   // Handler bejarasa
			for (int j = 0; j < 5; j++) {
				Alien tmp = handler.aliens[i][j];
				for(int k = 0; k < fired.size(); k++) {  // lovesek bejarasa
					if (tmp.isDead() == false && fired.get(k).getBounds().intersects(tmp.getBounds())) {
						tmp.die();
						fired.remove(fired.get(k));
						points += addedpoints;
					}
					
				}
			}
		}
		for(int k = 0; k < fired.size(); k++) {
			if (fired.get(k).getY() < 0 ) fired.remove(k);
		}
	}
	
	/**
	 * Ellenorzi, hogy az alienek altal kiadott loves eltalalta-e, ha igen levon az eletbol, 
	 * eltavolitja a lovest, es ha mar nincs tobb eletunk, meghivja a saveData fuggvenyt,
	 * es visszater a menube.
	 */
	
	public void hitByShot() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				Alien tmp = handler.aliens[i][j];
				ArrayList<Shot> s = tmp.getFired();
				for(int k = 0; k < s.size(); k++) {
					if (s.get(k).getBounds().intersects(getBounds())) {
						s.remove(k);
						lives--;
						if (lives == 0) {
							saveData();
							game.GameState = STATE.Menu; 
						}
					}
				}
				
	 		}
		}
	}
	
	/**
	 * Szerializalas fuggvenye, a pontszamot es az aktualis idopontot hozzaadja a listahoz, es kiirja egy fajlba
	 * a listat.
	 */
	
	
	public void saveData() {
		try {
            FileOutputStream fileOut = new FileOutputStream("scores.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            java.util.Date today = Calendar.getInstance().getTime();        
            String reportDate = df.format(today);
            Score s = new Score(reportDate, getPoints());
            scores.add(s);
            out.writeObject(scores);
            out.close();
            fileOut.close();
        } catch(IOException i) {
            i.printStackTrace();
        } 
    }
	
	/**
	 * tick Override, meghivja minden loves tick metodusat, leellenorzi talalt-e a lovese, es hogy eltalaltak
	 * az alienek vagy sem. Ellenorzi tovabba, hogy van-e meg elo alien, ha nincs ujakat "rajzol".
	 */
	
	@Override
	public void tick() {
		for(int i = 0; i < fired.size(); i++) { //lovesek bejarasa
			fired.get(i).tick();
		}
		
		collision(); //lottunk-e le alient
		hitByShot(); // talalt-e el loves
		
		if(handler.allDead()) {  //ha minden alien halott, ujrarajzolodik
			handler.fill();
			addedpoints += 10;
		}
	}
	
	/**
	 * render Override, kirajzolja a jatekost, es meghivja a lovesek render metodusat.
	 */

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.GREEN);
		g.drawImage(img, getX(), getY(), null);
		for(int i = 0; i < fired.size(); i++) {
			fired.get(i).render(g);
		}
		//g.drawRect(getX()+2, getY(), 48, 20);
	}

}
