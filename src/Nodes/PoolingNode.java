package Nodes;

import Nodes.ActivationFunction.TYPE;

public class PoolingNode extends Node {
	boolean isMax;

	public PoolingNode(TYPE type, boolean isMax) {
		super(type);
		this.isMax=isMax;
		// TODO Auto-generated constructor stub
		biasWeight = new Weight(false, 0.0);
	}

	Node used;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		used = inputs.get(0);
		for (Node n : inputs) {
			if (n.output > used.output) {
				used = n;
			}
		}
		output = used.output;
	}

	@Override
	public void backProp() {
		// TODO Auto-generated method stub
		double dOut = 0;
		for (Node n : outputs) {
			dOut += n.dInputs.get(this);
		}
		for (Node n : inputs) {
			if (n == used) {
				dInputs.put(n, dOut);
			} else {
				dInputs.put(n, 0.0);
			}
		}
	}

}
