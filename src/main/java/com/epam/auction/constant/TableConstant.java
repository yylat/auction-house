package com.epam.auction.constant;

public final class TableConstant {

    private TableConstant() {
    }

    public static final class User {
        public final static String COLUMN_ID = "user_id";
        public final static String COLUMN_USERNAME = "username";
        public final static String COLUMN_PASSWORD = "password";
        public final static String COLUMN_LAST_NAME = "last_name";
        public final static String COLUMN_MIDDLE_NAME = "middle_name";
        public final static String COLUMN_FIRST_NAME = "first_name";
        public final static String COLUMN_PHONE_NUMBER = "phone_number";
        public final static String COLUMN_EMAIL = "email";
        public final static String COLUMN_BALANCE = "balance";
        public final static String COLUMN_IS_DELETED = "is_deleted";
        public final static String COLUMN_USER_ROLE_ID = "user_role_id";

        public final static String QUERY_FIND_ALL =
                "SELECT `user_id`, `username`, `password`, `last_name`, `middle_name`, `first_name`, `phone_number`, `email`, `balance`, `is_deleted`, `user_role_id` FROM `user`";
        public final static String QUERY_FIND_BY_ID =
                "SELECT `user_id`, `username`, `password`, `last_name`, `middle_name`, `first_name`, `phone_number`, `email`, `balance`, `is_deleted`, `user_role_id` FROM `user` WHERE `user_id` = ?";
        public final static String QUERY_DELETE =
                "UPDATE `user` SET `is_deleted` = 1 WHERE `user_id` = ?";
        public final static String QUERY_CREATE =
                "INSERT INTO `user`(`username`, `password`, `last_name`, `middle_name`, `first_name`, `phone_number`, `email`, `balance`, `is_deleted`, `user_role_id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        public final static String QUERY_UPDATE =
                "UPDATE `user` SET `username` = ?, `password` = ?, `last_name` = ?, `middle_name` = ?, `first_name` = ?, `phone_number` = ?, `email` = ?, `balance` = ?, `is_deleted` = ?, `user_role_id` = ? WHERE `user_id` = ?";

        public final static String QUERY_IS_EXIST =
                "SELECT `user_id`, `last_name`, `middle_name`, `first_name`, `phone_number`, `email`, `balance`, `is_deleted`, `user_role_id` FROM `user` WHERE `username` = ? AND `password` = ?";

        public final static String QUERY_IS_EXIST_USERNAME =
                "SELECT EXISTS(SELECT * FROM `user` WHERE `username` = ?)";
        public final static String QUERY_IS_EXIST_EMAIL =
                "SELECT EXISTS(SELECT * FROM `user` WHERE `email` = ?)";
    }

    public static final class Bid {
        public final static String COLUMN_ID = "user_id";
        public final static String COLUMN_ITEM_ID = "item_id";
        public final static String COLUMN_BIDDER_ID = "bidder_id";
        public final static String COLUMN_BID_VALUE = "bid_value_id";
        public final static String COLUMN_IS_WINNING = "bid_status_id";

        public final static String QUERY_FIND_ALL =
                "SELECT `bid_id`, `item_id`, `bidder_id`, `bid_value`, `is_winning` FROM `bid`";
        public final static String QUERY_FIND_BY_ID =
                "SELECT `bid_id`, `item_id`, `bidder_id`, `bid_value`, `is_winning` FROM `bid` WHERE `bid_id` = ?";
        public final static String QUERY_CREATE =
                "{? = CALL insert_bid (?, ?, ?)}";

        public final static String QUERY_FIND_ALL_FOR_USER =
                "SELECT `bid_id`, `item_id`, `bidder_id`, `bid_value`, `is_winning` FROM `bid` WHERE `bidder_id` = ? ORDER BY `bid_id` DESC";
    }

    public static final class ItemCategory {
        public final static String COLUMN_ID = "item_category_id";
        public final static String COLUMN_DESCRIPTION = "category_description";
        public final static String COLUMN_PARENT_CATEGORY_ID = "parent_item_category_id";

        public final static String QUERY_FIND_ALL =
                "SELECT `item_category_id`, `category_description`, `parent_item_category_id` FROM `item_category`";
        public final static String QUERY_FIND_BY_ID =
                "SELECT `item_category_id`, `category_description`, `parent_item_category_id` FROM `item_category` WHERE `item_category_id` = ?";
        public final static String QUERY_DELETE =
                "DELETE FROM `item_category` WHERE `item_category_id` = ?";
        public final static String QUERY_CREATE =
                "INSERT INTO `item_category`(`category_description`, `parent_item_category_id`) VALUES (?, ?)";
        public final static String QUERY_UPDATE =
                "UPDATE `item_category` SET `category_description` = ?, `parent_item_category_id` = ? WHERE `item_category_id` = ?";
    }

    public static final class Item {
        public final static String COLUMN_ID = "item_id";
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_DESCRIPTION = "description";
        public final static String COLUMN_START_PRICE = "start_price";
        public final static String COLUMN_BLITZ_PRICE = "blitz_price";
        public final static String COLUMN_ACTUAL_PRICE = "actual_price";
        public final static String COLUMN_START_DATE = "start_date";
        public final static String COLUMN_CLOSE_DATE = "close_date";
        public final static String COLUMN_STATUS_ID = "item_status_id";
        public final static String COLUMN_CATEGORY_ID = "item_category_id";
        public final static String COLUMN_SELLER_ID = "seller_id";

        public final static String QUERY_FIND_ALL =
                "SELECT `item_id`, `name`, `description`, `start_price`, `blitz_price`, `actual_price`, `start_date`, `close_date`, `item_status_id`, `item_category_id`, `seller_id` FROM `item`";
        public final static String QUERY_FIND_BY_ID =
                "SELECT `item_id`, `name`, `description`, `start_price`, `blitz_price`, `actual_price`, `start_date`, `close_date`, `item_status_id`, `item_category_id`, `seller_id` FROM `item` WHERE `item_id` = ?";
        public final static String QUERY_CREATE =
                "INSERT INTO `item`(`name`, `description`, `start_price`, `blitz_price`, `actual_price`, `start_date`, `close_date`, `item_status_id`, `item_category_id`, `seller_id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        public final static String QUERY_UPDATE =
                "UPDATE `item` SET `name` = ?, `description` = ?, `start_price` = ?, `blitz_price` = ?, `actual_price` = ?, `start_date` = ?, `close_date` = ?, `item_status_id` = ?, `item_category_id` = ?, `seller_id` = ? WHERE `item_id` = ?";

        public final static String QUERY_FIND_ALL_FOR_USER =
                "SELECT `item_id`, `name`, `description`, `start_price`, `blitz_price`, `actual_price`, `start_date`, `close_date`, `item_status_id`, `item_category_id`, `seller_id` FROM `item` WHERE `seller_id` = ?";

        public final static String QUERY_FIND_CERTAIN_ITEMS =
                "SELECT `item_id`, `name`, `description`, `start_price`, `blitz_price`, `actual_price`, `start_date`, `close_date`, `item_status_id`, `item_category_id`, `seller_id` FROM `item` WHERE `item_status_id` IN(?)";
        public final static String QUERY_APPROVE_ITEM =
                "UPDATE `item` SET `item_status_id` = ? WHERE `item_id` = ?";
        public final static String QUERY_FIND_ITEMS_WITH_STATUS =
                "SELECT `item_id`, `name`, `description`, `start_price`, `blitz_price`, `actual_price`, `start_date`, `close_date`, `item_status_id`, `item_category_id`, `seller_id` FROM `item` WHERE `item_status_id` = ?";

    }

    public static final class Notification {
        public final static String COLUMN_ID = "notification_id";
        public final static String COLUMN_DESCRIPTION = "notification_description";
        public final static String COLUMN_USER_ID = "user_id";
        public final static String COLUMN_ITEM_ID = "item_id";

        public final static String QUERY_FIND_ALL =
                "SELECT `notification_id`, `notification_description`, `user_id`, `item_id` FROM `notification`";
        public final static String QUERY_FIND_BY_ID =
                "SELECT `notification_id`, `notification_description`, `user_id`, `item_id` FROM `notification` WHERE `notification_id` = ?";
        public final static String QUERY_DELETE =
                "DELETE FROM `notification` WHERE `notification_id` = ?";
        public final static String QUERY_CREATE =
                "INSERT INTO `notification`(`notification_description`, `user_id`, `item_id`) VALUES (?, ?, ?)";
        public final static String QUERY_UPDATE =
                "UPDATE `notification` SET `notification_description` = ?, `user_id` = ?, `item_id` = ? WHERE `notification_id` = ?";
    }

    public static final class Photo {
        public final static String COLUMN_ID = "photo_id";
        public final static String COLUMN_PHOTO_FILE = "photo_file";
        public final static String COLUMN_ITEM_ID = "item_id";

        public final static String QUERY_FIND_ALL =
                "SELECT `photo_id`, `photo_file`, `item_id` FROM `photo`";
        public final static String QUERY_FIND_BY_ID =
                "SELECT `photo_id`, `photo_file`, `item_id` FROM `photo` WHERE `photo_id` = ?";
        public final static String QUERY_DELETE =
                "DELETE FROM `photo` WHERE `photo_id` = ?";
        public final static String QUERY_CREATE =
                "INSERT INTO `photo`(`photo_file`, `item_id`) VALUES (?, ?)";
        public final static String QUERY_UPDATE =
                "UPDATE `photo` SET `photo_file` = ?, `item_id` = ? WHERE `photo_id` = ?";

        public final static String QUERY_FIND_ITEM_PHOTO =
                "SELECT `photo_id`, `photo_file`, `item_id` FROM `photo` WHERE `item_id` = ? LIMIT 1";
        public final static String QUERY_FIND_ALL_FOR_ITEM =
                "SELECT `photo_id`, `photo_file`, `item_id` FROM `photo` WHERE `item_id` = ?";
    }

}