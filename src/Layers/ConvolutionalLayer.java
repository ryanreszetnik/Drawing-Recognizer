package Layers;

import Nodes.*;

public class ConvolutionalLayer extends Layer{

	
	int filterXSize;
	int filterYSize;
	int padding;
	Weight[][][] filters;
	
	public ConvolutionalLayer(int numFilter, int filterXSize, int filterYSize,int padding, Layer previous,Nodes.ActivationFunction.TYPE activation){
		super(numFilter*previous.numFilters, previous.xsize-filterXSize+1+2*padding, previous.ysize-filterYSize+1+2*padding, previous.numChannels,activation);
		// TODO Auto-generated constructor stub
		this.filterXSize=filterXSize;
		this.filterYSize=filterYSize;
		this.padding=padding;
		filters= new Weight[numFilters][filterXSize][filterYSize];
		for(int f = 0; f< numFilters; f++){
			for(int x =0; x<xsize; x++){
				for(int y = 0; y<ysize; y++){
					for(int c = 0; c<numChannels; c++){
						nodes[f][x][y][c] = new ConvolutionalNode(activation,previous.getNumNodes(),getNumNodes());
					}
				}
			}
			for(int xfil = 0; xfil<filterXSize;xfil++){
				for(int yfil = 0; yfil<filterYSize;yfil++){
					filters[f][xfil][yfil]=new Weight(true);
				}
			}
		}
		addConnections(previous);
	}

	@Override
	public void addConnections(Layer previous) {
		// TODO Auto-generated method stub
		for(int f = 0; f< numFilters; f++){
			for(int finalX =0; finalX<xsize; finalX++){
				for(int finalY = 0; finalY<ysize; finalY++){
					for(int c = 0; c<numChannels; c++){
						
						for(int x = finalX-padding; x<finalX-padding+filterXSize;x++){
							for(int y = finalY-padding; y<finalY-padding+filterYSize;y++){
								
								if(x>=0&&x<previous.xsize&&y>=0&&y<previous.ysize){
//									System.out.println((f*1.0)/numFilters*previous.numFilters +" ("+ x+ ", "+y +")>("+finalX+", "+finalY+") "+c +" through filter ("+(x-(finalX-padding)+", "+(y-(finalY-padding))+")"));
								nodes[f][finalX][finalY][c].addInput(previous.nodes[(int)((f*1.0)/numFilters*previous.numFilters)][x][y][c],filters[f][x-(finalX-padding)][y-(finalY-padding)]);
								previous.nodes[(int)((f*1.0)/numFilters*previous.numFilters)][x][y][c].addOutput(nodes[f][finalX][finalY][c]);
						
								}
							}
						}
						
					}
				}
			}
		}
	}
}
