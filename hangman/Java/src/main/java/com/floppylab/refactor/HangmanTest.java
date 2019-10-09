package com.floppylab.refactor;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HangmanTest {

	ArrayList<String> wordList;
	HashMap<Integer, ArrayList<String>> wordMap;
	
	@Before
	public void setUp() throws Exception {
		wordList = new ArrayList<String>( Arrays.asList( "this", 
														 "is",
														 "just",
														 "a",
														 "test",
														 "list") );
		
		ArrayList<String> list1char = new ArrayList<String>(Arrays.asList("a"));
		ArrayList<String> list2char = new ArrayList<String>(Arrays.asList("is"));
		ArrayList<String> list4char = new ArrayList<String>(Arrays.asList("this", 
																		  "just",
																		  "test",
																		  "list"));
		wordMap = new HashMap<Integer, ArrayList<String>>();
		wordMap.put(1, list1char);
		wordMap.put(2, list2char);
		wordMap.put(4, list4char);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void readWordsTest() throws IOException {
		ArrayList<String> words = ResourceReader.readWords("/words4test.in");
		assertEquals("Readed exactly 4 words", 4, words.size());
	}

	@Test
	public void readEmptyResouce() throws IOException {
		ArrayList<String> words = ResourceReader.readWords("/empty.in");
		assertTrue("Readed an empty list", words.isEmpty());
	}

	@Test(expected = IOException.class)
	public void readNonExistingResource() throws IOException {
		ResourceReader.readWords("/notexist.in");
	}
	
	@Test
	public void groupWordsByLengthNonEmptyList() {
		ArrayList<String> wordList = new ArrayList<String>();
		HashMap<Integer, ArrayList<String>> wordsByLength = Hangman.groupWordsByLength(wordList);
		assertTrue("Is words by length map empty", wordsByLength.isEmpty());
	}
	
	@Test
	public void groupWordsByLengthNullList() {
		HashMap<Integer, ArrayList<String>> wordsByLength = Hangman.groupWordsByLength(null);
		assertTrue("Is words by length map empty", wordsByLength.isEmpty());
	}

	@Test
	public void chooseRandomWord4Chars() {
		String word = Hangman.chooseRandomWord(4, wordMap);
		assertEquals(4, word.length());
	}

	@Test
	public void chooseRandomWordNonExist() {
		String word = Hangman.chooseRandomWord(0, wordMap);
		assertEquals(0, word.length());
	}
	
	@Test
	public void addCharToEmptyPreviousList() {
		ArrayList<Character> previousChars = new ArrayList<Character>();
		Hangman.addCharToPreviouslyEnteredList('a', previousChars);
		assertEquals("Add character to empty list", 1, previousChars.size());
	}

	@Test
	public void addDuplicateCharToEmptyPreviousList() {
		ArrayList<Character> previousChars = new ArrayList<Character>('a');
		Hangman.addCharToPreviouslyEnteredList('a', previousChars);
		assertEquals("Add duplicate character to empty list", 1, previousChars.size());
	}
}
