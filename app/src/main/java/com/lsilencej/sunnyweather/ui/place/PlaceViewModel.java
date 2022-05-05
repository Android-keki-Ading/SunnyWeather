package com.lsilencej.sunnyweather.ui.place;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.lsilencej.sunnyweather.logic.Repository;
import com.lsilencej.sunnyweather.logic.model.PlaceResponse.Place;
import com.lsilencej.sunnyweather.logic.model.PlaceResponse.PlaceResponse;

import java.util.ArrayList;
import java.util.List;

public class PlaceViewModel extends ViewModel {

    private MutableLiveData<String> searchLiveData = new MutableLiveData<String>();
    private List<Place> placeList = new ArrayList<>();


    private LiveData<PlaceResponse> placeLiveData = Transformations.switchMap(searchLiveData, new Function<String, LiveData<PlaceResponse>>() {
        @Override
        public LiveData<PlaceResponse> apply(String input) {
            return Repository.searchPlaces(input);
        }
    });

    public void searchPlaces(String query) {
        searchLiveData.setValue(query);
    }

    public LiveData<PlaceResponse> getPlaceLiveData() {
        return placeLiveData;
    }

    public List<Place> getPlaceList() {
        return placeList;
    }

    public void savePlace(Place place) {
        Repository.savePlace(place);
    }

    public Place getSavedPlace() {
        return Repository.getSavedPlace();
    }

    public Boolean isPlaceSaved() {
        return Repository.isPlaceSaved();
    }

}
