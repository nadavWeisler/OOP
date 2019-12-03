import oop.ex3.searchengine.Hotel;
import oop.ex3.searchengine.HotelDataset;

import java.util.Arrays;
import java.util.HashMap;

public class BoopingSite {
    private Hotel[] hotels;

    /**
     * BoobingSite constructor
     * @param name Hotel dataset name
     */
    public BoopingSite(String name) {
        this.hotels = HotelDataset.getHotels(name);
    }

    /**
     *
     * @param city
     * @return
     */
    public Hotel[] getHotelsInCityByRating(String city) {
        HashMap<Integer, Hotel[]> hashMap = new HashMap<>();
        int hotelsCount = 0;
        for (Hotel hotel :
                this.hotels) {
            if (hotel.getCity().equals(city)) {
                hotelsCount++;
                if (hashMap.containsKey(hotel.getStarRating())) {
                    hashMap.put(hotel.getStarRating(), addNewHotel(hashMap, hotel));
                } else {
                    hashMap.put(hotel.getStarRating(), new Hotel[]{hotel});
                }
            }
        }

        int[] sortedKeys = Utils.IntSetToArray(hashMap.keySet());
        Arrays.sort(sortedKeys);


        Hotel[] results = new Hotel[hotelsCount];
        hotelsCount = 0;
        for (int rate :
                sortedKeys) {
            for (Hotel hotel :
                    hashMap.get(rate)) {
                results[hotelsCount] = hotel;
                hotelsCount++;
            }
        }

        return results;
    }

    /**
     * Replace hotel array to one include given hotel
     * @param hashMap Hotels hash map by rating
     * @param hotel Hotel to add
     */
    private Hotel[] addNewHotel(HashMap<Integer, Hotel[]> hashMap, Hotel hotel) {
        int length = hashMap.get(hotel.getStarRating()).length;
        Hotel[] help = new Hotel[length + 1];
        for (int i = 0; i < length; i++) {
            help[i] = hashMap.get(hotel.getStarRating())[i];
        }
        help[length] = hotel;
        return help;
    }

    /**
     *
     * @param latitude
     * @param longitude
     * @return
     */
    public Hotel[] getHotelsByProximity(double latitude, double longitude) {

        return new Hotel[0];
    }

    /**
     *
     * @param city
     * @param latitude
     * @param longitude
     * @return
     */
    public Hotel[] getHotelsInCityByProximity(String city, double latitude, double longitude) {
        return null;
    }
}
