package global;

//import java.io.File;
//import java.io.IOException;

//import javax.sound.sampled.*;

public class SoundManager {

/*	public AudioInputStream cur_playing_sound = null;
	
	long strlen = 0; // length of the current stream, in samples
	byte[] soundstream;
	
	public static AudioFormat stdFormat = new AudioFormat( AudioFormat.Encoding.PCM_SIGNED, 44100.0f, 32, 1, 4,
		      44100.0f, false );
	
	DataLine.Info dInfo = new DataLine.Info(SourceDataLine.class, stdFormat);

	SourceDataLine sdl = (SourceDataLine)AudioSystem.getLine( dInfo );
		sdl.open();
		sdl.start();

	
	public SoundManager(){
		// do nothing
	}
	
	public void loadAudio(File audiofile){
		try {
			cur_playing_sound = AudioSystem.getAudioInputStream(audiofile);
			strlen = cur_playing_sound.getFrameLength();
			// we're dealing with 32-bit audio, which is 4 bytes per sample.
			soundstream = new byte[(int)(strlen * 4)];
			int m = cur_playing_sound.read(soundstream);
			if(m != strlen * 4){
				throw new RuntimeException( "Read only " + m + " of " + audiofile + " out of " +
				          (strlen*2) + " sound bytes; reached EOF");
			}
		} catch (UnsupportedAudioFileException e) {
			// print a message saying, "File format not supported? WTF."
		} catch (IOException e) {
			// do something
		}
	}
*/	
}