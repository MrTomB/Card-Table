package com.tomb.CardTable;

import java.util.Scanner;

public class UserInput {
	
	public static int playerCount=0;
	public static int numberOfDecks=0;
	public static String willHit=null;
	public static int gameChoice=0;
	public static boolean allow_bets=false;
	
	static Scanner input = new Scanner(System.in); 
	
	public static void openScanner() {
		input = new Scanner(System.in);
	}
	
	public static void closeScanner() {
		input.close();
	}
	
	public static int requestInt(int min_range, int max_range) {
		
		int intValue = 0;
		boolean valid = false;
		
		while(intValue==0){
			
			try {
				valid = input.hasNextInt();
				//System.out.println(valid?"true":"false");
				intValue= input.nextInt();
				//System.out.println(intValue);
				
			} catch (Exception e) {
				System.out.println("Please enter only integers for the number of people participating in Black Jack.");
			} finally {
				
				if(min_range>intValue) {
					System.out.println("Please enter an integer greater than or equal to "+min_range);
					intValue=0;
				}else if(max_range<intValue) {
					System.out.println("Please enter an integer less or equal than "+max_range);
					intValue=0;
				}else {
					//System.out.println(intValue+" is a valid number");
				}
			}
			// We are done
			if(!valid) {
				break;
			}
		}
		
		return intValue;
	}
	
	public static void requestGame() {
		System.out.println("Dealer - 'Welcome to the Card Table. Let me ask my manager what game we are playing today.'");
		System.out.println("Floor Manager - 'This Card Table will be hosting a BlackJack [1], or Texas Hold'em[2] game'");
		gameChoice = requestInt(1,2);
	}
	
	
	public static void greetings() {
		
		requestGame();
		System.out.println(gameChoice==1 ? "Dealer - 'Welcome to the Black Jack Table'" : "Dealer - 'Welcome to the Texas Hold'em Table'");
		System.out.println("Dealer - 'How many individuals will be seated at the table.");
		playerCount = requestInt(1,6);
		
		System.out.println("Dealer - 'Are we taking bets tonight? Yes[1] or No[2]'");
		switch(requestInt(1,2)) {
			case 1: allow_bets=true; break;
			case 2: allow_bets=false; break;
		}
		
		System.out.println("Will the Players be counting cards Yes[1], and No[2]");
//		int countCards = 0;
//		countCards = requestInt(1,2);
		switch(requestInt(1,2)) {
			case 1: Player.am_counting_cards = true; break;
			case 2: Player.am_counting_cards = false; break;
		}
		System.out.println(Player.am_counting_cards ? 
				"Dealer - 'We have previously delt with card counters at this table, please play fairly..." :
			"Enjoy your game of Black Jack, and have Fun!");
		
		System.out.println("Dealer decide how many decks you want to use. Any number from 0 to 8");
		numberOfDecks = requestInt(1,8);
		
		System.out.println("\nDealer - 'The "+playerCount+" participants please take your seat.");
		System.out.println("Dealer = 'The number of Decks we are using at the table is "+numberOfDecks+"\n");

	}
	
	public static int requestPlayerAction() {
		int opt = 0;
		if((!BlackJack.opt_split) && BlackJack.first_hand) {
			System.out.println("Dealer - Would you like to [1]Hit ,[2]Stand, [3]Double Down, [4]Surrender");
			opt = 5;
			
		}else if(BlackJack.opt_split && BlackJack.first_hand) {
			System.out.println("Dealer - Would you like to [1]Hit ,[2]Stand, [3]Double Down, [4]Surrender [5]Split");
			opt = 6;
		}else {
			System.out.println("Dealer - Would you like to [1]Hit ,[2]Stand");
			opt = 3;
		}
		
		
		
		int move = 0;
		move = requestInt(0, opt);
		
		return move;
	}
	
}
