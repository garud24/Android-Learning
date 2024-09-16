package com.example.assignment03;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.AlertDialog;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private TextView focusImageTextView;
    private final String[] fruitNames = {"apple", "lemon", "mango", "peach", "strawberry", "tomato"};
    private final int[] fruitDrawables = {R.drawable.apple, R.drawable.lemon, R.drawable.mango, R.drawable.peach, R.drawable.strawberry, R.drawable.tomato};
    private String focusImageName;
    private int focusImageDrawable;
    private int focusImageCount;
    private List<Integer> gridPositions;
    private final Random random = new Random();
    private final Set<Integer> selectedPositions = new HashSet<>(); // To track already selected tiles

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridLayout);
        focusImageTextView = findViewById(R.id.focusImageTextView);
        Button resetButton = findViewById(R.id.resetButton);

        // Initialize the game when the app starts
        initializeGame();

        // Handle Reset button click
        resetButton.setOnClickListener(v -> initializeGame());
    }

    @SuppressLint("SetTextI18n")
    private void initializeGame() {
        // Clear the grid and selected positions before re-initializing
        gridLayout.removeAllViews();
        selectedPositions.clear();

        // Randomly choose the focus image
        int focusIndex = random.nextInt(fruitNames.length);
        focusImageName = fruitNames[focusIndex];
        focusImageDrawable = fruitDrawables[focusIndex];

        // Randomly decide the number of times the focus image should appear (N)
        focusImageCount = random.nextInt(8) + 1; // Random number between 1 and 8

        // Display the focus image name and count
        focusImageTextView.setText("Find All " + capitalize(focusImageName) + "s (" + focusImageCount + ")");

        // Create a list of 25 positions for the grid (5x5)
        gridPositions = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            gridPositions.add(i);
        }
        // Shuffle the positions to randomize where the images will go
        Collections.shuffle(gridPositions);

        // Shuffle all fruits before placement
        List<Integer> allFruitDrawables = new ArrayList<>();
        for (int i = 0; i < focusImageCount; i++) {
            allFruitDrawables.add(focusImageDrawable);
        }
        for (int i = focusImageCount; i < 25; i++) {
            int randomImageIndex;
            do {
                randomImageIndex = random.nextInt(fruitNames.length);
            } while (randomImageIndex == focusIndex); // Avoid placing the focus image again
            allFruitDrawables.add(fruitDrawables[randomImageIndex]);
        }
        Collections.shuffle(allFruitDrawables); // Shuffle to make sure fruits are randomly placed

        // Place the shuffled fruits in the grid
        for (int i = 0; i < 25; i++) {
            boolean isFocusImage = (allFruitDrawables.get(i) == focusImageDrawable);
            addImageToGrid(i, allFruitDrawables.get(i), isFocusImage);
        }
    }

    // Helper method to add an ImageView to the grid
    private void addImageToGrid(final int position, final int drawableRes, final boolean isFocusImage) {
        final ImageView imageView = new ImageView(this);
        imageView.setImageResource(drawableRes);
        imageView.setTag(drawableRes); // Track the drawable associated with this ImageView
        imageView.setLayoutParams(new GridLayout.LayoutParams());
        imageView.setAdjustViewBounds(true);
        imageView.setPadding(8, 8, 8, 8);

        // Check if this position has already been selected (locked)
        if (selectedPositions.contains(position)) {
            imageView.setAlpha(0.5f); // Make it blurred if already selected
            imageView.setOnClickListener(null); // Disable further clicks on this tile
        } else {
            // Set up the click listener for the image tile
            imageView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    // Ignore if the image was already selected
                    if (selectedPositions.contains(position)) {
                        return;
                    }

                    // Check if the clicked image is the focus image based on its drawable, not position
                    if ((Integer) imageView.getTag() == focusImageDrawable) {
                        // Correct selection
                        selectedPositions.add(position);  // Lock the current position
                        imageView.setAlpha(0.5f);         // Reduce alpha to indicate it's been selected (blurring)
                        imageView.setOnClickListener(null); // Disable further clicks on this tile

                        focusImageCount--; // Decrement the remaining count
                        focusImageTextView.setText("Find All " + capitalize(focusImageName) + "s (" + focusImageCount + ")");

                        if (focusImageCount == 0) {
                            // All focus images found, show a dialog
                            showCompletionDialog();
                        } else {
                            // Rearrange the remaining unselected images
                            rearrangeUnselectedImages();
                        }
                    }
                }
            });
        }

        gridLayout.addView(imageView);  // Add the ImageView to the grid
    }

    // Helper method to rearrange unselected images on the grid
    private void rearrangeUnselectedImages() {
        List<ImageView> unselectedViews = new ArrayList<>();
        List<Integer> unselectedDrawables = new ArrayList<>();

        // Collect unselected views and their drawable resources (images)
        for (int i = 0; i < gridPositions.size(); i++) {  // Traverse based on positions, not the layout index
            // Only consider images that are not selected (not blurred)
            if (!selectedPositions.contains(i)) {
                ImageView imageView = (ImageView) gridLayout.getChildAt(i);
                unselectedViews.add(imageView);  // Store the unselected views
                unselectedDrawables.add((Integer) imageView.getTag());  // Store the drawable resource
            }
        }

        // Shuffle the unselected drawable resources (not the views)
        Collections.shuffle(unselectedDrawables);

        // Reapply the shuffled drawables to the unselected views
        for (int i = 0; i < unselectedViews.size(); i++) {
            ImageView imageView = unselectedViews.get(i);
            imageView.setImageResource(unselectedDrawables.get(i));  // Update image resource
            imageView.setTag(unselectedDrawables.get(i));  // Update the tag to track the drawable resource
        }
    }

    // Show dialog when all focus images are found
    private void showCompletionDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Congratulations!")
                .setMessage("You have found all the " + capitalize(focusImageName) + "s!")
                .setPositiveButton("OK", (dialog, which) -> {
                    // Reinitialize the game when the dialog is dismissed
                    initializeGame();
                })
                .show();
    }

    // Helper method to capitalize the first letter of a string
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
