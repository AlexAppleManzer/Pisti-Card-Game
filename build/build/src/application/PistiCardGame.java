package application;
	
import java.util.Scanner;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class PistiCardGame extends Application {
	static Player player1;
	static Player player2;
	static Deck center;
	static Card centerCard;
	static int lastCapture;
	static ImageView centerCardg;
	static HBox bottom;
	static HBox top;
	static BorderPane root;
	static Pane right;
	@Override
	public void start(Stage primaryStage) {
		
		Deck deck = new Deck();
		deck.resetDeck();
		do {
			deck.shuffle();
		} while(deck.getCard(deck.getAmount() - 1).getNum() == 11);
		
		right = new Pane();
		right.setPrefWidth(300);
		right.setPrefHeight(900);
		
		Button btn1 = new Button("Restart");
		btn1.setPrefSize(150,50);
		btn1.setOnAction((ActionEvent e) -> {
			System.out.println("resetting");
			lastCapture = 0;
			Deck deck1 = new Deck();
			deck1.resetDeck();
			do {
				deck1.shuffle();
			} while(deck1.getCard(deck1.getAmount() - 1).getNum() == 11);
			center = deck1.drawDeck(4);
			updateCenter();
			player1.setHand(deck.drawDeck(4));
			player2.setHand(deck.drawDeck(4));
			player1.capture = new Deck();
			player2.capture = new Deck();
			player1.calculatePoints(1);
			player2.calculatePoints(1);
			top = makeBotHand(4);
			initHand(player1.getHand());
			root.setBottom(bottom);
			root.setCenter(centerCardg);
			root.setTop(top);
			//System.out.println("Player1 capture: " + player1.capture.toString());
			//System.out.println("Player2 capture: " + player2.capture.toString());
		});
		btn1.setLayoutY(180);
		Button btn2 = new Button("Close");
		btn2.setPrefSize(150,50);
		btn2.setOnAction((ActionEvent e) -> {
		    primaryStage.close();
		});
		btn2.setLayoutY(180);
		btn2.setLayoutX(150);
		
		right.getChildren().addAll(btn1, btn2);
		player1 = new Player();
		player2 = new Player();
		
		player2.points.setX(65);
		player2.points.setY(120);
		
		player1.points.setX(65);
		player1.points.setY(390);
		
		right.getChildren().addAll(player1.points, player2.points);
		
		center = deck.drawDeck(4);
		player1.setHand(deck.drawDeck(4));
		player2.setHand(deck.drawDeck(4));
		top = makeBotHand(4);
		initHand(player1.getHand());
		
		BackgroundImage background = new BackgroundImage(new Image("cards2/Background.png"),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		
		root = new BorderPane();
		root.setBottom(bottom);
		root.setRight(right);
		root.setCenter(centerCardg);
		
		root.setTop(top);
		root.setBackground(new Background(background));
		Scene scene = new Scene(root,1440,900);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Pisti Card Game");
		primaryStage.getIcons().add(new Image("cards2/aces.png"));
		primaryStage.show();

	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void initHand(Deck hand) {
		HBox result = new HBox();
		ImageView card1 = PistiCardGame.makeCardImage(hand.getCard(0), 1);
		ImageView card2 = PistiCardGame.makeCardImage(hand.getCard(1), 1);
		ImageView card3 = PistiCardGame.makeCardImage(hand.getCard(2), 1);
		ImageView card4 = PistiCardGame.makeCardImage(hand.getCard(3), 1);
		result.getChildren().addAll(card1, card2, card3,card4);
		result.setSpacing(30);
		result.setPadding(new Insets(0, 0, 0, 275));
		bottom = result;
		
		centerCardg = makeCardImage(center.getCard(center.getAmount() - 1), 0);
		
	}
	
	public static void playCard(Card card) {
		try {
		Card cardPlayed = null;
		//System.out.println(card.toString());
		//System.out.println(player1.getHand().toString());
		//System.out.println(player2.getHand().toString());
		for(int i = 0; i < player1.getHand().getAmount(); i++) {
			//System.out.println(i);
			if(player1.getHand().getCard(i).equals(card))
				cardPlayed = player1.playCard(i);
		}
		if(cardPlayed != null) {
		if(center.getAmount() >= 1) {
			if(center.getCard(center.getAmount() - 1).getNum() == cardPlayed.getNum() || cardPlayed.getNum() == 11) {
				System.out.println("Its a match!");
				lastCapture = 1;
				if(center.getAmount() == 1) {
					
					player1.pisti++;
					System.out.println("Pisti Scored!");
					if (center.getCard(center.getAmount() - 1).getNum() == 11) {
						player1.pisti++;
						System.out.println("Double Pisti!");
					}
				}
				
				player1.capture.addDeck(center);
				player1.capture.addCard(cardPlayed);
				//System.out.println("Capture: " + player1.capture.toString());
				player1.calculatePoints(player2.capture.getAmount());
				player2.calculatePoints(player1.capture.getAmount());
				center = new Deck();
			}
			else	{
				center.addCard(cardPlayed);
				updateCenter();
			}
		}
		else {
			center.addCard(cardPlayed);
			updateCenter();
		}
		
		boolean played = false;
		
		//System.out.println("center amount " + center.getAmount());
		if(center.getAmount() > 1) {
			//System.out.println("1");
			for(int i = 0; i < player2.getHand().getAmount(); i++) {
				//System.out.println("Attempt " + i + ": ");
				//System.out.println(player2.getHand().getCard(i).toString());
				//System.out.println(center.getCard(center.getAmount() - 1).toString());
				if(played == false && player2.getHand().getCard(i).getNum() == center.getCard(center.getAmount() - 1).getNum()) {
					match(player2.getHand().getCard(i));
					played = true;
				}
			}
			for(int i = 0; i < player2.getHand().getAmount(); i++) {
				//System.out.println("Attempt " + i + ": ");
				//System.out.println(player2.getHand().getCard(i).toString());
				//System.out.println(center.getCard(center.getAmount() - 1).toString());
				if(played == false && player2.getHand().getCard(i).getNum() == 11) {
					match(player2.getHand().getCard(i));
					played = true;
				}
			}
		}
		if(played == false) {
			center.addCard(player2.hand.draw());
			updateCenter();
			//System.out.println("Darn");
		}
		
		top = makeBotHand(player2.getHand().getAmount());
		root.setTop(top);
		}
		else
			throw new ArrayIndexOutOfBoundsException();
		
		//System.out.println("player2 card amount: " + player2.getHand().getAmount());
		//System.out.println(player1.getHand().toString());
		if(player2.getHand().getAmount() == 0) {
			if(lastCapture == 1) {
				player1.capture.addDeck(center);
				player1.calculatePoints(player2.capture.getAmount());
				player2.calculatePoints(player1.capture.getAmount());
			}
			else if (lastCapture == 2) {
				player2.capture.addDeck(center);
				player2.calculatePoints(player1.capture.getAmount());
				player1.calculatePoints(player2.capture.getAmount());
			}
			if(player1.calculatePoints(player2.capture.getAmount()) < 101 && player2.calculatePoints(player1.capture.getAmount()) < 101) {
				System.out.println("resetting");
				lastCapture = 0;
				Deck deck = new Deck();
				deck.resetDeck();
				do {
					deck.shuffle();
				} while(deck.getCard(deck.getAmount() - 1).getNum() == 11);
				center = deck.drawDeck(4);
				updateCenter();
				player1.setHand(deck.drawDeck(4));
				player2.setHand(deck.drawDeck(4));
				top = makeBotHand(4);
				initHand(player1.getHand());
				root.setBottom(bottom);
				root.setCenter(centerCardg);
				root.setTop(top);
				//System.out.println("Player1 capture: " + player1.capture.toString());
				//System.out.println("Player2 capture: " + player2.capture.toString());
			}
			else {
				if(player1.calculatePoints(player2.capture.getAmount()) < player2.calculatePoints(player1.capture.getAmount())) {
					Text victoryText = new Text("You Lose!");
					victoryText.setFont(new Font(170));
					root.setCenter(victoryText);
				}
				else {
					Text victoryText = new Text("You Win!");
					victoryText.setFont(new Font(170));
					root.setCenter(victoryText);
				}
			}
		}
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Something went wrong! Closing...");
			System.out.println("Player 1 hand: ");
			System.out.println(player1.hand.toString());
			System.out.println("Player 2 hand: ");
			System.out.println(player2.hand.toString());
			System.out.println("Center: ");
			System.out.println(center.toString());
			System.exit(0);
		}
	}
	
	public static void match(Card cardPlayed) {
		
		for(int i = 0; i < player2.getHand().getAmount(); i++) {
			//System.out.println(i);
			if(player2.getHand().getCard(i).equals(cardPlayed))
				player2.playCard(i);
		}
		
		System.out.println("Its a match!");
		lastCapture = 2;
		if(center.getAmount() == 1) {
			
			player2.pisti++;
			System.out.println("Pisti Scored!");
			if (center.getCard(center.getAmount() - 1).getNum() == 11) {
				player2.pisti++;
				System.out.println("Double Pisti!");
			}
		}
		
		player2.capture.addDeck(center);
		player2.capture.addCard(cardPlayed);
		//System.out.println("Capture: " + player2.capture.toString());
		player2.calculatePoints(player1.capture.getAmount());
		player1.calculatePoints(player2.capture.getAmount());
		center = new Deck();
		updateCenter();
	}
	
	public static HBox makeBotHand(int num) {
		HBox result = new HBox();
		for( int i = 0; i < num; i++) {
			result.getChildren().add(makeCardImage());
		}
		
		result.getChildren().add(makeCardImage(new Card('s', 10), 2));
		
		result.setSpacing(30);
		result.setPadding(new Insets(0, 0, 0, 275));
		return result;
	}
	
	public static ImageView makeCardImage() {
		ImageView result = new ImageView();
		result.setImage(new Image("Cards2/red_back.png"));
		result.setFitHeight(220);
		result.setPreserveRatio(true);
		return result;
	}
	
	public static void updateCenter() {
		if(center.getAmount() == 0) {
			centerCardg.setVisible(false);
		}
		else {
			Card card = center.getCard(center.getAmount() - 1);
			String path = new String();
			path = "cards2/";
			path = path + Integer.toString(card.getNum());
			path = path + Character.toUpperCase(card.getSuit()) + ".png";
			//System.out.println("ImageURL: " + path);
			centerCardg.setImage(new Image(path));
			System.out.println("Updating center");
			centerCardg.setVisible(true);
		}
	}
	
	public static ImageView makeCardImage(Card card, int yes) {
		ImageView result = new ImageView();
		String path = new String();
		path = "cards2/";
		path = path + Integer.toString(card.getNum());
		path = path + Character.toUpperCase(card.getSuit()) + ".png";
		//System.out.println("ImageURL: " + path);
		result.setImage(new Image(path));
		result.setFitHeight(220);
		result.setPreserveRatio(true);
		
		if (yes == 1) {
			result.setOnMousePressed((t) ->  {
				result.setVisible(false);
				playCard(card);
			});
		}
		else if (yes == 2) {
			result.setVisible(false);
		}
		return result;
	}

}
