package Layers;

import Nodes.*;

public class InputLayer extends Layer{

	public InputLayer(int xsize, int ysize, int numChannels) {
		super(1, xsize, ysize, numChannels,Nodes.ActivationFunction.TYPE.Linear);
		// TODO Auto-generated constructor stub
		for(int f = 0; f< numFilters; f++){
			for(int x =0; x<xsize; x++){
				for(int y = 0; y<ysize; y++){
					for(int c = 0; c<numChannels; c++){
						nodes[f][x][y][c] = new InputNode(ActivationFunction.TYPE.Linear);
					}
				}
			}
		}
	}
	public void run(double[][][] inputs){
		for(int x= 0; x< inputs.length; x++){
			for(int y = 0; y< inputs[x].length;y++){
				for(int c = 0; c< inputs[x][y].length;c++){
					nodes[0][x][y][c].output=inputs[x][y][c];
				}
			}
		}
	}

	@Override
	public void addConnections(Layer previous) {
		// TODO Auto-generated method stub
		
	}

}
