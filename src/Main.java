import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh.Type;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
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
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import com.jme3.util.SkyFactory;
import com.jme3.util.SkyFactory.EnvMapType;




/** Basic jMonkeyEngine game template. */
public class Main extends SimpleApplication {

	Node skyNode;
	Node entitiesNode;

	Material blue;
	Material red;
	Material green;
	Material white;
	Material yellow;

	Material[] colors;
	ArrayList<Spatial> entityContainer;

	ExplosionEmitter expl;
	

	Random rand;
	Quaternion q;
	Chrono creationChrono, movementChrono;

	public static long MOVEMENT_DELAY = 10; 	//0,01 seconds
	public static long CREATION_DELAY = 3000; 	//3 second

	private final static Trigger TRIGGER_COLOR = new MouseButtonTrigger(MouseInput.BUTTON_LEFT);

	private final static Trigger TRIGGER_RIGHT = new KeyTrigger(KeyInput.KEY_D);
	private final static Trigger TRIGGER_LEFT = new KeyTrigger(KeyInput.KEY_A);


	private final static String MAPPING_ROTATE = "Rotate";
	private final static String MAPPING_LEFT = "Left";
	private final static String MAPPING_RIGHT = "Right";

	private void setUpInputManager() {
		inputManager.addMapping(MAPPING_ROTATE, TRIGGER_COLOR);
		inputManager.addMapping(MAPPING_LEFT, TRIGGER_LEFT);
		inputManager.addMapping(MAPPING_RIGHT, TRIGGER_RIGHT);

		inputManager.addListener(analogListener,new String[]{MAPPING_ROTATE,MAPPING_RIGHT,MAPPING_LEFT});
		inputManager.addListener(actionListener,new String[]{MAPPING_ROTATE,MAPPING_RIGHT,MAPPING_LEFT});

	}

	private ActionListener actionListener = new ActionListener() {
		public void onAction(String name, boolean isPressed, float tpf)
		{
			if(name.equals(MAPPING_ROTATE)){
				CollisionResults results = new CollisionResults();
				Ray ray = new Ray(cam.getLocation(), cam.getDirection());
				entitiesNode.collideWith(ray, results);
				if (results.size() > 0) {
					Geometry target = results.getClosestCollision().getGeometry();
					rootNode.attachChild(expl.getDebris());
					rootNode.attachChild(expl.getFire());
					expl.explode(target.getLocalTranslation());
					target.removeFromParent();

				} else {
					//System.out.println("Selection: Nothing" );
				}
			}
			//			Vector3f cam_loc = cam.getLocation();
			if(name.equals(MAPPING_RIGHT)){
				
			}
			if(name.equals(MAPPING_LEFT)){

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
		setDisplayFps(false);
		setDisplayStatView(false);
		
		skyNode = new Node();
		entitiesNode = new Node();
		rand = new Random();
		entityContainer = new ArrayList<Spatial>();
		q = new Quaternion();
		creationChrono = new Chrono(CREATION_DELAY);
		movementChrono = new Chrono(MOVEMENT_DELAY);
		expl = new ExplosionEmitter(assetManager);

		Spatial sky = SkyFactory.createSky(assetManager, "sky_1.png" ,EnvMapType.EquirectMap);
		skyNode.attachChild(sky);
		
		DirectionalLight sun = new DirectionalLight();
		sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f).normalizeLocal());

		rootNode.addLight(sun);
		rootNode.attachChild(entitiesNode);
		rootNode.attachChild(skyNode);
		
	}
	
	private void initCrossHairs() {
		guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
		BitmapText ch = new BitmapText(guiFont, false);
		ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
		ch.setText("+");
		ch.setLocalTranslation(settings.getWidth() / 2 - ch.getLineWidth()/2, settings.getHeight() / 2 + ch.getLineHeight()/2, 0);
		guiNode.attachChild(ch);
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
	private Spatial createNewEnemy() {
		Spatial spat = assetManager.loadModel("B-747.obj");
		spat.scale(0.30f);
		spat.move(getAleatVector());
		return spat;
	}

	private Vector3f getAleatVector(){
		int x = rand.nextInt(81)-40; //between 40 and -40
		int y = rand.nextInt(81)-40; //between 40 and -40
		int z = rand.nextInt(21)-320; //between 300 and -320
		return new Vector3f(x,y,z);

	}
	@Override
	/** (optional) Interact with update loop here */
	public void simpleUpdate(float tpf) {
		Spatial spat;

		if(creationChrono.isDelay()){
			spat = createNewEnemy();
			entityContainer.add(spat);
			entitiesNode.attachChild(spat);
		}

		if(movementChrono.isDelay()){
			q.fromAngleAxis(1*FastMath.DEG_TO_RAD, Vector3f.UNIT_Z);
			for(Spatial i: entityContainer){
				i.move(new Vector3f(0,0,1));
				if(rand.nextInt(100)<=10) i.rotate(q);
			}
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
		//app.setShowSettings(false); //No muestra los ajustes
		app.setSettings(set);
		app.start();
	}
}