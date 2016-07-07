import java.util.ArrayList;
import java.util.Random;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;




/** Basic jMonkeyEngine game template. */
public class Main extends SimpleApplication {

	Node playerNode;
	Node entitiesNode;

	Material blue;
	Material red;
	Material green;
	Material white;
	Material yellow;

	Material[] colors;
	ArrayList<Geometry> entityContainer;

	Random rand;

	private static int counter=0;

	private final static Trigger TRIGGER_COLOR = new MouseButtonTrigger(MouseInput.BUTTON_LEFT);
	
	private final static Trigger TRIGGER_RIGHT = new KeyTrigger(KeyInput.KEY_D);
	private final static Trigger TRIGGER_LEFT = new KeyTrigger(KeyInput.KEY_A);

	
	private final static String MAPPING_ROTATE = "Rotate";
	
	private void setUpInputManager() {
		inputManager.addMapping(MAPPING_ROTATE, TRIGGER_COLOR);
		inputManager.addListener(analogListener,new String[]{MAPPING_ROTATE});
		inputManager.addListener(actionListener,new String[]{MAPPING_ROTATE});

	}

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

		initCrossHairs();
		setUpInputManager();
		setUpColors(true);

		playerNode = new Node();
		entitiesNode = new Node();
		rand = new Random();
		entityContainer = new ArrayList<Geometry>();

		rootNode.attachChild(entitiesNode);
		rootNode.attachChild(playerNode);
	}
	private void initCrossHairs() {
		guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
		BitmapText ch = new BitmapText(guiFont, false);
		ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
		ch.setText("+"); // crosshairs
		ch.setLocalTranslation( // center
				settings.getWidth() / 2 - ch.getLineWidth()/2, settings.getHeight() / 2 + ch.getLineHeight()/2, 0);
		guiNode.attachChild(ch);

	}
	private Material getRandomColor(){
		int aleat = (int) (Math.abs(Math.random()*100)%4)+1;
		return colors[aleat];
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
	private Geometry createNewEnemy() {
		Sphere s = new Sphere(10,10,1f);
		Geometry geom = new Geometry("Sphere",s);
		geom.setMaterial(colors[0]);
		geom.move(getAleatVector());
		return geom;
	}

	private Vector3f getAleatVector(){
		//X delimitado entre 50 y -50
		//Y delimitado entre 50 y -50
		//Z delimitado entre -300 Y -320
		int x = rand.nextInt(101)-50;
		int y = rand.nextInt(101)-50;
		int z = rand.nextInt(21)-320;
		return new Vector3f(x,y,z);
	}
	@Override
	/** (optional) Interact with update loop here */
	public void simpleUpdate(float tpf) {
		if(counter==10){		//Very ineficcient delay
			Geometry geom;
			geom = createNewEnemy();
			entityContainer.add(geom);
			entitiesNode.attachChild(geom);
			Quaternion q = new Quaternion();
			q.fromAngleAxis(1*FastMath.DEG_TO_RAD, Vector3f.UNIT_Z);
			for(Geometry i: entityContainer){
				i.move(new Vector3f(0,0,1));
				i.rotate(q);
			}
			counter=0;
		}else{
			counter++;
		}
	}
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