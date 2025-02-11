package src.java.Components;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;

import src.java.myUtils;

import javax.sound.sampled.LineListener;

public class Sound {
  public boolean running = false;
  Clip clip;

  File[] soundURL = new File[10];

  public LineHandler lineHandler = new LineHandler(this);

  public long time = 0;

  public Sound() {
    this.loadSounds();
  }

  public Sound(int i) {
    this.loadSounds();
    this.setFile(i);
  }

  public void loadSounds() {
    soundURL[0] = new File(myUtils.audioPath + "MeowMeow.wav");
    soundURL[1] = new File(myUtils.audioPath + "anath.wav");
    soundURL[2] = new File(myUtils.audioPath + "crySound.wav");
    soundURL[3] = new File(myUtils.audioPath + "walk.wav");
    soundURL[4] = new File(myUtils.audioPath + "ghast0.wav");
    soundURL[5] = new File(myUtils.audioPath + "ghast1.wav");
    soundURL[6] = new File(myUtils.audioPath + "abeSale.wav");
    soundURL[7] = new File(myUtils.audioPath + "yippee.wav");
    soundURL[8] = new File(myUtils.audioPath + "Cats_Sped_Up.wav");
  }

  public void setFile(int i) {
    try {
      AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
      clip = AudioSystem.getClip();
      clip.open(ais);
      clip.addLineListener(this.lineHandler);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void play() {
    this.running = true;
    this.clip.setMicrosecondPosition(this.time);
    clip.start();
  }

  public void loop() {
    clip.loop(Clip.LOOP_CONTINUOUSLY);
  }

  public void stop() {
    this.running = false;
    clip.stop();
  }

  public void pause() {
    this.time = this.clip.getMicrosecondPosition();
    this.clip.stop();
  }
}

class LineHandler implements LineListener {
  public Sound sound;

  public LineHandler(Sound sound) {
    this.sound = sound;
  }

  @Override
  public void update(LineEvent event) {
    if (event.getType().equals(Type.STOP)) {
      this.sound.running = false;
    }
  }
}