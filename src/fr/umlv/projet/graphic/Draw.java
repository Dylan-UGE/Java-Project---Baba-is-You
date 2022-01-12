package fr.umlv.projet.graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.umlv.projet.structure.Decor;
import fr.umlv.projet.structure.Element;
import fr.umlv.projet.structure.GameBoard;
import fr.umlv.zen5.*;

/**
 * The class for the graphical part.
 */
public class Draw {
	private final GameBoard board;
	private final int StepX;
	private final int StepY;
	private Image picture;

	/**
	 * Constructor of the class.
	 * 
	 * @param board The game board.
	 * @param WindowSizeX The length of the window.
	 * @param WindowSizeY The width of the window.
	 */
	public Draw(GameBoard board, int WindowSizeX, int WindowSizeY) {
		this.board = board;
		this.StepX = WindowSizeX / board.NbCaseX();
		this.StepY = WindowSizeY / board.NbCaseY();
	}
	
	
	/**
	 * Draw the text if the player win or lose.
	 * 
	 * @param context Where draw the text.
	 * @param choice 1 for the win text and 0 for the lose text.
	 */
	public void drawText(ApplicationContext context, int choice) {
		context.renderFrame(graphics -> {
			graphics.setColor(Color.WHITE);
			graphics.setFont(new Font("TimesRoman", Font.PLAIN, 34));
			
			if (choice == 1)
				graphics.drawString("CONGRATULATION, YOU HAVE WIN", ((board.NbCaseX() - 13) * StepX) / 2, StepY * 5);
			
			else if (choice == 0)
				graphics.drawString("GAME OVER", ((board.NbCaseX() - 5) * StepX) / 2, StepY * 5);
		});
	}
	
	
	/**
	 * Stop ms milliseconds the program.
	 * 
	 * @param ms The number in ms to wait.
	 */
	public void wait(int ms) {
		try {
			Thread.sleep(ms);
		} 
		
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Build the path where the image associate to value is stock.
	 * 
	 * @param value The element for build the path.
	 * 
	 * @return The string who represent the path.
	 */
	public static String path(Element value) {
		var path = new StringBuilder("src/img/" + value.toString().toUpperCase());
		
		if (value instanceof Decor)
			path.append("/").append(value.toString().toUpperCase()).append(".gif");
		
		else
			path.append("/Text_").append(value.toString().toUpperCase()).append(".gif");
		
		var stringPath = path.toString();
		
		return stringPath;
	}


	/**
	 * Draw the game.
	 * 
	 * @param context Where draw pictures.
	 */
	public void drawPicture(ApplicationContext context) {
		context.renderFrame(graphics -> {
			graphics.setColor(Color.BLACK);
	        graphics.fillRect(0, 0, board.NbCaseX() * StepX, board.NbCaseY() * StepY);
	        
			for (var key : board.board().keySet()) {
				var TabValue = board.board().get(key);
				
				if (TabValue == null || TabValue.isEmpty())
					continue;
				
				for (var value : TabValue) {
					try {
						picture = ImageIO.read(new File(path(value)));
					} catch (IOException e) {
						e.printStackTrace();
					}
		
					graphics.drawImage(picture, StepX * key.x(), StepY * key.y(), StepX, StepY, null);
				}
			}
	     });
	}
}
