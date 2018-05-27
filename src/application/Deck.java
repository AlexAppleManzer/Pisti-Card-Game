package application;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	
	private ArrayList<Card> cards;
	
	public Deck() {
		cards  = new ArrayList<Card>();
	}
	
	public Deck (Card[] cards) {
		this.cards = new ArrayList<Card>();
		for(int i = 0; i < cards.length; i++) {
			this.cards.add(cards[i]);
		}
	}
	
	public void addCard(Card card) {
		cards.add(card);
	}
	
	public Card getCard(int index) {
		return cards.get(index);
	}
	
	public int getAmount() {
		return cards.size();
	}
	
	public boolean hasCard(char suit, int num) {
		for(int i = 0; i < cards.size(); i++) {
			if (cards.get(i).equals(suit, num))
				return true;
		}
		return false;
	}
	
	public void addDeck(Deck deck) {
		for (int i = 0; i < deck.getAmount(); i++){
			cards.add(deck.getCard(i));
		}
	}
	
	public void resetDeck() {
		cards = new ArrayList<Card>();
		for(int i = 1; i < 14; i++) {
			cards.add(new Card('s', i));
			cards.add(new Card('c', i));
			cards.add(new Card('d', i));
			cards.add(new Card('h', i));
		}
	}
	
	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	public Card draw() {
		Card result = cards.get(0);
		cards.remove(0);
		return result;
	}
	
	public Card[] draw(int n) {
		Card[] result = new Card[n];
		for(int i = 0; i < n; i++) {
			result[i] = draw();
		}
		
		return result;
	}
	
	public Deck drawDeck(int n) {
		Card[] result = new Card[n];
		for(int i = 0; i < n; i++) {
			result[i] = draw();
		}
		
		return new Deck(result);
	}
	
	public Card playCard(int n) {
		Card result = cards.get(n);
		cards.remove(n);
		return result;
	}
	
	public void addToDeck(Card card) {
		cards.add(card);
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		for(int i = 0; i < cards.size(); i++) {
			result.append("\nCard " + (i + 1) + ": " + cards.get(i).toString());
		}
		return result.toString();
	}
}
