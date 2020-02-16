package haziv2;

import java.io.Serializable;
import java.util.Comparator;

public class Score implements Serializable{
	private static final long serialVersionUID = 1L;
	private String date;
	private int points;
	
	/**
	 * Eredmenyek konstruktora
	 * @param date Az adott datum, amikor el lett erve a pontszam.
	 * @param points Az elert pontszam
	 */
	
	public Score(String date, int points) {
		this.date = date;
		this.points = points;
	}
	
	/**
	 * Ennek a segitsegevel rendezzuk az eredmenyeket.
	 * @return Visszaadja ket "Score" kozul melyiknek nagyobb a pontszama.
	 */
	
	public static Comparator<Score> getCompByPoints()
	{   
	 Comparator<Score> comp = new Comparator<Score>(){
	     @Override
	     public int compare(Score s1, Score s2)
	     {
	         return Integer.compare(s2.getPoints(), s1.getPoints());
	     }        
	 };
	 return comp;
	}  
	
	/**
	 * Getter
	 * @return Score datumat adja vissza.
	 */
	
	public String getDate() {
		return date;
	}
	
	/**
	 * Getter
	 * @return Visszaadja az elert pontszamot.
	 */
	
	public int getPoints() {
		return points;
	}
	
	
}
