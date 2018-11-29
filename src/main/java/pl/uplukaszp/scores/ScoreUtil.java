package pl.uplukaszp.scores;

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
	private static final String FILE_NAME = "save";

	/**
	 * Loads scores from file, appends new score, sort all of them and save 10 best
	 * scores in file
	 */
	public static void save(String name, String points) {
		ObjectOutputStream stream = null;
		List<Score> scores = load();
		scores.add(new Score(name, points));
		List<Score> topTen = scores.stream().sorted().limit(10).collect(Collectors.toList());
		Serializable toStream = (Serializable) topTen;
		try {
			stream = new ObjectOutputStream(new FileOutputStream(new File(FILE_NAME)));
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

	/**
	 * Loads scores from file, saved best scores. When file not exist, is broken etc
	 * return empty list
	 */
	@SuppressWarnings("unchecked")
	public static List<Score> load() {
		ObjectInputStream stream = null;
		List<Score> scores = null;
		try {
			stream = new ObjectInputStream(new FileInputStream(new File(FILE_NAME)));
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
