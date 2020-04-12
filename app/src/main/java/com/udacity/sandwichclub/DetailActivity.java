package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    //Using Butterknife library to bind the views
    @BindView(R.id.image_iv)
    ImageView ingredientsIV;
    @BindView(R.id.also_known_tv)
    TextView alsoKnownAsTV;
    @BindView(R.id.ingredients_tv)
    TextView ingredientsTV;
    @BindView(R.id.description_tv)
    TextView description_TV;
    @BindView(R.id.origin_tv)
    TextView originTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        assert intent != null;
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                //load image
                .load(sandwich.getImage())
                //set error image
                .error(R.drawable.download)
                .into(ingredientsIV);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {


        //setting alsoKnownAs to  corresponding text view
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        if (!alsoKnownAs.isEmpty()) {
            for (String anotherName : alsoKnownAs
            ) {
                alsoKnownAsTV.append("\u2022  ");
                alsoKnownAsTV.append(anotherName + "\n");
            }

        } else {
            alsoKnownAsTV.setText(R.string.not_available);
        }

        //setting ingredients to  corresponding text view
        List<String> ingredients = sandwich.getIngredients();
        if (!ingredients.isEmpty()) {
            for (String ingredient : ingredients
            ) {
                ingredientsTV.append("\u2022  ");
                ingredientsTV.append(ingredient + "\n");
            }
        } else {
            ingredientsTV.setText(R.string.not_available);
        }

        //setting place of origin to corresponding text view
        if (!sandwich.getPlaceOfOrigin().isEmpty()) {
            originTV.setText(sandwich.getPlaceOfOrigin());
        } else {
            originTV.setText(R.string.not_available);
        }

        //setting description to  corresponding text view
        if (!sandwich.getDescription().isEmpty()) {
            description_TV.setText(sandwich.getDescription());
        } else {
            description_TV.setText(R.string.not_available);
        }

    }
}
