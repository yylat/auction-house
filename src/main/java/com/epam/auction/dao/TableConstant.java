package com.epam.auction.dao;

public final class TableConstant {

    private TableConstant() {
    }

    // User table

    public static final String USER_COLUMN_ID = "user_id";
    public static final String USER_COLUMN_USERNAME = "username";
    public static final String USER_COLUMN_PASSWORD = "password";
    public static final String USER_COLUMN_LAST_NAME = "last_name";
    public static final String USER_COLUMN_MIDDLE_NAME = "middle_name";
    public static final String USER_COLUMN_FIRST_NAME = "first_name";
    public static final String USER_COLUMN_PHONE_NUMBER = "phone_number";
    public static final String USER_COLUMN_EMAIL = "email";
    public static final String USER_COLUMN_BALANCE = "balance";
    public static final String USER_COLUMN_IS_BANNED = "is_banned";
    public static final String USER_COLUMN_USER_ROLE_ID = "user_role_id";

    public static final String USER_QUERY_FIND_ALL =
            "SELECT `user_id`, `username`, `password`, `last_name`, `middle_name`, `first_name`, `phone_number`, `email`, `balance`, `is_banned`, `user_role_id` FROM `user`";
    public static final String USER_QUERY_FIND_BY_ID =
            "SELECT `user_id`, `username`, `password`, `last_name`, `middle_name`, `first_name`, `phone_number`, `email`, `balance`, `is_banned`, `user_role_id` FROM `user` WHERE `user_id` = ?";
    public static final String USER_QUERY_CREATE =
            "INSERT INTO `user`(`username`, `password`, `last_name`, `middle_name`, `first_name`, `phone_number`, `email`, `balance`, `is_banned`, `user_role_id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String USER_QUERY_UPDATE =
            "UPDATE `user` SET `username` = ?, `password` = ?, `last_name` = ?, `middle_name` = ?, `first_name` = ?, `phone_number` = ?, `email` = ?, `balance` = ?, `is_banned` = ?, `user_role_id` = ? WHERE `user_id` = ?";

    public static final String USER_QUERY_IS_EXIST =
            "SELECT `user_id`, `last_name`, `middle_name`, `first_name`, `phone_number`, `email`, `balance`, `is_banned`, `user_role_id` FROM `user` WHERE `username` = ? AND `password` = ?";

    public static final String USER_QUERY_IS_EXIST_USERNAME =
            "SELECT EXISTS(SELECT * FROM `user` WHERE `username` = ?)";
    public static final String USER_QUERY_IS_EXIST_EMAIL =
            "SELECT EXISTS(SELECT * FROM `user` WHERE `email` = ?)";

    public static final String USER_QUERY_FIND_ROWS_COUNT =
            "SELECT COUNT(*) FROM `user` WHERE `user_id` != ?";
    public static final String USER_QUERY_FIND_USERS_LIMIT =
            USER_QUERY_FIND_ALL + " WHERE `user_id` != ? ORDER BY `user_id` DESC LIMIT ?, ?";

    public static final String USER_QUERY_UPDATE_STATUS =
            "UPDATE `user` SET `is_banned` = ? WHERE `user_id` = ?";

    //    Bid table

    public static final String BID_COLUMN_ID = "bid_id";
    public static final String BID_COLUMN_ITEM_ID = "item_id";
    public static final String BID_COLUMN_BIDDER_ID = "bidder_id";
    public static final String BID_COLUMN_BID_VALUE = "bid_value";
    public static final String BID_COLUMN_IS_WINNING = "is_winning";

    public static final String BID_QUERY_FIND_ALL =
            "SELECT `bid_id`, `item_id`, `bidder_id`, `bid_value`, `is_winning` FROM `bid`";
    public static final String BID_QUERY_FIND_BY_ID =
            "SELECT `bid_id`, `item_id`, `bidder_id`, `bid_value`, `is_winning` FROM `bid` WHERE `bid_id` = ?";
    public static final String BID_QUERY_CREATE =
            "{CALL insert_bid (?, ?, ?)}";

    public static final String BID_QUERY_FIND_FOR_USER_LIMIT =
            BID_QUERY_FIND_ALL + " WHERE `bidder_id` = ? ORDER BY `bid_id` DESC LIMIT ?, ?";
    public static final String BID_QUERY_FIND_NUMBER_FOR_USER =
            "SELECT COUNT(*) FROM `bid` WHERE `bidder_id` = ?";

    public static final String BID_QUERY_FIND_WINNING =
            BID_QUERY_FIND_ALL + " WHERE `item_id` = ? AND `is_winning` = 1";


    //    ItemCategory table

    public static final String ITEM_CATEGORY_COLUMN_ID = "item_category_id";
    public static final String ITEM_CATEGORY_COLUMN_DESCRIPTION = "category_description";
    public static final String ITEM_CATEGORY_COLUMN_PARENT_CATEGORY_ID = "parent_item_category_id";

    public static final String ITEM_CATEGORY_QUERY_FIND_ALL =
            "SELECT `item_category_id`, `category_description`, `parent_item_category_id` FROM `item_category`";
    public static final String ITEM_CATEGORY_QUERY_FIND_BY_ID =
            "SELECT `item_category_id`, `category_description`, `parent_item_category_id` FROM `item_category` WHERE `item_category_id` = ?";
    public static final String ITEM_CATEGORY_QUERY_DELETE =
            "DELETE FROM `item_category` WHERE `item_category_id` = ?";
    public static final String ITEM_CATEGORY_QUERY_CREATE =
            "INSERT INTO `item_category`(`category_description`, `parent_item_category_id`) VALUES (?, ?)";
    public static final String ITEM_CATEGORY_QUERY_UPDATE =
            "UPDATE `item_category` SET `category_description` = ?, `parent_item_category_id` = ? WHERE `item_category_id` = ?";


    //    Item table

    public static final String ITEM_COLUMN_ID = "item_id";
    public static final String ITEM_COLUMN_NAME = "name";
    public static final String ITEM_COLUMN_DESCRIPTION = "description";
    public static final String ITEM_COLUMN_START_PRICE = "start_price";
    public static final String ITEM_COLUMN_BLITZ_PRICE = "blitz_price";
    public static final String ITEM_COLUMN_ACTUAL_PRICE = "actual_price";
    public static final String ITEM_COLUMN_START_DATE = "start_date";
    public static final String ITEM_COLUMN_CLOSE_DATE = "close_date";
    public static final String ITEM_COLUMN_STATUS_ID = "item_status_id";
    public static final String ITEM_COLUMN_CATEGORY_ID = "item_category_id";
    public static final String ITEM_COLUMN_SELLER_ID = "seller_id";

    public static final String ITEM_QUERY_FIND_ALL =
            "SELECT `item_id`, `name`, `description`, `start_price`, `blitz_price`, `actual_price`, `start_date`, `close_date`, `item_status_id`, `item_category_id`, `seller_id` FROM `item`";
    public static final String ITEM_QUERY_FIND_BY_ID =
            "SELECT `item_id`, `name`, `description`, `start_price`, `blitz_price`, `actual_price`, `start_date`, `close_date`, `item_status_id`, `item_category_id`, `seller_id` FROM `item` WHERE `item_id` = ?";
    public static final String ITEM_QUERY_CREATE =
            "INSERT INTO `item`(`name`, `description`, `start_price`, `blitz_price`, `actual_price`, `start_date`, `close_date`, `item_status_id`, `item_category_id`, `seller_id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String ITEM_QUERY_UPDATE =
            "UPDATE `item` SET `name` = ?, `description` = ?, `start_price` = ?, `blitz_price` = ?, `actual_price` = ?, `start_date` = ?, `close_date` = ?, `item_status_id` = ?, `item_category_id` = ?, `seller_id` = ? WHERE `item_id` = ?";

    public static final String ITEM_QUERY_UPDATE_STATUS =
            "UPDATE `item` SET `item_status_id` = ? WHERE `item_id` = ?";
    ;
    public static final String ITEM_QUERY_FIND_ROWS_COUNT =
            "SELECT COUNT(*) FROM `item`";

    public static final String ITEM_QUERY_FIND_NUMBER_FOR_USER =
            "SELECT COUNT(*) FROM `item` WHERE `seller_id` = ?";

    public static final String ITEM_QUERY_LIMIT = " LIMIT ?, ?";

    private static final String ITEM_QUERY_PURCHASED_JOIN =
            " INNER JOIN bid ON item.item_id = bid.item_id WHERE bid.bidder_id = ? AND item.item_status_id = 3 AND bid.is_winning = 1";
    public static final String ITEM_QUERY_PURCHASED_ROWS_COUNT =
            ITEM_QUERY_FIND_ROWS_COUNT + ITEM_QUERY_PURCHASED_JOIN;
    public static final String ITEM_QUERY_PURCHASED =
            "SELECT `item`.`item_id`, `item`.`name`, `item`.`description`, `item`.`start_price`, `item`.`blitz_price`, `item`.`actual_price`, `item`.`start_date`, `item`.`close_date`, `item`.`item_status_id`, `item`.`item_category_id`, `item`.`seller_id` FROM `item`" +
                    ITEM_QUERY_PURCHASED_JOIN;

    //    Notification table

    public static final String NOTIFICATION_COLUMN_ID = "notification_id";
    public static final String NOTIFICATION_COLUMN_TYPE = "notification_type_id";
    public static final String NOTIFICATION_COLUMN_USER_ID = "user_id";
    public static final String NOTIFICATION_COLUMN_ITEM_ID = "item_id";
    public static final String NOTIFICATION_COLUMN_DATE_TIME = "date_time";

    public static final String NOTIFICATION_QUERY_FIND_ALL =
            "SELECT `notification_id`, `notification_type_id`, `user_id`, `item_id`, `date_time` FROM `notification`";
    public static final String NOTIFICATION_QUERY_FIND_BY_ID =
            "SELECT `notification_id`, `notification_type_id`, `user_id`, `item_id`, `date_time` FROM `notification` WHERE `notification_id` = ?";
    public static final String NOTIFICATION_QUERY_DELETE =
            "DELETE FROM `notification` WHERE `notification_id` = ?";
    public static final String NOTIFICATION_QUERY_CREATE =
            "INSERT INTO `notification`(`notification_type_id`, `user_id`, `item_id`, `date_time`) VALUES (?, ?, ?, ?)";
    public static final String NOTIFICATION_QUERY_UPDATE =
            "UPDATE `notification` SET `notification_type_id` = ?, `user_id` = ?, `item_id` = ?, `date_time` = ? WHERE `notification_id` = ?";

    public static final String NOTIFICATION_QUERY_FOR_USER =
            NOTIFICATION_QUERY_FIND_ALL + " WHERE `user_id` = ? ORDER BY `notification_id` DESC LIMIT ?";
    public static final String NOTIFICATION_QUERY_FIND_NEXT_FOR_USER =
            NOTIFICATION_QUERY_FIND_ALL + " WHERE `user_id` = ? AND `notification_id` < ? ORDER BY `notification_id` DESC LIMIT ?";
    public static final String NOTIFICATION_QUERY_FIND_PREV_FOR_USER =
            NOTIFICATION_QUERY_FIND_ALL + " WHERE `user_id` = ? AND `notification_id` > ? ORDER BY `notification_id` DESC LIMIT ?";

    public static final String NOTIFICATION_QUERY_FIND_FOR_USER_LIMIT =
            NOTIFICATION_QUERY_FIND_ALL + " WHERE `user_id` = ? ORDER BY `notification_id` DESC LIMIT ?, ?";
    public static final String NOTIFICATION_QUERY_FIND_NUMBER_FOR_USER =
            "SELECT COUNT(*) FROM `notification` WHERE `user_id` = ?";

    //    Photo table

    public static final String PHOTO_COLUMN_ID = "photo_id";
    public static final String PHOTO_COLUMN_PHOTO_FILE = "photo_file";
    public static final String PHOTO_COLUMN_ITEM_ID = "item_id";

    public static final String PHOTO_QUERY_FIND_ALL =
            "SELECT `photo_id`, `photo_file`, `item_id` FROM `photo`";
    public static final String PHOTO_QUERY_FIND_BY_ID =
            "SELECT `photo_id`, `photo_file`, `item_id` FROM `photo` WHERE `photo_id` = ?";
    public static final String PHOTO_QUERY_DELETE =
            "DELETE FROM `photo` WHERE `photo_id` = ?";
    public static final String PHOTO_QUERY_CREATE =
            "INSERT INTO `photo`(`photo_file`, `item_id`) VALUES (?, ?)";
    public static final String PHOTO_QUERY_UPDATE =
            "UPDATE `photo` SET `photo_file` = ?, `item_id` = ? WHERE `photo_id` = ?";

    public static final String PHOTO_QUERY_FIND_ITEM_PHOTO =
            "SELECT `photo_id`, `photo_file`, `item_id` FROM `photo` WHERE `item_id` = ? LIMIT 1";
    public static final String PHOTO_QUERY_FIND_ALL_FOR_ITEM =
            "SELECT `photo_id`, `photo_file`, `item_id` FROM `photo` WHERE `item_id` = ?";

    public static final String PHOTO_QUERY_DELETE_ITEM_PHOTOS =
            "DELETE FROM `photo` WHERE `item_id` = ?";


}