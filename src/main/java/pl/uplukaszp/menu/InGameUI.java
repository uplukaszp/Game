package pl.uplukaszp.menu;

import com.almasb.fxgl.app.FXGL;

import javafx.beans.binding.StringBinding;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class InGameUI extends HBox {
	public InGameUI(StringBinding scoreBinding, StringBinding bulletsBinding, int w, int h) {
		super(20);
		this.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: black;");
		this.setTranslateX(w - 190);
		this.setTranslateY(h - 110);

		VBox scoreBox = createColumn("Bullets", bulletsBinding);
		VBox bulletsBox = createColumn("Score", scoreBinding);
		this.getChildren().add(bulletsBox);
		this.getChildren().add(scoreBox);
	}

	private VBox createColumn(String name, StringBinding binding) {
		VBox box = new VBox(20);
		box.setAlignment(Pos.CENTER);
		Text text = FXGL.getUIFactory().newText("", Color.BLACK, 22);
		text.textProperty().bind(binding);
		box.getChildren().addAll(FXGL.getUIFactory().newText(name, Color.BLACK, 22), text);
		return box;
	}
}
