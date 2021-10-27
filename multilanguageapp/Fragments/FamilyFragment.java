package com.example.multilanguageapp.Fragments;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.multilanguageapp.R;
import com.example.multilanguageapp.Model.Word;
import com.example.multilanguageapp.Adapters.WordAdapter;

import java.util.ArrayList;

/**
 * {@link Fragment} that displays a list of family vocabulary words.
 */

public class FamilyFragment extends Fragment{
    /** Handles playback of all the sound files */
    private MediaPlayer iMediaPlayer;

    /** Handles audio focus when playing a sound file */
    private AudioManager iAudioManager;

    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private final AudioManager.OnAudioFocusChangeListener iOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                iMediaPlayer.pause();
                iMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                iMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private final MediaPlayer.OnCompletionListener iCompletionListener = mediaPlayer -> {
        // Now that the sound file has finished playing, release the media player resources.
        releaseMediaPlayer();
    };

    public FamilyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        // Create and setup the {@link AudioManager} to request audio focus
        iAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<>();

        words.add(new Word(R.string.family_father, "ayah", R.mipmap.family_father,R.raw.i_family_father));
        words.add(new Word(R.string.family_mother, "ibu", R.mipmap.family_mother,R.raw.i_family_mother));
        words.add(new Word(R.string.family_son, "putra", R.mipmap.family_son,R.raw.i_family_son));
        words.add(new Word(R.string.family_daughter, "anak perempuan", R.mipmap.family_daughter,R.raw.i_family_daughter));
        words.add(new Word(R.string.family_older_brother, "kakak", R.mipmap.family_older_brother,R.raw.i_family_older_brother));
        words.add(new Word(R.string.family_younger_brother, "adik laki-laki", R.mipmap.family_younger_brother,R.raw.i_family_younger_brother));
        words.add(new Word(R.string.family_older_sister, "kakak perempuan", R.mipmap.family_older_sister,R.raw.i_family_older_sister));
        words.add(new Word(R.string.family_younger_sister, "adik perempuan", R.mipmap.family_younger_sister,R.raw.i_family_younger_sister));
        words.add(new Word(R.string.family_grandmother, "nenek", R.mipmap.family_grandmother,R.raw.i_family_grandmother));
        words.add(new Word(R.string.family_grandfather, "kakek", R.mipmap.family_grandfather,R.raw.i_family_grandfather));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_family);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = rootView.findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);

        // Set a click listener to play the audio when the list item is clicked on
        listView.setOnItemClickListener((adapterView, view, position, l) -> {
            // Release the media player if it currently exists because we are about to
            // play a different sound file
            releaseMediaPlayer();

            // Get the {@link Word} object at the given position the user clicked on
            Word word = words.get(position);

            // Request audio focus so in order to play the audio file. The app needs to play a
            // short audio file, so we will request audio focus with a short amount of time
            // with AUDIOFOCUS_GAIN_TRANSIENT.
            int result = iAudioManager.requestAudioFocus(iOnAudioFocusChangeListener,
                    AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                // We have audio focus now.

                // Create and setup the {@link MediaPlayer} for the audio resource associated
                // with the current word
                iMediaPlayer = MediaPlayer.create(getActivity(), word.getiAudioResourceId());

                // Start the audio file
                iMediaPlayer.start();

                // Setup a listener on the media player, so that we can stop and release the
                // media player once the sound has finished playing.
                iMediaPlayer.setOnCompletionListener(iCompletionListener);
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();

        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (iMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            iMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            iMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            iAudioManager.abandonAudioFocus(iOnAudioFocusChangeListener);
        }
    }
}
