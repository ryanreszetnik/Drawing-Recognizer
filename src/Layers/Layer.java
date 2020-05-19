package Layers;

import Nodes.*;

public abstract class Layer {

	public int numFilters;
	public int xsize;
	public int ysize;
	public int numChannels;
	Node[][][][] nodes;//[filter][x][y][channel]
	public Nodes.ActivationFunction.TYPE activationFunction;
	
	public Layer(int numFilters, int xsize, int ysize, int numChannels, Nodes.ActivationFunction.TYPE activation){
		nodes = new Node[numFilters][xsize][ysize][numChannels];
		this.numChannels=numChannels;
		this.numFilters=numFilters;
		this.xsize=xsize;
		this.ysize=ysize;
		activationFunction = activation;
	}
	public void backPropagate(){
		for(Node[][][] nodes3: nodes){
			for(Node[][] nodes2: nodes3){
				for(Node[] nodes1: nodes2){
					for(Node node: nodes1){
						node.backProp();
					}
				}
			}
		}
	}
	public void run(){
		for(Node[][][] nodes3: nodes){
			for(Node[][] nodes2: nodes3){
				for(Node[] nodes1: nodes2){
					for(Node node: nodes1){
						node.run();
					}
				}
			}
		}
	}
	public int getNumNodes(){
		return numFilters*xsize*ysize*numChannels;
	}
	public void applyAllAdjustments(){
		for(Node[][][] nodes3: nodes){
			for(Node[][] nodes2: nodes3){
				for(Node[] nodes1: nodes2){
					for(Node node: nodes1){
						node.adjustAllWeights();
					}
				}
			}
		}
	}
	public String toString(){
		String output = "Print Layer: ";
		for(Node[][][] nodes3: nodes){
			for(Node[][] nodes2: nodes3){
				for(Node[] nodes1: nodes2){
					for(Node node: nodes1){
						output+="["+node.output+"]";
					}
				}
			}
		}
		return output;
	}
	
	public abstract void addConnections(Layer previous);
	
}
