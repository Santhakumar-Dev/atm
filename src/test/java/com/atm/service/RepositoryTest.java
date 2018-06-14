package com.atm.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.atm.model.Notes;
import com.atm.model.Notes.Denom;


import junit.framework.TestCase;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.SamePropertyValuesAs.*;


import static org.junit.Assert.*;


public class RepositoryTest extends TestCase {

	private Repository repo = new Repository();
	
	@Test
	public void testInitialise() {
		
		repo.initialise();
		assert(repo.getAvailableNotes().isEmpty());
	}

	@Test
	public void testAddNotes() throws Exception{

		List<Notes> notes = new ArrayList<>();
		
		notes.add(new Notes(Denom.HUNDRED, 5));
		notes.add(new Notes(Denom.FIFTY, 5));
		
		long totalAmount = (100 * 5) + (50 * 5);
		
		repo.initialise();
		
		repo.addNotes(notes);
		
		assertEquals(repo.getAvailableAmount(), totalAmount);
		
		
	}

	@Test
	public void testWithdraw() throws Exception{

		List<Notes> notes = new ArrayList();
		List<Notes> expectedNotes = new ArrayList<>();
		
		notes.add(new Notes(Denom.HUNDRED, 5));
		notes.add(new Notes(Denom.FIFTY, 5));
		
		expectedNotes.add(new Notes(Denom.HUNDRED, 1));
		expectedNotes.add(new Notes(Denom.FIFTY, 1));
		
		repo.initialise();
		
		repo.addNotes(notes);
		
		List<Notes> dispensedNotes = repo.withdraw(150);
		
		assertThat(dispensedNotes, samePropertyValuesAs(expectedNotes));
		
	}

	@Test
	public void testGetAvailableNotes() throws Exception{
	
		List<Notes> notes = new ArrayList();
				
		notes.add(new Notes(Denom.HUNDRED, 5));
		notes.add(new Notes(Denom.FIFTY, 5));
		
		repo.initialise();
		
		repo.addNotes(notes);
		
		List<Notes> availableNotes = repo.getAvailableNotes();
		
		assertThat(availableNotes, samePropertyValuesAs(notes));

		
	}
	
	
	@Test
	public void testWithdraw_InvalidAmount() throws Exception{
		
		List<Notes> notes = new ArrayList();
				
		notes.add(new Notes(Denom.HUNDRED, 5));
		notes.add(new Notes(Denom.FIFTY, 5));
		notes.add(new Notes(Denom.TWENTY, 5));
		
	
		repo.initialise();
		
		repo.addNotes(notes);
		
		try{
			List<Notes> dispensedNotes = repo.withdraw(80);
		
			fail();
		}
		catch(Exception ex){
			assertNotNull(ex.getMessage());
			
		}
	}

}
