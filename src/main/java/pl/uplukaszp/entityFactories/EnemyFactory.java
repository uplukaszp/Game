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
	
	static final int LAYERS = 4;
	static final String TEXTURE_NAME = "plane.png";
	static final int SCREEN_WIDTH = FXGL.getAppWidth();
	static final int LAYER_WIDTH = 40;
	static final Color COLOR_FRIENDLY_PLANE = Color.BLACK;
	static final Color COLOR_ENEMY_PLANE = Color.GRAY;
	static final Color COLOR_ENEMY_PLANE_WITH_BONUS = Color.DARKGRAY;
	static final int CHANCE_TO_PICK_FRIEND = 15;
	static final int CHANCE_TO_PICK_ENEMY_WITH_BONUS = 10;

	int lastLayer = 1;
	Random random = new Random();

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
		enemy.setPosition(new Vec2(SCREEN_WIDTH, lastLayer * LAYER_WIDTH));

		enemy.getView().setEffect(getColorEffect(c));

		enemy.setProperty("bonus", calculateBonus(c));
		return enemy;
	}

	private PhysicsComponent initPhysics() {
		PhysicsComponent physicsComponent = new PhysicsComponent();
		physicsComponent.setBodyType(BodyType.KINEMATIC);
		physicsComponent.setOnPhysicsInitialized(() -> {
			physicsComponent.setBodyLinearVelocity(new Vec2(-lastLayer * 2, 0));
		});
		return physicsComponent;
	}

	private Color drawColor() {
		int randomNumber = random.nextInt(100);
		System.out.println(randomNumber);
		Color c = COLOR_ENEMY_PLANE;
		if (randomNumber <= CHANCE_TO_PICK_FRIEND)
			c = COLOR_FRIENDLY_PLANE;
		if (randomNumber > CHANCE_TO_PICK_FRIEND && randomNumber < CHANCE_TO_PICK_ENEMY_WITH_BONUS + CHANCE_TO_PICK_FRIEND)
			c = COLOR_ENEMY_PLANE_WITH_BONUS;
		return c;
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
		if (c.equals(Color.BLACK))
			return -1;
		else if (c.equals(Color.DARKGRAY))
			return 1;
		else if (c.equals(Color.GREY))
			return 2;
		else
			return 0;
	}

	private void setNextLayer() {
		if (lastLayer < LAYERS)
			lastLayer++;
		else
			lastLayer = 1;

	}

}