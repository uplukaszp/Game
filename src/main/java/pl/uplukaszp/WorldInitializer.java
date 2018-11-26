package pl.uplukaszp;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.time.Timer;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import pl.uplukaszp.bullet.BulletFactory;
import pl.uplukaszp.enemy.EnemyFactory;

public class WorldInitializer {

	public static void initialize(GameWorld gameWorld, Timer t) {
		addEntityFactories(gameWorld);
		addPlayer(gameWorld);
		addNonMoVableEntities(gameWorld);
		addEnemySpawnTimer(t, gameWorld);
	}

	private static void addEntityFactories(GameWorld gameWorld) {
		gameWorld.addEntityFactory(new EnemyFactory());
		gameWorld.addEntityFactory(new BulletFactory());
	}

	private static void addNonMoVableEntities(GameWorld gameWorld) {
		gameWorld.addEntity(
				Entities.builder().at(0, 598).viewFromNodeWithBBox(new Rectangle(800, 2, Color.BLACK)).build());
		Entity playerNonMovablePart = Entities.builder().at(-200, 478).viewFromTextureWithBBox("tank.png").build();
		playerNonMovablePart.setScaleX(0.2);
		playerNonMovablePart.setScaleY(0.2);
		gameWorld.addEntity(playerNonMovablePart);

	}

	private static void addPlayer(GameWorld gameWorld) {
		Entity player;

		player = Entities.builder().at(60, 550).viewFromNode(new Rectangle(5, 30)).type(EntityType.player).build();
		gameWorld.addEntities(player);
	}

	private static void addEnemySpawnTimer(Timer t, GameWorld gameWorld) {
		t.runAtInterval(() -> {
			gameWorld.spawn("Enemy");
		}, Duration.seconds(1));

	}
}
