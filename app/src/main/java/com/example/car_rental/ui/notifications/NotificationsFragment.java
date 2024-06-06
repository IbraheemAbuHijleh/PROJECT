package com.example.car_rental.ui.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.example.car_rental.R;
import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SpecialOffersAdapter adapter;
    private List<SpecialOffer> specialOffers;

    private static final String CHANNEL_ID = "special_offers_channel";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_special_offers);
        progressBar = rootView.findViewById(R.id.progressBar_specialOffers);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        specialOffers = new ArrayList<>();
        adapter = new SpecialOffersAdapter(specialOffers);
        recyclerView.setAdapter(adapter);

        // Load special offers (this could be a network request in a real app)
        loadSpecialOffers();

        return rootView;
    }

    private void loadSpecialOffers() {
        // dummy data
        specialOffers.add(new SpecialOffer(1,"BMW trigger", 1234,"20%", "$1000", "29-11-2024","3000$"));
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);

        // display the notification
        displayNotification("Special Offer", "Check out the new special offers on cars!");
    }

    private void displayNotification(String title, String content) {
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Special Offers", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Notifications for special offers");
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.niti)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        notificationManager.notify(1, notification);
    }
}