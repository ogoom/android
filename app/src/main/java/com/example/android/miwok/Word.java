package com.example.android.miwok;

/**
 * Created by ogoom on 10-Jul-17.
 */

public class Word {
    private String defaultTranslation;
    private String miwokTranslation;
    private int imageResourceId = IMAGE_ID_PROVIDED;
    private static final int IMAGE_ID_PROVIDED = -1;



    private int audioResourceId;


    public Word(String defaultT, String miwok, int audioResourceId) {
        this.defaultTranslation = defaultT;
        this.miwokTranslation = miwok;
        this.audioResourceId = audioResourceId;


    }

    public Word(String defaultT, String miwok, int imageResourceId, int audioResourceId) {
        this.defaultTranslation = defaultT;
        this.miwokTranslation = miwok;
        this.imageResourceId = imageResourceId;
        this.audioResourceId = audioResourceId;

    }

    public String getDefaultTranslation() {

        return defaultTranslation;
    }

    public String getMiwokTranslation() {
        return miwokTranslation;
    }

    public int getImageResourceId() {

        return imageResourceId;
    }
    public boolean hasImage(){
        return imageResourceId != IMAGE_ID_PROVIDED;

    }
    public int getAudioResourceId() {
        return audioResourceId;
    }
    /**
     * Returns the string representation of the {@link Word} object.
     */
    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + defaultTranslation + '\'' +
                ", mMiwokTranslation='" + miwokTranslation + '\'' +
                ", mAudioResourceId=" + audioResourceId +
                ", mImageResourceId=" + imageResourceId +
                '}';
    }
}
