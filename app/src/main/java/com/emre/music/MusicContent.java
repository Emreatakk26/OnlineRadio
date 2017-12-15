package com.emre.music;

import com.emre.onlineradio.R;

import java.util.ArrayList;
import java.util.List;

public class MusicContent {

    public static final List<MusicItem> ITEMS = new ArrayList<>();
    public static int radioposition = 0;


    static {
        ITEMS.add(new MusicItem(R.drawable.radyoalaturka, "Radyo Alaturka"));
        ITEMS.add(new MusicItem(R.drawable.kralfm, "Kral FM" ));
        ITEMS.add(new MusicItem(R.drawable.powerturk, "PowerTürk"));
        ITEMS.add(new MusicItem(R.drawable.bestfm, "Best FM"));
        ITEMS.add(new MusicItem(R.drawable.babaradyo, "Baba Radyo"));
    }

    public static class MusicItem {

        private final int mCover;
        private final String mTitle;

        public MusicItem(int cover, String title) {
            mCover = cover;
            mTitle = title;
        }

        public int getCover() {
            return mCover;
        }

        public String getTitle() {
            return mTitle;
        }
    }
}