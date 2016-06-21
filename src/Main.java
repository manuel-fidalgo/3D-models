import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;



/** Basic jMonkeyEngine game template. */
public class Main extends SimpleApplication {

	Node playerNode;
	Material mat_terrain;

	private final static Trigger TRIGGER_COLOR = new KeyTrigger(KeyInput.KEY_SPACE);
	private final static String MAPPING_ROTATE = "Rotate";

	private ActionListener actionListener = new ActionListener() {
		public void onAction(String name, boolean isPressed, float tpf)
		{
			playerNode.move(cam.getDirection());
		}
	};
	private AnalogListener analogListener = new AnalogListener() {
		public void onAnalog(String name, float intensity, float tpf) {

		}
	};

	@Override
	/** initialize the scene here */
	public void simpleInitApp() {

		cam.setLocation(new Vector3f(0, 5, -10));
		inputManager.addMapping(MAPPING_ROTATE, TRIGGER_COLOR);
		inputManager.addListener(analogListener,new String[]{MAPPING_ROTATE});
		inputManager.addListener(actionListener,new String[]{MAPPING_ROTATE});

		setDisplayFps(false);
		setDisplayStatView(false);

		//Creamos tres nodos o grupos de elementos
		playerNode = new Node();
		Node towerNode = new Node();
		Node creepNode = new Node();
		Node floorNode = new Node();


		//Creamos los materiales para los elementos
		Material blue = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		blue.setColor("Color", ColorRGBA.Blue);
		Material red = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
		red.setColor("Color", ColorRGBA.Red);
		red.getAdditionalRenderState().setWireframe(true);//Para sacar lo mesh
		Material white = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		white.setColor("Color",ColorRGBA.White);
		Material yellow = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		yellow.setColor("Color",ColorRGBA.Yellow);
		Material green = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		green.setColor("Color",ColorRGBA.Green);

		//
		Box floor = new Box(new Vector3f(20, 0, 20),new Vector3f(-20, -1, -20));
		Geometry floorGeom = new Geometry("box",floor);
		floorGeom.setMaterial(white);

		Box player = new Box(new Vector3f(-1,0,-1), new Vector3f(+1,2,+1));
		Geometry playerGeom = new Geometry("box",player);
		playerGeom.setMaterial(yellow);

		Box[] towers = new Box[6];
		Geometry[] towersGeom = new Geometry[6];
		for (int i = 0; i < towers.length; i++) {
			if(i%2==0){
				towers[i] = new Box(new Vector3f(-4,0,(i+1)*2), new Vector3f(-3,3,((i+1)*2)+1));		//En la derecha
			}else{
				towers[i] = new Box(new Vector3f(+4,0,(i+1)*2), new Vector3f(3,3,((i+1)*2)+1));		//En la izaquierda
			}
			towersGeom[i] = new Geometry("box",towers[i]);
			towersGeom[i].setMaterial(green);
			towerNode.attachChild(towersGeom[i]);
		}
		playerNode.attachChild(playerGeom);
		floorNode.attachChild(floorGeom);
		Geometry sp = new Geometry("box",new Sphere(16, 16, 1.0f));
		sp.move(new Vector3f(0,4,0));
		sp.setMaterial(red);
		towerNode.attachChild(sp);

		DirectionalLight sun = new DirectionalLight();
		sun.setDirection(new Vector3f(0f,6f,0f));
		sun.setColor(ColorRGBA.White);
		rootNode.addLight(sun);

		//Añadimos los nodos creados al root
		rootNode.attachChild(floorNode);
		rootNode.attachChild(creepNode);
		rootNode.attachChild(towerNode);
		rootNode.attachChild(playerNode);
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