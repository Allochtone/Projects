package game;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class Chronometre extends Service<Void>{
	long tempsini;
	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {

			@Override
			protected Void call() {
				tempsini = System.currentTimeMillis();
				String millisecs = "00";
				String secs = "00";
				String minutes = "00";
				do {
					millisecs = (Long.toString((System.currentTimeMillis() - tempsini)/10));
					if(Integer.parseInt(millisecs) <= 9)
						millisecs = "0"+String.valueOf(Integer.parseInt(millisecs));
					if(millisecs.length() >= 3){
						millisecs = "00";
						tempsini = System.currentTimeMillis();
						secs = String.valueOf(Integer.parseInt(secs)+1);
						if(Integer.parseInt(secs) <= 9)
							secs = "0"+String.valueOf(Integer.parseInt(secs));
						if(Integer.parseInt(secs) >= 60){
							secs = "00";
							minutes = String.valueOf(Integer.parseInt(minutes)+1);
							if(Integer.parseInt(minutes) <= 9)
								minutes = "0"+String.valueOf(Integer.parseInt(minutes));
						}
					}
					updateMessage(minutes+":"+secs+":"+millisecs);
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						
					}
					} while (!this.isCancelled());
				return null;
			}
		};
	}
	
	public long temps(){
		return (tempsini-System.currentTimeMillis());
	}

}
