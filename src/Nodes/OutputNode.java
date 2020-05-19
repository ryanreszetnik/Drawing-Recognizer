package Nodes;

import Nodes.ActivationFunction.TYPE;

public class OutputNode extends Node{

	double eOut;
	
	public OutputNode(TYPE type, int prevLayerSize, int thisLayerSize) {
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
		double dOut = (output - eOut);
//		System.out.println("BackProp" + inputs.size());
		double dTotal = activationFunction.derivative(total)*dOut;
		for (Node n : inputs) {
			dInputs.put(n, dTotal * weights.get(n).getWeight());
			weights.get(n).adjustWeight(dTotal * n.output);
//			System.out.println(dTotal * n.output);
		}
		biasWeight.adjustWeight(bias * dTotal);
		
	}
	public void setExpectedOutput(double eOut){
		this.eOut=eOut;
	}
	public double getCost(){
		return output-eOut;
	}

}
