package pl.uplukaszp.entityFactories;

import pl.uplukaszp.EntityType;

import java.util.Random;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;

import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;

public class EnemyFactory implements EntityFactory {
	static final int SCREEN_WIDTH = FXGL.getAppWidth();

	static final String TEXTURE_NAME = "plane.png";

	/**
	 * Enemies are spawned at fixed height, determined from the equation: number of
	 * layer*Layer height. Enemies on each layer have their own speed, to avoid
	 * collision between enemies.
	 */
	static final int LAYERS = 4;
	static final int LAYER_HEIGHT = 40;
	static final int SPEED_MULTIPLIER = 2;

	/**
	 * Type of enemy(plane) determines its color and point bonus during a collision
	 */
	static final Color COLOR_FRIENDLY_PLANE = Color.BLACK;
	static final Color COLOR_ENEMY_PLANE = Color.GRAY;
	static final Color COLOR_ENEMY_PLANE_WITH_BONUS = Color.DARKGRAY;
	static final int CHANCE_TO_PICK_FRIEND = 25;
	static final int CHANCE_TO_PICK_ENEMY_WITH_BONUS = 10;

	int lastLayer = 1;
	Random random = new Random();

	/** Creates new enemy with random type */
	@Spawns("Enemy")
	public Entity newEnemy(SpawnData data) {
		Entity enemy = buildEnemy(data);
		setNextLayer();
		return enemy;
	}

	private Entity buildEnemy(SpawnData data) {
		Entity enemy;
		Color c = drawColor();
		PhysicsComponent physics = initPhysics();
		CollidableComponent collidable = new CollidableComponent(true);

		enemy = Entities.builder().type(EntityType.plane).from(data).viewFromTextureWithBBox(TEXTURE_NAME)
				.with(physics, collidable).build();
		enemy.setPosition(new Vec2(SCREEN_WIDTH, lastLayer * LAYER_HEIGHT));

		enemy.getView().setEffect(getColorEffect(c));

		enemy.setProperty("bonus", calculateBonus(c));
		return enemy;
	}

	private PhysicsComponent initPhysics() {
		PhysicsComponent physicsComponent = new PhysicsComponent();
		physicsComponent.setBodyType(BodyType.KINEMATIC);
		physicsComponent.setOnPhysicsInitialized(() -> {
			physicsComponent.setBodyLinearVelocity(new Vec2(-lastLayer * SPEED_MULTIPLIER, 0));
		});
		return physicsComponent;
	}

	/**
	 * Color and type are drawn based on CHANCE_TO_PICK_FRIEND and
	 * CHANCE_TO_PICK_ENEMY_WITH_BONUS value
	 */
	private Color drawColor() {
		int randomNumber = random.nextInt(100);
		if (randomNumber <= CHANCE_TO_PICK_ENEMY_WITH_BONUS)
			return COLOR_ENEMY_PLANE_WITH_BONUS;
		else if (randomNumber > CHANCE_TO_PICK_ENEMY_WITH_BONUS
				&& randomNumber < CHANCE_TO_PICK_ENEMY_WITH_BONUS + CHANCE_TO_PICK_FRIEND)
			return COLOR_FRIENDLY_PLANE;
		else
			return COLOR_ENEMY_PLANE;
	}

	private Effect getColorEffect(Color c) {
		Lighting l = new Lighting();
		l.setDiffuseConstant(1.0);
		l.setSpecularConstant(0.0);
		l.setSpecularExponent(0.0);
		l.setSurfaceScale(0.0);
		l.setLight(new Light.Distant(90, 90, c));
		return l;
	}

	private int calculateBonus(Color c) {
		if (c.equals(COLOR_FRIENDLY_PLANE))
			return -1;
		else if (c.equals(COLOR_ENEMY_PLANE))
			return 1;
		else if (c.equals(COLOR_ENEMY_PLANE_WITH_BONUS))
			return 2;
		else
			return 0;
	}

	/** Ensures, that next enemy is spawned at a different height */
	private void setNextLayer() {
		if (lastLayer < LAYERS)
			lastLayer++;
		else
			lastLayer = 1;

	}

}