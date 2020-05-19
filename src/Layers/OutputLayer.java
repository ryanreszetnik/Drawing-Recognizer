package Layers;

import Nodes.*;

public class OutputLayer extends Layer{
	int size;

	public OutputLayer(int size, Layer previous,Nodes.ActivationFunction.TYPE activation) {
		super(1, 1, 1, size,activation);
		// TODO Auto-generated constructor stub
		this.size=size;
		for(int i = 0; i < size; i++){
			nodes[0][0][0][i]=new OutputNode(activation,previous.getNumNodes(),size);
		}
		addConnections(previous);
	}

	@Override
	public void addConnections(Layer previous) {
		// TODO Auto-generated method stub
		for(Node[][][] node3: previous.nodes){
			for(Node[][] node2: node3){
				for(Node[] node1:node2){
					for(Node node: node1){
						for(int i = 0; i < nodes[0][0][0].length; i++){
							nodes[0][0][0][i].addInput(node,new Weight(true,activationFunction,previous.getNumNodes(),size));
							node.addOutput(nodes[0][0][0][i]);
						}
					}
				}
			}
		}
		
	}
	public double[] getOutput(){
		double[] output = new double[size];
		for(int i=0; i < nodes[0][0][0].length; i++){
			output[i] = nodes[0][0][0][i].getOutput();
		}
		return output;
	}
	public void setExpectedOut(double[] vals){
		for(int i = 0; i <nodes[0][0][0].length;i++){
			((OutputNode)nodes[0][0][0][i]).setExpectedOutput(vals[i]);
		}
	}
	public double Cost(){
		double cost = 0;
		for(int i = 0; i < nodes[0][0][0].length; i++){
			cost+=((OutputNode)nodes[0][0][0][i]).getCost();
		}
		return cost;
	}

}
