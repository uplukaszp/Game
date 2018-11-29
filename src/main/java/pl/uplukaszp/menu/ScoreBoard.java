package pl.uplukaszp.menu;

import static com.almasb.fxgl.app.FXGL.getUIFactory;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.uplukaszp.scores.Score;
import pl.uplukaszp.scores.ScoreUtil;

/** Table with top 10 scores */
public class ScoreBoard extends VBox {
	private static final int TITILE_HEIGHT = 50;
	private static final int SCOREBOARD_HEIGHT = 410;
	private static final int SCOREBOARD_WIDTH = 450;

	ScoreBoard() {
		List<Score> scoreMap = ScoreUtil.load();

		addTitleList();
		addScores(scoreMap);

	}

	private void addTitleList() {
		ListView<String> titleList = getUIFactory().newListView(FXCollections.observableArrayList("High Scores"));
		titleList.setPrefHeight(TITILE_HEIGHT);
		this.getChildren().add(titleList);
	}

	private void addScores(List<Score> scoreList) {
		HBox box = new HBox();
		this.getChildren().add(box);
		box.setPrefWidth(SCOREBOARD_WIDTH);
		box.setPrefHeight(SCOREBOARD_HEIGHT);

		List<String> names = scoreList.stream().map(Score::getName).collect(Collectors.toList());
		List<String> points = scoreList.stream().map(Score::getPoints).collect(Collectors.toList());

		box.getChildren().add(createList("Name", names));
		box.getChildren().add(createList("Points", points));
	}

	private ListView<String> createList(String columnName, Collection<String> rows) {
		ObservableList<String> observable = FXCollections.observableArrayList();
		ListView<String> listView = getUIFactory().newListView(observable);

		observable.add(columnName);
		observable.addAll(rows);

		listView.setMouseTransparent(true);
		listView.setFocusTraversable(false);
		return listView;
	}
}
