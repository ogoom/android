/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;

public class NumbersActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    AudioManager am;

    final AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // Permanent loss of audio focus
                // Pause playback immediately
                mediaPlayer.pause();
                // Wait 30 seconds before stopping playback
                mHandler.postDelayed(mDelayedStopRunnable,
                        TimeUnit.SECONDS.toMillis(30));
            }
            else if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT) {
                // Pause playback
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // Lower the volume, keep playing
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Your app has been granted audio focus again
                // Raise volume to normal, restart playback if necessary
                mediaPlayer.start();
            }
        }
    };
    private Handler mHandler = new Handler();

    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            am.abandonAudioFocus(afChangeListener);
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        am = (AudioManager) NumbersActivity.this.getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("one","lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two","otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three","tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four","oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five","massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six","temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven","kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight","kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine","wo’e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("ten","na’aacha", R.drawable.number_ten, R.raw.number_ten));



        WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.category_numbers);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                int result = am.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);


                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Start playback
                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, words.get(position).getAudioResourceId());
                    mediaPlayer.start();
                }
                mediaPlayer.setOnCompletionListener(completionListener);
                //Toast.makeText(NumbersActivity.this, "Testing ListView", Toast.LENGTH_SHORT);
            }
        });

    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
        }
    }

    private Runnable mDelayedStopRunnable = new Runnable() {
        @Override
        public void run() {
            mediaPlayer.stop();
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
