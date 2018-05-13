package marin.bralic.sounds;

public class SoundsHandler {
	private Sound[] sounds=new Sound[7];
	private int soundStatus=0, eyes=0;
	
	public SoundsHandler(boolean soundOnOff){					
		sounds[0]=new Sound(getClass().getResource("/media/Ready.wav")){
			@Override
			public void run(){
				while(!exit){ 
					if(status){
						soundClip.setFramePosition(0);
						soundClip.start();
						status=false;
					}
					try{ Thread.sleep(50);  }catch(InterruptedException e){}
				}
			}
		};
		sounds[1]=new Sound(getClass().getResource("/media/waka.wav"));
		sounds[2]=new Sound(getClass().getResource("/media/normal.wav"));
		sounds[3]=new Sound(getClass().getResource("/media/run.wav"));
		sounds[4]=new Sound(getClass().getResource("/media/eyes.wav"));
		sounds[5]=new Sound(getClass().getResource("/media/itemEat.wav")){
			@Override
			public void run(){
				while(!exit){    							
					if(status){
						soundClip.setFramePosition(0);
						soundClip.start();
						status=false;
					}
					try{ Thread.sleep(50);  }catch(InterruptedException e){}
				}
			}
		};
		sounds[6]=new Sound(getClass().getResource("/media/Lost.wav")){
			@Override
			public void run(){
				while(!exit){    							
					if(status){
						soundClip.start();
						status=false;
					}
					try{ Thread.sleep(50);  }catch(InterruptedException e){}
				}
			}
		};
		
		if(soundOnOff) for(int i=0;i<sounds.length;++i) sounds[i].start();
	}
	
	
	
	public void normalSound(){
		soundStatus=0;		
		sounds[2].turnOnOff(true);	
		sounds[3].turnOnOff(false);
		sounds[4].turnOnOff(false);
	}
	
    public void runSound(){
    	soundStatus=1;
		sounds[2].turnOnOff(false);
		sounds[3].turnOnOff(true);		
		sounds[4].turnOnOff(false);
	}
    
    public void eyesSound(boolean s){
    	if(s){
    		++eyes;
    		sounds[3].turnOnOff(false);		
    		sounds[4].turnOnOff(true);
    	}else{
    		--eyes;
    		if(eyes==0){
    			
    			if(soundStatus==1){
    				sounds[2].turnOnOff(false);		
    				sounds[3].turnOnOff(true);		
    	    		sounds[4].turnOnOff(false);
    	    		
    	    		
    			}else{
    				sounds[2].turnOnOff(true);	
    				sounds[3].turnOnOff(false);		
    	    		sounds[4].turnOnOff(false);
    			}
    		}   		
    	}
	}
    
    public void wakaSound(boolean s){
    	sounds[1].turnOnOff(s);  	
    }
	
    
    public void playReadySound(){
    	sounds[0].turnOnOff(true);
    	sounds[1].turnOnOff(false);
    	sounds[2].turnOnOff(false);
    	sounds[3].turnOnOff(false);
    	sounds[4].turnOnOff(false);
    }
    
	public void playEatItemSound(){
		sounds[5].turnOnOff(true);
	}
    
    public void playLostSound(){
    	sounds[6].turnOnOff(true);
    	sounds[1].turnOnOff(false);
    	sounds[2].turnOnOff(false);
    	sounds[3].turnOnOff(false);
    	sounds[4].turnOnOff(false);
    }
	
    
	public void pauseAll(){
		sounds[1].turnOnOff(false);
		sounds[2].turnOnOff(false);	
		sounds[3].turnOnOff(false);	
		sounds[4].turnOnOff(false);
	}
	
	public void resume(){
		if(soundStatus==0) sounds[2].turnOnOff(true);	
		else if(soundStatus==1 && eyes==0) sounds[3].turnOnOff(true);	
		else if(soundStatus==1 && eyes>0) sounds[4].turnOnOff(true);	
	}
	
	public void stopAll(){
		for(int i=0;i<sounds.length;++i){
			sounds[i].turnOnOff(false);	
			sounds[i].threadEnd();
		}			
	}

}
