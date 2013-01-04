package view;

/**
 * Die Klasse AudioThread erzeug ein neues Audio Objekt f�r den �bergebenen String und erzeugt einen neuen Thread
 * 
 *
 */
public class AudioThread extends Thread {
	
	 private Audio audio;
	 	   
	    /**
	     *  Der Konstruktor erzeugt ein neues Objekt der Klasse Audio
	     *  
	     * @param str der Datenpfad der �bergebenen audiodatei
	     */
	    public AudioThread(String str){
	        audio = new Audio (str);	        
	    }
	    /**
	     * Spielt das audio Objekt in einem Thread ab
	     * 
	     */
	    @Override
	    public void run(){
	        if(audio != null)audio.Play();
	    }
	    

}

