package com.dicequestion.arvind;

/* Author : Arvind Purushotham
 * Test solution for HCSS 
 */

public class Cup 
{
	private static final Cup instance_of_cup = new Cup();
	private CupSvc cupService = new CupSvc();

	private Cup(){}
	
	public static Cup getInstance()
	{		
		return instance_of_cup;		
	}
	
	public void fillCup(int no_of_dice)
	{
		cupService.setNo_of_dice(no_of_dice);
	}
	
	public int play()
	{
		return cupService.play();		
	}	

}
