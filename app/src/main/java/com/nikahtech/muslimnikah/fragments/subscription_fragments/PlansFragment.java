package com.nikahtech.muslimnikah.fragments.subscription_fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.nikahtech.muslimnikah.R;
import com.nikahtech.muslimnikah.activities.MainActivity;
import com.nikahtech.muslimnikah.databinding.FragmentPlansBinding;
import com.nikahtech.muslimnikah.utils.ViewAnimationUtil;
import com.nikahtech.muslimnikah.utils.ViewInfoExtractor;

import java.util.Arrays;
import java.util.List;

public class PlansFragment extends Fragment {

    FragmentPlansBinding binding;
    private Context context;


    public PlansFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlansBinding.inflate(inflater, container, false);
        context = requireContext();

        // Setup all subscription cards
        setupAllCards();

        return binding.getRoot();
    }

    /**
     * Sets up click listeners for all subscription cards using a reusable method.
     */
    private void setupAllCards() {
        setupSubscriptionCard(
                binding.subscriptionCard1,
                binding.subExpandableLay1,
                binding.subArrowImg1,
                R.color.colorAccent,
                R.color.colorAccent
        );

        setupSubscriptionCard(
                binding.subscriptionCard2,
                binding.subExpandableLay2,
                binding.subArrowImg2,
                R.color.colorTextHint,
                R.color.colorTextHint
        );

        setupSubscriptionCard(
                binding.subscriptionCard3,
                binding.subExpandableLay3,
                binding.subArrowImg3,
                R.color.colorWarning,
                R.color.colorWarning
        );

        setupSubscriptionCard(
                binding.subscriptionCard4,
                binding.subExpandableLay4,
                binding.subArrowImg4,
                R.color.colorInfo,
                R.color.colorInfo
        );

        setupSubscriptionCard(
                binding.subscriptionCard5,
                binding.subExpandableLay5,
                binding.subArrowImg5,
                R.color.purple_500,
                R.color.purple_500
        );

        setupSubscribeButtons();
    }

    private void setupSubscribeButtons() {
        binding.subPayBtn1.setOnClickListener(view -> {

            ViewInfoExtractor.TextViewInfo b1 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf1Txt1);
            b1.setPosition(1);

            ViewInfoExtractor.TextViewInfo b2 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf1Txt2);
            b2.setPosition(2);

            ViewInfoExtractor.TextViewInfo b3 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf1Txt3);
            b3.setPosition(3);

            ViewInfoExtractor.TextViewInfo b4 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf1Txt4);
            b4.setPosition(4);

            ViewInfoExtractor.TextViewInfo b5 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf1Txt5);
            b5.setPosition(5);

            List benefits = Arrays.asList(b1, b2, b3, b4, b5);

            Log.d("kujdvhuijkvhgsduivg", "setupSubscribeButtons: "+extractPrice(binding.subPayBtn1));

            moveToCheckOut(
                    "BRONZE",
                    extractPrice(binding.subPayBtn1),
                    extractValidity(binding.sb1Validity),
                    benefits
            );

        });

        binding.subPayBtn2.setOnClickListener(view -> {

            ViewInfoExtractor.TextViewInfo b1 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf2Txt1);
            b1.setPosition(1);

            ViewInfoExtractor.TextViewInfo b2 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf2Txt2);
            b2.setPosition(2);

            ViewInfoExtractor.TextViewInfo b3 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf2Txt3);
            b3.setPosition(3);

            ViewInfoExtractor.TextViewInfo b4 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf2Txt4);
            b4.setPosition(4);

            ViewInfoExtractor.TextViewInfo b5 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf2Txt5);
            b5.setPosition(5);

            List benefits = Arrays.asList(b1, b2, b3, b4, b5);

            moveToCheckOut(
                    "SILVER",
                    extractPrice(binding.subPayBtn2),
                    extractValidity(binding.sb2Validity),
                    benefits
            );

        });

        binding.subPayBtn3.setOnClickListener(view -> {

            ViewInfoExtractor.TextViewInfo b1 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf3Txt1);
            b1.setPosition(1);

            ViewInfoExtractor.TextViewInfo b2 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf3Txt2);
            b2.setPosition(2);

            ViewInfoExtractor.TextViewInfo b3 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf3Txt3);
            b3.setPosition(3);

            ViewInfoExtractor.TextViewInfo b4 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf3Txt4);
            b4.setPosition(4);

            ViewInfoExtractor.TextViewInfo b5 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf3Txt5);
            b5.setPosition(5);

            ViewInfoExtractor.TextViewInfo b6 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf3Txt6);
            b6.setPosition(6);

            ViewInfoExtractor.TextViewInfo b7 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf3Txt7);
            b7.setPosition(7);

            List benefits = Arrays.asList(b1, b2, b3, b4, b5, b6, b7);

            moveToCheckOut(
                    "GOLD",
                    extractPrice(binding.subPayBtn3),
                    extractValidity(binding.sb3Validity),
                    benefits
            );

        });


        binding.subPayBtn4.setOnClickListener(view -> {

            ViewInfoExtractor.TextViewInfo b1 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf4Txt1);
            b1.setPosition(1);

            ViewInfoExtractor.TextViewInfo b2 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf4Txt2);
            b2.setPosition(2);

            ViewInfoExtractor.TextViewInfo b3 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf4Txt3);
            b3.setPosition(3);

            ViewInfoExtractor.TextViewInfo b4 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf4Txt4);
            b4.setPosition(4);

            ViewInfoExtractor.TextViewInfo b5 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf4Txt5);
            b5.setPosition(5);

            ViewInfoExtractor.TextViewInfo b6 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf4Txt6);
            b6.setPosition(6);

            ViewInfoExtractor.TextViewInfo b7 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf4Txt7);
            b7.setPosition(7);

            ViewInfoExtractor.TextViewInfo b8 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf4Txt8);
            b8.setPosition(8);

            List benefits = Arrays.asList(b1, b2, b3, b4, b5, b6, b7, b8);

            moveToCheckOut(
                    "DIAMOND",
                    extractPrice(binding.subPayBtn4),
                    extractValidity(binding.sb4Validity),
                    benefits
            );

        });

        binding.subPayBtn5.setOnClickListener(view -> {

            ViewInfoExtractor.TextViewInfo b1 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf5Txt1);
            b1.setPosition(1);

            ViewInfoExtractor.TextViewInfo b2 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf5Txt2);
            b2.setPosition(2);

            ViewInfoExtractor.TextViewInfo b3 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf5Txt3);
            b3.setPosition(3);

            ViewInfoExtractor.TextViewInfo b4 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf5Txt4);
            b4.setPosition(4);

            ViewInfoExtractor.TextViewInfo b5 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf5Txt5);
            b5.setPosition(5);

            ViewInfoExtractor.TextViewInfo b6 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf5Txt6);
            b6.setPosition(6);

            ViewInfoExtractor.TextViewInfo b7 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf5Txt7);
            b7.setPosition(7);

            ViewInfoExtractor.TextViewInfo b8 = ViewInfoExtractor.extractTextViewInfo(context, binding.sbf5Txt8);
            b8.setPosition(8);

            List benefits = Arrays.asList(b1, b2, b3, b4, b5, b6, b7, b8);

            moveToCheckOut(
                    "NIKAH",
                    extractPrice(binding.subPayBtn5),
                    extractValidity(binding.sb5Validity),
                    benefits
            );

        });

    }
    private String extractValidity(TextView sb5Validity) {
        return sb5Validity.getText().toString().replace("@ ", "Validity for ");
    }

    private Double extractPrice(MaterialButton payBtn) {
        if (payBtn == null || payBtn.getText() == null) {
            return 0.0;
        }

        try {
            // 🔹 Get text and clean it
            String text = payBtn.getText().toString();

            // 🔹 Remove all characters except digits and decimal point
            // Example: "Subscribe for ₹1,499.00" -> "1499.00"
            String numeric = text.replaceAll("[^\\d.]", "");

            // 🔹 If no valid number found, return 0
            if (numeric.isEmpty()) {
                return 0.0;
            }

            // 🔹 Parse double safely
            return Double.parseDouble(numeric);

        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    /**
     * Handles expand/collapse logic for a single subscription card.
     *
     * @param cardView     The parent card view.
     * @param expandable   The expandable layout inside the card.
     * @param arrowView    The arrow image view used to indicate expansion.
     * @param defaultColor The default stroke color when collapsed.
     */
    private void setupSubscriptionCard(View cardView, View expandable, View arrowView, int defaultColor, int highlightColor) {
        cardView.setOnClickListener(v -> {
            boolean isExpanded = expandable.getVisibility() == View.VISIBLE;

            if (isExpanded) {
                collapseCard(cardView, expandable, arrowView, defaultColor);
            } else {
                expandCard(cardView, expandable, arrowView, highlightColor);
            }
        });
    }

    private void moveToCheckOut(String planName, Double planPrice, String validity, List<ViewInfoExtractor.TextViewInfo> planBenefits) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("planName", planName);
        intent.putExtra("planPrice", planPrice);
        intent.putExtra("planValidity", validity);
//        intent.putExtra("planBenefits", JSONUtil.toJson(planBenefits));
        context.startActivity(intent);
    }

    /**
     * Expands the given subscription card with animation and style changes.
     */
    private void expandCard(View cardView, View expandable, View arrowView, int highlightColor) {
        ViewAnimationUtil.expand(expandable);

        // Highlight card border
        setCardStyle(cardView, highlightColor, 2);

        // Rotate arrow to indicate expansion
        arrowView.animate()
                .rotation(90f)
                .setDuration(300)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    /**
     * Collapses the given subscription card with animation and resets styles.
     */
    private void collapseCard(View cardView, View expandable, View arrowView, int defaultColor) {
        ViewAnimationUtil.collapse(expandable);

        // Reset card border
        setCardStyle(cardView, defaultColor, 1);

        // Reset arrow rotation
        arrowView.animate()
                .rotation(0f)
                .setDuration(300)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    /**
     * Updates the stroke color and width of a MaterialCardView.
     */
    private void setCardStyle(View cardView, int colorResId, int strokeWidthDp) {
        if (cardView instanceof MaterialCardView) {
            MaterialCardView card = (MaterialCardView) cardView;
            card.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(context, colorResId)));
            card.setStrokeWidth(dpToPx(strokeWidthDp));
        }
    }

    /**
     * Converts dp to pixels based on display density.
     */
    private int dpToPx(int dp) {
        return Math.round(dp * context.getResources().getDisplayMetrics().density);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Prevent memory leaks
    }
}