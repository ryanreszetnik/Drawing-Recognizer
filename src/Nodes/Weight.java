package Nodes;


public class Weight {
	private double weight;
	public double dweight;
	private boolean canLearn;
	public static double learningRate;// = 0.01;// 0.01 for sigmoid 0.0000052
	public static int batchSize;

	public Weight(boolean canLearn,ActivationFunction.TYPE actFunc, int numInputs, int currentLayerSize) {
		this.canLearn = canLearn;
		if (actFunc == ActivationFunction.TYPE.Sigmoid) {
			weight = Math.random() * 2 - 1;
		} else if (actFunc == ActivationFunction.TYPE.ReLU) {
			double val = Math.sqrt(6.0 / (numInputs + currentLayerSize));
			weight = (Math.random() * 2 - 1) * val;
		} else if (actFunc == ActivationFunction.TYPE.SoftMax) {
			double val = Math.sqrt(6.0 / (numInputs + currentLayerSize));
			weight = (Math.random() * 2 - 1) * val;
		}

	}
	public Weight(boolean canLearn) {
		this.canLearn = canLearn;
		weight = Math.random() * 2 - 1;
	}
	public Weight(boolean canLearn, double weight){
		this.weight=weight;
		this.canLearn=canLearn;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public void adjustWeight(double change) {
		this.dweight += change;
		
	}
	public void applyAdjustments(){
		if (canLearn) {
			double before=weight;
			this.weight -= dweight * learningRate/batchSize;
//			System.out.println(before + " to " + weight +" by "+ dweight);
		}
		dweight=0;
	}

	public double getWeight() {
		return weight;
	}

}
