package pl.uplukaszp.menu;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.scene.FXGLMenu;
import com.almasb.fxgl.scene.SceneFactory;
import com.almasb.fxgl.scene.menu.MenuType;

public class MenuFactory extends SceneFactory {

	@Override
	public FXGLMenu newMainMenu(GameApplication app) {
		return new MainMenu(app, MenuType.MAIN_MENU);
	}

	@Override
	public FXGLMenu newGameMenu(GameApplication app) {
		return new MainMenu(app, MenuType.GAME_MENU);

	}
}
