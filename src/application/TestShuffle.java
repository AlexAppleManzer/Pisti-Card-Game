package application;

public class TestShuffle {
	
	public static void main(String args[]) {
		Deck testDeck = new Deck();
		testDeck.resetDeck();
		System.out.println(testDeck.toString());
		testDeck.shuffle();
		System.out.println();
		System.out.println(testDeck.toString());
		System.out.println("\nCard 1: " + testDeck.getCard(0).toString());
		System.out.println("Card 2: " + testDeck.getCard(1).toString());
		System.out.println("Card drawn: " + testDeck.draw().toString());
		System.out.println("Card drawn: " + testDeck.draw().toString());
		
		
		
		System.out.println();
		System.out.println();
		
		Card[] testCards = {new Card('h', 2), new Card('s', 3), new Card('d', 4), new Card('c', 1)};
		testDeck = new Deck(testCards);
		System.out.println("Deck cards: " + testDeck.getCard(0).toString() + ", "+ testDeck.getCard(1).toString() 
				+ ", "+ testDeck.getCard(2).toString() + ", "+ testDeck.getCard(3).toString());
		System.out.println("Deck length: " + testDeck.getAmount());

		
	}
}
