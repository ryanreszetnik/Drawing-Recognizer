package Nodes;

import Nodes.ActivationFunction.TYPE;

public class DenseNode extends Node{

	public DenseNode(TYPE type, int prevLayerSize, int thisLayerSize) {
		super(type);
		// TODO Auto-generated constructor stub
		biasWeight= new Weight(true,type, prevLayerSize, thisLayerSize);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		total = 0;
		for (Node n : inputs) {
			total += n.output * weights.get(n).getWeight();
		}
		total += bias * biasWeight.getWeight();
		output = activationFunction.run(total);
	}

	@Override
	public void backProp() {
		// TODO Auto-generated method stub
		double dOut = 0;
		for (Node n : outputs) {
			dOut += n.dInputs.get(this);
		}
//		System.out.println("dOut" + dOut);
		double dTotal = activationFunction.derivative(total)*dOut;
		for (Node n : inputs) {
			dInputs.put(n, dTotal * weights.get(n).getWeight());
			double before = weights.get(n).dweight;
			weights.get(n).adjustWeight(dTotal * n.output);
//			System.out.println("Weight changed" + before + " to "+ weights.get(n).dweight + " "+ n.output + " "+ dTotal);
		}
		biasWeight.adjustWeight(bias * dTotal);
	}

}
