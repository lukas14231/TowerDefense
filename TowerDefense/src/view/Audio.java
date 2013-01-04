package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
 * Die Klasse Audio lädt eine Audiodatei ein und spielt sie ab
 * 
 * 
 */
public class Audio {

	private AdvancedPlayer player;
	/**
	 * Der Konstruktor liest die Audioadatei von dem übergebenen Stringpfad ein
	 * 
	 * @param str der Pfad indem sich die Audiodatei befindet
	 */
	public Audio(String str) {
		try {
			player = new AdvancedPlayer(new FileInputStream(str));
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");

		} catch (JavaLayerException e) {
			System.out.println("JavaLayerException");
		}
	}

	/**
	 * Spielt die Geladene Datei ab
	 */
	public void Play() {
		try {
			player.play();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
