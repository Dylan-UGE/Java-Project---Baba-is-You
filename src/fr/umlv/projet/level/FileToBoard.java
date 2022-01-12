package fr.umlv.projet.level;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import fr.umlv.projet.graphic.Case;
import fr.umlv.projet.structure.GameBoard;
import fr.umlv.projet.structure.Property;
import fr.umlv.projet.structure.Decor;
import fr.umlv.projet.structure.Name;
import fr.umlv.projet.structure.Operator;

/**
 * This class permit to convert a
 * txt file into a level of the game.
 */
public class FileToBoard {
	/**
	 * Create a game board from a level file.
	 * 
	 * @param levelname The name of the level to build.
	 * 
	 * @throws IOException If there is a problem with the BufferedReader.
	 * 
	 * @return The complete game board.
	 */
	public static GameBoard createGameBoard(String levelname) throws IOException {
		var reader = new BufferedReader(new FileReader("src/worlds/world1/" + levelname));
		var line = reader.readLine();
		var words = line.split(" ");
		var board = new GameBoard(Integer.decode(words[0]), Integer.decode(words[1]));
		
		for (line = reader.readLine(); line != null; line = reader.readLine()) {
			if (line.isEmpty() == false) {
				words = line.split(" ");
				
				var cas = new Case(Integer.decode(words[0]), Integer.decode(words[1]));	
				
				switch(words[2]) {
					case "Name" : board.add(cas, Name.valueOf(words[3]));
						break;
					case "Decor" : board.add(cas, Decor.valueOf(words[3]));
						break;
					case "Operator" : board.add(cas, Operator.valueOf(words[3]));
						break;
					case "Property" : board.add(cas, Property.valueOf(words[3]));
						break;
				}
			}
		}
		
		reader.close();
		
		return board;
	}
}
