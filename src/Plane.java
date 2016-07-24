import java.util.Queue;

import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.collision.UnsupportedCollisionException;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;

public class Plane extends Spatial{
	
	private int currentAngle;
	private int currentDirection;
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	
	Chrono explosionchrono;
	static int EXPLOSION_DELAY = 100; //100 miliseconds
	
	public Plane(){
		this.currentAngle = 0;
		this.explosionchrono = new Chrono(EXPLOSION_DELAY);
	}
	
	public void explode(){
		
	}
	
	@Override
	public int collideWith(Collidable arg0, CollisionResults arg1) throws UnsupportedCollisionException {
		return 0;
	}

	@Override
	protected void breadthFirstTraversal(SceneGraphVisitor arg0, Queue<Spatial> arg1) {
		
	}

	@Override
	public void depthFirstTraversal(SceneGraphVisitor arg0) {
		
	}

	@Override
	public int getTriangleCount() {
		return 0;
	}

	@Override
	public int getVertexCount() {
		return 0;
	}

	@Override
	public void setModelBound(BoundingVolume arg0) {
		
	}

	@Override
	public void updateModelBound() {
		
	}

}
