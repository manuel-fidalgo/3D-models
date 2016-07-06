import java.util.Random;

import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.terrain.noise.Color;

/**
 * Creates and move the enemys
 * @author Manuel
 *
 */
public class EntityGenerator extends Thread{
	
	public boolean flag;
	public Node node;
	Material[] colors;
	Random rand;
	
	public EntityGenerator(Node node, Material[] colors) {
		this.flag = true;
		this.node = node;
		this.colors = colors;
		rand  = new Random();
	}
	
	@Override
	public void run(){
		while(flag){
			//Generation loop;
			try{ Thread.sleep(1); }catch(InterruptedException e){}
			node.attachChild(createNewEnemy());
			node.move(new Vector3f(0,0,-1));
		}
	}
	private Geometry createNewEnemy() {
		Sphere s = new Sphere(20,20,1f);
		Geometry geom = new Geometry("Sphere",s);
		geom.setMaterial(colors[0]);
		geom.move(getAleatVector());
		return geom;
	}
	//X delimitado entre 50 y -50
	//Y delimitado entre 50 y -50
	//Z delimitado entre -50 Y -70
	private Vector3f getAleatVector(){
		int x = rand.nextInt(101)-50;
		int y = rand.nextInt(101)-50;
		int z = rand.nextInt(21)-70;
		return new Vector3f(x,y,z);
	}
}
