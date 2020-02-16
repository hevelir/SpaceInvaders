package haziv2;

import java.awt.Graphics;
import java.util.Random;

public class Handler {

	Alien aliens[][] = new Alien[6][5];
	private int level;
	
	/**
	 * Feltolti az "AlienMatrixot" alienekkel.
	 */
	public void fill() {
		int indi = 0;
		int indj = 0;
		for (int i = 56; indi < 6; i+=48) {
			indj = 0;
			for (int j = 0; indj < 5; j += 32) {
				aliens[indi][indj] = new Alien(64+i, 64+j);
				indj++;
			}
			indi++;
		}
		level++;
	}
	
	/**
	 * 
	 * @return Visszaad egy booleant, ami megadja, hogy minden Alien halott-e a matrixban
	 */
	
	public boolean allDead() {
		boolean b = true;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				Alien tmp = aliens[i][j];
				if (tmp.isDead() == false) b = false;
			}
		}
		return b;
	}
	
	/**
	 * Vegigmegy a fuggveny a megadott oszlopon, es megnezi hogy melyik az utolso sor benne, ahol elo alien talalhato
	 * @param column Amelyik oszlopban keressuk az utolso sort.
	 * @return Visszaadja az utolso sort ahol elo alien van.
	 */
	
	public int getLastforShoot(int column) {
		int last = -1;
		for (int i = 0; i < 5; i++) {
			if (aliens[column][i].isDead() == false) last = i;
		}
		return last;
	}
	
	/**
	 * Ellenorzi melyik az az utolso oszlop, ahol van elo alien.
	 * @return Visszaadja az utolso oszlop erteket.
	 */
	
	public int getLastColumn() {
		int i = 0; 
		for ( i = 5; i >= 0; i--) {
			for (int j = 4; j >= 0; j--) {
				Alien tmp = aliens[i][j];
				if (!tmp.isDead()) return i;
			}
		}
		return i;
	}
	
	/**
	 * Ellenorzi melyik az utolso sor, ahol van elo alien.
	 * @return Visszaadja az utolso sor erteket.
	 */
	
	public int getLastRow() {
		int j = 0; 
		for (int i = 5; i >= 0; i--) {
			for ( j = 4; j >= 0; j--) {
				Alien tmp = aliens[i][j];
				if (!tmp.isDead()) return j;
			}
		}
		return j;
	}
	
	/**
	 * Ellenorzi melyik az elso sor, ahol van elo alien.
	 * @return Visszaadja az elso sor erteket.
	 */
	
	public int getFirstRow() {
		int j = 0; 
		for (int i = 0; i < 5; i++) {
			for ( j = 0; j < 4; j++) {
				Alien tmp = aliens[i][j];
				if (!tmp.isDead()) return j;
			}
		}
		return j;
	}
	
	/**
	 * Ellenorzi melyik az elso oszlop, ahol van elo alien.
	 * @return Visszaadja az elso oszlop erteket.
	 */
	
	public int getFirstColumn() {
		int i = 0; 
		for ( i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				Alien tmp = aliens[i][j];
				if (!tmp.isDead()) return i;
			}
		}
		return i;
	}
	
	/**
	 * A loves valosul meg ezzel a fugvennyel. Az alienek loveseit egy random szam hatarozza meg.
	 * Ha ez a random szam kisebb mint negy (ennyi oszlopa van a matrixnak) akkor lo egyet,
	 * megpedig az adott random szam oszlopanak utolso soraban levo alienje.
	 */
	
	public void randomShoot() {
		Random r = new Random();
		int row = r.nextInt(300);
		if(row <= level*4 && getLastforShoot(row % 5) != -1) { 
			row = row % 5;
			aliens[row][getLastforShoot(row)].shoot();
		}
	}
	
	/**
	 * A matrix le fel mozgasat hatarozza meg, ha elernek egy bizonyos x koordinatat, akkor az Y koordinata
	 * megvaltozik, annak fuggvenyeben hogy milyen Y koordianatanal vannak.
	 */
	
	public void verticalMove() {
		if ((aliens[getLastColumn()][getLastRow()].getX() == 479 && aliens[getLastColumn()][getLastRow()].getY() < 270 )
				|| (aliens[getFirstColumn()][getFirstRow()].getX() == 15 && aliens[getFirstColumn()][getFirstRow()].getY() < 270)) {
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 5; j++) {
					Alien tmp = aliens[i][j];
					tmp.setY(tmp.getY()+10);; 
				}
			}
		}
		else if ((aliens[getLastColumn()][getLastRow()].getX() == 479 ) || (aliens[getFirstColumn()][getFirstRow()].getX() == 15 )) {
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 5; j++) {
					Alien tmp = aliens[i][j];
					tmp.setY(tmp.getY()-20);; 
				}
			}
		}
	}
	
	/**
	 * A tick metodus, ami meghivja minden matrixban talalhato alien tick metodusat. illetve felelos
	 * a verticalMove es randomShoot fuggvenyek meghivasaert.
	 */
	
	public void tick() {
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				Alien tmp = aliens[i][j];
				if(!tmp.isDead()) tmp.tick(); 
			}
		}
		
		verticalMove();
		randomShoot();
		
	}
	
	/**
	 * Meghivja minden matrixban talalhato alien render metodusat.
	 * @param g
	 */
	
	public void render(Graphics g) {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				Alien tmp = aliens[i][j];
				if (!tmp.isDead()) aliens[i][j].render(g);;
				
			}
		}
	}
	
	
}
