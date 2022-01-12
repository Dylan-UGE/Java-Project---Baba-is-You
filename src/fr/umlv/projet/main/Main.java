package fr.umlv.projet.main;

import java.awt.Color;
import java.io.IOException;

import fr.umlv.projet.event.Move;
import fr.umlv.projet.graphic.Draw;
import fr.umlv.projet.level.FileToBoard;
import fr.umlv.projet.rule.ApplieRule;
import fr.umlv.projet.rule.Rules;
import fr.umlv.projet.structure.GameBoard;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ScreenInfo;

/**
 * Main-Class.
 */
public class Main {
	/**
	 * main method of the Main-Class.
	 * 
	 * @param args list of argument
	 */
	public static void main(String[] args) {
		Application.run(Color.BLACK, context -> {
			ScreenInfo screenInfo = context.getScreenInfo();
		    Float width = screenInfo.getWidth();
		    Float height = screenInfo.getHeight();
		    
		    GameBoard board;
		    
		    for (var i = 0; i != 8; i++) {
				try {
					board = FileToBoard.createGameBoard("level" + i + ".txt");
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
					return;
				}
				
			    var drawing = new Draw(board, width.intValue(), height.intValue());
			    drawing.drawPicture(context);
			    
			    var rules = new Rules();
			    
			    for (;;) {
			    	rules.findRule(board);
			    	
			    	ApplieRule.applieRule(board, rules);
			    	
			    	if (rules.isAlive(board) == false) {
			    		drawing.drawPicture(context);
			    		drawing.drawText(context, 0);
			    		drawing.wait(2000);
			    		context.exit(0);
			    	}
			    	
			    	if (rules.isWin(board) == true) {
			    		drawing.drawPicture(context);
			    		drawing.drawText(context, 1);
			    		drawing.wait(2000);
			    		break;
			    	}	
			    	
			    	Move.moves(board, context, rules);
			        
			        drawing.drawPicture(context);
			        drawing.wait(50);
			    }
		    }
		    
		    context.exit(0);
		});
	}
}
