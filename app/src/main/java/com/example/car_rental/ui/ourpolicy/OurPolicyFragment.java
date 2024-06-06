package com.example.car_rental.ui.ourpolicy;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.car_rental.R;

public class OurPolicyFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_our_policy, container, false);
        TextView htmlTextView = root.findViewById(R.id.htmlTextView);

        // HTML content to be displayed
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +

                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "    <h1>Privacy Policy</h1>\n" +
                "    <p>Last updated: [6/5/2024]</p>\n" +
                "\n" +
                "    <p>Welcome to RYDI. This privacy policy explains how we collect, use, and protect your personal information when you use our car rental services.</p>\n" +
                "\n" +
                "    <h2>Information We Collect</h2>\n" +
                "    <p>We may collect the following types of information:</p>\n" +
                "    <ul>\n" +
                "        <li>Personal identification information (Name, email address, phone number, etc.)</li>\n" +
                "        <li>Payment information (credit card details, billing address, etc.)</li>\n" +
                "        <li>Driver's license information</li>\n" +
                "        <li>Rental history and preferences</li>\n" +
                "        <li>Usage data (pages visited, actions taken on the site, etc.)</li>\n" +
                "    </ul>\n" +
                "\n" +
                "    <h2>How We Use Your Information</h2>\n" +
                "    <p>We use the information we collect for the following purposes:</p>\n" +
                "    <ul>\n" +
                "        <li>To provide and maintain our services</li>\n" +
                "        <li>To process transactions and send you related information</li>\n" +
                "        <li>To improve our services and customer experience</li>\n" +
                "        <li>To communicate with you, including sending promotional offers and updates</li>\n" +
                "        <li>To comply with legal obligations</li>\n" +
                "    </ul>\n" +
                "\n" +
                "    <h2>How We Protect Your Information</h2>\n" +
                "    <p>We implement a variety of security measures to maintain the safety of your personal information. Your personal information is contained behind secured networks and is only accessible by a limited number of persons who have special access rights to such systems, and are required to keep the information confidential.</p>\n" +
                "\n" +
                "    <h2>Sharing Your Information</h2>\n" +
                "    <p>We do not sell, trade, or otherwise transfer to outside parties your Personally Identifiable Information unless we provide users with advance notice. This does not include website hosting partners and other parties who assist us in operating our website, conducting our business, or serving our users, so long as those parties agree to keep this information confidential.</p>\n" +
                "\n" +
                "    <h2>Your Consent</h2>\n" +
                "    <p>By using our site or services, you consent to our privacy policy.</p>\n" +
                "\n" +
                "    <h2>Changes to Our Privacy Policy</h2>\n" +
                "    <p>If we decide to change our privacy policy, we will post those changes on this page. Policy changes will apply only to information collected after the date of the change.</p>\n" +
                "\n" +
                "    <h2>Contact Us</h2>\n" +
                "    <p>If there are any questions regarding this privacy policy, you may contact us using the information below:</p>\n" +
                "    <p>RYDI<br>\n" +
                "        [Rammallah]<br>\n" +
                "        [ramallah, Bierah, 2345729##$%]<br>\n" +
                "        [yunanawahdah@gmail.com]<br>\n" +
                "        [0597908705]</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

        htmlTextView.setText(Html.fromHtml(htmlContent));
        return root;
    }
}
