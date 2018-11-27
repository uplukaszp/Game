package scores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreUtil {
	public static void save(String name, String points) {
		ObjectOutputStream stream = null;
		List<Score> scores = load();
		scores.add(new Score(name, points));
		List<Score> topTen = scores.stream().sorted().limit(10).collect(Collectors.toList());
		Serializable toStream=(Serializable) topTen;
		try {
			stream = new ObjectOutputStream(new FileOutputStream(new File("save")));
			stream.writeObject(toStream);
			stream.flush();
		} catch (IOException e) {
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException ex) {
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Score> load() {
		ObjectInputStream stream = null;
		List<Score> scores = null;
		try {
			stream = new ObjectInputStream(new FileInputStream(new File("save")));
			Object temp = stream.readObject();
				scores = (List<Score>) temp;
		} catch (Exception e) {
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
				}
			}
			if (scores == null)
				scores = new ArrayList<>();
		}
		return scores;
	}

}
