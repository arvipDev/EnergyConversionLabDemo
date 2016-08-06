package com.dicequestion.arvind;

/* Author : Arvind Purushotham
 * Test solution for HCSS 
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class Solution {
	
	// Creating a "singleton" Cup.
	private static Cup cup = Cup.getInstance();
	private static Scanner input;
	
	public static void main(String[] args) 
	{
		solutionOne();		
		System.out.println("----------------------------------------------------------------------------------------------");
			

		System.out.println("Here is my solution to the second problem ");
		solutionTwo();		
		System.out.println("----------------------------------------------------------------------------------------------");
		

		System.out.println("Here is my solution to the Third problem ");
		solutionThree();
		System.out.println("----------------------------------------------------------------------------------------------");
		
	}
	
	// provides solution one
	private static void solutionOne()
	{
		//Solution 1 -		
		System.out.println("Hello, lets begin.");
		System.out.println("Here is my solution to the first problem");
		
		// rolls a six-sided die to obtain a random value between 1 and 6.
		// filling the cup with a single die
		cup.fillCup(1);
		System.out.println("Rolling 1 die: " + cup.play());		
		
	}
	
	//provides solution two
	private static void solutionTwo()
	{
		//uses cup to roll multiple dice
		//Receiving input from the player - (No of dice to roll). Filling the cup.
		input = new Scanner(System.in);
		System.out.print("How many dice do you want to fill in the cup before rolling ? ");	
		
		try
		{
			int no_of_dice = input.nextInt();
			if(no_of_dice <= 0)
			{
				System.out.println("You need to roll at least 1 die to play the game.");
			}
			else if(no_of_dice >= 2147483647)
			{
				System.out.println("Cant find that many dice, sorry");
			}
			else
			{
				cup.fillCup(no_of_dice);
				
				//Lets Play!!!... rolling the dice that are in the cup and getting a number
				System.out.println("Rolling " + no_of_dice + " dice in a cup: " + cup.play());		
			}
			
		}
		catch (InputMismatchException e)
		{
			e.getStackTrace();
			System.out.println("Please enter a valid number other than -ve numbers, fractions or 0 ");
			solutionTwo();
		}
		
	}
	

	//provides solution three
	private static void solutionThree()
	{
		//uses loaded die with 50% probability
		input = new Scanner(System.in);
		LoadedDieSvcImpl loadedDie = new LoadedDieSvcImpl();
		try
		{
			System.out.print("Please load the die with a number - (Predetermined number) ");
			int no_on_die = input.nextInt();
			if(no_on_die <= 0 || no_on_die >= 7)
			{
				System.out.println("This number is not on the die.");
			}
			else
			{				
				loadedDie.setLoadedValue(no_on_die);
				//Lets Play!!!... rolling a predetermined die
				System.out.println("Rolling a loaded die set to '" + no_on_die + "': " + loadedDie.roll());
				
				System.out.println("Do you want to roll again - (Please type \"y\" if you do or \"n\" if you dont) ");
				Scanner scan = new Scanner(System.in);
				String roll_again = scan.nextLine();
				while(roll_again.equalsIgnoreCase("y"))
				{
					System.out.println("Rolling a loaded die set to '" + no_on_die + "': " + loadedDie.roll());
					roll_again = scan.nextLine();
					System.out.println("Do you want to roll again");
					if(!roll_again.equalsIgnoreCase("y"))
					{
						System.out.println("You did not choose to type \"y\" , Thank you for playing, bye.");
					}
				}
				scan.close();
			}
			System.out.println("Thank you for playing. ");
			
		}
		catch (InputMismatchException e)
		{
			e.getStackTrace();
			System.out.println("Please enter a valid number other than -ve numbers, fractions or 0 ");
			solutionThree();
		}
		finally {
			input.close();
		}
	}	

}
