package com.dicequestion.arvind;

/* Author : Arvind Purushotham
 * Test solution for HCSS 
 */

public class CupSvc 
{
	private int no_of_dice;
	
	public int getNo_of_dice() {
		return no_of_dice;
	}

	public void setNo_of_dice(int no_of_dice) {
		this.no_of_dice = no_of_dice;
	}
	
	public int play()
	{
		int value = 0;
		
		DieImpl die = new DieImpl();
		for(int i = 0; i < no_of_dice; i++)
		{		
			value = value + die.roll();
		}		
		return value;		
	}	
	
}
