import java.awt.Color;

public class BrushSettings {

	private float size;
	private Color color;
	
	public BrushSettings() {
		this.size = 5.0f;
		this.color = Color.BLACK;
	}
	
	public float getSize() {
		return this.size;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void setSize(float a) {
		this.size = a;
	}
	
	public void setColor(Color a) {
		this.color = a;
	}
}
