package com.example.android.miwok;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ogoom on 10-Jul-17.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private int colorCat;
    public WordAdapter(Context context, ArrayList<Word> words) {
        super(context, 0, words);
    }
    public WordAdapter(Context context, ArrayList<Word> words, int colorCatx) {
        super(context, 0, words);
        this.colorCat = colorCatx;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        final Word currentWord = getItem(position);
        LinearLayout cont = (LinearLayout) listItemView.findViewById(R.id.container);
        int color =  ContextCompat.getColor(getContext(), colorCat);
        cont.setBackgroundColor(color);


        TextView miwokT = (TextView) listItemView.findViewById(R.id.list_item1);
        TextView defaultT = (TextView) listItemView.findViewById(R.id.list_item2);
        ImageView icon = (ImageView) listItemView.findViewById(R.id.iconImage);
        miwokT.setText(currentWord.getMiwokTranslation());
        defaultT.setText(currentWord.getDefaultTranslation());
        if (currentWord.hasImage()) {
            icon.setImageResource(currentWord.getImageResourceId());
            icon.setVisibility(View.VISIBLE);
        }else
            icon.setVisibility(View.GONE);



        return listItemView;


    }
}
