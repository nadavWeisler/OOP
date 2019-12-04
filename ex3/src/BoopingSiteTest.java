import oop.ex3.searchengine.Hotel;
import oop.ex3.searchengine.HotelDataset;
import org.junit.Assert;

public class BoopingSiteTest {

    /**
     * hotel_dataset.txt
     */
    private static final String HOTEL_DATASET = "hotels_dataset.txt";

    /**
     * hotel_tst1.txt
     */
    private static final String HOTEL_TEST1 = "hotels_tst1.txt";

    /**
     * hotels_tst2.txt
     */
    private static final String HOTEL_TEST2 = "hotels_tst2.txt";

    /**
     * BoopingSite object
     */
    private static BoopingSite site;

    /**
     * Current class for testing
     */
    private static String currentClass;

    /**
     *  Prepare for tests
     */
    private static void prepareTest() {
        currentClass = "BoopingSite";
    }

    /**
     * Get all hotels from array from one city
     * @param city City string
     * @param hotels Hotels array
     * @return Hotels array from given city
     */
    private static Hotel[] allHotelsInCity(String city, Hotel[] hotels) {
        int count = 0;
        for (Hotel hotel :
                hotels) {
            if (hotel.getCity().equals(city)) {
                count++;
            }
        }
        Hotel[] result = new Hotel[count];
        count = 0;
        for (Hotel hotel :
                hotels) {
            if (hotel.getCity().equals(city)) {
                result[count] = hotel;
                count++;
            }
        }
        return result;
    }

    /**
     * Return the hotel with the max rate
     * @param hotel1 Hotel object 1
     * @param hotel2 Hotel object 2
     * @return Max rate hotel object
     */
    private static Hotel RateMax(Hotel hotel1, Hotel hotel2) {
        if (hotel1.getStarRating() == hotel2.getStarRating()) {
            int alphabet = hotel1.getPropertyName().compareTo(hotel2.getPropertyName());
            if (alphabet < 0) {
                return hotel1;
            } else {
                return hotel2;
            }
        } else {
            if (hotel1.getStarRating() > hotel2.getStarRating()) {
                return hotel2;
            } else {
                return hotel1;
            }
        }
    }

    /**
     * Get distance between hotel and specific dot
     *
     * @param hotel     Hotel
     * @param latitude  X
     * @param longitude Y
     * @return Distance from hotel (double)
     */
    private static double getDistance(Hotel hotel, double latitude, double longitude) {
        return Math.sqrt(Math.pow((hotel.getLatitude() - latitude), 2) +
                Math.pow((hotel.getLongitude() - longitude), 2));
    }

    /**
     * Get Hotel with max proximity
     * @param hotel1 Hotel object 1
     * @param hotel2 Hotel object 2
     * @param x Latitude
     * @param y Longitude
     * @return Max proximity hotel
     */
    private static Hotel ProximityMax(Hotel hotel1, Hotel hotel2, double x, double y) {
        if (getDistance(hotel1, x, y) == getDistance(hotel2, x, y)) {
            if (hotel1.getNumPOI() > hotel2.getNumPOI()) {
                return hotel1;
            } else {
                return hotel2;
            }
        } else if (getDistance(hotel1, x, y) > getDistance(hotel2, x, y)) {
            return hotel2;
        } else {
            return hotel1;
        }
    }

    /**
     * Test "getHotelsInCityByRating" method
     */
    private static void getHotelsInCityByRatingTest() {
        int count = 1;

        //BoopingSiteTest getHotelsInCityByRatingTest Test 1
        site = new BoopingSite("");
        Hotel[] hotels_in_manali_by_rating = site.getHotelsInCityByRating("manali");
        Assert.assertEquals(Utils.GenerateTestString(count,
                currentClass,
                "getHotelsInCityByRatingTest"),
                hotels_in_manali_by_rating.length,
                0);
        count++;

        //BoopingSiteTest getHotelsInCityByRatingTest Test 2
        site = new BoopingSite("NOT_EXIST.png");
        hotels_in_manali_by_rating = site.getHotelsInCityByRating("manali");
        Assert.assertEquals(Utils.GenerateTestString(count,
                currentClass,
                "getHotelsInCityByRatingTest"),
                hotels_in_manali_by_rating.length,
                0);
        count++;

        //BoopingSiteTest getHotelsInCityByRatingTest Test 3
        site = new BoopingSite(HOTEL_DATASET);
        Hotel[] all_hotels = HotelDataset.getHotels(HOTEL_DATASET);
        Hotel[] all_hotels_from_manali = allHotelsInCity("manali", all_hotels);
        hotels_in_manali_by_rating = site.getHotelsInCityByRating("manali");
        TestHotelsByRating(count, all_hotels_from_manali, hotels_in_manali_by_rating);
        count++;

        //BoopingSiteTest getHotelsInCityByRatingTest Test 4
        site = new BoopingSite(HOTEL_TEST1);
        all_hotels = HotelDataset.getHotels(HOTEL_TEST1);
        Hotel[] all_hotels_from_delphi = allHotelsInCity("delphi", all_hotels);
        Hotel[] hotels_in_delphi_by_rating = site.getHotelsInCityByRating("delphi");
        TestHotelsByRating(count, all_hotels_from_delphi, hotels_in_delphi_by_rating);

        //BoopingSiteTest getHotelsInCityByRatingTest Test 5
        site = new BoopingSite(HOTEL_TEST2);
        all_hotels = HotelDataset.getHotels(HOTEL_TEST2);
        Hotel[] all_hotels_from_dimona = allHotelsInCity("dimona", all_hotels);
        Hotel[] hotels_in_dimona_by_rating = site.getHotelsInCityByRating("dimona");
        TestHotelsByRating(count, all_hotels_from_dimona, hotels_in_dimona_by_rating);
    }

    /**
     * Test hotel ranking
     *
     * @param count                  Test count int
     * @param allHotelsFromCity      All hotels from city
     * @param hotelsFromCityByRating Result
     */
    private static void TestHotelsByRating(int count, Hotel[] allHotelsFromCity, Hotel[] hotelsFromCityByRating) {
        Assert.assertEquals(Utils.GenerateTestString(count,
                currentClass,
                "getHotelsInCityByRatingTest"),
                hotelsFromCityByRating.length,
                allHotelsFromCity.length);

        for (int i = 1; i < hotelsFromCityByRating.length; i++) {
            Assert.assertSame(Utils.GenerateTestString(count,
                    "BoopingSite",
                    "getHotelsInCityByRatingTest"),
                    RateMax(hotelsFromCityByRating[i], hotelsFromCityByRating[i - 1]).getPropertyName(),
                    hotelsFromCityByRating[i - 1].getPropertyName());
        }
    }

    /**
     * Test hotels proximity
     * @param count Test count
     * @param resultHotels Hotels array
     * @param x Latitude
     * @param y Longitude
     */
    private static void TestHotelsProximity(int count, Hotel[] resultHotels, double x, double y) {
        for (int i = 1; i < resultHotels.length; i++) {
            Assert.assertSame(Utils.GenerateTestString(count,
                    currentClass,
                    "getHotelsByProximity"),
                    ProximityMax(resultHotels[i], resultHotels[i - 1], x, y).getPropertyName(),
                    resultHotels[i - 1].getPropertyName());
        }
    }

    /**
     * Test Hotels city and proximity
     * @param count Test count
     * @param resultHotels Hotels array
     * @param x Latitude
     * @param y Longitude
     * @param city City string
     */
    private static void TestHotelCityAndProximity(int count, Hotel[] resultHotels,
                                                  double x, double y, String city) {
        boolean result = true;
        for (Hotel hotel :
                resultHotels) {
            if (!hotel.getCity().equals(city)) {
                result = false;
                break;
            }
        }

        Assert.assertTrue(Utils.GenerateTestString(count, currentClass, "TestHotelCityAndProximity"),
                result);

        TestHotelsProximity(count, resultHotels, x, y);
    }

    /**
     *  Test getHotelsByProximity method
     */
    private static void getHotelsByProximityTest() {
        int count = 1;

        //BoopingSiteTest getHotelsByProximity Test 1
        site = new BoopingSite("");
        Hotel[] hotelsByProximity = site.getHotelsByProximity(1, 2);
        Assert.assertEquals(Utils.GenerateTestString(count,
                currentClass,
                "getHotelsByProximity"),
                hotelsByProximity.length,
                0);
        count++;

        //BoopingSiteTest getHotelsByProximity Test 2
        site = new BoopingSite("NOT_EXIST.png");
        hotelsByProximity = site.getHotelsByProximity(1, 2);
        Assert.assertEquals(Utils.GenerateTestString(count,
                currentClass,
                "getHotelsByProximity"),
                hotelsByProximity.length,
                0);
        count++;

        //BoopingSiteTest getHotelsByProximity Test 3
        site = new BoopingSite(HOTEL_DATASET);
        hotelsByProximity = site.getHotelsByProximity(1, 2);
        TestHotelsProximity(count, hotelsByProximity, 1, 2);
        count++;

        //BoopingSiteTest getHotelsByProximity Test 4
        site = new BoopingSite(HOTEL_TEST1);
        hotelsByProximity = site.getHotelsByProximity(23, 2);
        TestHotelsProximity(count, hotelsByProximity, 23, 2);
        count++;

        //BoopingSiteTest getHotelsByProximity Test 5
        site = new BoopingSite(HOTEL_TEST2);
        hotelsByProximity = site.getHotelsByProximity(100, 20);
        TestHotelsProximity(count, hotelsByProximity, 100, 20);
    }

    /**
     *  Test getHotelsInCityByProximity method
     */
    private static void getHotelsInCityByProximity() {
        int count = 1;

        //BoopingSiteTest getHotelsInCityByProximity Test 1
        site = new BoopingSite("");
        Hotel[] hotelsByProximityAndCity = site.getHotelsInCityByProximity("", 1, 2);
        Assert.assertEquals(Utils.GenerateTestString(count,
                currentClass,
                "getHotelsInCityByProximity"),
                hotelsByProximityAndCity.length,
                0);
        count++;

        //BoopingSiteTest getHotelsInCityByProximity Test 2
        site = new BoopingSite("NOT_EXIST.png");
        hotelsByProximityAndCity = site.getHotelsInCityByProximity("manali", 1, 2);
        Assert.assertEquals(Utils.GenerateTestString(count,
                currentClass,
                "getHotelsByProximity"),
                hotelsByProximityAndCity.length,
                0);
        count++;

        //BoopingSiteTest getHotelsInCityByProximity Test 3
        site = new BoopingSite(HOTEL_DATASET);
        hotelsByProximityAndCity = site.getHotelsInCityByProximity("manali", 1, 2);
        TestHotelCityAndProximity(count, hotelsByProximityAndCity, 1, 2, "manali");
        count++;

        //BoopingSiteTest getHotelsInCityByProximity Test 4
        site = new BoopingSite(HOTEL_TEST1);
        hotelsByProximityAndCity = site.getHotelsInCityByProximity("delphi", 23, 2);
        TestHotelCityAndProximity(count, hotelsByProximityAndCity, 23, 2, "delphi");
        count++;

        //BoopingSiteTest getHotelsInCityByProximity Test 5
        site = new BoopingSite(HOTEL_TEST2);
        hotelsByProximityAndCity = site.getHotelsInCityByProximity("dimona", 100, 20);
        TestHotelCityAndProximity(count, hotelsByProximityAndCity, 100, 20, "dimona");
    }

    /**
     *  Test all boopingSite methods
     */
    public static void TestAllBoopingSite(){
        prepareTest();
        getHotelsInCityByRatingTest();
        getHotelsByProximityTest();
        getHotelsInCityByProximity();
    }
}
