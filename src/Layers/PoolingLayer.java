package Layers;

import Nodes.PoolingNode;
import Nodes.Weight;

public class PoolingLayer extends Layer{

	public int poolSize;
	public PoolingLayer(boolean isMax, int poolSize, Layer previous) {
		super(previous.numFilters, previous.xsize/poolSize, previous.ysize/poolSize, previous.numChannels,Nodes.ActivationFunction.TYPE.Linear);
		// TODO Auto-generated constructor stub
		this.poolSize=poolSize;
		for(int f = 0; f< numFilters; f++){
			for(int x =0; x<xsize; x++){
				for(int y = 0; y<ysize; y++){
					for(int c = 0; c<numChannels; c++){
						nodes[f][x][y][c] = new PoolingNode(Nodes.ActivationFunction.TYPE.Linear,isMax);
					}
				}
			}
		}
		addConnections(previous);
	}

	@Override
	public void addConnections(Layer previous) {
		// TODO Auto-generated method stub
		for(int f = 0; f< numFilters; f++){
			for(int x =0; x<xsize; x++){
				for(int y = 0; y<ysize; y++){
					for(int c = 0; c<numChannels; c++){
						int startingX = x*poolSize;
						int startingY = y*poolSize;
						for(int prevX = startingX; prevX<startingX+poolSize;prevX++){
							for(int prevY = startingY; prevY<startingY+poolSize;prevY++){
								nodes[f][x][y][c].addInput(previous.nodes[f][prevX][prevY][c],new Weight(false,1.0));
								previous.nodes[f][prevX][prevY][c].addOutput(nodes[f][x][y][c]);
							}
						}
						
					}
				}
			}
		}
	}

}
