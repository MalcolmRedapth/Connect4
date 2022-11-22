
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.awt.Font;

public class SU23232862 {
	// Board size
	static int X = 6;
	static int Y = 7;
	// This variable can be used to turn your debugging output on and off.
	// Preferably use
	static boolean DEBUG = true;
	// Shows the number of special tokens a player has left
	static int[] p1powers = { 2, 2, 2 };
	static int[] p2powers = { 2, 2, 2 };
	static int[] curppowers = new int[3];
	static int pos = -1;
	// Shows which player's turn it is, true for player 1 and false for
	// player 2
	static boolean player1 = true;
	// The 6-by-7 grid that represents the gameboard, it is initially empty
	static int[][] grid = new int[X][Y];
	static int winner[] = { 0, 0 };
	// Gui is used to determine if the game is played in the terminal or with the User Interface
	static int Gui = 0;

	public static void main(String[] args) {
		String mode = args[0];
		boolean gui = false;
		// use argument launch variable "G: to launch with Gui and "T" to launch in terminal
		if (mode.equals("G")) {
			gui = true;
		}
		if (mode.equals("T")) {
			gui = false;
		}

		int input = -1;
		int numberOfGames = 1;

		
		if (!gui) {
			// Terminal mode

			// Display 10 line title
			StdOut.println("****************************************************************************");
			StdOut.println("*  _______  _______  __    _  __    _  _______  _______  _______  _   ___  *"
					+ "\n* |       ||       ||  |  | ||  |  | ||       ||       ||       || | |   | *"
					+ "\n* |       ||   _   ||   |_| ||   |_| ||    ___||       ||_     _|| |_|   | *"
					+ "\n* |       ||  | |  ||       ||       ||   |___ |       |  |   |  |       | *"
					+ "\n* |      _||  |_|  ||  _    ||  _    ||    ___||      _|  |   |  |___    | *"
					+ "\n* |     |_ |       || | |   || | |   ||   |___ |     |_   |   |      |   | *"
					+ "\n* |_______||_______||_|  |__||_|  |__||_______||_______|  |___|      |___| *");
			StdOut.println("*                                                                          *");
			StdOut.println("****************************************************************************");
			

			while (true) {
				Options();
				// Read in chosen move, check that the data is numeric
				boolean flag = false;
				while (flag == false) {
					try {
						input = StdIn.readInt();
					} catch (Exception ex) {
						StdOut.println("Please choose a valid option");
						Display(grid);
						Options();
						continue;
					}
					if (input >= 0 && input < 7) {
						flag = true;
					} else {
						StdOut.println("Please choose a valid option");
						Display(grid);
						Options();
						continue;
					}
				}
				switch (input) {
				case 0:
					Exit();
					break;

				case 1:
					Input();
					PlaceColumn();
					break;

				case 2:
					// Read in chosen column
					// Check that value is within the given bounds, check
					// that the data is numeric
					Input();
					if (curppowers[0] > 0) {
						// Play bomb disc in chosen column and reduce the
						// number of bombs left
						Bomb(Gui);
						// Check that player has bomb discs left to play,
						// otherwise print out an error message
					} else {
						StdOut.println("You have no bomb power discs left");
						player1 = !player1;
					}
					break;

				case 3:
					// Read in chosen column
					// Check that value is within the given bounds, check
					// that the data is numeric
					Input();
					if (curppowers[1] > 0) {
						//  Play teleport disc in chosen column and reduce the
						// number of teleporters left
						Teleporter(Gui);
						// Check that player has teleport discs left to play,
						// otherwise print out an error message
					} else {
						StdOut.println("You have no teleporter power discs left");
						player1 = !player1;
					}
					break;

				case 4:
					// Read in chosen column
					// Check that value is within the given bounds, check
					// that the data is numeric
					Input();
					if (curppowers[2] > 0) {
						// Play Colour Change disc in chosen column and reduce
						// the number of colour changers left
						ColourChanger();
						// Check that player has Colour Change discs left to
						// play, otherwise print out an error message
					} else {
						StdOut.println("You have no colour changer power discs left");
						player1 = !player1;
					}
					break;

				case 5:
					Display(grid);
					// Displays the current gameboard again, doesn't count as a
					// move, so the player stays the same
					player1 = !player1;
					break;

				case 6:
					grid = Test(StdIn.readString());
					// Reads in a test file and loads the gameboard from it.
					player1 = !player1;
					break;
				// This part will be used during testing, please DO NOT change
				// it. This will result in loss of marks
				case 7:
					Save(StdIn.readString(), grid);
					player1 = !player1;
					break;

				default: // Invalid choice was made, print out an error
					StdOut.println("Please choose a valid option");
					// message but do not switch player turns
					break;
				}
				// Displays the grid after a new move was played
				Display(grid);
				// Checks whether a winning condition was met
				CheckWin();
				int winner1 = winner[0];
				int winner2 = winner[1];
				int counter = 0;
				for (int i = 0; i < 7; i++) {
					if (grid[0][i] == 0) {
						counter++;
					}
				}
				if (winner1 == 1 || winner2 == 2 || counter == 0) {
					if (winner1 == 1 && winner2 != 2) {
						StdOut.println("Player 1 wins!");
					} else if (winner2 == 2 && winner1 != 1) {
						StdOut.println("Player 2 wins!");
					} else if (counter == 0 || (winner[0] == 1 && winner[1] == 2)) {
						StdOut.println("Draw!");

					}
					StdOut.println("Do you want to play again? \n 1. Yes \n 2. No");
					int restart = 0;
					flag = true;
					while (flag) {
						try {
							restart = StdIn.readInt();
						} catch (Exception ex) {
							StdOut.println("Choose either 1 for Yes or 2 for No:");
						}
						if (restart == 1 || restart == 2) {
							flag = false;
						} else {
							StdOut.println("Choose either 1 for Yes or 2 for No:");
						}

					}
					if (restart == 2) {
						Exit();
					} else if (restart == 1) {
						numberOfGames++;
						for (int i = 0; i < 6; i++) {
							for (int j = 0; j < 7; j++) {
								grid[i][j] = 0;
							}
						}
						if (numberOfGames % 2 == 0) {
							player1 = true;
						} else {
							player1 = false;
						}
						for (int i = 0; i < 3; i++) {
							p1powers[i] = 2;
							p2powers[i] = 2;
						}
					}
				}

				// Switch the players turns
				player1 = !player1;

			}
		}

		else {
			Gui = 1;
			// Graphics mode
			StdDraw.enableDoubleBuffering();
			StdDraw.setCanvasSize(975, 925);
			StdDraw.setXscale(0, 10);
			StdDraw.setYscale(0, 10);
			// Animation
			Animation();

			//
			StdDraw.setPenColor(StdDraw.DARK_GRAY);
			StdDraw.filledSquare(5, 5, 5);

			GuiDisplay();
			int select = 3;
			while (true) {
				if (player1) {
					curppowers = p1powers;
				} else {
					curppowers = p2powers;
				}

				Font newfont = new Font("Arial", Font.BOLD, 18);
				// Read in chosen move

				int choice = 1;
				input = -1;
				int showop = 0;

				pos = -1;
				// take in
				play: while (!StdDraw.isKeyPressed(' ')) {
					if (choice != 0 && StdDraw.isMousePressed() && StdDraw.mouseX() > 0.5 && StdDraw.mouseX() < 7.5
							&& StdDraw.mouseY() > 0.5 && StdDraw.mouseY() < 6.5) {
						while (StdDraw.isMousePressed()) {

						}
						break;

					}
					
					if (StdDraw.mouseX() > 8.1 && StdDraw.mouseX() < 9.6 && StdDraw.mouseY() > 6.05
							&& StdDraw.mouseY() < 6.65) {
						if (StdDraw.isMousePressed()) {
							choice = 1;
						}
					} else if (StdDraw.mouseX() > 8.1 && StdDraw.mouseX() < 9.6 && StdDraw.mouseY() > 4.8
							&& StdDraw.mouseY() < 5.4) {
						if (StdDraw.isMousePressed()) {

							choice = 2;
						}
					} else if (StdDraw.mouseX() > 8.1 && StdDraw.mouseX() < 9.6 && StdDraw.mouseY() > 3.55
							&& StdDraw.mouseY() < 4.15) {
						if (StdDraw.isMousePressed()) {

							choice = 3;
						}
					} else if (StdDraw.mouseX() > 8.1 && StdDraw.mouseX() < 9.6 && StdDraw.mouseY() > 2.3
							&& StdDraw.mouseY() < 2.9) {
						if (StdDraw.isMousePressed()) {

							choice = 4;
						}
					} else if (StdDraw.mouseX() > 8.1 && StdDraw.mouseX() < 9.6 && StdDraw.mouseY() > 0.3
							&& StdDraw.mouseY() < 0.9) {
						if (StdDraw.isMousePressed()) {

							choice = 0;

						}
					} else if (StdDraw.isKeyPressed(38)) {
						choice = choice - 1;
						if (choice == -1) {
							choice = 4;
						}
						while (StdDraw.isKeyPressed(38)) {

						}

					} else if (StdDraw.isKeyPressed(40)) {
						choice = choice + 1;
						if (choice == 5) {
							choice = 0;
						}
						while (StdDraw.isKeyPressed(40)) {

						}
					}
					//
					if (StdDraw.mouseX() > 0.5 && StdDraw.mouseX() < 1.5 && StdDraw.mouseY() > 0.5
							&& StdDraw.mouseY() < 6.5) {
						select = 0;
					} else if (StdDraw.mouseX() > 1.5 && StdDraw.mouseX() < 2.5 && StdDraw.mouseY() > 0.5
							&& StdDraw.mouseY() < 6.5) {
						select = 1;
					} else if (StdDraw.mouseX() > 2.5 && StdDraw.mouseX() < 3.5 && StdDraw.mouseY() > 0.5
							&& StdDraw.mouseY() < 6.5) {
						select = 2;
					} else if (StdDraw.mouseX() > 3.5 && StdDraw.mouseX() < 4.5 && StdDraw.mouseY() > 0.5
							&& StdDraw.mouseY() < 6.5) {
						select = 3;
					} else if (StdDraw.mouseX() > 4.5 && StdDraw.mouseX() < 5.5 && StdDraw.mouseY() > 0.5
							&& StdDraw.mouseY() < 6.5) {
						select = 4;
					} else if (StdDraw.mouseX() > 5.5 && StdDraw.mouseX() < 6.5 && StdDraw.mouseY() > 0.5
							&& StdDraw.mouseY() < 6.5) {
						select = 5;
					} else if (StdDraw.mouseX() > 6.5 && StdDraw.mouseX() < 7.5 && StdDraw.mouseY() > 0.5
							&& StdDraw.mouseY() < 6.5) {
						select = 6;
					}

					if (StdDraw.isKeyPressed('1')) {
						while (StdDraw.isKeyPressed('1')) {
							select = 0;
						}

					} else if (StdDraw.isKeyPressed('2')) {
						while (StdDraw.isKeyPressed('2')) {
							select = 1;
						}
					} else if (StdDraw.isKeyPressed('3')) {
						while (StdDraw.isKeyPressed('3')) {
							select = 2;
						}
					} else if (StdDraw.isKeyPressed('4')) {
						while (StdDraw.isKeyPressed('4')) {

							select = 3;
						}
					} else if (StdDraw.isKeyPressed('5')) {
						while (StdDraw.isKeyPressed('5')) {

							select = 4;
						}
					} else if (StdDraw.isKeyPressed('6')) {
						while (StdDraw.isKeyPressed('6')) {

							select = 5;
						}
					} else if (StdDraw.isKeyPressed('7')) {
						while (StdDraw.isKeyPressed('7')) {

							select = 6;
						}
					} else if (StdDraw.isKeyPressed(37)) {
						select = select - 1;
						if (select == -1) {
							select = 6;
						}
						while (StdDraw.isKeyPressed(37)) {

						}

					} else if (StdDraw.isKeyPressed(39)) {
						select = select + 1;
						if (select == 7) {
							select = 0;
						}
						while (StdDraw.isKeyPressed(39)) {

						}
					}

					// reset arrows
					GuiDisplay();
					StdDraw.setPenColor(StdDraw.DARK_GRAY);
					if (select != 0) {
						double[] x = { 0.4, 1, 1.6, 1 };
						double[] y = { 7.55, 7.3, 7.55, 6.95 };
						StdDraw.filledPolygon(x, y);
					}
					if (select != 1) {
						double[] x = { 1.4, 2, 2.6, 2 };
						double[] y = { 7.55, 7.3, 7.55, 6.95 };
						StdDraw.filledPolygon(x, y);
					}
					if (select != 2) {
						double[] x = { 2.4, 3, 3.6, 3 };
						double[] y = { 7.55, 7.3, 7.55, 6.95 };
						StdDraw.filledPolygon(x, y);
					}
					if (select != 3) {
						double[] x = { 3.4, 4, 4.6, 4 };
						double[] y = { 7.55, 7.3, 7.55, 6.95 };
						StdDraw.filledPolygon(x, y);
					}
					if (select != 4) {
						double[] x = { 4.4, 5, 5.6, 5 };
						double[] y = { 7.55, 7.3, 7.55, 6.95 };
						StdDraw.filledPolygon(x, y);
					}
					if (select != 5) {
						double[] x = { 5.4, 6, 6.6, 6 };
						double[] y = { 7.55, 7.3, 7.55, 6.95 };
						StdDraw.filledPolygon(x, y);
					}
					if (select != 6) {
						double[] x = { 6.4, 7, 7.6, 7 };
						double[] y = { 7.55, 7.3, 7.55, 6.95 };
						StdDraw.filledPolygon(x, y);
					}
					
					// show change

					StdDraw.setPenRadius(0.125);
					StdDraw.setPenColor(StdDraw.BOOK_BLUE);
					if (choice == 0) {
						StdDraw.line(8.1, 0.6, 9.6, 0.6);
					} else if (choice == 1) {
						StdDraw.line(8.1, 6.35, 9.6, 6.35);
					} else if (choice == 2) {
						StdDraw.line(8.1, 5.1, 9.6, 5.1);
					} else if (choice == 3) {
						StdDraw.line(8.1, 3.85, 9.6, 3.85);
					} else if (choice == 4) {
						StdDraw.line(8.1, 2.6, 9.6, 2.6);
					}

					
					StdDraw.setPenRadius(0.005);
					StdDraw.setPenColor(StdDraw.GREEN);
					if (select == 0) {
						double[] x = { 0.5, 1, 1.5, 1 };
						double[] y = { 7.5, 7.25, 7.5, 7 };
						StdDraw.filledPolygon(x, y);
					} else if (select == 1) {
						double[] x = { 1.5, 2, 2.5, 2 };
						double[] y = { 7.5, 7.25, 7.5, 7 };
						StdDraw.filledPolygon(x, y);
					} else if (select == 2) {
						double[] x = { 2.5, 3, 3.5, 3 };
						double[] y = { 7.5, 7.25, 7.5, 7 };
						StdDraw.filledPolygon(x, y);
					} else if (select == 3) {
						double[] x = { 3.5, 4, 4.5, 4 };
						double[] y = { 7.5, 7.25, 7.5, 7 };
						StdDraw.filledPolygon(x, y);
					} else if (select == 4) {
						double[] x = { 4.5, 5, 5.5, 5 };
						double[] y = { 7.5, 7.25, 7.5, 7 };
						StdDraw.filledPolygon(x, y);
					} else if (select == 5) {
						double[] x = { 5.5, 6, 6.5, 6 };
						double[] y = { 7.5, 7.25, 7.5, 7 };
						StdDraw.filledPolygon(x, y);
					} else if (select == 6) {
						double[] x = { 6.5, 7, 7.5, 7 };
						double[] y = { 7.5, 7.25, 7.5, 7 };
						StdDraw.filledPolygon(x, y);
					}

					// affect change

					if (showop != choice) {
						StdDraw.show();
						showop = choice;
						StdDraw.setPenColor(StdDraw.DARK_GRAY);
						StdDraw.filledRectangle(8.85, 3.5, 2, 3.5);

					}

					if (StdDraw.isMousePressed() && choice == 0 && StdDraw.mouseX() > 8.1 && StdDraw.mouseX() < 9.6
							&& StdDraw.mouseY() > 0.3 && StdDraw.mouseY() < 0.9) {

						break play;

					}
				}
				while (StdDraw.isKeyPressed(' ')) {

				}
				input = choice;
				pos = select;
				switch (input) {
				case 0:
					int exit = 0;
					StdDraw.setPenRadius(0.125);
					StdDraw.setPenColor(StdDraw.BOOK_BLUE);
					StdDraw.line(8.1, 0.6, 9.6, 0.6);
					GuiDisplay();

					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.filledCircle(5.5, 5.5, 0.1);
					StdDraw.filledCircle(5.5, 2.5, 0.1);
					StdDraw.filledCircle(2.5, 2.5, 0.1);
					StdDraw.filledCircle(2.5, 5.5, 0.1);
					StdDraw.filledRectangle(5.5, 4, 0.1, 1.5);
					StdDraw.filledRectangle(2.5, 4, 0.1, 1.5);
					StdDraw.filledRectangle(4, 5.5, 1.5, 0.1);
					StdDraw.filledRectangle(4, 2.5, 1.5, 0.1);
					StdDraw.setPenColor(StdDraw.DARK_GRAY);
					StdDraw.filledSquare(4, 4, 1.55);
					//
					StdDraw.setPenColor(StdDraw.RED);
					StdDraw.text(4, 4.5, "Would like to Quit?");
					StdDraw.setPenColor(StdDraw.GREEN);
					StdDraw.filledCircle(3.24, 3.24, 0.35);
					StdDraw.setPenColor(StdDraw.WHITE);
					StdDraw.filledCircle(3.26, 3.26, 0.3);
					StdDraw.setPenColor(StdDraw.GREEN);
					StdDraw.filledCircle(3.24, 3.24, 0.3);
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.text(3.24, 3.24, "Yes");
					//
					StdDraw.setPenColor(StdDraw.YELLOW);
					StdDraw.filledCircle(4.72, 3.24, 0.35);
					StdDraw.setPenColor(StdDraw.WHITE);
					StdDraw.filledCircle(4.74, 3.26, 0.3);
					StdDraw.setPenColor(StdDraw.YELLOW);
					StdDraw.filledCircle(4.72, 3.24, 0.3);
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.text(4.72, 3.24, "No");
					StdDraw.show();
					while (exit == 0) {
						if (StdDraw.isMousePressed()) {
							if (StdDraw.mouseX() > 2.89 && StdDraw.mouseX() < 3.59 && StdDraw.mouseY() > 2.89
									&& StdDraw.mouseY() < 3.59) {
								while (StdDraw.isMousePressed()) {
									exit = 1;
								}
							} else if (StdDraw.mouseX() > 4.42 && StdDraw.mouseX() < 5.12 && StdDraw.mouseY() > 2.94
									&& StdDraw.mouseY() < 3.64) {
								while (StdDraw.isMousePressed()) {
									exit = 2;
								}
							}
						} else if (StdDraw.isKeyPressed(89)) {
							exit = 1;
						} else if (StdDraw.isKeyPressed(78)) {
							exit = 2;
						}
					}
					if (exit == 1) {
						Exit();
					}
					player1 = !player1;
					break;

				case 1:

					GuiPlaceColumn();
					break;

				case 2:
					// Read in chosen column
					// Check that value is within the given bounds, check
					// that the data is numeric

					if (curppowers[0] > 0) {
						// Play bomb disc in chosen column and reduce the
						// number of bombs left
						Bomb(Gui);
						// Check that player has bomb discs left to play,
						// otherwise print out an error message
					} else {
						StdOut.println("You have no bomb power discs left");
						player1 = !player1;
					}
					break;

				case 3:
					// Read in chosen column
					// Check that value is within the given bounds, check
					// that the data is numeric

					if (curppowers[1] > 0) {
						// Play teleport disc in chosen column and reduce the
						// number of teleporters left
						Teleporter(Gui);
						// Check that player has teleport discs left to play,
						// otherwise print out an error message
					} else {
						StdOut.println("You have no teleporter power discs left");
						player1 = !player1;
					}
					break;

				case 4:
					// Read in chosen column
					// Check that value is within the given bounds, check
					// that the data is numeric

					if (curppowers[2] > 0) {
						// Play Colour Change disc in chosen column and reduce
						// the number of colour changers left
						ColourChanger();
						// Check that player has Colour Change discs left to
						// play, otherwise print out an error message
					} else {
						StdOut.println("You have no colour changer power discs left");
						player1 = !player1;
					}
					break;

				case 6:
					grid = Test(StdIn.readString());
					// Reads in a test file and loads the gameboard from it.
					player1 = !player1;
					break;

					// Invalid choice was made, print out an error
				default: 
					StdOut.println("Please choose a valid option");
					// message but do not switch player turns
					break;
				}

				// Checks whether a winning condition was met
				CheckWin();
				int winner1 = winner[0];
				int winner2 = winner[1];
				int counter = 0;
				for (int i = 0; i < 7; i++) {
					if (grid[0][i] == 0) {
						counter++;
					}
				}
				if (winner1 == 1 || winner2 == 2 || counter == 0) {
					GuiDisplay();
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.filledCircle(5.5, 5.5, 0.1);
					StdDraw.filledCircle(5.5, 2.5, 0.1);
					StdDraw.filledCircle(2.5, 2.5, 0.1);
					StdDraw.filledCircle(2.5, 5.5, 0.1);
					StdDraw.filledRectangle(5.5, 4, 0.1, 1.5);
					StdDraw.filledRectangle(2.5, 4, 0.1, 1.5);
					StdDraw.filledRectangle(4, 5.5, 1.5, 0.1);
					StdDraw.filledRectangle(4, 2.5, 1.5, 0.1);
					if (winner1 == 1 && winner2 != 2) {
						StdDraw.setPenColor(StdDraw.DARK_GRAY);
						StdDraw.filledSquare(4, 4, 1.55);
						StdDraw.setPenColor(StdDraw.RED);
						StdDraw.setFont(newfont);
						StdDraw.text(4, 5, "Player 1 wins!");

						StdDraw.show();
					} else if (winner2 == 2 && winner1 != 1) {
						StdDraw.setPenColor(StdDraw.DARK_GRAY);
						StdDraw.filledSquare(4, 4, 1.5);
						StdDraw.setPenColor(StdDraw.YELLOW);
						StdDraw.setFont(newfont);
						StdDraw.text(4, 5, "Player 2 wins!");

						StdDraw.show();
					} else if (counter == 0 || (winner[0] == 1 && winner[1] == 2)) {
						StdDraw.setPenColor(StdDraw.DARK_GRAY);
						StdDraw.filledSquare(4, 4, 1.55);
						StdDraw.setPenColor(StdDraw.BOOK_BLUE);
						StdDraw.setFont(newfont);
						StdDraw.text(4, 5, "Draw!");
						StdDraw.show();

					}
					StdDraw.text(4, 4.5, "Would you like to play again?");
					StdDraw.setPenColor(StdDraw.GREEN);
					StdDraw.filledCircle(3.24, 3.24, 0.35);
					StdDraw.setPenColor(StdDraw.WHITE);
					StdDraw.filledCircle(3.26, 3.26, 0.3);
					StdDraw.setPenColor(StdDraw.GREEN);
					StdDraw.filledCircle(3.24, 3.24, 0.3);
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.text(3.24, 3.24, "Yes");
					//
					StdDraw.setPenColor(StdDraw.YELLOW);
					StdDraw.filledCircle(4.72, 3.24, 0.35);
					StdDraw.setPenColor(StdDraw.WHITE);
					StdDraw.filledCircle(4.74, 3.26, 0.3);
					StdDraw.setPenColor(StdDraw.YELLOW);
					StdDraw.filledCircle(4.72, 3.24, 0.3);
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.text(4.72, 3.24, "No");
					StdDraw.show();
					int restart = 0;
					// wait for input
					while (restart == 0) {
						if (StdDraw.isMousePressed()) {
							if (StdDraw.mouseX() > 2.89 && StdDraw.mouseX() < 3.59 && StdDraw.mouseY() > 2.89
									&& StdDraw.mouseY() < 3.59) {
								while (StdDraw.isMousePressed()) {
									restart = 1;
								}
							} else if (StdDraw.mouseX() > 4.42 && StdDraw.mouseX() < 5.12 && StdDraw.mouseY() > 2.94
									&& StdDraw.mouseY() < 3.64) {
								while (StdDraw.isMousePressed()) {
									restart = 2;
								}
							}

						} else if (StdDraw.isKeyPressed(89)) {
							restart = 1;

						} else if (StdDraw.isKeyPressed(78)) {
							restart = 2;

						}

					}

					if (restart == 2) {
						Exit();
					} else if (restart == 1) {
						numberOfGames++;
						for (int i = 0; i < 6; i++) {
							for (int j = 0; j < 7; j++) {
								grid[i][j] = 0;
							}
						}
						if (numberOfGames % 2 == 0) {
							player1 = true;
						} else {
							player1 = false;
						}
						for (int i = 0; i < 3; i++) {
							p1powers[i] = 2;
							p2powers[i] = 2;
						}
					}
				}

				// Switch the players turns
				player1 = !player1;

			}
		}
	}

	public static void Bomb(int Gui) {
		if (grid[0][pos] != 0) {

		} else {
			if (pos > 0 && pos < 6) {
				if (grid[5][pos] == 0) {
					for (int i = 0; i < 2; i++) {
						grid[5 - i][pos - 1] = 0;
						grid[5 - i][pos + 1] = 0;
					}
				} else if (grid[0][pos] == 0 && grid[1][pos] != 0) {
					for (int i = 0; i < 2; i++) {
						for (int j = -1; j < 2; j++) {
							grid[0 + i][pos + j] = 0;
						}
					}

				}

				else {
					standard: for (int i = 1; i < 5; i++) {
						if (grid[i + 1][pos] != 0) {
							for (int j = -1; j < 2; j++) {
								for (int k = -1; k < 2; k++) {
									grid[i + j][pos + k] = 0;
								}
							}
							break standard;
						}

					}
				}
			} else if (pos == 0) {
				if (grid[5][pos] == 0) {
					grid[5][pos + 1] = 0;
					grid[4][pos + 1] = 0;
				}

				else if (grid[0][pos] == 0 && grid[1][pos] != 0) {
					for (int i = 0; i < 2; i++) {
						for (int j = 0; j < 2; j++) {
							grid[0 + i][pos + j] = 0;
						}
					}

				}

				else {
					for (int i = 1; i < 5; i++) {
						if (grid[i + 1][pos] != 0) {
							for (int j = -1; j < 2; j++) {
								for (int k = 0; k < 2; k++) {
									grid[i + j][pos + k] = 0;
								}
							}
						}
					}
				}

			} else if (pos == 6) {
				if (grid[5][pos] == 0) {
					grid[5][pos - 1] = 0;
					grid[4][pos - 1] = 0;
				}

				else if (grid[0][pos] == 0 && grid[1][pos] != 0) {
					for (int i = 0; i < 2; i++) {
						for (int j = -1; j < 1; j++) {
							grid[0 + i][pos + j] = 0;
						}
					}

				}

				else {
					for (int i = 1; i < 5; i++) {

						if (grid[i + 1][pos] != 0) {
							for (int j = -1; j < 2; j++) {
								for (int k = 0; k < 2; k++) {
									grid[i + j][pos - k] = 0;
								}
							}
						}
					}
				}
			}

			// drop down
			for (int i = 0; i < 7; i++) {
				int count = 0;
				for (int j = 5; j > -1; j--) {
					if (grid[j][i] == 0) {
						count++;
					}
					if (grid[j][i] != 0 && count != 0) {
						int moved = grid[j][i];
						grid[j + count][i] = moved;
						grid[j][i] = 0;
					}
				}

			}
		}
		if (Gui == 1) {
			GuiPlaceColumn();
		} else {
			PlaceColumn();
		}

		if (player1) {
			p1powers[0] = curppowers[0] - 1;
		} else if (!player1) {
			p2powers[0] = curppowers[0] - 1;
		}
	}

	public static void Teleporter(int Gui) {
		int displace = 0;
		for (int i = 0; i < 6; i++) {
			if (grid[i][pos] != 0) {
				displace = grid[i][pos];
				if ((pos + 3) < 7) {
					for (int j = 0; j < 5; j++) {
						if (grid[5][pos + 3] == 0) {
							grid[5][pos + 3] = displace;
							break;
						}
						
						if (grid[0][pos + 3] != 0) {
							if(Gui == 0){
							StdOut.println("Column is full.");
							}
							break;
						
						}
						if (grid[j + 1][pos + 3] != 0) {
							grid[j][pos + 3] = displace;
							break;
						}
					}

				} else if ((pos + 3) >= 7) {
					for (int k = 0; k < 5; k++) {
						if (grid[5][pos - 4] == 0) {
							grid[5][pos - 4] = displace;
							break;
						}
						
						if (grid[0][pos - 4] != 0) {
							if(Gui ==0){
							StdOut.println("Column is full.");
							} 
							break;
						
						}
						if (grid[k + 1][pos - 4] != 0) {
							grid[k][pos - 4] = displace;
							break;
						}
					}

				}

				if (player1) {
					grid[i][pos] = 1;
				} else if (!player1) {
					grid[i][pos] = 2;
				}
				break;
			}

		}
		if (player1) {
			p1powers[1] = curppowers[1] - 1;
		} else if (!player1) {
			p2powers[1] = curppowers[1] - 1;
		}
	}

	public static void ColourChanger() {
		for (int i = 0; i < 5; i++) {
			if (grid[0][pos] != 0) {
				if (grid[0][pos] == 1) {
					grid[0][pos] = 2;
					break;
				} else if (grid[0][pos] == 2) {
					grid[0][pos] = 1;
					break;
				}
			}
			if (grid[i + 1][pos] != 0) {
				if (grid[i + 1][pos] == 1) {
					grid[i + 1][pos] = 2;
					break;
				} else if (grid[i + 1][pos] == 2) {
					grid[i + 1][pos] = 1;
					break;
				}
			} else if (grid[5][pos] == 0) {
				if (!player1) {
					grid[5][pos] = 1;
				} else if (player1) {
					grid[5][pos] = 2;
				}
			}
		}
		if (player1) {
			p1powers[2] = curppowers[2] - 1;
		} else if (!player1) {
			p2powers[2] = curppowers[2] - 1;
		}
	}

	public static void PlaceColumn() {
		if (grid[0][pos] != 0) {
			StdOut.println("Column is full.");
		} else {
			for (int i = 0; i < 5; i++) {
				if (grid[i + 1][pos] != 0) {
					if (player1) {
						grid[i][pos] = 1;

						break;
					} else {
						grid[i][pos] = 2;

						break;
					}
				}
			}
		}
		if (grid[5][pos] == 0) {
			if (player1) {
				grid[5][pos] = 1;
			} else {
				grid[5][pos] = 2;
			}

		}
	}

	public static void GuiPlaceColumn() {
		if (grid[0][pos] != 0) {
		} else {
			for (int i = 0; i < 5; i++) {
				if (grid[i + 1][pos] != 0) {
					if (player1) {
						grid[i][pos] = 1;

						break;
					} else {
						grid[i][pos] = 2;

						break;
					}
				}
			}
		}
		if (grid[5][pos] == 0) {
			if (player1) {
				grid[5][pos] = 1;
			} else {
				grid[5][pos] = 2;
			}

		}
	}

	public static void Input() {
		StdOut.println("Choose a column to play in:");
		boolean flag = true;
		while (flag) {
			try {
				pos = StdIn.readInt(); // TODO: Read in chosen column
			} catch (Exception ex) {
				StdOut.println("Illegal move, please input legal move:");
				continue;
			}
			if (pos >= 0 && pos < 7) {
				flag = false;
			} else {
				StdOut.println("Illegal move, please input legal move:");
			}
		}
	}

	public static void Options() {
		if (player1) {
			StdOut.println("Player 1's turn (You are Red):");
			curppowers = p1powers;
		} else {
			StdOut.println("Player 2's turn (You are Yellow):");
			curppowers = p2powers;
		}
		StdOut.println("Choose your move: \n 1. Play Normal \n 2. Play Bomb (" + curppowers[0]
				+ " left) \n 3. Play Teleportation (" + curppowers[1] + " left) \n 4. Play Colour Changer ("
				+ curppowers[2] + " left)\n 5. Display Gameboard \n 6. Load Test File \n 0. Exit");

	}

	public static void GuiDisplay() {

		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.filledCircle(8.1, 6.35, 0.3);
		StdDraw.filledCircle(9.6, 6.35, 0.3);
		StdDraw.filledRectangle(8.85, 6.35, 0.75, 0.3);
		Font newfont = new Font("Arial", Font.BOLD, 18);
		StdDraw.setFont(newfont);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(8.85, 6.35, "PLAY NORMAL");
		//

		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.filledCircle(8.1, 5.1, 0.3);
		StdDraw.filledCircle(9.6, 5.1, 0.3);
		StdDraw.filledRectangle(8.85, 5.1, 0.75, 0.3);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(8.85, 5.1, "PLAY BOMB");

		//
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.filledCircle(8.1, 3.85, 0.3);
		StdDraw.filledCircle(9.6, 3.85, 0.3);
		StdDraw.filledRectangle(8.85, 3.85, 0.75, 0.3);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(8.85, 3.85, "PLAY TELEPORTER");
		//

		//
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.filledCircle(8.1, 2.6, 0.3);
		StdDraw.filledCircle(9.6, 2.6, 0.3);
		StdDraw.filledRectangle(8.85, 2.6, 0.75, 0.3);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(8.85, 2.65, "PLAY COLOUR");
		StdDraw.text(8.85, 2.45, "CHANGER");
		//

		//
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.filledCircle(8.1, 0.6, 0.3);
		StdDraw.filledCircle(9.6, 0.6, 0.3);
		StdDraw.filledRectangle(8.85, 0.6, 0.75, 0.3);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(8.85, 0.6, "EXIT");

		//
		StdDraw.setPenColor(StdDraw.BLACK);
		Font titlefont = new Font("Arial", Font.BOLD, 125);
		StdDraw.setFont(titlefont);
		StdDraw.text(3.75, 8.5, "Connect 4");
		StdDraw.setPenRadius(0.03);
		StdDraw.line(0.6, 7.85, 6.9, 7.85);
		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledRectangle(8.85, 9.35, 0.75, 0.35);
		StdDraw.filledCircle(8.1, 9.35, 0.35);
		StdDraw.filledCircle(9.6, 9.35, 0.35);
		Font plfont = new Font("Arial", Font.BOLD, 18);
		StdDraw.setFont(plfont);
		if (player1) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.text(8.85, 9.35, "Player 1's turn:");
		} else if (!player1) {
			StdDraw.setPenColor(StdDraw.YELLOW);
			StdDraw.text(8.85, 9.35, "Player 2's turn:");
		}

		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledCircle(8.35, 3.225, 0.15);
		StdDraw.filledCircle(9.35, 3.225, 0.15);
		StdDraw.filledRectangle(8.85, 3.225, 0.5, 0.15);
		//
		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledCircle(8.35, 4.475, 0.15);
		StdDraw.filledCircle(9.35, 4.475, 0.15);
		StdDraw.filledRectangle(8.85, 4.475, 0.5, 0.15);
		//
		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		StdDraw.filledCircle(8.35, 1.975, 0.15);
		StdDraw.filledCircle(9.35, 1.975, 0.15);
		StdDraw.filledRectangle(8.85, 1.975, 0.5, 0.15);
		//
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.filledCircle(0.5, 0.5, 0.2);
		StdDraw.filledCircle(0.5, 6.5, 0.2);
		StdDraw.filledCircle(7.5, 0.5, 0.2);
		StdDraw.filledCircle(7.5, 6.5, 0.2);
		StdDraw.filledRectangle(0.5, 3.5, 0.2, 3);
		StdDraw.filledRectangle(7.5, 3.5, 0.2, 3);
		StdDraw.filledRectangle(4, 0.5, 3.5, 0.2);
		StdDraw.filledRectangle(4, 6.5, 3.5, 0.2);
		StdDraw.setPenColor(StdDraw.BLUE);
		StdDraw.filledRectangle(4, 3.5, 3.5, 3);
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				if (grid[5 - i][j] == 0) {
					StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
				} else if (grid[5 - i][j] == 1) {
					StdDraw.setPenColor(StdDraw.RED);
				} else if (grid[5 - i][j] == 2) {
					StdDraw.setPenColor(StdDraw.YELLOW);
				}
				StdDraw.filledCircle(j + 1, i + 1, 0.4);
			}
			StdDraw.setPenColor(StdDraw.DARK_GRAY);
			StdDraw.filledSquare(8.85, 7.9, 0.76);
		}
		if (player1) {

			StdDraw.setPenColor(StdDraw.RED);
		} else if (!player1) {
			StdDraw.setPenColor(StdDraw.YELLOW);
		}
		StdDraw.filledCircle(8.85, 7.9, 0.75);
		//

		int redbomb = p1powers[0];
		int redteleporter = p1powers[1];
		int redcolourchanger = p1powers[2];

		StdDraw.setPenColor(StdDraw.RED);
		for (int i = 0; i < redbomb; i++) {
			StdDraw.filledCircle(8.45 + i * 0.25, 4.475, 0.1);
		}
		for (int i = 0; i < redteleporter; i++) {
			StdDraw.filledCircle(8.45 + i * 0.25, 3.225, 0.1);
		}
		for (int i = 0; i < redcolourchanger; i++) {
			StdDraw.filledCircle(8.45 + i * 0.25, 1.975, 0.1);
		}

		int yelbomb = p2powers[0];
		int yelteleporter = p2powers[1];
		int yelcolourchanger = p2powers[2];

		StdDraw.setPenColor(StdDraw.YELLOW);
		for (int i = 0; i < yelbomb; i++) {
			StdDraw.filledCircle(9 + i * 0.25, 4.475, 0.1);
		}
		for (int i = 0; i < yelteleporter; i++) {
			StdDraw.filledCircle(9 + i * 0.25, 3.225, 0.1);
		}
		for (int i = 0; i < yelcolourchanger; i++) {
			StdDraw.filledCircle(9 + i * 0.25, 1.975, 0.1);
		}

		StdDraw.show();
	}

	public static void Animation() {
		StdDraw.setXscale(0, 10);
		StdDraw.setYscale(0, 10);
		double randomh[] = new double[10];
		for (int k = 0; k < 10; k++) {
			randomh[k] = (Math.random() * 5) + 5;
		}
		double randoml[] = new double[10];
		for (int k = 0; k < 10; k++) {
			randoml[k] = (Math.random() * 5);
		}

		double randomc[] = new double[10];
		for (int k = 0; k < 10; k++) {
			randomc[k] = (Math.random() * 2.5);
		}

		for (double i = 0; i < 18; i = i + 0.03) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.filledRectangle(5, 2.5, 5, 2.5);
			StdDraw.setPenColor(StdDraw.YELLOW);
			StdDraw.filledRectangle(5, 7.5, 5, 2.5);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.filledRectangle(5, 5, 5, 0.05);

			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.filledCircle(i - randoml[0], randomh[0], 0.1);
			StdDraw.filledCircle(i - randoml[1], randomh[1], 0.1);
			StdDraw.filledCircle(i - randoml[2], randomh[2], 0.1);
			StdDraw.filledCircle(i - randoml[3], randomh[3], 0.1);
			StdDraw.filledCircle(i - randoml[4], randomh[4], 0.1);
			StdDraw.filledCircle(i - randoml[5], randomh[5], 0.1);
			StdDraw.filledCircle(i - randoml[6], randomh[6], 0.1);
			StdDraw.filledCircle(i - randoml[7], randomh[7], 0.1);
			StdDraw.filledCircle(i - randoml[8], randomh[8], 0.1);
			StdDraw.filledCircle(i - randoml[9], randomh[9], 0.1);
			//
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.filledCircle(i - randoml[0] - randomc[0] / 2, randomc[0] + 5, 0.1);
			StdDraw.filledCircle(i - randoml[1] - randomc[1] / 2, randomc[1] + 5, 0.1);
			StdDraw.filledCircle(i - randoml[2] - randomc[2] / 2, randomc[2] + 5, 0.1);
			StdDraw.filledCircle(i - randoml[3] - randomc[3] / 2, randomc[3] + 5, 0.1);
			StdDraw.filledCircle(i - randoml[4] - randomc[4] / 2, randomc[4] + 5, 0.1);
			StdDraw.filledCircle(i - randoml[5] - randomc[5] / 2, randomc[5] + 5, 0.1);
			StdDraw.filledCircle(i - randoml[6] - randomc[6] / 2, randomc[6] + 5, 0.1);
			StdDraw.filledCircle(i - randoml[7] - randomc[7] / 2, randomc[7] + 5, 0.1);
			StdDraw.filledCircle(i - randoml[8] - randomc[8] / 2, randomc[8] + 5, 0.1);
			StdDraw.filledCircle(i - randoml[9] - randomc[9] / 2, randomc[9] + 5, 0.1);
			//

			StdDraw.setPenColor(StdDraw.YELLOW);
			StdDraw.filledCircle(10 - (i - randoml[0]), randomh[0] - 5, 0.1);
			StdDraw.filledCircle(10 - (i - randoml[1]), randomh[1] - 5, 0.1);
			StdDraw.filledCircle(10 - (i - randoml[2]), randomh[2] - 5, 0.1);
			StdDraw.filledCircle(10 - (i - randoml[3]), randomh[3] - 5, 0.1);
			StdDraw.filledCircle(10 - (i - randoml[4]), randomh[4] - 5, 0.1);
			StdDraw.filledCircle(10 - (i - randoml[5]), randomh[5] - 5, 0.1);
			StdDraw.filledCircle(10 - (i - randoml[6]), randomh[6] - 5, 0.1);
			StdDraw.filledCircle(10 - (i - randoml[7]), randomh[7] - 5, 0.1);
			StdDraw.filledCircle(10 - (i - randoml[8]), randomh[8] - 5, 0.1);
			StdDraw.filledCircle(10 - (i - randoml[9]), randomh[9] - 5, 0.1);
			//
			StdDraw.filledCircle(10 - (i - randoml[0]) + randomc[0], randomc[0] + 1, 0.1);
			StdDraw.filledCircle(10 - (i - randoml[1]) + randomc[1], randomc[1] + 1, 0.1);
			StdDraw.filledCircle(10 - (i - randoml[2]) + randomc[2], randomc[2] + 1, 0.1);
			StdDraw.filledCircle(10 - (i - randoml[3]) + randomc[3], randomc[3] + 1, 0.1);
			StdDraw.filledCircle(10 - (i - randoml[4]) + randomc[4], randomc[4] + 1, 0.1);
			StdDraw.filledCircle(10 - (i - randoml[5]) + randomc[5], randomc[5] + 1, 0.1);
			StdDraw.filledCircle(10 - (i - randoml[6]) + randomc[6], randomc[6] + 1, 0.1);
			StdDraw.filledCircle(10 - (i - randoml[7]) + randomc[7], randomc[7] + 1, 0.1);
			StdDraw.filledCircle(10 - (i - randoml[8]) + randomc[8], randomc[8] + 1, 0.1);
			StdDraw.filledCircle(10 - (i - randoml[9]) + randomc[9], randomc[9] + 1, 0.1);
			//
			StdDraw.setPenColor(StdDraw.BLACK);
			Font bigfont = new Font("Arial", Font.BOLD, 150);
			StdDraw.setFont(bigfont);
			StdDraw.text(5, 5, "CONNECT 4");
			//
			StdDraw.filledRectangle(5, 8, 4.5, 0.15);
			StdDraw.filledRectangle(5, 2, 4.5, 0.15);
			StdDraw.filledCircle(9.5, 8, 0.15);
			StdDraw.filledCircle(0.5, 8, 0.15);
			StdDraw.filledCircle(9.5, 2, 0.15);
			StdDraw.filledCircle(0.5, 2, 0.15);
			StdDraw.filledRectangle(0.5, 3.25, 0.15, 0.75);
			StdDraw.filledRectangle(9.5, 3.25, 0.15, 0.75);
			StdDraw.filledCircle(9.5, 4, 0.15);
			StdDraw.filledCircle(9.5, 2.5, 0.15);
			StdDraw.filledCircle(0.5, 4, 0.15);
			StdDraw.filledCircle(0.5, 2.5, 0.15);

			StdDraw.filledRectangle(0.5, 6.75, 0.15, 0.6);
			StdDraw.filledRectangle(9.5, 6.75, 0.15, 0.6);
			StdDraw.filledCircle(9.5, 7.35, 0.15);
			StdDraw.filledCircle(9.5, 6.15, 0.15);
			StdDraw.filledCircle(0.5, 7.35, 0.15);
			StdDraw.filledCircle(0.5, 6.15, 0.15);

			StdDraw.show();

		}

	}

	/**
	 * Displays the grid, empty spots as *, player 1 as R and player 2 as Y.
	 * Shows column and row numbers at the top.
	 * 
	 * @param grid
	 *            The current board state
	 */
	public static void Display(int[][] grid) {
		StdOut.println();
		for (int j = 0; j < 8; j++) {
			if (j == 0) {
				StdOut.print("  ");
			} else {
				StdOut.print(j - 1 + " ");
			}
		}
		StdOut.print(" ");
		StdOut.println("");

		for (int i = 0; i < 6; i++) {
			StdOut.print((i) + " ");
			for (int j = 0; j < 7; j++) {
				if (grid[i][j] == 0) {
					StdOut.print("*" + " ");
				} else if (grid[i][j] == 1) {
					StdOut.print("R" + " ");
				} else if (grid[i][j] == 2) {
					StdOut.print("Y" + " ");
				}

			}
			StdOut.print(" ");
			StdOut.println();
		}
		StdOut.println();
		// Display the given board state with empty spots as *, player 1
		// as R and player 2 as Y. Shows column and row numbers at the top.
	}

	/**
	 * Exits the current game state
	 */
	public static void Exit() {
		// Exit the game
		System.exit(0);
	}

	public static void CheckWin() {
		for (int i = 0; i < 2; i++) {
			winner[i] = 0;
		}
		// Check for all the possible win conditions as well as for a possible draw.
		// horizontal
		for (int i = 0; i < 6; i++) {
			for (int k = 0; k < 4; k++) {
				int count1 = 0;
				int count2 = 0;
				for (int j = k; j < (4 + k); j++) {
					if (grid[i][j] == 1) {
						count1++;
					}
					if (grid[i][j] == 2) {
						count2++;
					}
					if (count1 == 4) {
						winner[0] = 1;
						break;
					}
					if (count2 == 4) {
						winner[1] = 2;
						break;
					}

				}
			}
		}
		// vertical
		for (int i = 0; i < 7; i++) {
			for (int k = 0; k < 3; k++) {
				int count1 = 0;
				int count2 = 0;
				for (int j = k; j < (4 + k); j++) {
					if (grid[j][i] == 1) {
						count1++;
					}
					if (grid[j][i] == 2) {
						count2++;
					}
					if (count1 == 4) {
						winner[0] = 1;
						break;
					}
					if (count2 == 4) {
						winner[1] = 2;
						break;
					}

				}
			}
		}
		// diagonal \
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				int count1 = 0;
				int count2 = 0;
				for (int k = 0; k < 4; k++) {
					if (grid[i + k][j + k] == 1) {
						count1++;
					}
					if (grid[i + k][j + k] == 2) {
						count2++;
					}
					if (count1 == 4) {
						winner[0] = 1;
						break;
					}
					if (count2 == 4) {
						winner[1] = 2;
						break;
					}

				}

			}
		}
		// diagonal /
		for (int i = 0; i < 3; i++) {
			for (int j = 6; j > 2; j--) {
				int count1 = 0;
				int count2 = 0;
				for (int k = 3; k >= 0; k--) {
					if (grid[i + k][j - k] == 1) {
						count1++;
					}
					if (grid[i + k][j - k] == 2) {
						count2++;
					}
					if (count1 == 4) {
						winner[0] = 1;
						break;
					}
					if (count2 == 4) {
						winner[1] = 2;
						break;
					}

				}

			}
		}
	}

	/**
	 * Plays a bomb disc that blows up the surrounding tokens and drops down
	 * tokens above it. Should not affect the board state if there's no space in
	 * the column. In that case, print the error message: "Column is full."
	 * 
	 * @param i
	 *            Column that the disc is going to be played in
	 * @param grid
	 *            The current board state
	 * @param player1
	 *            The current player
	 * @return grid The modified board state
	 */

	public static int[][] Test(String name) {
		int[][] grid = new int[6][7];
		try {
			File file = new File(name + ".txt");
			Scanner sc = new Scanner(file);
			
			for (int i = 0; i < X; i++) {
				for (int j = 0; j < Y; j++) {
					grid[i][j] = sc.nextInt();
				}
			}
			sc.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return grid;
	}

	/**
	 * Saves the current game board to a text file.
	 * 
	 * @param name
	 *            The name of the given file
	 * @param grid
	 *            The current game board
	 * @return
	 */
	// Used for testing
	public static int[][] Save(String name, int[][] grid) {
		try {
			FileWriter fileWriter = new FileWriter(name + ".txt");
			for (int i = 0; i < X; i++) {
				for (int j = 0; j < Y; j++) {
					fileWriter.write(Integer.toString(grid[i][j]) + " ");
				}
				fileWriter.write("\n");
			}
			fileWriter.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return grid;
	}

	/**
	 * Debugging tool, preferably use this since we can then turn off your
	 * debugging output if you forgot to remove it. Only prints out if the DEBUG
	 * variable is set to true.
	 * 
	 * @param line
	 *            The String you would like to print out.
	 */
	public static void Debug(String line) {
		if (DEBUG)
			System.out.println(line);
	}
}
