package com.teradata.dmp.dpsdk;

/**
 * Dimensions
 *
 * @author Teradata
 */
public class Dimensions {

    public final static String FLXONE_CUSTOMER_ID = "m"; // Customer ID of FLXone.
    public final static String FINGERPRINT = "fp"; // The hash capable of fingerprinting devices (32 bits) only available in js pixels, else it will be 0.
    public final static String INTERACTION_TYPE = "it"; // 0 = "No Interaction, 1 = "Play, 2 = "Pause, 3 = "Finished, 4 = "Time Spent, 5 = "Swipe, 6 = "Ad Closed, 7 = "Ad Viewed, 8 = "Scroll, 9 = "Ad Hover, 10 = "Conversion, 11 = "Post View Conversion, 12 = "Form Field Focus, 13 = "Form Field Click, 14 = "Form Field Keydown, 15 = "User Defined Conversion, 16 = "Classifier match, 17 = "Classifier non-match, 18 = "FLXone testing, 19 = "FLXone optimization
    public final static String INTERACTION_VALUE = "iv"; // E.g. in case of video played, 20.0 (seconds).
    public final static String PIXEL_ID = "id"; // Pixel ID.
    public final static String GEO_COUNTRY = "_geo_c"; // Geo country in 3 characters conform ISO 3166-1 alpha-3.
    public final static String GEO_STATE = "_geo_s"; // Geo state conform maxmind geo ip.
    public final static String GEO_CITY = "_geo_t"; // Geo city (e.g. New York City, Gemert, Eindhoven) format in string, lowercase, utf8.
    public final static String GEO_POSTAL_CODE = "_geo_p"; // Postal code (e.g. 1234AB).
    public final static String GEO_DMA_CODE = "_geo_d"; // Designated market area code (e.g. 807).
    public final static String GEO_LAT = "lat"; // Geo latitude.
    public final static String GEO_LONG = "lon"; // Geo longitude.
    public final static String BROWSER_DATETIME = "bdt"; // YYYY-MM-DD HH:MM:SS
    public final static String BROWSER_TIMEZONE_OFFSET = "btzo"; // Offset in minutes to UTC (both positive and negative values are valid).
    public final static String BROWSER_RESOLUTION_WIDTH = "bresw"; // Pixel width of the browser.
    public final static String BROWSER_RESOLUTION_HEIGHT = "bresh"; // Pixel height of the browser.
    public final static String SCREEN_RESOLUTION_WIDTH = "bsresw"; // Pixel width of the screen.
    public final static String SCREEN_RESOLUTION_HEIGHT = "bsresh"; // Pixel height of the screen.
    public final static String OPERATING_SYSTEM = "_os"; // Operating system mapped to integer. 0 = "Unknown, 1 = "Windows, 2 = "Mac, 3 = "Linux, 4 = "Android, 5 = "iOS Etc.§
    public final static String OPERATING_SYSTEM_VERSION = "_osv"; // Full version of the operating system (Windows 8 = "8, Android KitKat = "4.4) iOS 7 = "7, Etc.
    public final static String DEVICE_TYPE = "_devt"; // 1 = "Desktop, 2 = "Tablet, 3 = "Smartphone, 4 = "Smart TV, Etc.
    public final static String DEVICE_BRAND = "_devb"; // E.g. Samsung, Windows, Apple, Dell, Blackberry, Huawei, etc.
    public final static String DEVICE_MODEL = "_devm"; // E.g. Galaxy S5, iPhone 6, Nokia 3310
    public final static String CONNECTION_TYPE = "_ct"; // 1 = "Wired, 2 = "WiFi, 3 = "Mobile
    public final static String CONNECTION_CARRIER = "_car"; // E.g. Vodafone, AT&T, Verizon, etc.
    public final static String EVENT_REFERER_URL = "r"; // Page visited before event_url, must be populated through URL parameter.
    public final static String EVENT_URL = "eurl"; // URL of the current event, e.g. impression shown at http://www.thetimes.co.uk/tto/business/
    public final static String AD_POSITION = "adp"; // 1 = "Above the fold, 2 = "Under the fold
    public final static String AD_POSITION_OFFSET = "adpos"; // Pixels offset until top of page.
    public final static String PAGE_HEIGHT = "phegh"; // Pixels height of page where the ad was served.
    public final static String SITE_DOMAIN = "_sdom"; // Extract hostname of the URL (order: event_url > referer_url).
    public final static String PLATFORM = "pl"; // 1 = "FlxOne, 2 = "AppNexus, 3 = "Google, 4 = "Rubicon, 5 = "AdGear, 6 = "The Trade Desk Etc.
    public final static String PLATFORM_EXCHANGE = "plx"; // In case of buying from Google Adx through AppNexus this will contain the identifier for Google Adx. E.g. 1= "FlxOne, 2 = "AppNexus, 3 = "Google, 4 = "Rubicon, Etc.
    public final static String AUCTION_ID = "aid"; // Platform auction ID.
    public final static String BUYER_ID = "buyid"; // Buyer ID. If the platform is FlxOne, this will be the same as flxone_customer_id. In any other cases it will represent the external platform buyer member ID.
    public final static String SELLER_ID = "selid"; // Seller ID. If the platform is FlxOne, this will be the same as flxone_customer_id. In any other cases it will represent the external platform seller member ID.
    public final static String PUBLISHER_ID = "pubid"; // Platform publisher ID.
    public final static String SITE_ID = "sitid"; // Platform side ID (a unique identifier for a specific site, e.g. youtube.com, or "angry birds")
    public final static String SITE_TYPE = "sitt"; // 1 = "Website, 2 = "App, Etc.
    public final static String ADVERTISER_ID = "advid"; // Platform Advertiser ID
    public final static String PLACEMENT_ID = "plid"; // Platform Placement ID
    public final static String CREATIVE_ID = "cid"; // Platform Creative ID
    public final static String CAMPAIGN_ID = "cpid"; // Platform Campaign ID
    public final static String LINEITEM_ID = "liid"; // Platform Line Item ID
    public final static String INSERTION_ORDER_ID = "ioid"; // Platform Insertion Order ID
    public final static String ESTIMATED_CLEAR_PRICE = "ecp"; // The Estimated Clear Price for this impression.
    public final static String FLOOR_PRICE = "flp"; // Reserved price for this impression, below this price the impression will not be sold.
    public final static String FLOOR_TYPE = "flt"; // 0 = "Unknown, 1 = "No Floor, 2 = "Hard Floor, 3 = "Soft Floor
    public final static String BID_PRICE = "bid"; // Bid Price.
    public final static String BID_SPEND = "bidclr"; // The actual spend for an impression, excluding fees.
    public final static String DATA_FEES = "dfees"; // Data Provider fees.
    public final static String ADDITIONAL_FEES = "afees"; // Any additional fees.
    public final static String CURRENCY = "curr"; // Currency code (e.g. USD, EUR, etc.).
    public final static String AUCTION_RESULT = "ares"; // The result of an impresion. E.g. 1 = "Sold, 2 = "Unsold. Etc.
    public final static String CREATIVE_SIZE = "csiz"; // [width]x[height] (e.g. 300x250).
    public final static String EXTERNAL_USER_ID = "euid"; // User ID of the external platform.
    public final static String EXTERNAL_DATA = "data"; // JSON formatted key-values.
    public final static String SECONDARY_USER_ID = "secid"; // 3rd party user ID provider.
    public final static String SECONDARY_USER_ID_CONFIDENCE = "seccon"; // Number between 0 and 1 in confidence.
    public final static String DEVICE_ID_GOOGLE_ADID = "devid_g"; // Google Ad ID
    public final static String DEVICE_ID_APPLE_IDA = "devid_a"; // Apple Advertising identifier
    public final static String DEVICE_ID_ODIN = "devid_o"; // Odin identifier (sha1)
    public final static String DEVICE_ID_OPENUDID = "devid_u"; // Open UDID identifier
    public final static String DEVICE_ID_SHA1 = "idsha1"; // SHA-1 hash representing device
    public final static String DEVICE_ID_MD5 = "idmd5"; // MD5 hash representing device
    public final static String EXTERNAL_PIXEL_ID = "epx"; // External platform pixel ID
    public final static String INTERACTION_USER_DEFINED_SUBTYPE = "itst"; // Subtype of "interaction_type" which can be defined by the user
}
