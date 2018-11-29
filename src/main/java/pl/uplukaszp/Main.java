package pl.uplukaszp;

import java.util.Map;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.RotationComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.settings.GameSettings;

import javafx.beans.binding.StringBinding;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import pl.uplukaszp.collisionHandlers.CollisionBetweenBulletAndEnemyHandler;
import pl.uplukaszp.collisionHandlers.CollisionWithBoundsHandler;
import pl.uplukaszp.menu.InGameUI;
import pl.uplukaszp.menu.MenuFactory;

public class Main extends GameApplication {
	private static final int INITIAL_NUMBER_OF_BULLETS = 10;
	private static final int ROTATION_STEP = 5;

	Entity player;
	MenuFactory menuFactory = new MenuFactory();

	@Override
	protected void initSettings(GameSettings settings) {
		settings.setWidth(800);
		settings.setHeight(600);
		settings.setMenuEnabled(true);
		settings.setSceneFactory(menuFactory);
		settings.setCSS("style.css");
	}

	@Override
	protected void initUI() {
		StringBinding score = getGameState().intProperty("score").asString();
		StringBinding bullets = getGameState().intProperty("bullets").asString();
		getGameScene().addUINode(new InGameUI(score, bullets, getWidth(), getHeight()));
	}

	@Override
	protected void initGameVars(Map<String, Object> vars) {
		vars.put("score", 0);
		vars.put("bullets", INITIAL_NUMBER_OF_BULLETS);
	}

	@Override
	protected void initGame() {
		WorldInitializer.initialize();
		player = getGameWorld().getEntitiesByType(EntityType.player).get(0);
	}

	@Override
	protected void initInput() {
		Input input = getInput();
		input.addEventHandler(ScrollEvent.SCROLL, this::rotatePlayer);
		input.addAction(new ShootAction(menuFactory.getProfileName()), MouseButton.MIDDLE);
	}

	private void rotatePlayer(ScrollEvent event) {
		RotationComponent rotation = player.getComponent(RotationComponent.class);
		if (event.getDeltaY() > 0) {
			if (rotation.getValue() < 90) {
				rotation.rotateBy(ROTATION_STEP);
			}
		} else {
			if (rotation.getValue() > 0) {
				rotation.rotateBy(-ROTATION_STEP);
			}
		}
	}

	@Override
	protected void initPhysics() {
		PhysicsWorld physics = getPhysicsWorld();
		physics.addCollisionHandler(new CollisionBetweenBulletAndEnemyHandler(getGameState()));
		physics.addCollisionHandler(new CollisionWithBoundsHandler(EntityType.bullet));
		physics.addCollisionHandler(new CollisionWithBoundsHandler(EntityType.plane));
	}

	public static void main(String[] args) {
		launch(args);
	}
}