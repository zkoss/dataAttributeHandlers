package org.zkoss.handlers.ArborJSTree.model;

public class ArborJSNode{
	private String name;
	private float mass;
	private Boolean fixed;
	private String label;
	private String color;
	private String shape;
		
	public ArborJSNode(String name) {
		super();
		this.name = name;
	}
	
	public ArborJSNode(String name, float mass, Boolean fixed, String label, String color, String shape) {
		super();
		this.name = name;
		this.mass = mass;
		this.fixed = fixed;
		this.label = label;
		this.color = color;
		this.shape = shape;
	}
	public ArborJSNode(String name, String label) {
		super();
		this.name = name;
		this.label = label;
	}

	/** Get the node mass. the node’s resistance to movement and repulsive power.   */
	public float getMass() {
		return mass;
	}
	/** Set the node mass. the node’s resistance to movement and repulsive power. */
	public void setMass(float mass) {
		this.mass = mass;
	}
	/** Get the node fixed status. A fixed node cannot move.   */
	public Boolean getFixed() {
		return fixed;
	}
	/** Set the node fixed status. A fixed node cannot move.  */
	public void setFixed(Boolean fixed) {
		this.fixed = fixed;
	}
	/** Get the node label. The text of the node.   */
	public String getLabel() {
		return label;
	}
	/** Set the node label. The text of the node. */
	public void setLabel(String label) {
		this.label = label;
	}
	/** Get the node color. Accepts any valid css color such as red, or #00FF00;   */
	public String getColor() {
		return color;
	}
	/** Set the node color. Accepts any valid css color such as red, or #00FF00;   */
	public void setColor(String color) {
		this.color = color;
	}
	/** Get the node shape. can be box or dot   */
	public String getShape() {
		return shape;
	}
	/** Get the node mass. the node’s resistance to movement and repulsive power.   */
	public void setShape(String shape) {
		this.shape = shape;
	}
	/** Get the node mass. the node’s resistance to movement and repulsive power.   */
	public String getName() {
		return name;
	}
	
	
}
