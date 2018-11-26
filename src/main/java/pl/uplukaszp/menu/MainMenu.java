package pl.uplukaszp.menu;

import static com.almasb.fxgl.app.FXGL.getUIFactory;

import java.util.Arrays;
import java.util.List;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.scene.FXGLMenu;
import com.almasb.fxgl.scene.menu.MenuType;
import com.almasb.fxgl.util.Supplier;

import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
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

		contentRoot.setTranslateX(280);
		contentRoot.setTranslateY(130);

		menuRoot.getChildren().add(vbox);
		contentRoot.getChildren().add(EMPTY);

		vbox.getChildren().set(0, menuBody);
		vbox.getChildren().set(1, createContentScore());
		activeProperty().addListener((observable, wasActive, isActive) -> {
			if (!isActive) {
				// the scene is no longer active so reset everything
				// so that next time scene is active everything is loaded properly
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
		Text titleView = FXGL.getUIFactory().newText(app.getSettings().getTitle(), 18);
		titleView.setTranslateY(30);
		return titleView;
	}

	@Override
	protected Node createVersionView(String version) {
		Text view = FXGL.getUIFactory().newText(version, 16);
		view.setTranslateX(app.getWidth() - view.getLayoutBounds().getWidth());
		view.setTranslateY(20);
		return view;
	}

	@Override
	protected Node createProfileView(String profileName) {
		Text view = FXGL.getUIFactory().newText(profileName, 24);
		view.setTranslateX(app.getWidth() - view.getLayoutBounds().getWidth());
		view.setTranslateY(50);
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
			System.out.println(e.getSource());
			System.out.println(action);
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
		HBox box = new HBox(20);
		box.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		List<Button> n = Arrays.asList(getUIFactory().newButton("Name"), getUIFactory().newButton("aaa"),
				getUIFactory().newButton("cc"), getUIFactory().newButton("xx"));
		List<Button> p = Arrays.asList(getUIFactory().newButton("Points"), getUIFactory().newButton("2"),
				getUIFactory().newButton("1"), getUIFactory().newButton("0"));
		VBox names = new VBox(20);
		names.getChildren().addAll(n);
		VBox points = new VBox(20);
		points.getChildren().addAll(p);
		box.getChildren().addAll(names, points);
		return new MenuContent(box);
	}
}
