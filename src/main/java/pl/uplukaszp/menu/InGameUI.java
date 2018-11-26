package pl.uplukaszp.menu;

import com.almasb.fxgl.app.FXGL;

import javafx.beans.binding.StringBinding;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class InGameUI extends HBox {
	public InGameUI(StringBinding scoreBinding, StringBinding bulletsBinding,int w, int h) {
		super(20);
		
		VBox scoreBox = new VBox(20);
		VBox bulletsBox = new VBox(20);
		
		Text score = FXGL.getUIFactory().newText("", Color.BLACK, 22);
		Text bullets = FXGL.getUIFactory().newText("", Color.BLACK, 22);
		score.textProperty().bind(scoreBinding);
		bullets.textProperty().bind(bulletsBinding);
		scoreBox.getChildren().addAll(FXGL.getUIFactory().newText("Score", Color.BLACK, 22), score);
		bulletsBox.getChildren().addAll(FXGL.getUIFactory().newText("Bullets", Color.BLACK, 22), bullets);
		this.setTranslateX(w - 150);
		this.setTranslateY(h - 100);
		this.getChildren().add(bulletsBox);
		this.getChildren().add(scoreBox);
		System.err.println(this);
	}
	
	public HBox get(StringBinding scoreBinding, StringBinding bulletsBinding, int w, int h)
	{
		HBox b=new HBox();
		VBox scoreBox = new VBox(20);
		VBox bulletsBox = new VBox(20);
		
		Text score = FXGL.getUIFactory().newText("", Color.BLACK, 22);
		Text bullets = FXGL.getUIFactory().newText("", Color.BLACK, 22);
		score.textProperty().bind(scoreBinding);
		bullets.textProperty().bind(bulletsBinding);
		scoreBox.getChildren().addAll(FXGL.getUIFactory().newText("Score", Color.BLACK, 22), score);
		bulletsBox.getChildren().addAll(FXGL.getUIFactory().newText("Bullets", Color.BLACK, 22), bullets);
		b.setTranslateX(w - 100);
		b.setTranslateY(h - 100);
		b.getChildren().add(bulletsBox);
		b.getChildren().add(scoreBox);
		return b;
	}

}
