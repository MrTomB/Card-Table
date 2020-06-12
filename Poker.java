/* Thomas Burch
 * tomburch777@gmail.com
 * Github: MrTomB
 */

package com.tomb.CardTable;

import java.util.ListIterator;
import java.util.Map;

public class Poker {
	
	// For Poker
	public static int smallBlind = 0;
	public static int bigBlind = 0;
	public static int rasied = 0;
	public static int raised_at = -1;
	
	public static void DealHands(){
		for(Player p : Player.seatedPlayers) {
			p.player_hand.add(Deck.getInstance().drawCard());
		}
		for(Player p : Player.seatedPlayers) {
			p.player_hand.add(Deck.getInstance().drawCard());
		}
	}
	
	// Go around the table allowing every player to raise (by the amount of the big blind or more),
	// call (match the current bet), or fold (give up for the round). 
	// Moving clockwise from the player in the small blind,
	// bets will be made until every player has folded, put in their chips, or matched the amount of other players combined.
	
	public static int requestSmallBlind(Player p) {
		p.bet = UserInput.requestInt(0, p.max_money);
		return p.bet;
	}
	
	public static int requestBigBlind(Player p) {
		p.bet = UserInput.requestInt(smallBlind, p.max_money);
		return (int)p.bet;
	}
	
	public static int requestAnte(Player p) {
		p.bet = UserInput.requestInt(bigBlind, p.max_money);
		return p.bet;
	}

	
	public static void BettingRound() {
		
		boolean setBlinds = false;
		
		// first round of bets, button at first player
		if(Player.init_button) {
			Player.seatedPlayers.getFirst().has_button = true;
			Player.init_button = false;
		}
		
		
		for(int i=0; i<Player.seatedPlayers.size(); i++) {	
			
			if(!setBlinds) {
				
				if(Player.seatedPlayers.get(i).has_button == true) {
				
					if(Player.seatedPlayers.getLast().has_button) {
						smallBlind = requestSmallBlind(Player.seatedPlayers.getLast());
						bigBlind = requestBigBlind(Player.seatedPlayers.getFirst());
						// move for-loop itr to 2 for else statement
						i=1;
						// move player button for next game...
						Player.seatedPlayers.element().has_button = true;
					}else {
						smallBlind = requestSmallBlind(Player.seatedPlayers.get(i));
						bigBlind = requestBigBlind(Player.seatedPlayers.get(i+1));
						
						if(Player.seatedPlayers.get(i+2)!=null) {
							i+=2;
						}else {
							i=0;
						}
						
						Player.seatedPlayers.get(i+1).has_button = true;
					}
					setBlinds = true;
					break; // from for-loop
				}
				
			}else {
				
				if(requestAnte(Player.seatedPlayers.get(i)) > bigBlind) {
					raised_at=i;
				}else if(raised_at == i) {
					break;
				}
				

			}
			// i think this will break loop early if looped back to person who raised
			if(raised_at != -1) {
				
			}
		}
	}
	
	public static void printRiver() {
		Map<CardValue, CardSuit> last_card = Dealer.getInstance().dealer_hand.peekLast();
		last_card.forEach((k,v)->{
			System.out.println("\t\t"+k + " of " + v+"\n");
		});
	}
	
	
	public static void DealFlop() {
		// then 1 card for dealer, face up, players can increment running_count
		for(int i=0; i<3; i++) {
			DealCommunityCard();
		}
	}
	
	// Second Round of Bets
	
	public static void DealCommunityCard() {
		Dealer.getInstance().dealer_hand.add(Deck.getInstance().drawCard());
	}
	
	// Third Round of Bets
	
	// DealCard() River card
	
	// Last Round of Bets
	
	
	
}
