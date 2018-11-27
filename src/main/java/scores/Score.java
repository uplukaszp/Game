package scores;

import java.io.Serializable;

public class Score implements Comparable<Score>,Serializable {
	String name;
	String points;

	public Score(String name, String points) {
		super();
		this.name = name;
		this.points = points;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	@Override
	public int compareTo(Score o) {
		Integer p1=Integer.valueOf(points);
		Integer p2=Integer.valueOf(o.points);
		return p2.compareTo(p1);
	}

	@Override
	public String toString() {
		return "Score [name=" + name + ", points=" + points + "]";
	}
}
