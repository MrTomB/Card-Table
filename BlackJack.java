package com.tomb.CardTable;

import java.util.LinkedList;
import java.util.Map;

public class BlackJack {
	
	
//	public LinkedList<Map<CardValue, CardSuit>> player_hand;
	public static LinkedList<Map<CardValue, CardSuit>> split_hand1 = null;
	public static LinkedList<Map<CardValue, CardSuit>> split_hand2 = null; 
	//public static LinkedList<Integer> game_result = new LinkedList<>();
	public boolean have_split;
	
//	pass bool to toggle split print statement
	public static boolean opt_split = false;
	
//	these 4 variables used to check first hand for split
	public static boolean first_hand = true;
	public static int toggle = 0;
	
//	check the first and second card in player's first hand
	public static int[] first_card = new int[2];
	public static int[] second_card = new int[2];
	
//	used to check sum of hand
	public static Integer[] sumHand = new Integer[2];
	public static CardValue one = null;
	public static CardValue two = null;
	
	
	public static void PlaceWagers() {
		for(Player p : Player.seatedPlayers) {
			System.out.println("Dealer - 'Player "+p.seatNumber+" how much do you want to bet.");
			p.bet = UserInput.requestInt(5, 100);
		}
	}
	             
	
// Deal inital Black Jack Hand
	public static void DealHands() {
		// 1 card left to right for players
		for(Player p : Player.seatedPlayers) {
			p.player_hand.add(Deck.getInstance().drawCard());
		}
		
		// one card face down, so do not increment Player Count
		Dealer.getInstance().dealer_hand.add(Deck.getInstance().active_deck.pop());
		Deck.getInstance().drawn_cards--;
		
		// repeat so each player has 2 cards
		for(Player p : Player.seatedPlayers) {
			p.player_hand.add(Deck.getInstance().drawCard());
		}
		// then 1 card for dealer, face up, players can increment running_count
		Dealer.getInstance().dealer_hand.add(Deck.getInstance().drawCard());
		
		System.out.println("Dealer's card");
		//printHand(Dealer.getInstance().dealer_hand);
		
		Map<CardValue, CardSuit> lastElement = Dealer.getInstance().dealer_hand.getLast();
		lastElement.forEach((k,v) -> {
			
			if(k == CardValue.Ace) {
				int[] aceVals = k.getAceNum();
				System.out.println("\t\t"+k + " of " + v+", "+aceVals[0]+" or "+aceVals[1]);
			}else {
				System.out.println("\t\t"+k + " of " + v+", "+k.getCardNum());	
			}
		});
		
		
		for(Player p : Player.seatedPlayers) {
			System.out.println("Player "+p.seatNumber+ " hand.");
			printHand(p.player_hand);
		}
		System.out.println("----------------------------------------------------------------------");	
	}
	
	public static void printHand(LinkedList<Map<CardValue, CardSuit>> hand) {
		// Java 8, previous used entrySet()...
		for(Map<CardValue, CardSuit> i : hand) {
			i.forEach((k, v) -> {
				
				if(k == CardValue.Ace) {
					int[] aceVals = k.getAceNum();
					System.out.println("\t\t"+k + " of " + v+", "+aceVals[0]+" or "+aceVals[1]);
				}else {
					System.out.println("\t\t"+k + " of " + v+", "+k.getCardNum());	
				}
			});
		}
	}
	
	
	public static boolean requestMove(Player p, LinkedList<Map<CardValue, CardSuit>> hand) {
		//System.out.println("Black Jack - requestMove()");
		int move = 0;
		boolean turn_end = false;
		
		
		if(sumHand[0]==21 || sumHand[1]==21) {
			System.out.println("Dealer - You have 21!");
			p.game_result.add(Integer.valueOf(21));
			turn_end=true;
			
		}else if(sumHand[0]>21 && sumHand[1]>21) {
			System.out.println("Dealer - You have bust.");
			p.game_result.add(Integer.valueOf(-1));
			turn_end=true;
			
		}else {
		
			move = UserInput.requestPlayerAction();
			opt_split=false;
			first_hand=false;
			
			//System.out.println((move == 1) ? "You have decided to Hit": "You have decided to Pass");
			switch(move) {
			case 1:
				hand.add(Deck.getInstance().drawCard());
				sumHand[0]=0;
				sumHand[1]=0;
				break;
			case 2:
				
				if(sumHand[0]>sumHand[1] && sumHand[0]<21) {
					p.game_result.add(Integer.valueOf(sumHand[0]));
				}else {
					p.game_result.add(Integer.valueOf(sumHand[1]));
				}
				
				turn_end=true;
				break;
			case 3:
				System.out.println("Dealer - 'Player "+p.seatNumber+" has double downed.");
				p.has_doubleDown = true;
				hand.add(Deck.getInstance().drawCard());
				//checkHand(p, hand);
				break;
			case 4:
				System.out.println("Dealer - 'Player "+p.seatNumber+" has surrended.");
				p.has_surrendered = true;
				turn_end=true;
				break;
			case 5:
				p.have_split=true;
				System.out.println("Dealer - 'Player "+p.seatNumber+" has split & double downed.");
				turn_end = SplitHand(p);
				break;
			}
		}
		return turn_end;
	}
	

	public static void checkHand(Player p, LinkedList<Map<CardValue, CardSuit>> hand){
		//System.out.println("Black Jack - checkHand");
		boolean turn_end = false;
		sumHand[0] = 0;
		sumHand[1] = 0;
		first_card[0] = 0;
		first_card[1] = 0;
		
		do {
		
		// Java 8, previous used entrySet()...
		for(Map<CardValue, CardSuit> i : hand) {
			i.forEach((k, v) -> {
				
				if(first_hand) {
					switch(toggle) {
					case 0: 
						one = k;
						toggle = 1;
						break;
					case 1:
						two = k;
						toggle = 0;
						//first_hand = false;
					}
				}
				
				if(k == CardValue.Ace) {
					int[] aceVals = k.getAceNum();
					System.out.println("\t\t"+k + " of " + v+", "+aceVals[0]+" or "+aceVals[1]);
					sumHand[0] = sumHand[0] + aceVals[0]; // opt value 1
					sumHand[1] = sumHand[1] + aceVals[1]; // opt value 2
					
				}else {
			
					System.out.println("\t\t"+k + " of " + v+", "+k.getCardNum());
					sumHand[0] = sumHand[0] + k.getCardNum();
					sumHand[1] = sumHand[1] + k.getCardNum();
					
				}
			});
		}
		
		System.out.println((sumHand[0]>sumHand[1]&&sumHand[0]<=21)||sumHand[1]>21 ? "Hand's sum "+sumHand[0] : "Hand's sum "+sumHand[1]+"\n");
		//System.out.println(Player.am_counting_cards ? "card_count "+Player.card_count : "not counting cards...");
		
		// print count number
		if(Player.am_counting_cards && p!=null) {
			double decks_left = (double)((UserInput.numberOfDecks*52)-Deck.getInstance().drawn_cards)/(double)52;
			double true_count = ((double)Player.card_count/decks_left);
			//double bet_count = true_count-1;
			//System.out.println("decks_left: "+decks_left);
			System.out.println("true_count: "+true_count);
			//System.out.println("bet_count: "+bet_count);
		}
		
		
		// check if player
		if(p!=null) {
			
			if(p.has_doubleDown) {		// check if player has double down
			
				p.bet *=2;
				if(sumHand[0]>sumHand[1] && sumHand[0]<21) {
					p.game_result.add(Integer.valueOf(sumHand[0]));
				}else{
					p.game_result.add(Integer.valueOf(sumHand[1]));
				}
				p.has_doubleDown=false;
				turn_end = true;
		
			}else if(p.has_surrendered){	// check if player has surrendered
				p.bet /= 2;
				p.max_money += p.bet;
				p.game_result.add(Integer.valueOf(-1));
				turn_end = true;
		
			}else {
				// compare the cards two of the same rank 2-A, since enum have same def
				if(one == two) {
				//System.out.println("Allow # & # Split Opt in print statement");
					opt_split = true;
				}
				one=null;
				two=null;		
			}
			
			if(!turn_end || p.has_surrendered) {
				turn_end = requestMove(p, hand);
			}
		// else is dealer
		}else {
			turn_end = dealerMove(sumHand);
		}
		
		// reinitialize for next hand
		sumHand[0] = 0;
		sumHand[1] = 0; 
		
		}while(!turn_end);
		
	}
	

	
	
	public static boolean SplitHand(Player p) {
		//System.out.println("Black Jack - splitHand()");
		split_hand1 = new LinkedList<>();
		split_hand2 = new LinkedList<>();

		//while(p.player_hand.peekFirst()!=null) {
		try {
			split_hand1.add(p.player_hand.pop());
			split_hand2.add(p.player_hand.pop());			
		}catch(Exception e) {
			System.out.println("Player Hand is empty");
		}
		
		try {
			split_hand1.add(Deck.getInstance().drawCard());
			split_hand2.add(Deck.getInstance().drawCard());
		}catch(Exception e) {
			System.out.println("Deck is empty");
		}
		
		printHand(split_hand1);
		System.out.println();
		printHand(split_hand2);
		System.out.println("----------------------------------------------------------------------");
		checkHand(p, split_hand1);
		checkHand(p, split_hand2);
		
		split_hand1=null;
		split_hand2=null;
		opt_split = false;
		return true;
	}
	
	public static void playersTakesTurns() {
		for(Player p : Player.seatedPlayers) {
			
			System.out.println("Player "+p.seatNumber+ " taking turn.");
			first_hand = true;
			opt_split = false;
			checkHand(p, p.player_hand);
			
			System.out.println("Player "+p.seatNumber+"'s Turn has ended with hand of "+p.game_result);
			System.out.println("----------------------------------------------------------------------");
		}
	}
	
	public static boolean dealerMove(Integer[] sumHand) {
		boolean turn_end = false;
		
		if(sumHand[0]==21 || sumHand[1]==21) {
			System.out.println("Dealer - I have 21!");
			Dealer.getInstance().game_result.add(21);
			turn_end=true;
		}else if(sumHand[0]>21 || sumHand[1]>21) {
			System.out.println("Dealer - I have bust.");
			Dealer.getInstance().game_result.add(-1);
			turn_end=true;
		}else if(sumHand[0]>=18 && sumHand[1]>=18){
			
			if(sumHand[0]==sumHand[1] || sumHand[0]>sumHand[1]) {
				Dealer.getInstance().game_result.add(sumHand[0]);
			}else{
				// sumHand[1]>sumHand[0]
				Dealer.getInstance().game_result.add(sumHand[1]);
			}
			System.out.println("Dealer - 'I can no longer hit, hand sum is "+Dealer.getInstance().game_result.element()+"'");
			
			turn_end=true;
		}else {
			System.out.println("Dealer - 'My cards are less than or equal to 17, I will hit again'");
			Dealer.getInstance().dealer_hand.add(Deck.getInstance().drawCard());	
		}
		
		return turn_end;
	}
	

	public static void dealerTakesTurn() {
		System.out.println("Dealer - I will begin my turn.");
		BlackJack.checkHand(null, Dealer.getInstance().dealer_hand);
		System.out.println("----------------------------------------------------------------------");
	}

	
	public static void printGameResults() {
		
		Integer d = 0;
		d = Dealer.getInstance().game_result.pop();
		//System.out.println("d: "+d);
		
		for(Player p : Player.seatedPlayers) {
			
		if(!p.has_surrendered) {
			if(p.have_split) {
				
				Integer split1 = p.game_result.pop();
				Integer split2 = p.game_result.pop();
				
				if(split1 == d && split1!=-1) {
					System.out.println("Player "+p.seatNumber+" split hand 1 has tied against the House.");
					//p.max_money += p.bet;
				}else if(split1 > d) {
					System.out.println("Player "+p.seatNumber+" split hand 1 has won against the House.");
					p.max_money += (2*p.bet);
				}else {
					System.out.println("Player "+p.seatNumber+" split hand 1 has lost against the House.");
					p.max_money -= p.bet;
				}
				if(split2 == d && split2!=-1) {
					System.out.println("Player "+p.seatNumber+" split hand 2 has tied against the House.");
					p.max_money += p.bet;
				}else if(split2 > d) {
					System.out.println("Player "+p.seatNumber+" split hand 2 has won against the House.");
					p.max_money += (p.bet+p.bet);
				}else {
					System.out.println("Player "+p.seatNumber+" split hand 2 has lost against the House.");
					p.max_money -= p.bet;
				}
				
				p.have_split = false;
				
			}else{
				Integer i = 0;
				try {
					i = p.game_result.pop();
				}catch(Exception e) {
					System.out.println("player "+p.seatNumber+" game_result is empty");
				}
				//System.out.println("Player "+p.seatNumber+": "+i);
				
				if(i == d && i!=-1) {
					System.out.println("Player "+p.seatNumber+" has tied against the House, a Push.");
					//p.max_money += p.bet();
					
				}else if(i > d) {
					System.out.print("Player "+p.seatNumber+" has won against the House");
					if(p.player_hand.size()==2 && i==21) {
						System.out.println(" with a 3/2 payout.");
						p.max_money += (1.5*p.bet)+p.bet;
					}else {
						System.out.println(".");
						p.max_money += p.bet+p.bet;
					}
					
				}else {
					System.out.println("Player "+p.seatNumber+" has lost against the House.");
					p.max_money -= p.bet;
				}
			}
			System.out.println("Player "+p.seatNumber+" remaining chips value "+p.max_money);
		}
		p.has_surrendered = false;
		}
		
	}
	
	
}

