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
	
	public Plane(){
		this.currentAngle = 0;
	}
	
	@Override
	public int collideWith(Collidable arg0, CollisionResults arg1) throws UnsupportedCollisionException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void breadthFirstTraversal(SceneGraphVisitor arg0, Queue<Spatial> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void depthFirstTraversal(SceneGraphVisitor arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTriangleCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getVertexCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setModelBound(BoundingVolume arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateModelBound() {
		// TODO Auto-generated method stub
		
	}

}