/*
MainActivity.java file
Srushti Sanjay Samant and Himanshu Kiran Garud
Group 26
Assignment 3
 */

package com.example.assignment03;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewGameStatus;
    private Button resetButton;

    private final int[] imageViewIds = {
            R.id.imageViewRow1Card1, R.id.imageViewRow1Card2, R.id.imageViewRow1Card3, R.id.imageViewRow1Card4, R.id.imageViewRow1Card5,
            R.id.imageViewRow2Card1, R.id.imageViewRow2Card2, R.id.imageViewRow2Card3, R.id.imageViewRow2Card4, R.id.imageViewRow2Card5,
            R.id.imageViewRow3Card1, R.id.imageViewRow3Card2, R.id.imageViewRow3Card3, R.id.imageViewRow3Card4, R.id.imageViewRow3Card5,
            R.id.imageViewRow4Card1, R.id.imageViewRow4Card2, R.id.imageViewRow4Card3, R.id.imageViewRow4Card4, R.id.imageViewRow4Card5,
            R.id.imageViewRow5Card1, R.id.imageViewRow5Card2, R.id.imageViewRow5Card3, R.id.imageViewRow5Card4, R.id.imageViewRow5Card5
    };

    private final int[] drawableIds = {
            R.drawable.mango, R.drawable.apple, R.drawable.lemon, R.drawable.strawberry, R.drawable.peach
    };

    private int focusImage;
    private int focusImageCount;
    private int remainingToFind;
    private List<ImageView> availableImageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewGameStatus = findViewById(R.id.textView);
        resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(this);

        initializeGame();
    }

    private void initializeGame() {
        Random random = new Random();
        focusImage = drawableIds[random.nextInt(drawableIds.length)];
        focusImageCount = random.nextInt(8) + 1;  // N is in range [1,8]
        remainingToFind = focusImageCount;

        availableImageViews = new ArrayList<>();

        List<Integer> imageList = new ArrayList<>();
        for (int i = 0; i < focusImageCount; i++) {
            imageList.add(focusImage);
        }

        while (imageList.size() < imageViewIds.length) {
            int randomImage = drawableIds[random.nextInt(drawableIds.length)];
            if (randomImage != focusImage) {
                imageList.add(randomImage);
            }
        }

        Collections.shuffle(imageList);

        for (int i = 0; i < imageViewIds.length; i++) {
            ImageView imageView = findViewById(imageViewIds[i]);
            imageView.setImageResource(imageList.get(i));
            imageView.setTag(imageList.get(i));
            imageView.setAlpha(1.0f);
            imageView.setOnClickListener(this);

            availableImageViews.add(imageView);
        }

        updateGameStatus();
    }

    private void updateGameStatus() {
        String focusImageName = getResources().getResourceEntryName(focusImage); // Get drawable name
        textViewGameStatus.setText("Find All " + focusImageName + "s (" + remainingToFind + ")");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.resetButton) {
            initializeGame();
        } else if (view instanceof ImageView) {
            ImageView clickedImage = (ImageView) view;
            int imageId = (int) clickedImage.getTag();

            if (imageId == focusImage) {
                remainingToFind--;
                updateGameStatus();
                applyAlphaEffect(clickedImage);

                availableImageViews.remove(clickedImage);

                shuffleRemainingImages();

                if (remainingToFind == 0) {
                    showCompletionDialog();
                }
            }
        }
    }

    private void applyAlphaEffect(ImageView imageView) {
        imageView.setAlpha(0.5f);
    }

    //Handling shuffle logic

    private void shuffleRemainingImages() {
        List<Integer> remainingImages = new ArrayList<>();
        for (ImageView imageView : availableImageViews) {
            remainingImages.add((Integer) imageView.getTag());
        }

        Collections.shuffle(remainingImages);

        for (int i = 0; i < availableImageViews.size(); i++) {
            ImageView imageView = availableImageViews.get(i);
            imageView.setImageResource(remainingImages.get(i));
            imageView.setTag(remainingImages.get(i));
        }
    }

    private void showCompletionDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Found All Shapes")
                .setMessage("Congratulations!! You have found all the " + getResources().getResourceEntryName(focusImage) + "s!")
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                    initializeGame();  // Reset the game after the dialog is dismissed
                })
                .show();
    }

}
