package com.example.multilanguageapp.Model;

public class Word {

    //Default Translation for word
    private final int iDefaultTranslation;

    // Indonesian Translation for word
    private final String iIndonesianTranslation;

    private int iImageResourceId = NO_IMAGE_PROVIDED;

    /** Constant value that represents no image was provided for this word */
    private static final int NO_IMAGE_PROVIDED = -1;

    //Audio resource id for the word
    private final int iAudioResourceId;

    // create a new word object
    public Word(int defaultTranslation, String indonesianTranslation, int audioResourceId) {
        iDefaultTranslation = defaultTranslation;
        iIndonesianTranslation = indonesianTranslation;
        iAudioResourceId = audioResourceId;
    }

    public Word(int defaultTranslation, String indonesianTranslation, int imageResourceId,int audioResourceId) {
        iDefaultTranslation = defaultTranslation;
        iIndonesianTranslation = indonesianTranslation;
        iImageResourceId = imageResourceId;
        iAudioResourceId = audioResourceId;
    }

    // get the default translation of word
    public int getmDefaultTranslation() {
        return iDefaultTranslation;
    }


    // get the indonesian translation for word
    public String getiIndonesianTranslation() {
        return iIndonesianTranslation;
    }


    public int getImageResourceId() {
        return iImageResourceId;
    }

    /**
     * Returns whether or not there is an image for this word.
     */
    public boolean hasImage() {
        return iImageResourceId != NO_IMAGE_PROVIDED;
    }

    //return the audio resource id of the word

    public int getiAudioResourceId(){

        return iAudioResourceId;
    }

}
