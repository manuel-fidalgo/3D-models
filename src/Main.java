import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;

import de.lessvoid.nifty.input.mouse.MouseInputEventProcessor;



/** Basic jMonkeyEngine game template. */
public class Main extends SimpleApplication {

	Node playerNode;
	Node entitiesNode;
	
	EntityGenerator generator;
	
	Material blue;
	Material red;
	Material green;
	Material white;
	Material yellow;
	
	Material[] colors;

	private final static Trigger TRIGGER_COLOR = new KeyTrigger(MouseInput.BUTTON_LEFT);
	private final static String MAPPING_ROTATE = "Rotate";

	private ActionListener actionListener = new ActionListener() {
		public void onAction(String name, boolean isPressed, float tpf)
		{

		}
	};
	private AnalogListener analogListener = new AnalogListener() {
		public void onAnalog(String name, float intensity, float tpf) {
			
			if(name.equals(MAPPING_ROTATE)){
				CollisionResults results = new CollisionResults();
				Ray ray = new Ray(cam.getLocation(), cam.getDirection());
				rootNode.collideWith(ray, results);
				if (results.size() > 0) {
					Geometry target = results.getClosestCollision().getGeometry();
					target.setMaterial(getRandomColor());
				} else {
					System.out.println("Selection: Nothing" );
				}
			}
		}
	};

	@Override
	/** initialize the scene here */
	public void simpleInitApp() {
		
		System.out.println(cam.getLocation().toString());
		setUpInputManager();
		setUpColors(true);

		playerNode = new Node();
		entitiesNode = new Node();
		
		Sphere s = new Sphere(19, 19, 1f);
		Geometry g = new Geometry("Sphere",s);
		g.setMaterial(blue);
		playerNode.attachChild(g);


		rootNode.attachChild(entitiesNode);
		rootNode.attachChild(playerNode);
	}
	private Material getRandomColor(){
		return colors[(int) (Math.abs(Math.random()*100)%5)];
	}
	
	private void setUpInputManager() {
		inputManager.addMapping(MAPPING_ROTATE, TRIGGER_COLOR);
		inputManager.addListener(analogListener,new String[]{MAPPING_ROTATE});
		inputManager.addListener(actionListener,new String[]{MAPPING_ROTATE});
		
	}
	private void setUpColors(boolean wireframe) {
		blue = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		blue.setColor("Color", ColorRGBA.Blue);
		blue.getAdditionalRenderState().setWireframe(wireframe);
		
		red = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
		red.setColor("Color", ColorRGBA.Red);
		red.getAdditionalRenderState().setWireframe(wireframe);
		
		white = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		white.setColor("Color",ColorRGBA.White);
		white.getAdditionalRenderState().setWireframe(wireframe);
		
		yellow = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		yellow.setColor("Color",ColorRGBA.Yellow);
		yellow.getAdditionalRenderState().setWireframe(wireframe);
		
		green = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		green.setColor("Color",ColorRGBA.Green);
		green.getAdditionalRenderState().setWireframe(wireframe);
		
	}
	@Override
	/** (optional) Interact with update loop here */
	public void simpleUpdate(float tpf) {}
	@Override
	/** (optional) Advanced renderer/frameBuffer modifications */
	public void simpleRender(RenderManager rm) {}
	/** Start the jMonkeyEngine application */
	public static void main(String[] args) {
		AppSettings set = new AppSettings(true);
		set.setTitle("Just testing");
		Main app = new Main();
		app.setSettings(set);
		app.start();
	}
}