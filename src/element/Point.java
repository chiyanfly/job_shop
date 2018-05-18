package element;

import org.antlr.v4.runtime.atn.SemanticContext.PrecedencePredicate;

public class Point {

	private String label;
	private int value;

	private int machine;
	private int machinecost;

	public Point(String l, int v, int m, int mc) {
		// TODO Auto-generated constructor stub
		label = l;
		value = v;
		machine = m;
		machinecost = mc;

	}

	public int getMachine() {
		return machine;
	}

	public void setMachine(int machine) {
		this.machine = machine;
	}

	public int getMachinecost() {
		return machinecost;
	}

	public void setMachinecost(int machinecost) {
		this.machinecost = machinecost;
	}

	public String getLabel() {
		return label;
	}

	public int getValue() {
		return value;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setValue(int value) {
		this.value = value;
	}

}