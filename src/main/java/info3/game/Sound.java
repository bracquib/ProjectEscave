package info3.game;

import java.io.IOException;
import java.io.RandomAccessFile;

import info3.game.graphics.GameCanvas;
import info3.game.sound.RandomFileInputStream;

public class Sound {

	String m_name;
	String m_filename;

	public void load(String name, String filename, long duration, float volume, GameCanvas canvas) throws IOException {
		m_name = name;
		m_filename = filename;
		RandomAccessFile file = new RandomAccessFile(filename, "r");
		RandomFileInputStream fis = new RandomFileInputStream(file);
		canvas.playSound(name, fis, duration, volume);
	}

}
