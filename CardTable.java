package com.tomb.CardTable;

public class CardTable {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		UserInput.greetings();
		
		Deck.getInstance().createDeck(UserInput.numberOfDecks);
		Deck.getInstance().shuffleDeck();
	
		Dealer.getInstance().SeatPlayers();
		Dealer.getInstance().DealGame();
		
		UserInput.closeScanner();
		
		System.out.println("exit..."); 
        // Terminate JVM 
        System.exit(0);
	}

}
