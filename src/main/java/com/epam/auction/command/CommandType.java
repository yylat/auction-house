package com.epam.auction.command;

import com.epam.auction.command.admin.ApproveItemCommand;
import com.epam.auction.command.admin.DiscardItemCommand;
import com.epam.auction.command.admin.LoadItemsForCheckCommand;
import com.epam.auction.command.admin.LoadUsersCommand;
import com.epam.auction.command.admin.SearchUsersCommand;
import com.epam.auction.command.admin.UpdateUserStatusCommand;
import com.epam.auction.command.bid.MakeBidCommand;
import com.epam.auction.command.item.CancelAuctionCommand;
import com.epam.auction.command.item.ConfirmDeliveryCommand;
import com.epam.auction.command.item.CreateItemCommand;
import com.epam.auction.command.item.DeleteItemCommand;
import com.epam.auction.command.item.LoadActiveItemsCommand;
import com.epam.auction.command.item.LoadComingItemsCommand;
import com.epam.auction.command.item.LoadItemCommand;
import com.epam.auction.command.item.LoadPurchasedItemsCommand;
import com.epam.auction.command.item.LoadUserItemsCommand;
import com.epam.auction.command.item.ReportViolationCommand;
import com.epam.auction.command.item.SearchItemsCommand;
import com.epam.auction.command.item.UpdateItemCommand;
import com.epam.auction.command.notification.LoadNotificationsCommand;
import com.epam.auction.command.bid.LoadBidsCommand;
import com.epam.auction.command.common.ChangeLocaleCommand;
import com.epam.auction.command.photo.AddPhotosCommand;
import com.epam.auction.command.photo.DeletePhotosCommand;
import com.epam.auction.command.photo.LoadAllPhotosCommand;
import com.epam.auction.command.photo.LoadPhotoCommand;
import com.epam.auction.command.photo.LoadPhotosForDeleteCommand;
import com.epam.auction.command.user.ChangeEmailCommand;
import com.epam.auction.command.user.ChangePasswordCommand;
import com.epam.auction.command.user.ChangeUsernameCommand;
import com.epam.auction.command.user.LogOutCommand;
import com.epam.auction.command.user.ShowProfileCommand;
import com.epam.auction.command.user.SignInCommand;
import com.epam.auction.command.user.SignUpCommand;
import com.epam.auction.command.user.UpdateProfileCommand;
import com.epam.auction.controller.RequestContent;
import com.epam.auction.exception.ReceiverException;
import com.epam.auction.receiver.AdminReceiver;
import com.epam.auction.receiver.BidReceiver;
import com.epam.auction.receiver.CommonReceiver;
import com.epam.auction.receiver.ItemCategoryReceiver;
import com.epam.auction.receiver.ItemReceiver;
import com.epam.auction.receiver.NotificationReceiver;
import com.epam.auction.receiver.PhotoReceiver;
import com.epam.auction.receiver.UserReceiver;
import com.epam.auction.receiver.impl.ReceiverFactory;

import java.util.Arrays;

/**
 * Stores command names, their command objects and
 * receiver method to execute command.
 */
public enum CommandType {

    SIGN_UP(new SignUpCommand(ReceiverFactory.getInstance().getUserReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((UserReceiver) getCommand().getReceiver()).signUp(requestContent);
        }
    },
    SIGN_IN(new SignInCommand(ReceiverFactory.getInstance().getUserReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((UserReceiver) getCommand().getReceiver()).signIn(requestContent);
        }
    },
    CHANGE_LOCALE(new ChangeLocaleCommand(ReceiverFactory.getInstance().getCommonReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((CommonReceiver) getCommand().getReceiver()).changeLocale(requestContent);
        }
    },
    LOG_OUT(new LogOutCommand(ReceiverFactory.getInstance().getUserReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) {
            ((UserReceiver) getCommand().getReceiver()).logOut(requestContent);
        }
    },
    LOAD_USERS(new LoadUsersCommand(ReceiverFactory.getInstance().getUserReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((UserReceiver) getCommand().getReceiver()).loadUsers(requestContent);
        }
    },
    LOAD_USER_ITEMS(new LoadUserItemsCommand(ReceiverFactory.getInstance().getItemReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).loadUserItems(requestContent);
        }
    },
    LOAD_CATEGORIES(new LoadUserItemsCommand(ReceiverFactory.getInstance().getItemCategoryReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemCategoryReceiver) getCommand().getReceiver()).loadCategories(requestContent);
        }
    },
    CREATE_ITEM(new CreateItemCommand(ReceiverFactory.getInstance().getItemReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).createItem(requestContent);
        }
    },
    LOAD_PHOTO(new LoadPhotoCommand(ReceiverFactory.getInstance().getPhotoReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((PhotoReceiver) getCommand().getReceiver()).loadPhoto(requestContent);
        }
    },
    APPROVE_ITEM(new ApproveItemCommand(ReceiverFactory.getInstance().getItemReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).approveItem(requestContent);
        }
    },
    LOAD_ITEMS_FOR_CHECK(new LoadItemsForCheckCommand(ReceiverFactory.getInstance().getItemReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).loadItemsForCheck(requestContent);
        }
    },
    LOAD_ITEM(new LoadItemCommand(ReceiverFactory.getInstance().getItemReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).loadItem(requestContent);
        }
    },
    LOAD_ALL_PHOTOS(new LoadAllPhotosCommand(ReceiverFactory.getInstance().getPhotoReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((PhotoReceiver) getCommand().getReceiver()).loadAllPhotos(requestContent);
        }
    },
    LOAD_BIDS(new LoadBidsCommand(ReceiverFactory.getInstance().getBidReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((BidReceiver) getCommand().getReceiver()).loadBids(requestContent);
        }
    },
    LOAD_ACTIVE_ITEMS(new LoadActiveItemsCommand(ReceiverFactory.getInstance().getItemReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).loadActiveItems(requestContent);
        }
    },
    DISCARD_ITEM(new DiscardItemCommand(ReceiverFactory.getInstance().getItemReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).discardItem(requestContent);
        }
    },
    LOAD_NOTIFICATIONS(new LoadNotificationsCommand(ReceiverFactory.getInstance().getNotificationReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((NotificationReceiver) getCommand().getReceiver()).loadNotifications(requestContent);
        }
    },
    MAKE_BID(new MakeBidCommand(ReceiverFactory.getInstance().getBidReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((BidReceiver) getCommand().getReceiver()).makeBid(requestContent);
        }
    },
    LOAD_COMING_ITEMS(new LoadComingItemsCommand(ReceiverFactory.getInstance().getItemReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).loadComingItems(requestContent);
        }
    },
    CHANGE_USERNAME(new ChangeUsernameCommand(ReceiverFactory.getInstance().getUserReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((UserReceiver) getCommand().getReceiver()).changeUsername(requestContent);
        }
    },
    CHANGE_EMAIL(new ChangeEmailCommand(ReceiverFactory.getInstance().getUserReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((UserReceiver) getCommand().getReceiver()).changeEmail(requestContent);
        }
    },
    CHANGE_PASSWORD(new ChangePasswordCommand(ReceiverFactory.getInstance().getUserReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((UserReceiver) getCommand().getReceiver()).changePassword(requestContent);
        }
    },
    UPDATE_PROFILE(new UpdateProfileCommand(ReceiverFactory.getInstance().getUserReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((UserReceiver) getCommand().getReceiver()).updateProfile(requestContent);
        }
    },
    LOAD_PURCHASED_ITEMS(new LoadPurchasedItemsCommand(ReceiverFactory.getInstance().getItemReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).loadPurchasedItems(requestContent);
        }
    },
    UPDATE_ITEM(new UpdateItemCommand(ReceiverFactory.getInstance().getItemReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).updateItem(requestContent);
        }
    },
    LOAD_PHOTOS_FOR_DELETE(new LoadPhotosForDeleteCommand(ReceiverFactory.getInstance().getPhotoReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((PhotoReceiver) getCommand().getReceiver()).loadPhotosForDelete(requestContent);
        }
    },
    DELETE_PHOTOS(new DeletePhotosCommand(ReceiverFactory.getInstance().getPhotoReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((PhotoReceiver) getCommand().getReceiver()).deletePhotos(requestContent);
        }
    },
    ADD_PHOTOS(new AddPhotosCommand(ReceiverFactory.getInstance().getItemReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).addPhotos(requestContent);
        }
    },
    DELETE_ITEM(new DeleteItemCommand(ReceiverFactory.getInstance().getItemReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).deleteItem(requestContent);
        }
    },
    CANCEL_AUCTION(new CancelAuctionCommand(ReceiverFactory.getInstance().getItemReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).cancelAuction(requestContent);
        }
    },
    UPDATE_USER_STATUS(new UpdateUserStatusCommand(ReceiverFactory.getInstance().getAdminReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((AdminReceiver) getCommand().getReceiver()).updateUserStatus(requestContent);
        }
    },
    SEARCH_USERS(new SearchUsersCommand(ReceiverFactory.getInstance().getUserReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((UserReceiver) getCommand().getReceiver()).findUsersByUsername(requestContent);
        }
    },
    SEARCH_ITEMS(new SearchItemsCommand(ReceiverFactory.getInstance().getItemReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).searchItems(requestContent);
        }
    },
    SHOW_PROFILE(new ShowProfileCommand(ReceiverFactory.getInstance().getUserReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((UserReceiver) getCommand().getReceiver()).showProfile(requestContent);
        }
    },
    CONFIRM_DELIVERY(new ConfirmDeliveryCommand(ReceiverFactory.getInstance().getItemReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).confirmDelivery(requestContent);
        }
    },
    REPORT_VIOLATION(new ReportViolationCommand(ReceiverFactory.getInstance().getItemReceiver())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((ItemReceiver) getCommand().getReceiver()).reportViolation(requestContent);
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