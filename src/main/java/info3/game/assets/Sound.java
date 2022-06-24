package info3.game.assets;

import java.io.IOException;
import java.io.RandomAccessFile;

import info3.game.graphics.GameCanvas;
import info3.game.sound.RandomFileInputStream;

public class Sound extends Asset {
	public String name;
	RandomFileInputStream fis;
	long duration;
	float volume;

	public Sound(String name, String path, long durat, float vol) {
		super(path);
		// "src/main/resources/sword1.ogg"
		this.name = name;
		duration = durat;
		volume = vol;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void load() {
		RandomAccessFile file;
		try {
			file = new RandomAccessFile(path, "r");
			fis = new RandomFileInputStream(file);
		} catch (IOException e) {
			System.out.println("[WARN] Audio error");
		}

	}

	public void play(GameCanvas canvas) {
		canvas.playSound(this.name, this.fis, this.duration, this.volume);
	}

}
