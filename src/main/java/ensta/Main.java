package ensta;

import java.util.Scanner;

import ensta.controller.Game;

public class Main {
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String args[]) {
		boolean multiplayer = false;
		boolean end = false;
		String[] in;
		do {
			System.out.println("Voudriez-vous entrer le mode multijoueur ? (y/n) ");
			in = scanner.nextLine().toLowerCase().split("");
			if(in[0].equals("y") || in[0].equals("n")) {
				end = true;
			}
			else {
				System.out.println("Veuillez entrer 'y' ou 'n' !");
			}
		} while(end == false);
			
		if(in[0].equals("y")) {
			multiplayer = true;
		}
		
        new Game(multiplayer).init().run();
    }

}
