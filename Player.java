package com.tomb.CardTable;


import java.util.LinkedList;
import java.util.Map;


public class Player {
	
	public static LinkedList<Player> seatedPlayers = new LinkedList<>();
	
	public LinkedList<Map<CardValue, CardSuit>> player_hand;
	
	// end game result
	public LinkedList<Integer> game_result;
	public boolean have_split;
	
	// counting cards
	static boolean am_counting_cards = false;
	static int card_count = 0;
	
	String player_name;
	int seatNumber;
	
	public static boolean init_button = true;
	public boolean has_button;
	
	// For Poker
	public int bet;
	public int max_money;
	public boolean has_surrendered;
	public boolean has_doubleDown;
	
	public Player(int seatNumber) {
		this.player_hand = new LinkedList<>();
		this.seatNumber = seatNumber;
		this.game_result = new LinkedList<>();
		this.have_split = false;
		
		this.bet = 0;
		this.max_money = 0;
		this.has_surrendered = false;
		this.has_doubleDown = false;
	}

	
	public static void incrementCardCount(Map<CardValue, CardSuit> singe_card) {
			
		for(CardValue rank : singe_card.keySet()) {
			switch(rank) {
			case Two: card_count++; break;
			case Three: card_count++; break;
			case Four: card_count++; break;
			case Five: card_count++; break;
			case Six: card_count++; break;
			case Ten: card_count--; break;
			case Jack: card_count--; break;
			case Queen: card_count--; break;
			case King: card_count--; break;
			case Ace: card_count--; break;
			default: break; // neutral card 7,8,9
			}
		}
	}
	
	
	
}
