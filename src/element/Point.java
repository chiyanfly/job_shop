package element;

import java.util.ArrayList;

import org.antlr.v4.runtime.atn.SemanticContext.PrecedencePredicate;

public class Point {

	private String label;
	private int value;

	private int machine;
	private int machinecost;
	private ArrayList<Integer> list_possible_machine;

	public Point(String l, int v, int m, int mc,ArrayList<Integer> list) {
		// TODO Auto-generated constructor stub
		label = l;
		value = v;
		machine = m;
		machinecost = mc;
		list_possible_machine= list;

	}

	
	public ArrayList<Integer> getList_possible_machine() {
		return list_possible_machine;
	}
	
	public void setList_possible_machine(ArrayList<Integer> list_possible_machine) {
		this.list_possible_machine = list_possible_machine;
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