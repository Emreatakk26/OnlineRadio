package com.emre.music;

import com.emre.onlineradio.R;

import java.util.ArrayList;
import java.util.List;

public class MusicContent {

    public static final List<MusicItem> ITEMS = new ArrayList<>();
    public static int radioposition = 0;


    static {
        ITEMS.add(new MusicItem(R.drawable.album_cover_death_cab, "Radyo Alaturka", "Death Cab for Cutie", 515));
        ITEMS.add(new MusicItem(R.drawable.album_cover_the_1975, "Kral FM", "the 1975", 591));
        ITEMS.add(new MusicItem(R.drawable.album_cover_pinback, "PowerTürk", "Pinback", 215));
        ITEMS.add(new MusicItem(R.drawable.album_cover_soad, "Best FM", "System of a down", 242));
        ITEMS.add(new MusicItem(R.drawable.album_cover_two_door, "Baba Radyo", "Two Door Cinema Club", 164));
    }

    public static class MusicItem {

        private final int mCover;
        private final String mTitle;
        private final String mArtist;
        private final long mDuration;

        public MusicItem(int cover, String title, String artist, long duration) {
            mCover = cover;
            mTitle = title;
            mArtist = artist;
            mDuration = duration;
        }

        public int getCover() {
            return mCover;
        }

        public String getTitle() {
            return mTitle;
        }

        public String getArtist() {
            return mArtist;
        }

        public long getDuration() {
            return mDuration;
        }
    }
}