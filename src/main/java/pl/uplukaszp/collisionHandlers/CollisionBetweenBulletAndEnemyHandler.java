package pl.uplukaszp.collisionHandlers;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.gameplay.GameState;
import com.almasb.fxgl.physics.CollisionHandler;

import pl.uplukaszp.EntityType;

public class CollisionBetweenBulletAndEnemyHandler extends CollisionHandler {

	private GameState states;

	public CollisionBetweenBulletAndEnemyHandler(GameState states) {
		super(EntityType.bullet, EntityType.enemy);
		this.states = states;
	}

	@Override
	protected void onCollisionBegin(Entity enemy, Entity bullet) {
		bullet.removeFromWorld();
		enemy.removeFromWorld();
		states.setValue("score", states.getInt("score") + 1);
		states.setValue("bullets", states.getInt("bullets") + 1);

	}

}
