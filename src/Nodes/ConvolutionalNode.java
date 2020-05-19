package Nodes;

public class ConvolutionalNode extends Node{
double total;

	public ConvolutionalNode(ActivationFunction.TYPE type,int prevLayerSize,int thisLayerSize){
		super(type);
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
		double dTotal = activationFunction.derivative(total)*dOut;
		for (Node n : inputs) {
			dInputs.put(n, dTotal * weights.get(n).getWeight());
			weights.get(n).adjustWeight(dTotal * n.output);
		}
		biasWeight.adjustWeight(bias * dTotal);
	}

}
