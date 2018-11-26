package pl.uplukaszp;

import java.util.Map;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.RotationComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.settings.GameSettings;

import javafx.beans.binding.StringBinding;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import pl.uplukaszp.menu.InGameUI;
import pl.uplukaszp.menu.MenuFactory;

public class Main extends GameApplication {
	Entity player;

	@Override
	protected void initSettings(GameSettings settings) {
		settings.setWidth(800);
		settings.setHeight(600);
		settings.setMenuEnabled(true);
		settings.setSceneFactory(new MenuFactory());
	}

	@Override
	protected void initUI() {
		StringBinding score = getGameState().intProperty("score").asString();
		StringBinding bullets = getGameState().intProperty("bullets").asString();
		getGameScene().addUINode(new InGameUI(score, bullets,getWidth(),getHeight()));
	}

	@Override
	protected void initGameVars(Map<String, Object> vars) {
		vars.put("score", 0);
		vars.put("bullets", 10);
	}

	@Override
	protected void initGame() {
		WorldInitializer.initialize(getGameWorld(), getMasterTimer());
		player=getGameWorld().getEntitiesByType(EntityType.player).get(0);
	
	}

	@Override
	protected void initInput() {
		Input input = getInput();
		input.addEventHandler(ScrollEvent.SCROLL, event -> {
			RotationComponent rotation = player.getComponent(RotationComponent.class);
			if (event.getDeltaY() > 0) {
				if (rotation.getValue() < 90) {
					rotation.rotateBy(5);
				}
			} else {
				if (rotation.getValue() > 0) {
					rotation.rotateBy(-5);
				}
			}
		});
		input.addAction(new UserAction("Shoot") {
			@Override
			protected void onActionBegin() {
				Vec2 velocity = new Vec2();
				velocity.y = (float) Math.cos(Math.toRadians(player.getRotation()));
				velocity.x = (float) Math.sin(Math.toRadians(player.getRotation()));
				velocity = velocity.mul(20);
				Point2D center = new Point2D(player.getX(), player.getY() + 12);
				SpawnData spawnData = new SpawnData(center);
				spawnData.put("velocity", velocity);
				getGameWorld().spawn("Bullet", spawnData);
				getGameState().setValue("bullets", getGameState().getInt("bullets") - 1);
				if (getGameState().getInt("bullets") == 0) {
					getDisplay().showMessageBox("Your score: " + getGameState().getInt("score"), () -> {

					});
				}
			}
		}, MouseButton.MIDDLE);
	}

	@Override
	protected void initPhysics() {
		PhysicsWorld physics = getPhysicsWorld();
		physics.addCollisionHandler(new CollisionHandler(EntityType.enemy, EntityType.bullet) {
			@Override
			protected void onCollisionBegin(Entity enemy, Entity bullet) {
				bullet.removeFromWorld();
				enemy.removeFromWorld();
				getGameState().setValue("score", getGameState().getInt("score") + 1);
				getGameState().setValue("bullets", getGameState().getInt("bullets") + 1);

			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}