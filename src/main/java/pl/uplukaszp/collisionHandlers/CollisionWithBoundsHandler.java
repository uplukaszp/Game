package pl.uplukaszp.collisionHandlers;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

import pl.uplukaszp.EntityType;

public class CollisionWithBoundsHandler extends CollisionHandler {

	public CollisionWithBoundsHandler(EntityType entityType) {
		super(EntityType.bound, entityType);
	}

	@Override
	protected void onCollisionEnd(Entity bound, Entity entity) {
		entity.removeFromWorld();
	}
}
