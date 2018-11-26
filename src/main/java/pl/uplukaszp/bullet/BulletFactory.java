package pl.uplukaszp.bullet;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import pl.uplukaszp.EntityType;

public class BulletFactory implements EntityFactory {

	@Spawns("Bullet")
	public Entity newBullet(SpawnData data) {
		Entity bullet;
		PhysicsComponent physicsComponent = new PhysicsComponent();
		physicsComponent.setOnPhysicsInitialized(() -> {
			physicsComponent.setBodyLinearVelocity(data.get("velocity"));
		});
		physicsComponent.setBodyType(BodyType.DYNAMIC);
		bullet = Entities.builder().type(EntityType.bullet).from(data).viewFromNodeWithBBox(new Circle(3,Color.BLACK))
				.with(physicsComponent,new CollidableComponent(true)).build();
		return bullet;
	}
}
