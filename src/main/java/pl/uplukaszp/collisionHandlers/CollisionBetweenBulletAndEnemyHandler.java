package pl.uplukaszp.collisionHandlers;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.gameplay.GameState;
import com.almasb.fxgl.physics.CollisionHandler;

import pl.uplukaszp.EntityType;

public class CollisionBetweenBulletAndEnemyHandler extends CollisionHandler {

	private GameState states;

	public CollisionBetweenBulletAndEnemyHandler(GameState states) {
		super(EntityType.bullet, EntityType.plane);
		this.states = states;
	}

	@Override
	protected void onCollisionBegin(Entity bullet, Entity enemy) {
		bullet.removeFromWorld();
		enemy.removeFromWorld();
		int bonus = enemy.getProperties().getValue("bonus");
		updateStats(bonus);
	}

	private void updateStats(int bonus) {
		states.setValue("score", states.getInt("score") + bonus);
		states.setValue("bullets", states.getInt("bullets") + bonus);
	}

}
