package com.tomb.CardTable;

import java.util.LinkedList;

enum CardValue{
	
		Two(2), Three(3), Four(4), Five(5), Six(6), Seven(7), Eight(8), Nine(9), Ten(10), Jack(10), Queen(10), King(10), Ace(1, 11);
		
		
		private int card_value;
		private int[] ace_value = new int[2];
		
		private CardValue(int card_value) {
			this.card_value = card_value;
		}
		
		private CardValue(int ace1, int ace11) {
			this.ace_value[0]=ace1;
			this.ace_value[1]=ace11;
		}
		
		public int getCardNum() {
			return card_value;
		}
		
		public int[] getAceNum() {
			return ace_value;
		}
		
		
	}

	enum CardSuit{
		Spades(1), Diamonds(2), Clubs(3), Hearts(4);
		
		private int card_suit;
		
		private CardSuit(int card_suit) {
			this.card_suit = card_suit;
		}
		
		public int getCardSuit() {
			return card_suit;
		}
		
	}

	
