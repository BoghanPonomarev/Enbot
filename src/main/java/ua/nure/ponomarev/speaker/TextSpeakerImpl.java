package ua.nure.ponomarev.speaker;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.AudioFileFormat;
import java.io.File;

/**
 * @author Bogdan_Ponamarev.
 */
public class TextSpeakerImpl implements TextSpeaker {
    private static final Logger logger = LogManager.getLogger(TextSpeakerImpl.class);
    private static final String VOICE_NAME = "kevin";
    private static final String AUDIO_TRACK_PATH = "C:\\Users\\Администратор\\IdeaProjects\\Enbot\\src\\main\\resources\\audio_translates";
    private static final String KEVIN_VOICE_PROPERTY = "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory";
    private static int nameCounter = 1;

    /**
     * Block of code that cleans directory with audio files ,
     * because memory is not infinity and i do`nt good decisions to save
     * value of counter when application is off
     */
    static {
        File file = new File(AUDIO_TRACK_PATH);
        File[] files = file.listFiles();
        if (files != null) {
            for (File myFile : files) {
                if (myFile.isFile()) myFile.delete();
            }
        }
    }

    /**
     * Put file with voiced text in {@code AUDIO_TRACK_PATH}
     *
     * @param text text to voice
     * @return Full path of new file with voice
     */
    public String voiceText(String text) {
        System.setProperty("freetts.voices", KEVIN_VOICE_PROPERTY);
        logger.info("Using voice: %s", VOICE_NAME);
        VoiceManager voiceManager = VoiceManager.getInstance();
        SingleFileAudioPlayer audioPlayer = new SingleFileAudioPlayer(getAudioTrackPath(), AudioFileFormat.Type.WAVE);
        Voice voice = voiceManager.getVoice(VOICE_NAME);
        voice.setAudioPlayer(audioPlayer);
        voice.allocate();
        voice.speak(text);
        voice.deallocate();
        audioPlayer.close();
        return AUDIO_TRACK_PATH + "\\AudioTrack№" + nameCounter + ".wav";//+ nameCounter;
    }

    /**
     * @return full path of file with unique number
     */
    private String getAudioTrackPath() {
        return AUDIO_TRACK_PATH + "\\AudioTrack№" + nameCounter++;// + nameCounter++;
    }

    /* just leave it here */
    public static void main(String[] args) {
        TextSpeakerImpl textSpeaker = new TextSpeakerImpl();
        String s = textSpeaker.voiceText("Hello , haw are you?");
        System.out.println(s);
        TextSpeakerImpl textSpeaker2 = new TextSpeakerImpl();
        String s2 = textSpeaker2.voiceText("Hello , haw are you?");
        System.out.println(s2);
    }
}
