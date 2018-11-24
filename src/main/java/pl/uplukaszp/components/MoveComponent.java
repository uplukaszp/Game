package pl.uplukaszp.components;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.component.Component;

import javafx.geometry.Point2D;

public class MoveComponent extends Component {
	private Vec2 velocity;

	public MoveComponent(Vec2 velocity) {
		this.velocity = velocity;
	}

	@Override
	public void onUpdate(double tpf) {
		Point2D position = entity.getPosition();
		position = position.add(velocity.toPoint2D());
		entity.setPosition(position);
	}
}
