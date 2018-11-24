package pl.uplukaszp.enemy;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pl.uplukaszp.components.MoveComponent;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

public class EnemyFactory implements EntityFactory {
	int lastLayer = 0;
	int layers = 5;

	@Spawns("Enemy")
	public Entity newEnemy(SpawnData data) {
		Entity enemy;
		Vec2 startPosition = new Vec2(0, lastLayer * 40);
		if (lastLayer < 5)
			lastLayer++;
		else
			lastLayer = 0;

		enemy = Entities.builder().type(EnemyType.simple).from(data).viewFromNode(new Rectangle(70, 30, Color.BROWN))
				.with(new MoveComponent(new Vec2(lastLayer + 1, 0))).build();
		enemy.setPosition(startPosition);
		return enemy;
	}
}
