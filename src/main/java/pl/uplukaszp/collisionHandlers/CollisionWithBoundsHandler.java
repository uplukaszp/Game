package pl.uplukaszp.collisionHandlers;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

import pl.uplukaszp.EntityType;

/**
 * Detection of this collision means that the object leaves the game screen.
 * This entity should be removed.
 */
public class CollisionWithBoundsHandler extends CollisionHandler {

	public CollisionWithBoundsHandler(EntityType entityType) {
		super(EntityType.bound, entityType);
	}

	@Override
	protected void onCollisionEnd(Entity bound, Entity entity) {
		entity.removeFromWorld();
	}
}
