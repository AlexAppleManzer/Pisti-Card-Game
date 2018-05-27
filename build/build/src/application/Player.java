package application;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Player {
	protected Text points;
	protected int pisti;
	protected int majority;
	protected Deck capture;
	protected Deck hand;
	
	public Player() {
		points = new Text("0");
		points.setFont(new Font(100));
		hand = new Deck();
		capture = new Deck();
	}
	
	public int getPoints() {
		return Integer.parseInt(points.getText());
	}
	
	public void setHand(Deck hand) {
		this.hand = hand;
	}
	
	public Deck getHand() {
		return hand;
	}
	
	public Card playCard(int n) {
		 return hand.playCard(n);
	}
	
	public int calculatePoints (int thresh) {
		
		int result = capture.getAmount();
		if(capture.hasCard('c', 2))
			result++;
		if(capture.hasCard('d', 10))
			result++;
		if(capture.getAmount() > 6)
			result = result + 3; 
		points.setText(Integer.toString(result + (pisti * 10)));
		//System.out.println(result);
		//System.out.println(pisti * 10);
		return (result + (pisti * 10));
		
	}
}
