package application;
	
import java.util.Scanner;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class PistiTextTest extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		Player player1;
		Player player2;
		Deck center;
		Deck deck;
		Card cardPlayed;
		Scanner input = new Scanner(System.in);

			
		player1 = new Player();
		player2 = new Player();
		
		while (player1.getPoints() < 101 || player2.getPoints() < 101) {
			int lastCapture = 0;
			deck = new Deck();
			deck.resetDeck();
			deck.shuffle();
			
			do {
				deck.shuffle();
			} while(deck.getCard(deck.getAmount() - 1).getNum() == 11);
			
			center = deck.drawDeck(4);
			player1.setHand(deck.drawDeck(4));
			player2.setHand(deck.drawDeck(4));
			//player1.hand.addCard(new Card('s', 11));
			System.out.println();
			
			for(int i = 0; i < 4; i++) {
				
				if(center.getAmount() > 1) {
					System.out.println("\nCenter: \n" + center.getCard(center.getAmount() - 1));
				}
				System.out.println("\nplayer 1 points: " + player1.getPoints());
				System.out.println("p1: \n" + player1.getHand().toString());
				
				System.out.println("\nplayer 2 points: " + player2.getPoints());
				System.out.println("p2: \n" + player2.getHand().toString());
				
				System.out.println();
				System.out.println("Enter which card to play (p1): ");

				cardPlayed = player1.playCard(input.nextInt() - 1);
				
				if(center.getAmount() > 1) {
					if(center.getCard(center.getAmount() - 1).getNum() == cardPlayed.getNum() || cardPlayed.getNum() == 11) {
						System.out.println("Its a match!");
						lastCapture = 1;
						if(center.getAmount() == 2) {
							
							player1.pisti++;
							System.out.println("Pisti Scored!");
							if (center.getCard(center.getAmount() - 1).getNum() == 11) {
								player1.pisti++;
								System.out.println("Double Pisti!");
							}
						}
						
						player1.capture.addDeck(center);
						player1.capture.addCard(cardPlayed);
						System.out.println("Capture: " + player1.capture.toString());
						player1.calculatePoints(player2.capture.getAmount());
						center = new Deck();
					}
					else
						center.addCard(cardPlayed);
				}
				else
					center.addCard(cardPlayed);
				
				if(center.getAmount() > 1) {
					System.out.println("\nCenter: \n" + center.getCard(center.getAmount() - 1));
				}
				
				System.out.println("\nplayer 1 points: " + player1.getPoints());
				System.out.println("p1: \n" + player1.getHand().toString());
				
				System.out.println("\nplayer 2 points: " + player2.getPoints());
				System.out.println("p2: \n" + player2.getHand().toString());
				
				System.out.println();
				System.out.println("Enter which card to play (p1): ");
				cardPlayed = player2.playCard(input.nextInt() - 1);
				if(center.getAmount() > 1) {
					if(center.getCard(center.getAmount() - 1).getNum() == cardPlayed.getNum() || cardPlayed.getNum() == 11) {
						System.out.println("Its a match!");
						lastCapture = 2;
						if(center.getAmount() == 2) {
							
							player2.pisti++;
							System.out.println("Pisti Scored!");
							if (center.getCard(center.getAmount() - 1).getNum() == 11) {
								player2.pisti++;
								System.out.println("Double Pisti!");
							}
						}
						
						player2.capture.addDeck(center);
						player2.capture.addCard(cardPlayed);
						player2.calculatePoints(player1.capture.getAmount());
						center = new Deck();
					}
					else
						center.addCard(cardPlayed);
				}
				else
					center.addCard(cardPlayed);
				
			}	
			System.out.println("lastcap");
			if(lastCapture == 1) {
				System.out.println("let's go");
				player1.capture.addDeck(center);
				System.out.println(player1.capture.toString());
				player1.calculatePoints(player2.capture.getAmount());
			}
			else if(lastCapture == 2) {
				player2.capture.addDeck(center);
				player2.calculatePoints(player1.capture.getAmount());
			}
			
			
		}
		System.out.println(player1.getPoints() > player2.getPoints() ? "Player 1 wins!" : "Player 2 wins!");
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	

}
