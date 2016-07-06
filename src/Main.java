import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;




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

	private final static Trigger TRIGGER_COLOR = new KeyTrigger(KeyInput.KEY_SPACE);
	private final static String MAPPING_ROTATE = "Rotate";

	private ActionListener actionListener = new ActionListener() {
		public void onAction(String name, boolean isPressed, float tpf)
		{
			if(name.equals(MAPPING_ROTATE)){
				CollisionResults results = new CollisionResults();
				Ray ray = new Ray(cam.getLocation(), cam.getDirection());
				rootNode.collideWith(ray, results);
				if (results.size() > 0) {
					Geometry target = results.getClosestCollision().getGeometry();
					target.setMaterial(getRandomColor());
				} else {
					//System.out.println("Selection: Nothing" );
				}
			}
		}
	};
	private AnalogListener analogListener = new AnalogListener() {
		public void onAnalog(String name, float intensity, float tpf) {
			
		}
	};

	@Override
	/** initialize the scene here */
	public void simpleInitApp() {
		
		setUpInputManager();
		setUpColors(true);

		playerNode = new Node();
		entitiesNode = new Node();
		
		generator = new EntityGenerator(entitiesNode,colors);
		generator.start();
		
		rootNode.attachChild(entitiesNode);
		rootNode.attachChild(playerNode);
	}
	private Material getRandomColor(){
		int aleat = (int) (Math.abs(Math.random()*100)%5);
		return colors[aleat];
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
		
		colors = new Material[5];
		colors[0] = blue;
		colors[1] = red;
		colors[2] = white;
		colors[3] = yellow;
		colors[4] = green;
		
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