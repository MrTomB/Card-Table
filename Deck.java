package com.tomb.CardTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Deck {
	
	//private static Deck deck_instance = null;
	
	public Deque<Map<CardValue, CardSuit>> active_deck; //= new LinkedList<>();
	public int drawn_cards;
	
	private volatile static Deck deck_instance;
	
	// Singleton Class - I want one deck at a time
	private  Deck() {
		this.drawn_cards = 0;
		this.active_deck = new LinkedList<>();
	}
	
	static public Deck getInstance() {
		
		if(deck_instance==null) {
			synchronized(Deck.class) {
				if(deck_instance==null) {
					deck_instance = new Deck();
				}
			}
		}
		return deck_instance;
	}
	
	
	public Deque<Map<CardValue, CardSuit>> createDeck(int numberOfDecks){
		
		EnumSet<CardValue> values = EnumSet.allOf(CardValue.class);
		EnumSet<CardSuit> suits = EnumSet.allOf(CardSuit.class);
		ArrayList<CardValue> arr_values = new ArrayList<>(values);
		ArrayList<CardSuit> arr_suits = new ArrayList<>(suits); 
		values.clear();
		suits.clear();
		
		Integer neededCount = Integer.valueOf((numberOfDecks*52)-1);
		Integer deckCount = Integer.valueOf(0);
		Map<CardValue, CardSuit> newCard;
		
		do {
			for(CardSuit s: arr_suits) {
				for(CardValue v: arr_values) {
					newCard = new HashMap<>();
					newCard.put(v,s);
					//active_deck.put(deckCount, newCard);
					active_deck.add(newCard);
					deckCount++;
			}	
		}
			
		}while(deckCount < neededCount);
		arr_values.clear();
		arr_suits.clear();
		
		//System.out.println(active_deck.entrySet());
		//System.out.println(active_deck.size());
		//System.out.println(active_deck.toString());
		//System.out.println(active_deck.size());
		
		return active_deck;
	}
	
	public LinkedList<Map<CardValue, CardSuit>> riffleShuffle(){
		
		System.out.println("Riffle Shuffle...");
		
		LinkedList<Map<CardValue, CardSuit>> sub_list_deck0 = new LinkedList<>(((LinkedList<Map<CardValue, CardSuit>>) active_deck).subList(0, (active_deck.size()/2)));
		LinkedList<Map<CardValue, CardSuit>> sub_list_deck1 = new LinkedList<>(((LinkedList<Map<CardValue, CardSuit>>) active_deck).subList(active_deck.size()/2, active_deck.size()));
		
		LinkedList<Map<CardValue, CardSuit>> new_list_deck = new LinkedList<>();
		
		int pick_deck = 0;
		int deckCount=0;
		do{
			switch(pick_deck) {
			case 0:
				if(sub_list_deck0.peekLast()!=null) {
					new_list_deck.push(sub_list_deck0.removeLast());
				}
					pick_deck=1;
			case 1:
				if(sub_list_deck1.peekLast()!=null) {
					new_list_deck.push(sub_list_deck1.removeLast());
				}
				pick_deck=0;
			}
			deckCount++;
			
		}while(deckCount<active_deck.size());
		
		//System.out.println(new_list_deck.toString());
		//System.out.println(new_list_deck.size());
		
		return new_list_deck;
	}
	
	public Deque<Map<CardValue, CardSuit>> boxShuffle(){
		System.out.println("Box Shuffle...");
		
		Deque<Map<CardValue, CardSuit>> sub_list_deck0 = new LinkedList<>(((LinkedList<Map<CardValue, CardSuit>>) active_deck).subList(0, (active_deck.size()/4)));
		Deque<Map<CardValue, CardSuit>> sub_list_deck1 = new LinkedList<>(((LinkedList<Map<CardValue, CardSuit>>) active_deck).subList(active_deck.size()/4, 2*(active_deck.size()/4)));
		Deque<Map<CardValue, CardSuit>> sub_list_deck2 = new LinkedList<>(((LinkedList<Map<CardValue, CardSuit>>) active_deck).subList(2*(active_deck.size()/4), 3*(active_deck.size()/4)));
		Deque<Map<CardValue, CardSuit>> sub_list_deck3 = new LinkedList<>(((LinkedList<Map<CardValue, CardSuit>>) active_deck).subList(3*(active_deck.size()/4), active_deck.size()));
		
		Deque<Map<CardValue, CardSuit>> new_list_deck = new LinkedList<>();
		
		new_list_deck.addAll(sub_list_deck3);
		new_list_deck.addAll(sub_list_deck2);
		new_list_deck.addAll(sub_list_deck1);
		new_list_deck.addAll(sub_list_deck0);
		
		return new_list_deck;
	}
	
	// Professional Shuffling
	public void shuffleDeck(){
		
		System.out.println("Shuffling Deck...");
		System.out.println("Washing Cards...");
		Collections.shuffle((List<?>) active_deck);
		
		active_deck = riffleShuffle();
		active_deck = riffleShuffle();
		active_deck = boxShuffle();
		active_deck = riffleShuffle();
		
		System.out.println();
		
	}
	
	public Map<CardValue, CardSuit> drawCard() {
		Map<CardValue, CardSuit> single_card = Deck.getInstance().active_deck.pop();
		Player.incrementCardCount(single_card);
		drawn_cards++;
		return single_card;
	}
	
}
