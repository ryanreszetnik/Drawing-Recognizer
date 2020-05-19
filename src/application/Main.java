package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Main extends Application {
	public static float px = 0;
	public static float py = 0;
	public static float psx = 0;
	public static float psy = 0;
	public static MnistMatrix[] mnistMatrix;
	public static MnistMatrix[] mnistMatrix2;
	static Network net;
	public static int trainingCounter = 0;
	public static double[][][] pixels = new double[28][28][1];
	Pane root;
	public static ArrayList<Line> lines = new ArrayList<>();
	Label guess = new Label("1");
	Button train = new Button("Train");
	TextField time = new TextField();
	public static int hours =0;
	public static int min = 0;
	public static int sec = 0;
	public static long end;
	public static Label totalTrained = new Label("0");

	@Override
	public void start(Stage primaryStage) {
		try {
			root = new Pane();
			Scene scene = new Scene(root, 350, 280);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Rectangle border = new Rectangle(280, 280);
			border.setFill(Color.BLACK);
			root.getChildren().add(border);
			guess.setFont(new Font("Arial", 30));
			guess.setTranslateX(300);
			guess.setTranslateY(100);
			train.setTranslateX(300);
			train.setTranslateY(230);
			train.setPrefSize(50, 50);
			time.setTranslateX(290);
			time.setTranslateY(150);
			time.setPrefWidth(50);
			totalTrained.setTranslateX(290);
			totalTrained.setTranslateY(50);
			root.getChildren().addAll(guess, train,time,totalTrained);
			setup();

			scene.setOnKeyPressed(e -> {
				switch (e.getCode()) {
				case R:
					clear();
					break;

				}
			});
			time.textProperty().addListener((observable, oldValue, newValue) -> {
				System.out.println("Changed from "+ oldValue +" to " +  newValue);
				String val = newValue;
				String temp ="";
				hours=0;
				min=0;
				sec=0;
				
				int counter = 0;
				for(int i= 0; i < val.length();i++){
					char c = val.charAt(i);
					if(c==':'){
						switch(counter){
						case 0:
							hours = Integer.valueOf(temp);
							break;
						case 1:
							min = Integer.valueOf(temp);
							break;
						case 2:
							
							break;
						}
						temp ="";
						counter++;
					}else{
						temp+=c;
					}
				}
				if(temp.length()>0)
					sec = Integer.valueOf(temp);
//				System.out.println(hours + ":" +min +":" + sec );
			});
			train.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
//					System.out.println(System.currentTimeMillis()/1000);
					end =System.currentTimeMillis()+(sec+min*60+hours*3600)*1000;
					Timestamp origional = new Timestamp(System.currentTimeMillis());
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(end);
					Timestamp later = new Timestamp(cal.getTime().getTime());
					System.out.println("Done at "+ later);
					train();
				}
				
			});

			root.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					px = (float) event.getSceneX();
					py = (float) event.getSceneY();
					psx = px / 10 + 300;
					psy = py / 10;
				}

			});

			root.setOnMouseDragged(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {

					float x = (float) mouseEvent.getSceneX();
					float y = (float) mouseEvent.getSceneY();
					float sx = x / 10 + 300;
					float sy = y / 10;
					if (x > 10 && x < 270 && y > 10 && y < 270) {
						Line l = new Line(px, py, x, y);
						Line l2 = new Line(psx, psy, sx, sy);
						// l.setFill(Color.WHITE);
						l.setStroke(Color.WHITE);
						l.setStrokeWidth(10);
						root.getChildren().add(l);
						root.getChildren().add(l2);
						lines.add(l);
						lines.add(l2);

						pixels[(int) (y / 10)][(int) (x / 10)][0] = 1;
						pixels[(int) (y / 10) + 1][(int) (x / 10)][0] = 1;
						pixels[(int) (y / 10) - 1][(int) (x / 10)][0] = 1;
						pixels[(int) (y / 10)][(int) (x / 10) + 1][0] = 1;
						pixels[(int) (y / 10)][(int) (x / 10) - 1][0] = 1;

					}
					psx = sx;
					psy = sy;
					px = x;
					py = y;

				}
			});
			root.setOnMouseReleased(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					double[] out = net.test(pixels);
					guess.setText(maxPos(out) + "");
				}

			});

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void remove(Line l) {
		root.getChildren().remove(l);
	}

	public void clear() {
		for (Line l : lines) {
			remove(l);
		}
		for (int i = 0; i < 28; i++) {
			for (int p = 0; p < 28; p++) {
				pixels[i][p][0] = 0;
			}
		}
	}

	public void print() {
		for (int i = 0; i < 28; i++) {
			for (int p = 0; p < 28; p++) {
				System.out.print("[" + pixels[p][i][0] + "]");
			}
			System.out.println();
		}
	}

	public void setup() {
		try {
			mnistMatrix = new MnistDataReader().readData(
					"/Users/rreszetnik/Documents/workspaceAI/Mnist/src/data/train-images.idx3-ubyte",
					"/Users/rreszetnik/Documents/workspaceAI/Mnist/src/data/train-labels.idx1-ubyte");
			mnistMatrix2 = new MnistDataReader().readData(
					"/Users/rreszetnik/Documents/workspaceAI/Mnist/src/data/t10k-images.idx3-ubyte",
					"/Users/rreszetnik/Documents/workspaceAI/Mnist/src/data/t10k-labels.idx1-ubyte");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		net = new Network(1, 0.01);
		net.addInputLayer(28, 28, 1);
		net.addConvolutionalLayer(24, 3, 0, Nodes.ActivationFunction.TYPE.ReLU);
		net.addPoolingLayer(true, 2);
		net.addDenseLayer(25, Nodes.ActivationFunction.TYPE.ReLU);
		net.addDenseLayer(16, Nodes.ActivationFunction.TYPE.SoftMax);
		net.addOutputLayer(10, Nodes.ActivationFunction.TYPE.SoftMax);
	}

	public static void train(int round) {

		double[][][] input = new double[28][28][1];
		double[] eOut;

		for (int i = 0; i < 28; i++) {
			for (int p = 0; p < 28; p++) {
				input[i][p][0] = mnistMatrix[round].getValue(i, p) / 255.0;
			}
		}
		if (round % 600 == 0) {
			System.out.print(".");
		}
		eOut = new double[10];
		eOut[mnistMatrix[round].getLabel()] = 1;
		net.train(input, eOut, round);
	}

	public static void test10k() {
		double[][][] input = new double[28][28][1];
		double[] eOut;
		int counter = 0;
		for (int round = 0; round < 10000; round++) {
			for (int i = 0; i < 28; i++) {
				for (int p = 0; p < 28; p++) {
					input[i][p][0] = mnistMatrix2[round].getValue(i, p) / 255.0;
				}
			}
			eOut = new double[10];
			eOut[mnistMatrix2[round].getLabel()] = 1;
			double[] output = net.test(input);
			if (maxPos(output) == mnistMatrix2[round].getLabel()) {
				counter++;
			}
		}

		System.out.println("Got: " + counter);
	}

	public static int maxPos(double[] vals) {
		int max = 0;
		double maxVal = vals[0];
		for (int i = 0; i < vals.length; i++) {
			if (vals[i] > maxVal) {
				max = i;
				maxVal = vals[i];
			}
		}
		return max;
	}

	public static void train() {
		while(System.currentTimeMillis()<end){
			train(trainingCounter % 60000);
			trainingCounter++;
		}
		totalTrained.setText(trainingCounter+"");
		System.out.println(" Done");

	}
}
