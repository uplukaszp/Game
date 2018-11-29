package pl.uplukaszp;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.gameplay.GameState;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.ui.Display;

import javafx.geometry.Point2D;
import pl.uplukaszp.scores.ScoreUtil;

public class ShootAction extends UserAction {
	String profileName = "";
	GameWorld gameWorld = FXGL.getGameWorld();
	GameState gameState = FXGL.getGameState();
	Display display = FXGL.getDisplay();
	Entity player = null;

	public ShootAction(String profileName) {
		super("Shoot");
		this.profileName = profileName;
	}

	@Override
	protected void onActionBegin() {
		loadPlayerEntity();
		spawnBullet();
		decrementBulletCounter();
		checkEndOfTheGame();
	}

	private void loadPlayerEntity() {
		if (player == null)
			player = gameWorld.getEntitiesByType(EntityType.player).get(0);

	}

	private void spawnBullet() {
		final int bulletOffset = 12;
		Point2D spawnPoin = new Point2D(player.getX(), player.getY() + bulletOffset);
		Vec2 initalVelocity = calculateVelocity();
		SpawnData spawnData = new SpawnData(spawnPoin);
		spawnData.put("velocity", initalVelocity);
		gameWorld.spawn("Bullet", spawnData);
	}

	private Vec2 calculateVelocity() {
		Vec2 velocity = new Vec2();
		velocity.y = (float) Math.cos(Math.toRadians(player.getRotation()));
		velocity.x = (float) Math.sin(Math.toRadians(player.getRotation()));
		velocity = velocity.mul(20);
		return velocity;
	}

	private void decrementBulletCounter() {
		gameState.setValue("bullets", gameState.getInt("bullets") - 1);

	}

	private void checkEndOfTheGame() {
		if (gameHasEnd()) {
			display.showMessageBox("Your score: " + gameState.getInt("score"), () -> {
				ScoreUtil.save(profileName, gameState.getInt("score").toString());
				/**
				 * The engine doesn't allow to programmatically return to main menu, so after
				 * save the Score, the game must be closed.
				 */
				FXGL.exit();
			});
		}
	}

	private boolean gameHasEnd() {
		return gameState.getInt("bullets") <= 0;
	}

}
