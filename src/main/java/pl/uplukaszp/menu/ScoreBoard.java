package pl.uplukaszp.menu;

import static com.almasb.fxgl.app.FXGL.getUIFactory;

import java.util.Collection;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.uplukaszp.ScoreUtil;

public class ScoreBoard extends VBox {
	ScoreBoard() {
		Map<String, String> scoreMap = ScoreUtil.load();

		addTitleList();
		addScores(scoreMap);

	}

	private void addTitleList() {
		ListView<String> titleList = getUIFactory().newListView(FXCollections.observableArrayList("High Scores"));
		titleList.setPrefHeight(50);
		this.getChildren().add(0, titleList);
	}

	private void addScores(Map<String, String> scoreMap) {
		HBox box = new HBox();
		this.getChildren().add(box);
		box.setPrefWidth(450);

		box.getChildren().add(createList("Name", scoreMap.keySet()));
		box.getChildren().add(createList("Points", scoreMap.values()));
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
