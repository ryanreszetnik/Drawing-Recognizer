package application;
import java.util.ArrayList;
import java.util.Arrays;

import Layers.*;
import Nodes.*;

public class Network {

	ArrayList<Layer> layers= new ArrayList<>();
	public Network(int batchSize,double learningRate){
		Weight.learningRate=learningRate;
		Weight.batchSize=batchSize;
	}
	
	public void train(double[][][] input, double[] eOut, int round){
		((InputLayer)(layers.get(0))).run(input);
		((OutputLayer)(layers.get(layers.size()-1))).setExpectedOut(eOut);
		for(Layer l:layers){
			l.run();
//			System.out.println(l);
		}
		double[] out = ((OutputLayer)(layers.get(layers.size()-1))).getOutput();
//		System.out.println("Expected: " +Main.maxPos(eOut) +" Returned: " + Main.maxPos(out) + " " + Arrays.toString(out) );
		for(int i = layers.size()-1; i>=0; i--){
			layers.get(i).backPropagate();
		}
//		double cost1 = ((OutputLayer)(layers.get(layers.size()-1))).Cost();
		
		if(round%Weight.batchSize==0){
			for(Layer l:layers){
				l.applyAllAdjustments();
			}
//			double out2[] =test(input);
//			double cost2 = ((OutputLayer)(layers.get(layers.size()-1))).Cost();
//			System.out.println("Tested " + (cost1-cost2) +Arrays.toString(out2));
			
		}
		
		
	}
	public double[] test(double[][][] input){
		((InputLayer)layers.get(0)).run(input);
		for(int i = 1; i < layers.size(); i++){
			layers.get(i).run();
		}
		return ((OutputLayer)layers.get(layers.size()-1)).getOutput();
	}

	public void addInputLayer(int xsize, int ysize, int channels){
		Layer l = new InputLayer(xsize, ysize, channels);
		layers.add(l);
	}
	public void addConvolutionalLayer(int numFilter, int filterSize,int padding,Nodes.ActivationFunction.TYPE activation){
		Layer l = new ConvolutionalLayer(numFilter, filterSize, filterSize,padding,layers.get(layers.size()-1),activation);
		layers.add(l);
	}
	public void addPoolingLayer(boolean isMax, int poolSize){
		Layer l = new PoolingLayer(isMax, poolSize, layers.get(layers.size()-1));
		layers.add(l);
	}

	public void addDenseLayer(int size,Nodes.ActivationFunction.TYPE activation){
		Layer l= new DenseLayer(size,layers.get(layers.size()-1),activation);
		layers.add(l);
	}
	public void addOutputLayer(int size,Nodes.ActivationFunction.TYPE activation){
		Layer l = new OutputLayer(size,layers.get(layers.size()-1),activation);
		layers.add(l);
	}
	public String toString(){
		String out = "";
		for(Layer l: layers){
			out+= "["+l.getNumNodes() +"]";
		}
		return out;
	}
	
	
}
