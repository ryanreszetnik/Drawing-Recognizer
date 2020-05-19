package Nodes;

public class ActivationFunction {

	public enum TYPE{
		ReLU,Sigmoid,SoftMax,Linear
	}
	public TYPE type;
	public ActivationFunction(TYPE t){
		this.type=t;
	}
	public double run(double in){
		switch(type){
		case ReLU:
			return Math.max(0,in);
		case Sigmoid:
			return 1.0 / (1.0 + Math.exp(-in));
		case SoftMax:
			return Math.log(1 + Math.exp(in));
		case Linear:
			return in;
		}
		return 0;
	}
	public double derivative(double in){
		switch(type){
		case ReLU:
			if (in > 0) {
				return 1;
			}
			return 0;
		case Sigmoid:
			double sig = run(in);
			return sig * (1 - sig);
		case SoftMax:
			return Math.exp(in) / (1.0 + Math.exp(in));
		case Linear:
			return 1;
		}
		return 0;
	}
	
	
}
