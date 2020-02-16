package haziv2;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;


public class Ingame extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 540;
	public static final int HEIGHT = WIDTH;
	private Thread thread;
	private boolean running = false;
	private Handler handler;
	private Player player;
	private Menu menu;
	
	
	public enum STATE{
		Menu,
		Ingame,
		Highscores
	}
	
	public STATE GameState = STATE.Menu;
	
	/**
	 * Ingame konstruktora, megalkotja a menut, jatekost, handlert.
	 */
	
	public Ingame() {
		ArrayList<Score> s = new ArrayList<Score>();
		handler = new Handler();
		handler.fill();
		player = new Player(handler, this, s);
		this.addKeyListener(new MyKeyAdapter(player));
		new Window(WIDTH, HEIGHT, "Space Invaders", this);
		
		menu = new Menu(this, handler, player, s);
		this.addMouseListener(menu);
	}
	
	/**
	 * Akkor hasznaljuk, amikor meghal egy jatekos, es ujrainditjuk a jatekot.
	 * @param h A jatek handlere.
	 * @param g A jatek soran hasznalt Ingame peldany.
	 * @param s Az a lista, amibe beolvassuk a jatek elejen a highscoreokat, es ennek segitsegevel iratodik ki.
	 */
	
	public void newPlayer(Handler h, Ingame g, ArrayList<Score> s) {
		player = new Player(h, g, s);
		this.addKeyListener(new MyKeyAdapter(player));
	}
	
	/**
	 * Szal start metodusa.
	 */
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	/**
	 * Szal stop metodusa
	 */
	
	
	public synchronized void stop() {
		/*try {
			thread.join();
			running = false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	
	/**
	 * A jatek alapja, a szal run metodusa, a Tick, render fuggvenyek meghivasanak szamat befolyasolja.
	 */
	
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running) {
				render();
			}
		}
		stop();
	}
	
	/**
	 * A run altal meghivott tick metodus, ami meghivja a handler es a player tick metodusat,ha jatekban vagyunk
	 */
	
	private void tick() {
		if (GameState == STATE.Ingame) {
		handler.tick();
		player.tick();
		}
	}
	
	/**
	 * Render metodus, ezt is a run fuggveny hivja meg, ez pedig tovabbhivja a menu illetve a handler-player render metodust
	 * Ha jatekban vagyunk megrajzolja az eletek szamat es a jelenlegi pontszamot is.
	 */
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		if (GameState != STATE.Ingame) {
			menu.render(g);
		}
		
	if (GameState == STATE.Ingame) {
		handler.render(g);
		player.render(g);
	
		// Pontok szamolasa
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Comic Sans MS", Font.CENTER_BASELINE, 24));
		g.drawString("Points: ", 360, 50); g.setColor(Color.GREEN);
		g.drawString(Integer.toString(player.getPoints()), 440, 50);
	//
		g.setColor(Color.RED);
		g.drawString("Lives ", 20, 50);
		for (int a = 0, b = 50; a < player.getLives(); a++, b+=50) {
			g.drawImage(player.getImage(), 40+b, 29, null);
		}
	}
		
		g.dispose();
		bs.show();
		
	}
	
	/**
	 * Main fuggveny
	 * @param args
	 */
	
	public static void main(String[] args) {
		new Ingame();
	}

	
}
