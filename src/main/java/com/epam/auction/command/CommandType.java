package com.epam.auction.command;

import com.epam.auction.command.admin.*;
import com.epam.auction.command.bid.MakeBidCommand;
import com.epam.auction.command.item.*;
import com.epam.auction.command.notification.LoadNotificationsCommand;
import com.epam.auction.command.photo.*;
import com.epam.auction.command.bid.LoadBidsCommand;
import com.epam.auction.command.common.ChangeLocaleCommand;
import com.epam.auction.command.user.*;
import com.epam.auction.controller.RequestContent;
import com.epam.auction.exception.ReceiverException;
import com.epam.auction.receiver.*;
import com.epam.auction.receiver.impl.*;

import java.util.Arrays;

/**
 * Stores command names, their command objects and
 * receiver method to execute command.
 */
public enum CommandType {

    SIGN_UP(new SignUpCommand(new UserReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((UserReceiver) getCommand().getReceiver()).signUp(requestContent);
        }
    },
    SIGN_IN(new SignInCommand(new UserReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((UserReceiver) getCommand().getReceiver()).signIn(requestContent);
        }
    },
    CHANGE_LOCALE(new ChangeLocaleCommand(new CommonReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((CommonReceiver) getCommand().getReceiver()).changeLocale(requestContent);
        }
    },
    LOG_OUT(new LogOutCommand(new UserReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) {
            ((UserReceiver) getCommand().getReceiver()).logOut(requestContent);
        }
    },
    LOAD_USERS(new LoadUsersCommand(new UserReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((UserReceiver) getCommand().getReceiver()).loadUsers(requestContent);
        }
    },
    LOAD_USER_ITEMS(new LoadUserItemsCommand(new ItemReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).loadUserItems(requestContent);
        }
    },
    LOAD_CATEGORIES(new LoadUserItemsCommand(new ItemReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).loadCategories(requestContent);
        }
    },
    CREATE_ITEM(new CreateItemCommand(new ItemReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).createItem(requestContent);
        }
    },
    LOAD_PHOTO(new LoadPhotoCommand(new PhotoReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((PhotoReceiver) getCommand().getReceiver()).loadPhoto(requestContent);
        }
    },
    APPROVE_ITEM(new ApproveItemCommand(new ItemReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).approveItem(requestContent);
        }
    },
    LOAD_ITEMS_FOR_CHECK(new LoadItemsForCheckCommand(new ItemReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).loadItemsForCheck(requestContent);
        }
    },
    LOAD_ITEM(new LoadItemCommand(new ItemReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).loadItem(requestContent);
        }
    },
    LOAD_ALL_PHOTOS(new LoadAllPhotosCommand(new PhotoReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((PhotoReceiver) getCommand().getReceiver()).loadAllPhotos(requestContent);
        }
    },
    LOAD_BIDS(new LoadBidsCommand(new BidReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((BidReceiver) getCommand().getReceiver()).loadBids(requestContent);
        }
    },
    LOAD_ACTIVE_ITEMS(new LoadActiveItemsCommand(new ItemReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).loadActiveItems(requestContent);
        }
    },
    DISCARD_ITEM(new DiscardItemCommand(new ItemReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).discardItem(requestContent);
        }
    },
    LOAD_NOTIFICATIONS(new LoadNotificationsCommand(new NotificationReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((NotificationReceiver) getCommand().getReceiver()).loadNotifications(requestContent);
        }
    },
    MAKE_BID(new MakeBidCommand(new BidReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((BidReceiver) getCommand().getReceiver()).makeBid(requestContent);
        }
    },
    REPLENISH_BALANCE(new ReplenishBalanceCommand(new UserReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((UserReceiver) getCommand().getReceiver()).replenishBalance(requestContent);
        }
    },
    LOAD_COMING_ITEMS(new LoadComingItemsCommand(new ItemReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).loadComingItems(requestContent);
        }
    },
    CHANGE_USERNAME(new ChangeUsernameCommand(new UserReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((UserReceiver) getCommand().getReceiver()).changeUsername(requestContent);
        }
    },
    CHANGE_EMAIL(new ChangeEmailCommand(new UserReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((UserReceiver) getCommand().getReceiver()).changeEmail(requestContent);
        }
    },
    CHANGE_PASSWORD(new ChangePasswordCommand(new UserReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((UserReceiver) getCommand().getReceiver()).changePassword(requestContent);
        }
    },
    UPDATE_PROFILE(new UpdateProfileCommand(new UserReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((UserReceiver) getCommand().getReceiver()).updateProfile(requestContent);
        }
    },
    LOAD_PURCHASED_ITEMS(new LoadPurchasedItemsCommand(new ItemReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).loadPurchasedItems(requestContent);
        }
    },
    UPDATE_ITEM(new UpdateItemCommand(new ItemReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).updateItem(requestContent);
        }
    },
    LOAD_PHOTOS_FOR_DELETE(new LoadPhotosForDeleteCommand(new PhotoReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((PhotoReceiver) getCommand().getReceiver()).loadPhotosForDelete(requestContent);
        }
    },
    DELETE_PHOTOS(new DeletePhotosCommand(new PhotoReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((PhotoReceiver) getCommand().getReceiver()).deletePhotos(requestContent);
        }
    },
    ADD_PHOTOS(new AddPhotosCommand(new ItemReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).addPhotos(requestContent);
        }
    },
    DELETE_ITEM(new DeleteItemCommand(new ItemReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).deleteItem(requestContent);
        }
    },
    CANCEL_AUCTION(new CancelAuctionCommand(new ItemReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).cancelAuction(requestContent);
        }
    },
    UPDATE_USER_STATUS(new UpdateUserStatusCommand(new AdminReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((AdminReceiver) getCommand().getReceiver()).updateUserStatus(requestContent);
        }
    },
    SEARCH_USERS(new SearchUsersCommand(new UserReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((UserReceiver) getCommand().getReceiver()).findUsersByUsername(requestContent);
        }
    },
    SEARCH_ITEMS(new SearchItemsCommand(new ItemReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).searchItems(requestContent);
        }
    };

    /**
     * Command.
     */
    private AbstractCommand command;

    /**
     * Constructs CommandType with command.
     *
     * @param command command
     */
    CommandType(AbstractCommand command) {
        this.command = command;
    }

    /**
     * Returns command.
     *
     * @return command
     */
    public AbstractCommand getCommand() {
        return command;
    }

    /**
     * Executes receiver method according to the command.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not do action
     */
    public abstract void doReceiver(RequestContent requestContent) throws ReceiverException;

    /**
     * Returns CommandType object with command.
     *
     * @param command command
     * @return CommandType object with command
     */
    public static CommandType takeCommandType(AbstractCommand command) {
        return Arrays.stream(CommandType.values())
                .filter(type -> type.getCommand().equals(command))
                .findFirst().orElse(LOAD_ACTIVE_ITEMS);
    }

}