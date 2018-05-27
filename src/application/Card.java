package application;

import javafx.scene.image.ImageView;

public class Card {
	private char suit; // s for spade, c for club, h for heart, d for diamond
	private int num; //11= jack 12= queen...1 = ace
	
	public Card(char suit, int num) {
		this.suit = suit;
		this.num = num;
	}
	
	public int getNum() {
		return num;
	}
	
	public void setNum(int n) {
		if(15 > num && num > 0)
			num = n;
		else
			System.out.println("Error! invalid number set!");
	}
	
	public char getSuit() {
		return suit;
	}
	
	public void setSuit(char suit) {
		if(suit == 's' || suit == 'c' ||suit == 'h' ||suit == 'd')
		this.suit = suit;
	}
	
	public boolean equals(char suit, int num) {
		return this.suit == suit && this.num == num;
	}
	
	public boolean equals(Card card) {
		return this.suit == card.suit && this.num == card.num;
	}
	
	public String toString() {
		
		StringBuilder answer = new StringBuilder();
		
		switch(num) {
			case 11: answer.append("Jack");
				break;
			case 12: answer.append("Queen");
				break;
			case 13: answer.append("King");
				break;
			case 1: answer.append("Ace");
				break;
			default: answer.append(num);
			break;
				
		}
		
		
		answer.append(" of ");
		
		
		if(suit == 's' || suit == 'c' ) {
			if(suit == 's')
				answer.append("spades");
			else
				answer.append("clubs");
		}
		
		else if(suit == 'h' || suit == 'd' ) {
			if(suit == 'h')
				answer.append("hearts");
			else
				answer.append("diamonds");
		}
		
		return answer.toString();
	}
}
