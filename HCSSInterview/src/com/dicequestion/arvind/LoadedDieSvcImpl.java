package com.dicequestion.arvind;

/* Author : Arvind Purushotham
 * Test solution for HCSS 
 */

public class LoadedDieSvcImpl implements IDice
{

	private int random_rolled;
	private boolean die_lock;
	private int loadedValue;
	
	public LoadedDieSvcImpl()
	{
		random_rolled = 0;
		die_lock = false;
	}
	
	@Override
	public synchronized int roll()
	{
		random_rolled = 1 + (int)(Math.random() * 6);
		
		if(die_lock)
		{
			die_lock = false;
			return loadedValue;
		}
		else
		{
			die_lock = true;
			if (random_rolled == loadedValue && random_rolled != 1)
			{
				return 1;
			}
			else if (random_rolled == loadedValue && random_rolled == 1)
			{
				return 2;
			}
			else
			{
				return random_rolled;
			}
		}
		
	}
	
	public void rollAgain(boolean rollAgain)
	{
		if(rollAgain)
		{
			roll();
		}
	}
	
	public int getLoadedValue() {
		return loadedValue;
	}

	public void setLoadedValue(int loadedValue) {
		this.loadedValue = loadedValue;
	}
}
