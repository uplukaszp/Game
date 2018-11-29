package pl.uplukaszp.menu;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.scene.FXGLMenu;
import com.almasb.fxgl.scene.SceneFactory;
import com.almasb.fxgl.scene.menu.MenuType;

public class MenuFactory extends SceneFactory {
	MainMenu main;

	@Override
	public FXGLMenu newMainMenu(GameApplication app) {
		main = new MainMenu(app, MenuType.MAIN_MENU);
		return main;
	}

	@Override
	public FXGLMenu newGameMenu(GameApplication app) {
		return new MainMenu(app, MenuType.GAME_MENU);
	}

	/** @see pl.uplukaszp.menu.MainMenu#createProfileView(String) */
	public String getProfileName() {
		return main.getProfileName();
	}
}
