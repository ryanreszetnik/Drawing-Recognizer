package Nodes;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Node {

	public Weight biasWeight;
	protected double bias =1;
	public double output;
	protected double total;
	
	protected ArrayList<Node> inputs = new ArrayList<>();
	protected HashMap<Node, Weight> weights = new HashMap<>();
	protected ArrayList<Node> outputs = new ArrayList<>();

	public HashMap<Node, Double> dInputs = new HashMap<>();
	
	protected ActivationFunction activationFunction;

	public Node(ActivationFunction.TYPE type){
		activationFunction = new ActivationFunction(type);
	}
	
	public void addInput(Node n, Weight w){
		inputs.add(n);
		weights.put(n, w);
		
	}
	public void addOutput(Node n){
		outputs.add(n);
	}
	public double getOutput(){
		return output;
	}
	public void adjustAllWeights(){
		for(Weight w:weights.values()){
			w.applyAdjustments();
		}
		biasWeight.applyAdjustments();
	}
	
	public abstract void run();
	public abstract void backProp();
	
	
}
