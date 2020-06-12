package com.tomb.CardTable;

import java.util.LinkedList;
import java.util.Map;

public class Dealer {

	private volatile static Dealer dealer_instance;
	
	public LinkedList<Map<CardValue, CardSuit>> dealer_hand;
	public LinkedList<Integer> game_result;
	
	private Dealer() {
		this.dealer_hand = new LinkedList<>();
		this.game_result = new LinkedList<>();
	}
	
	
	static public Dealer getInstance() {
		
		if(dealer_instance==null) {
			synchronized(Dealer.class) {
				if(dealer_instance==null) {
					dealer_instance = new Dealer();
				}
			}
		}
		return dealer_instance;
	}
	
	public void SeatPlayers() {
		// Initialize players
		for(int i = 1; i<=UserInput.playerCount; i++) {
			Player.seatedPlayers.add(new Player(i));
		}
		System.out.println("Number of Seated Players - "+Player.seatedPlayers.size());
		
	}
	
	public void DealGame() {
		
		while(UserInput.gameChoice!=3) {
			
			if(Deck.getInstance().active_deck.size()<=26) {
				
				// use new deck?
				System.out.println("\nDealer - 'We are trading decks out.\n");
				Deck.getInstance().createDeck(UserInput.numberOfDecks);
				Deck.getInstance().shuffleDeck();
				Player.card_count=0;
				
			}else{
				
				// hard set the game to black jack... pokeer not yet made
				UserInput.gameChoice = 1;
				
				switch(UserInput.gameChoice) {
				case 1:
					
					System.out.println(UserInput.allow_bets ? "Table Allows Bets" : "Table Does Not Allow Bets");
					if(UserInput.allow_bets) { BlackJack.PlaceWagers(); }
					
					BlackJack.DealHands();
					BlackJack.playersTakesTurns();
					BlackJack.dealerTakesTurn();
					BlackJack.printGameResults();
					ClearTable();
					break;
					
				case 2:
//					Poker.DealHands();
//					Poker.BettingRound();
//					Poker.DealFlop();
//					Poker.BettingRound();
//					Poker.DealCommunityCard();
//					Poker.BettingRound();
					break;
				}
				
			}
			
			System.out.println("Dealer - 'Stay at Table[1], Go to the Poker Tables[2], Stop Gambling...[3]");
			UserInput.gameChoice = UserInput.requestInt(1,3);
		}
	}
	
	public void ClearTable() {
		
		for(Player p : Player.seatedPlayers) {
			p.player_hand.clear();
		}
		Dealer.getInstance().dealer_hand.clear();
	}
	
}
