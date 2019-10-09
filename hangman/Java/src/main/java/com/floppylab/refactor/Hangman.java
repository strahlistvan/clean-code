package com.floppylab.refactor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Hangman {

	/**
	 * Group the given word list by the length of words. *
	 * 
	 * @param wordList ArrayList of words
	 * @return Map of wordLists indexed by the word length
	 */
	public static HashMap<Integer, ArrayList<String>> groupWordsByLength(ArrayList<String> wordList) {
		HashMap<Integer, ArrayList<String>> wordsByLength = new HashMap<Integer, ArrayList<String>>();

		if (wordList == null) {
			return wordsByLength;
		}

		for (int i = 0; i < wordList.size(); i++) {
			int length = wordList.get(i).length();
			if (!wordsByLength.containsKey(length)) {
				wordsByLength.put(length, new ArrayList<>());
			}
			wordsByLength.get(length).add(wordList.get(i));
		}
		return wordsByLength;
	}

	/** Read numbers until not found at least one word 
	 *  with the given length in dictionary.
	 * 
	 * @param scanner user input scanner object
	 * @param wordsByLength Map of wordLists indexed by the word length
	 * @return read number
	 */
	public static Integer readValidLetterCount(
			Scanner scanner, 
			HashMap<Integer, ArrayList<String>> wordsByLength )
	{
		System.out.print("Number of letters in the word? ");
		int letterCount = scanner.nextInt();

		while (!wordsByLength.containsKey(letterCount)) {
			System.out.print("Sorry, no words like that.\n");
			System.out.print("Number of letters in the word? ");
			letterCount = scanner.nextInt();
		}
		return letterCount;
	}
	
	/** Choose a random word with the given length
	 * 
	 * @param letterCount the length of the random word
	 * @param wordsByLength Map of wordLists indexed by the word length
	 * @return random word from wordsByLength dictionary
	 */
	public static String chooseRandomWord(
			Integer letterCount,
			HashMap<Integer, ArrayList<String>> wordsByLength ) 
	{
		ArrayList<String> wordListWithLength = wordsByLength.get(letterCount);
		if (wordListWithLength == null) {
			return "";
		}
		int randIndex = wordsByLength.get(letterCount).size();
		return wordListWithLength.get(new Random().nextInt(randIndex));
	}
	
	/** Check if all letter guessed.
	 * 
	 * @param isLetterGuessed logical array. 
	 * Tells if the k-th element is already guessed by user or not.
	 * @return has any unguessed letter or not
	 */
	public static Boolean isDone(boolean[] isLetterGuessed) {
		for (int i = 0; i < isLetterGuessed.length; ++i) {
			if (!isLetterGuessed[i]) {
				return false;
			}
		}	
		return true;
	}

	/**
	 * Add the input character to the list if it the list not contains it.
	 * @param chr Current character guess
	 * @param previousChars Previously guessed character list
	 */
	public static void addCharToPreviouslyEnteredList(
			Character chr,
			ArrayList<Character> previousChars ) 
	{
		if (!previousChars.contains(chr)) {
			previousChars.add(chr);
		}
	}
	
	public static void main(final String... args) {

		String inputFilePath = "/words.in";
		final Scanner userInput = new Scanner(System.in);
		ArrayList<Character> previousChars = new ArrayList<Character>();

		try {

			ArrayList<String> wordList = ResourceReader.readWords(inputFilePath);

			HashMap<Integer, ArrayList<String>> wordsByLetterCount = groupWordsByLength(wordList);

			int letterCount = readValidLetterCount(userInput, wordsByLetterCount);
			String word = chooseRandomWord(letterCount, wordsByLetterCount);

			boolean[] isLetterGuessed = new boolean[word.length()];
			int mistakeCount = 0;
			boolean done = true;

			while (mistakeCount < 10) {

				done = isDone(isLetterGuessed);
				
				if (isDone(isLetterGuessed)) {
					break;
				}

				System.out.print("Guess a letter: ");
				char chr = userInput.next().charAt(0);
				
				while (previousChars.contains(chr)) {
					System.out.println("Sorry, " + chr + " is already guessed");
					addCharToPreviouslyEnteredList(chr, previousChars);

					System.out.print("Guess a letter: ");
					chr = userInput.next().charAt(0);
				}

				addCharToPreviouslyEnteredList(chr, previousChars);
				
				boolean hit = false;
				for (int i = 0; i < word.length(); ++i) {
					if (word.charAt(i) == chr && !isLetterGuessed[i]) {
						isLetterGuessed[i] = true;
						hit = true;
					}
				}
				if (hit) {
					System.out.print("Hit!\n");
				} else {

					System.out.printf("Missed, mistake #%d out of %d\n", mistakeCount + 1, 10);
					++mistakeCount;

					PrintUtil.printHangman(mistakeCount);
				}
				PrintUtil.printWord(word, isLetterGuessed);
			}

			PrintUtil.printFinalResult(done, word);

		} catch (IOException e) {
			System.err.println("ERROR - Could not read file " + inputFilePath);
			e.printStackTrace();
		}
		userInput.close();
	}

}
