package Nodes;

import Nodes.ActivationFunction.TYPE;

public class InputNode extends Node{

	public InputNode(TYPE type) {
		super(type);
		// TODO Auto-generated constructor stub
		biasWeight= new Weight(false,0);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void backProp() {
		// TODO Auto-generated method stub
		
	}

}
