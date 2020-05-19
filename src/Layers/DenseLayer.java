package Layers;

import Nodes.*;


public class DenseLayer extends Layer{

	public DenseLayer(int size, Layer previous,Nodes.ActivationFunction.TYPE activation) {
		super(1, 1, 1, size,activation);
		// TODO Auto-generated constructor stub
		for(int i = 0; i < size; i++){
			nodes[0][0][0][i]=new DenseNode(activation,previous.getNumNodes(),size);
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
						for(Node[][][] node23: nodes){
							for(Node[][] node22: node23){
								for(Node[] node21:node22){
									for(Node node20: node21){
										node20.addInput(node, new Weight(true,activationFunction,previous.getNumNodes(),getNumNodes()));
										node.addOutput(node20);
									}
								}
							}
						}
					}
				}
			}
		}
	}

}
