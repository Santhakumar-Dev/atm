package com.atm.model;

public class Notes {

	 public enum Denom {
		 HUNDRED(100),
		 FIFTY(50),
		 TWENTY(20),
		 TEN(10),
		 FIVE(5);

	        private final int value;

	        Denom(int value) {
	            this.value = value;
	        }

	        public int getValue() {
	            return value;
	        }
	    }

	public Notes(Denom denom, long numberOfNotes){
		
		this.denom = denom;
		this.numberOfNotes = numberOfNotes;
	}
	
	private Denom denom;
	
	private long numberOfNotes;

	public Denom getDenom() {
		return denom;
	}

	public void setDenom(Denom denom) {
		this.denom = denom;
	}

	public long getNumberOfNotes() {
		return numberOfNotes;
	}

	public void setNumberOfNotes(long numberOfNotes) {
		this.numberOfNotes = numberOfNotes;
	}
	
}
