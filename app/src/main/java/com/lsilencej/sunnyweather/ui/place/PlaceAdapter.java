package com.lsilencej.sunnyweather.ui.place;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.lsilencej.sunnyweather.R;
import com.lsilencej.sunnyweather.logic.model.PlaceResponse.Place;
import com.lsilencej.sunnyweather.ui.weather.WeatherActivity;

import java.util.List;

/** 继承Recycler.Adapter, 指定泛型<PlaceAdapter.ViewHolder>
 * @package: com.lsilencej.sunnyweather.ui.place
 * @className: PlaceAdapter
 * @time: 2022-05-05 17:44
 */
public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    private PlaceFragment placeFragment;
    private List<Place> placeList;

    public PlaceAdapter(PlaceFragment placeFragment, List<Place> placeList) {
        this.placeFragment = placeFragment;
        this.placeList = placeList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView placeName;
        private TextView placeAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.placeName = itemView.findViewById(R.id.placeName);
            this.placeAddress = itemView.findViewById(R.id.placeAddress);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAbsoluteAdapterPosition();
                Place place = placeList.get(position);
                Activity activity = placeFragment.getActivity();
                if (activity instanceof WeatherActivity) {
                    WeatherActivity weatherActivity = (WeatherActivity) activity;
                    weatherActivity.getDrawerLayout().closeDrawers();
                    weatherActivity.getViewModel().setLocationLng(place.getLocation().getLng());
                    weatherActivity.getViewModel().setLocationLat(place.getLocation().getLat());
                    weatherActivity.getViewModel().setPlaceName(place.getName());
                    weatherActivity.refreshWeather();
                } else {
                    Intent intent = new Intent(parent.getContext(), WeatherActivity.class);
                    intent.putExtra("location_lng", place.getLocation().getLng());
                    intent.putExtra("location_lat", place.getLocation().getLat());
                    intent.putExtra("place_name", place.getName());
                    placeFragment.startActivity(intent);
                    if (placeFragment.getActivity() != null) {
                        placeFragment.getActivity().finish();
                    }
                }
                placeFragment.placeViewModel.savePlace(place);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Place place = placeList.get(position);
        holder.placeName.setText(place.getName());
        holder.placeAddress.setText(place.getAddress());
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

}
