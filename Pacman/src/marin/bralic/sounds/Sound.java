package marin.bralic.sounds;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound extends Thread{
	
	private AudioInputStream stream;
	protected Clip soundClip;
	protected boolean status, exit;
	
	public Sound(URL f){
		status=false;
		
		try {
			soundClip=AudioSystem.getClip();
			stream=AudioSystem.getAudioInputStream(f);
			soundClip.open(stream);
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {			
			e.printStackTrace();
		}
		
		exit=false;
	}
	
	public boolean isOn(){
		return status;
	}
	
	public void turnOnOff(boolean s){
		status=s;		
		if(!s) soundClip.stop();
	}
	
	public void threadEnd(){
		exit=true;
	}
	
	@Override
	public void run(){
		while(!exit){    							
			if(status){
				soundClip.loop(100);
			}
			try{ Thread.sleep(50);  }catch(InterruptedException e){}
		}		
	}
}
