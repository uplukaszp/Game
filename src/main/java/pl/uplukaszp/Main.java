package pl.uplukaszp;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.settings.GameSettings;

public class Main extends GameApplication {

	@Override
	protected void initSettings(GameSettings settings) {
		settings.setWidth(800);
		settings.setHeight(600);

	}

	public static void main(String[] args) {
		launch(args);
	}
}