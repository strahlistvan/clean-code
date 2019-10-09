package com.floppylab.refactor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ResourceReader {

	/**
	 * Reading text file line by line.
	 * 
	 * @param inputFilePath path of the input resource file
	 * @return List of words in the input file
	 * @throws IOException
	 **/
	public static ArrayList<String> readWords(String inputFilePath) throws IOException {

		String line;
		ArrayList<String> wordList = new ArrayList<String>();
		InputStream inputStream = Hangman.class.getResourceAsStream((inputFilePath));

		if (inputStream == null) {
			System.err.println("Resource does not exist: " + inputFilePath);
			throw new IOException();
		}

		InputStreamReader stremReader = new InputStreamReader(inputStream);
		BufferedReader inputFile = new BufferedReader(stremReader);

		while ((line = inputFile.readLine()) != null) {
			wordList.add(line);
		}
		return wordList;
	}
}
