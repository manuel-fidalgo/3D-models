import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh.Type;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

public class ExplosionEmitter {
	
	ParticleEmitter explosion;
	ParticleEmitter debris;
	AssetManager assetManager;
	
	
	public ExplosionEmitter(AssetManager assetManager){
		this.assetManager = assetManager;
		setUpExplosion();
	}
	
	private void setUpExplosion() {

		explosion = new ParticleEmitter("Explosion",Type.Triangle, 30);
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
		mat.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/flame.png"));
		explosion.setMaterial(mat);
		explosion.setImagesX(2); 
		explosion.setImagesY(2);
		explosion.setEndColor(new ColorRGBA(1f, 0f, 0f, 1f));   // red
		explosion.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
		explosion.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
		explosion.setStartSize(1.5f);
		explosion.setEndSize(0.1f);
		explosion.setGravity(0, 0, 0);
		explosion.setLowLife(1f);
		explosion.setHighLife(3f);
		explosion.getParticleInfluencer().setVelocityVariation(0.3f);
		debris = new ParticleEmitter("Debris",Type.Triangle, 10);
		Material debris_mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
		debris_mat.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/Debris.png"));
		debris.setMaterial(debris_mat);
		debris.setImagesX(3); 
		debris.setImagesY(3); // 3x3 texture animation
		debris.setSelectRandomImage(true);
		

	}
	public void explode(Vector3f vector3f){
		
		this.debris.setLocalTranslation(vector3f);
		this.explosion.setLocalTranslation(vector3f);
		
		this.debris.emitAllParticles();
		this.explosion.emitAllParticles();
	}
	
	public ParticleEmitter getFire(){
		return this.explosion;
	}
	public ParticleEmitter getDebris(){
		return this.debris;
	}
}
