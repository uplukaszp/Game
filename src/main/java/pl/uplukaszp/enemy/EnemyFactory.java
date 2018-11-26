package pl.uplukaszp.enemy;

import pl.uplukaszp.EntityType;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;

public class EnemyFactory implements EntityFactory {
	int lastLayer = 1;
	int layers = 4;

	@Spawns("Enemy")
	public Entity newEnemy(SpawnData data) {
		Entity enemy;
		PhysicsComponent physicsComponent = new PhysicsComponent();
		physicsComponent.setBodyType(BodyType.KINEMATIC);
		physicsComponent.setOnPhysicsInitialized(() -> {
			physicsComponent.setBodyLinearVelocity(new Vec2(-lastLayer * 2, 0));
		});
		Vec2 startPosition = new Vec2(800, lastLayer * 40);
		if (lastLayer < 5)
			lastLayer++;
		else
			lastLayer = 1;

		enemy = Entities.builder().type(EntityType.enemy).from(data).viewFromTextureWithBBox("plane.png")
				.with(physicsComponent, new CollidableComponent(true)).build();
		enemy.setPosition(startPosition);

		return enemy;
	}
}