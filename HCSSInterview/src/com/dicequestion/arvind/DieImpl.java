package com.dicequestion.arvind;

/* Author : Arvind Purushotham
 * Test solution for HCSS 
 */

public class DieImpl implements IDice
{
	private int random_rolled;
	
	public DieImpl()
	{
		random_rolled = 0;
	}
	
	@Override
	public int roll()
	{
		random_rolled = 1 + (int)(Math.random() * 6);
		return random_rolled;
	}
	
}
