/**
 * @author Manuel
 */
public class Chrono {
	
	
	public long DELAY;
	public long oldTime;
	public long newTime;
	
	public Chrono(long del){
		this.DELAY = del;
		this.oldTime = getMills();
	}
	public boolean isDelay(){
		this.newTime = System.currentTimeMillis();
		if(newTime - oldTime >= DELAY){
			oldTime = newTime;
			return true;
		}else{
			return false;
		}
	}
	public static long getMills(){
		return System.currentTimeMillis();
	}
	public void changeDelay(int newDel){
		this.DELAY = newDel;
	}
	public long getDelay(){
		return this.DELAY;
	}
}