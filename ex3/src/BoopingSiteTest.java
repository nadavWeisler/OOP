import oop.ex3.searchengine.Hotel;
import oop.ex3.searchengine.HotelDataset;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoopingSiteTest {
    private final String HOTEL_DATASET = "hotels_dataset.txt";
    private final String HOTEL_TEST1 = "hotels_tst1.txt";
    private final String HOTEL_TEST2 = "hotels_tst2.txt";
    private BoopingSite site;
    private String currentClass;

    @Before
    public void prepareTest() {
        this.currentClass = "BoopingSite";
    }

    private Hotel[] allHotelsInCity(String city, Hotel[] hotels) {
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

    private Hotel RateMax(Hotel hotel1, Hotel hotel2) {
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
    private double getDistance(Hotel hotel, double latitude, double longitude) {
        return Math.sqrt(Math.pow((hotel.getLatitude() - latitude), 2) +
                Math.pow((hotel.getLongitude() - longitude), 2));
    }

    private Hotel ProximityMax(Hotel hotel1, Hotel hotel2, double x, double y) {
        if (this.getDistance(hotel1, x, y) == this.getDistance(hotel2, x, y)) {
            if (hotel1.getNumPOI() > hotel2.getNumPOI()) {
                return hotel1;
            } else {
                return hotel2;
            }
        } else if (this.getDistance(hotel1, x, y) > this.getDistance(hotel2, x, y)) {
            return hotel2;
        } else {
            return hotel1;
        }
    }

    @Test
    public void getHotelsInCityByRatingTest() {
        int count = 1;

        //BoopingSiteTest getHotelsInCityByRatingTest Test 1
        this.site = new BoopingSite("");
        Hotel[] hotels_in_manali_by_rating = this.site.getHotelsInCityByRating("manali");
        Assert.assertEquals(Utils.GenerateTestString(count,
                this.currentClass,
                "getHotelsInCityByRatingTest"),
                hotels_in_manali_by_rating.length,
                0);
        count++;

        //BoopingSiteTest getHotelsInCityByRatingTest Test 2
        this.site = new BoopingSite("NOT_EXIST.png");
        hotels_in_manali_by_rating = this.site.getHotelsInCityByRating("manali");
        Assert.assertEquals(Utils.GenerateTestString(count,
                this.currentClass,
                "getHotelsInCityByRatingTest"),
                hotels_in_manali_by_rating.length,
                0);
        count++;

        //BoopingSiteTest getHotelsInCityByRatingTest Test 3
        this.site = new BoopingSite(this.HOTEL_DATASET);
        Hotel[] all_hotels = HotelDataset.getHotels(this.HOTEL_DATASET);
        Hotel[] all_hotels_from_manali = this.allHotelsInCity("manali", all_hotels);
        hotels_in_manali_by_rating = this.site.getHotelsInCityByRating("manali");
        TestHotelsByRating(count, all_hotels_from_manali, hotels_in_manali_by_rating);
        count++;

        //BoopingSiteTest getHotelsInCityByRatingTest Test 4
        this.site = new BoopingSite(this.HOTEL_TEST1);
        all_hotels = HotelDataset.getHotels(this.HOTEL_TEST1);
        Hotel[] all_hotels_from_delphi = this.allHotelsInCity("delphi", all_hotels);
        Hotel[] hotels_in_delphi_by_rating = this.site.getHotelsInCityByRating("delphi");
        TestHotelsByRating(count, all_hotels_from_delphi, hotels_in_delphi_by_rating);

        //BoopingSiteTest getHotelsInCityByRatingTest Test 5
        this.site = new BoopingSite(this.HOTEL_TEST2);
        all_hotels = HotelDataset.getHotels(this.HOTEL_TEST2);
        Hotel[] all_hotels_from_dimona = this.allHotelsInCity("dimona", all_hotels);
        Hotel[] hotels_in_dimona_by_rating = this.site.getHotelsInCityByRating("dimona");
        TestHotelsByRating(count, all_hotels_from_dimona, hotels_in_dimona_by_rating);
    }

    /**
     * Test hotel ranking
     *
     * @param count                  Test count int
     * @param allHotelsFromCity      All hotels from city
     * @param hotelsFromCityByRating Result
     */
    private void TestHotelsByRating(int count, Hotel[] allHotelsFromCity, Hotel[] hotelsFromCityByRating) {
        Assert.assertEquals(Utils.GenerateTestString(count,
                this.currentClass,
                "getHotelsInCityByRatingTest"),
                hotelsFromCityByRating.length,
                allHotelsFromCity.length);

        for (int i = 1; i < hotelsFromCityByRating.length; i++) {
            Assert.assertSame(Utils.GenerateTestString(count,
                    "BoopingSite",
                    "getHotelsInCityByRatingTest"),
                    this.RateMax(hotelsFromCityByRating[i], hotelsFromCityByRating[i - 1]).getPropertyName(),
                    hotelsFromCityByRating[i - 1].getPropertyName());
        }
    }

    private void TestHotelsProximity(int count, Hotel[] resultHotels, double x, double y) {
        for (int i = 1; i < resultHotels.length; i++) {
            Assert.assertSame(Utils.GenerateTestString(count,
                    this.currentClass,
                    "getHotelsByProximity"),
                    this.ProximityMax(resultHotels[i], resultHotels[i - 1], x, y).getPropertyName(),
                    resultHotels[i - 1].getPropertyName());
        }
    }

    private void TestHotelCityAndProximity(int count, Hotel[] resultHotels, double x, double y, String city) {
        boolean result = true;
        for (Hotel hotel :
                resultHotels) {
            if (!hotel.getCity().equals(city)) {
                result = false;
                break;
            }
        }

        Assert.assertTrue(Utils.GenerateTestString(count, this.currentClass, "TestHotelCityAndProximity"),
                result);

        this.TestHotelsProximity(count, resultHotels, x, y);
    }

    @Test
    public void getHotelsByProximity() {
        int count = 1;

        //BoopingSiteTest getHotelsByProximity Test 1
        this.site = new BoopingSite("");
        Hotel[] hotelsByProximity = this.site.getHotelsByProximity(1, 2);
        Assert.assertEquals(Utils.GenerateTestString(count,
                this.currentClass,
                "getHotelsByProximity"),
                hotelsByProximity.length,
                0);
        count++;

        //BoopingSiteTest getHotelsByProximity Test 2
        this.site = new BoopingSite("NOT_EXIST.png");
        hotelsByProximity = this.site.getHotelsByProximity(1, 2);
        Assert.assertEquals(Utils.GenerateTestString(count,
                this.currentClass,
                "getHotelsByProximity"),
                hotelsByProximity.length,
                0);
        count++;

        //BoopingSiteTest getHotelsByProximity Test 3
        this.site = new BoopingSite(this.HOTEL_DATASET);
        hotelsByProximity = this.site.getHotelsByProximity(1, 2);
        TestHotelsProximity(count, hotelsByProximity, 1, 2);
        count++;

        //BoopingSiteTest getHotelsByProximity Test 4
        this.site = new BoopingSite(this.HOTEL_TEST1);
        hotelsByProximity = this.site.getHotelsByProximity(23, 2);
        TestHotelsProximity(count, hotelsByProximity, 23, 2);
        count++;

        //BoopingSiteTest getHotelsByProximity Test 5
        this.site = new BoopingSite(this.HOTEL_TEST2);
        hotelsByProximity = this.site.getHotelsByProximity(100, 20);
        TestHotelsProximity(count, hotelsByProximity, 100, 20);
    }

    @Test
    public void getHotelsInCityByProximity() {
        int count = 1;

        //BoopingSiteTest getHotelsInCityByProximity Test 1
        this.site = new BoopingSite("");
        Hotel[] hotelsByProximityAndCity = this.site.getHotelsInCityByProximity("", 1, 2);
        Assert.assertEquals(Utils.GenerateTestString(count,
                this.currentClass,
                "getHotelsInCityByProximity"),
                hotelsByProximityAndCity.length,
                0);
        count++;

        //BoopingSiteTest getHotelsInCityByProximity Test 2
        this.site = new BoopingSite("NOT_EXIST.png");
        hotelsByProximityAndCity = this.site.getHotelsInCityByProximity("manali", 1, 2);
        Assert.assertEquals(Utils.GenerateTestString(count,
                this.currentClass,
                "getHotelsByProximity"),
                hotelsByProximityAndCity.length,
                0);
        count++;

        //BoopingSiteTest getHotelsInCityByProximity Test 3
        this.site = new BoopingSite(this.HOTEL_DATASET);
        hotelsByProximityAndCity = this.site.getHotelsInCityByProximity("manali", 1, 2);
        TestHotelCityAndProximity(count, hotelsByProximityAndCity, 1, 2, "manali");
        count++;

        //BoopingSiteTest getHotelsInCityByProximity Test 4
        this.site = new BoopingSite(this.HOTEL_TEST1);
        hotelsByProximityAndCity = this.site.getHotelsInCityByProximity("delphi", 23, 2);
        TestHotelsProximity(count, hotelsByProximityAndCity, 23, 2);
        count++;

        //BoopingSiteTest getHotelsInCityByProximity Test 5
        this.site = new BoopingSite(this.HOTEL_TEST2);
        hotelsByProximityAndCity = this.site.getHotelsInCityByProximity("dimona", 100, 20);
        TestHotelsProximity(count, hotelsByProximityAndCity, 100, 20);
    }
}
