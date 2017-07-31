package com.example.abhiraj.offersky;

/**
 * Created by Abhiraj on 17-04-2017.
 */

public class Constants {

    public interface Broadcast {
        String MALL_DATA_READY = "malldataReadybroadcast";
        String VISITOR_DATA_READY = "visitordataReadybroadcast";
        String COUPON_ALLOT = "coupon_allotted";
    }

    public interface IntentKeys{
        String SHOP_ID = "shopID";
        String VISITOR_NO = "visitor_no";
        String SHOP_PHOTOS_LIST = "shop_photos";
    }

    public interface Signup{
        String USER_DEFAULT_EMAIL_DOMAIN = "@offersky.com";
        String USER_DEFAULT_PASSWORD = "ds123456789";
    }


    public interface Geofence
    {
        static final String LATITUDE = "latitude";
        static final String LONGITUDE = "longitude";
        static final String RADIUS = "radius";

        static final String GEOFENCE_REQUEST_ID = "my geofence";

        static final String GEOFENCE_ENTER_BROADCAST = "geofence_enter";
        static final String GEOFENCE_EXIT_BROADCAST = "geofence_exit";

        static final String GEOFENCE_CREATED = "GeofenceCreated";

        static final String GOOGLE_API_CONNECTED = "APIConnected";

        static final String SHOW_EARNING_ICON = "show earning icon";

        static final String SHOW_DEFAULT_ICON = "show default icon";

    }

    public interface Location
    {
        static final String GPS_ENABLED = "GPSEnabled";
        static final String GPS_STATE_ON_BROADCAST = "GPS turned on";
        String GPS_STATE_OFF_BROADCAST = "GPS turned off";
        int REQUEST_CHECK_LOCATION_SETTINGS = 121;
    }

    public interface Permission
    {
        static final int ACCESS_FINE_LOCATION_PERMISSION = 999;
    }

    public interface SharedPreferences
    {
        static final String STEPS_FILE = "steps_file";
        static final String STEPS = "stores_steps";
        static final String MALL_ID = "mallId";
        static final String USER_PREF_FILE = "user_pref_file";
        String EARNING_STATUS = "earning_status";
        String EARNING = "earning";
        String NOT_EARNING = "not_earning";
        // to show or hide progress dialog
        String MALL_CHECK_STATUS = "mall_check_status";
        String MALL_CHECK_STATUS_CHECKING = "checking mall status";
        String MALL_CHECK_STATUS_NOT_CHECKING = "not checking mall status";

        String MALL_JSON = "mall_data_json_string";
    }

}
