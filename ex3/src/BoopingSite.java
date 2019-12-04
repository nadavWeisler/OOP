import oop.ex3.searchengine.Hotel;
import oop.ex3.searchengine.HotelDataset;

import java.util.Arrays;
import java.util.HashMap;

public class BoopingSite {
    private Hotel[] hotels;

    /**
     * BoobingSite constructor
     *
     * @param name Hotel dataset name
     */
    public BoopingSite(String name) {
        try {
            this.hotels = HotelDataset.getHotels(name);
        } catch (Exception e) {
            this.hotels = new Hotel[0];
        }
    }

    /**
     * Sort hotels array by hotel name
     *
     * @param hotelsToSort Hotel array to sort
     * @return Sorted hotel array
     */
    private Hotel[] sortHotelsByName(Hotel[] hotelsToSort) {
        Hotel[] result = new Hotel[hotelsToSort.length];
        int count = 0;
        Hotel current = hotelsToSort[0];
        for (int j = 0; j < hotelsToSort.length; j++) {
            for (int i = 0; i < hotelsToSort.length; i++) {
                if (hotelsToSort[i] == null) {
                    continue;
                }
                if (current == null ||
                        hotelsToSort[i].getPropertyName().compareTo(current.getPropertyName()) < 0) {
                    current = hotelsToSort[i];
                    count = i;
                }
            }
            result[j] = current;
            current = null;
            hotelsToSort[count] = null;
            count = 0;
        }
        return result;
    }

    /**
     * Sort hotels array by points of interest
     *
     * @param hotelsToSort Hotel array to sort
     * @return Sorted hotels array
     */
    private Hotel[] sortHotelsByInterest(Hotel[] hotelsToSort) {
        Hotel[] result = new Hotel[hotelsToSort.length];
        int count = 0;
        Hotel current = hotelsToSort[0];
        for (int j = 0; j < hotelsToSort.length; j++) {
            for (int i = 0; i < hotelsToSort.length; i++) {
                if (hotelsToSort[i] == null) {
                    continue;
                }
                if (current == null || hotelsToSort[i].getNumPOI() > current.getNumPOI()) {
                    current = hotelsToSort[i];
                    count = i;
                }
            }
            result[j] = current;
            current = null;
            hotelsToSort[count] = null;
            count = 0;
        }
        return result;
    }

    /**
     * Sort hotels in specific city by ranking and alphabet
     *
     * @param city City
     * @return Sorted hotels array
     */
    public Hotel[] getHotelsInCityByRating(String city) {
        Hotel[] hotelsFromCity = this.getHotelsFromOneCity(this.hotels, city);
        HashMap<Integer, Hotel[]> hashMap = new HashMap<>();
        for (Hotel hotel :
                hotelsFromCity) {
            if (hashMap.containsKey(hotel.getStarRating())) {
                hashMap.put(hotel.getStarRating(), addNewHotelByRating(hashMap, hotel));
            } else {
                hashMap.put(hotel.getStarRating(), new Hotel[]{hotel});
            }
        }

        int[] sortedKeys = Utils.IntSetToArray(hashMap.keySet());
        Arrays.sort(sortedKeys);

        Hotel[] results = new Hotel[hotelsFromCity.length];
        int hotelsCount = 0;
        for (int rate :
                sortedKeys) {
            for (Hotel hotel :
                    this.sortHotelsByName(hashMap.get(rate))) {
                results[hotelsCount] = hotel;
                hotelsCount++;
            }
        }

        return results;
    }

    /**
     * Replace hotel array to one include given hotel
     *
     * @param hashMap Hotels hash map by rating
     * @param hotel   Hotel to add
     */
    private Hotel[] addNewHotelByRating(HashMap<Integer, Hotel[]> hashMap, Hotel hotel) {
        int length = hashMap.get(hotel.getStarRating()).length;
        Hotel[] help = new Hotel[length + 1];
        for (int i = 0; i < length; i++) {
            help[i] = hashMap.get(hotel.getStarRating())[i];
        }
        help[length] = hotel;
        return help;
    }

    /**
     * Replace hotel array to one include given hotel
     *
     * @param hashMap  Hotels hash map by rating
     * @param hotel    Hotel to add
     * @param distance Distance from hotel
     * @return Hotel array
     */
    private Hotel[] addNewHotelByDistance(HashMap<Double, Hotel[]> hashMap, Hotel hotel, double distance) {
        int length = hashMap.get(distance).length;
        Hotel[] help = new Hotel[length + 1];
        for (int i = 0; i < length; i++) {
            help[i] = hashMap.get(distance)[i];
        }
        help[length] = hotel;
        return help;
    }

    /**
     * Get distance between hotel and specific dot
     *
     * @param hotel     Hotel
     * @param latitude  X
     * @param longitude Y
     * @return Distance from hotel (double)
     */
    private double getDistance(Hotel hotel, double latitude, double longitude) {
        return Math.sqrt(Math.pow((hotel.getLatitude() - latitude), 2) +
                Math.pow((hotel.getLongitude() - longitude), 2));
    }

    /**
     * Get hotel arrays from one city sorted by proximity
     * @param latitude X
     * @param longitude Y
     * @param city City string
     * @return Hotel array
     */
    public Hotel[] getHotelsByProximityAndCity(double latitude, double longitude, String city) {
        HashMap<Double, Hotel[]> hashMap = new HashMap<>();
        int hotelsCount = 0;
        double currentDistance;
        for (Hotel hotel :
                this.hotels) {
            if (!city.isEmpty() && !city.equals(hotel.getCity())) {
                continue;
            }
            hotelsCount++;
            currentDistance = this.getDistance(hotel, latitude, longitude);
            if (hashMap.containsKey(currentDistance)) {
                hashMap.put(currentDistance, addNewHotelByDistance(hashMap, hotel, currentDistance));
            } else {
                hashMap.put(currentDistance, new Hotel[]{hotel});
            }
        }

        double[] sortedKeys = Utils.DoubleSetToArray(hashMap.keySet());
        Arrays.sort(sortedKeys);

        Hotel[] results = new Hotel[hotelsCount];
        hotelsCount = 0;
        for (double distance :
                sortedKeys) {
            for (Hotel hotel :
                    this.sortHotelsByInterest(hashMap.get(distance))) {
                results[hotelsCount] = hotel;
                hotelsCount++;
            }
        }

        return results;
    }

    /**
     * Sort hotels by distance and point of interest
     *
     * @param latitude  X
     * @param longitude Y
     * @return Sorted hotels
     */
    public Hotel[] getHotelsByProximity(double latitude, double longitude) {
        return this.getHotelsByProximityAndCity(latitude, longitude, "");
    }

    /**
     * Get count of hotels in specific city
     *
     * @param hotelsToCount Hotels array
     * @param city          Specific city
     * @return Hotel count (int)
     */
    private int getCityCountInHotels(Hotel[] hotelsToCount, String city) {
        int count = 0;
        for (Hotel hotel :
                hotelsToCount) {
            if (hotel.getCity().equals(city)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Get hotels array from one city
     *
     * @param allHotels Hotels array
     * @param city      city
     * @return Hotel array
     */
    private Hotel[] getHotelsFromOneCity(Hotel[] allHotels, String city) {
        Hotel[] result = new Hotel[this.getCityCountInHotels(allHotels, city)];
        int count = 0;
        for (Hotel hotel :
                this.hotels) {
            if (hotel.getCity().equals(city)) {
                result[count] = hotel;
                count++;
            }
        }

        return result;
    }

    /**
     * Get sorted hotels array by distance and point of interest from one city
     *
     * @param city      City string
     * @param latitude  X
     * @param longitude Y
     * @return Sorted hotels array
     */
    public Hotel[] getHotelsInCityByProximity(String city, double latitude, double longitude) {
        return this.getHotelsByProximityAndCity(latitude, longitude, city);

    }
}
