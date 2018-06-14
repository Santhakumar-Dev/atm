package com.atm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.atm.model.Notes;
import com.atm.model.Notes.Denom;

public class Repository {

	
	Map<Notes.Denom, Long> repo = new HashMap<>();
	
	
	public void initialise(){
		
		repo.clear();
	}
	
	public void addNotes(List<Notes> cash) throws Exception{
		
		validate(cash);
			
		for(Notes Note : cash){
			
			long availableNotes = 0;
			
			if(repo.get(Note.getDenom()) != null){
				availableNotes = repo.get(Note.getDenom());
			}
			
			repo.put(Note.getDenom(), availableNotes + Note.getNumberOfNotes());
		}
		
	}
	
	private void validate(List<Notes> cash) throws Exception{
		
		for(Notes Note : cash){
			
			if(Note.getDenom() == null || Note.getNumberOfNotes() < 0){
				
				throw new Exception("Invalid Cash");
			}
		}
		
	}
	
	
	public List<Notes> withdraw(long value) throws Exception {
        
		validateWithdraw(value);
		
		List<Notes> notesToBeDispensed = calculateWithdraw(value);
		
		dispense(notesToBeDispensed);
		
		return notesToBeDispensed;
		
    }
	
	
	
	private void dispense(List<Notes> notesToBeDispensed) {
		
		for(Notes note : notesToBeDispensed){
			
			repo.put(note.getDenom(), repo.get(note.getDenom()) - note.getNumberOfNotes());
		}
		
	}
	
	private List<Notes> calculateWithdraw(long value) throws Exception{
		
		List<Notes> notesToBeDispensed = new ArrayList<>();
		
		long balance = value;
		
		for(Denom denom : Denom.values()){
			
			if(repo.get(denom) != null){
			
				long noOfNotes = calculateNoOfNotes(balance, denom);
				
				if(noOfNotes > 0){
					
					Notes notes = new Notes(denom, noOfNotes);
					notesToBeDispensed.add(notes);
				}
				
				balance = balance - (noOfNotes * denom.getValue());
				
				if(balance == 0){
					break;
				}
			}
		}
		
        if (balance > 0) {
            throw new Exception("The amount cannot be dispensed with the available combination.");
        }

       return notesToBeDispensed;
	}
	
	
	private long calculateNoOfNotes(long amount, Denom denom){
		
		long noOfNotes = amount / denom.getValue();
		
		if(noOfNotes > repo.get(denom)){
			noOfNotes = 0;
		}
		
		return noOfNotes;
	}

    private void validateWithdraw(long value) throws Exception {
    	
        if (value <= 0)
            throw new Exception("Invalid Amount.");
        if (value > getAvailableAmount())
            throw new Exception("Insufficient Amount.");
        if (!isValidAmount(value))
            throw new Exception("Amount not a multiple of valid denominations.");
    }

    private boolean isValidAmount(long value) {
        for (Notes.Denom denom : Notes.Denom.values()) {
            if (value % denom.getValue() == 0)
                return true;
        }
        return false;
    }

    /*public synchronized long getMinimalWithdrawValue() {
        for (Cash.Note note : Cash.Note.values()) {
            Dispenser dispenser = box.get(note);
            if (dispenser.getNumberOfNotes() > 0)
                return note.getValue();
        }
        return 0;
    }

    public synchronized boolean hasEnoughCashFor(long value) {
        return availableMoney() >= value;
    }

    public synchronized long sumInCash() {
        return availableMoney();
    }*/

    public long getAvailableAmount() {
    	
        long availableAmount = 0;
        
        for (Notes.Denom denom : Notes.Denom.values()) {
        	
        	if(repo.get(denom) != null){
        		availableAmount = availableAmount + repo.get(denom) * denom.getValue();
        	}
        }
        return availableAmount;
    }
    
    
    public List<Notes> getAvailableNotes() {
        
    	List<Notes> availableNotes = new ArrayList<>();
    	
    	if(!repo.isEmpty()){
	        for (Notes.Denom denom : Notes.Denom.values()) {
	           
	        	if(repo.get(denom) != null){
	        		availableNotes.add(new Notes(denom, repo.get(denom)));
	        	}
	        }
    	}
        return availableNotes;
    }
}
