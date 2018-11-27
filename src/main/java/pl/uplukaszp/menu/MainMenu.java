package pl.uplukaszp.menu;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.scene.FXGLMenu;
import com.almasb.fxgl.scene.menu.MenuType;
import com.almasb.fxgl.util.Supplier;

import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MainMenu extends FXGLMenu {
	private HBox vbox = new HBox(50);

	private Node menuBody;

	public MainMenu(GameApplication app, MenuType type) {
		super(app, type);

		menuBody = type == MenuType.MAIN_MENU ? createMenuBodyMainMenu() : createMenuBodyGameMenu();

		vbox.getChildren().addAll(new Pane(), new Pane());
		vbox.setTranslateX(50);
		vbox.setTranslateY(50);

		menuRoot.getChildren().add(vbox);
		contentRoot.getChildren().add(EMPTY);

		vbox.getChildren().set(0, menuBody);
		if (type == MenuType.MAIN_MENU)
			vbox.getChildren().set(1, createContentScore());
		activeProperty().addListener((observable, wasActive, isActive) -> {
			if (!isActive) {
				switchMenuTo(menuBody);
				switchMenuContentTo(EMPTY);
			}
		});
	}

	@Override
	protected Node createBackground(double width, double height) {
		return new Rectangle(app.getWidth(), app.getHeight(), Color.WHITE);
	}

	@Override
	protected Node createTitleView(String title) {
		Text titleView = FXGL.getUIFactory().newText("");
		return titleView;
	}

	@Override
	protected Node createVersionView(String version) {
		Text view = FXGL.getUIFactory().newText("");
		return view;
	}

	@Override
	protected Node createProfileView(String profileName) {
		Text view = FXGL.getUIFactory().newText("");
		return view;
	}

	@Override
	protected void switchMenuTo(Node menuBox) {
		vbox.getChildren().set(0, menuBox);
	}

	@Override
	protected void switchMenuContentTo(Node content) {
		contentRoot.getChildren().set(0, content);
	}

	private VBox createMenuBodyMainMenu() {
		Button btnNew = createActionButton(FXGL.localizedStringProperty("menu.newGame"), this::fireNewGame);
		Button btnLogout = createActionButton(FXGL.localizedStringProperty("menu.logout"), this::fireLogout);
		Button btnExit = createActionButton(FXGL.localizedStringProperty("menu.exit"), this::fireExit);
		return new VBox(10, btnNew, btnLogout, btnExit);
	}

	private VBox createMenuBodyGameMenu() {
		Button btnResume = createActionButton(FXGL.localizedStringProperty("menu.resume"), this::fireResume);
		Button btnExit = createActionButton(FXGL.localizedStringProperty("menu.exit"), this::fireExitToMainMenu);
		return new VBox(10, btnResume, btnExit);
	}

	/**
	 * Creates a new button with given name that performs given action on
	 * click/press.
	 *
	 * @param name   button name
	 * @param action button action
	 * @return new button
	 */
	protected final Button createActionButton(String name, Runnable action) {
		Button btn = FXGL.getUIFactory().newButton(name);
		btn.setOnAction(e -> action.run());
		return btn;
	}

	/**
	 * Creates a new button with given name that performs given action on
	 * click/press.
	 *
	 * @param name   button name (with binding)
	 * @param action button action
	 * @return new button
	 */
	protected final Button createActionButton(StringBinding name, Runnable action) {
		Button btn = FXGL.getUIFactory().newButton(name);
		btn.setOnAction(e -> {
			action.run();
		});
		return btn;
	}

	/**
	 * Creates a new button with given name that sets given content on click/press.
	 *
	 * @param name            button name
	 * @param contentSupplier content supplier
	 * @return new button
	 */
	@SuppressWarnings("unchecked")
	protected final Button createContentButton(String name, Supplier<MenuContent> contentSupplier) {
		Button btn = FXGL.getUIFactory().newButton(name);
		btn.setUserData(contentSupplier);
		btn.setOnAction(e -> switchMenuContentTo(((Supplier<MenuContent>) btn.getUserData()).get()));
		return btn;
	}

	/**
	 * Creates a new button with given name that sets given content on click/press.
	 *
	 * @param name            button name (with binding)
	 * @param contentSupplier content supplier
	 * @return new button
	 */
	@SuppressWarnings("unchecked")
	protected final Button createContentButton(StringBinding name, Supplier<MenuContent> contentSupplier) {
		Button btn = FXGL.getUIFactory().newButton(name);
		btn.setUserData(contentSupplier);
		btn.setOnAction(e -> switchMenuContentTo(((Supplier<MenuContent>) btn.getUserData()).get()));
		return btn;
	}

	protected final MenuContent createContentScore() {

		return new MenuContent(new ScoreBoard());
	}
}
