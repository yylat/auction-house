package com.epam.auction.command;

import com.epam.auction.command.admin.ApproveItemCommand;
import com.epam.auction.command.admin.DiscardItemCommand;
import com.epam.auction.command.admin.LoadItemsForCheckCommand;
import com.epam.auction.command.bid.MakeBidCommand;
import com.epam.auction.command.item.*;
import com.epam.auction.command.notification.LoadNotificationsCommand;
import com.epam.auction.command.photo.*;
import com.epam.auction.command.admin.LoadUsersCommand;
import com.epam.auction.command.bid.LoadBidsCommand;
import com.epam.auction.command.common.ChangeLocaleCommand;
import com.epam.auction.command.user.*;
import com.epam.auction.exception.ReceiverException;
import com.epam.auction.receiver.*;
import com.epam.auction.receiver.impl.*;

import java.util.Arrays;

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
    LOAD_USERS(new LoadUsersCommand(new PaginationReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((PaginationReceiver) getCommand().getReceiver()).loadUsers(requestContent);
        }
    },
    LOAD_USER_ITEMS(new LoadUserItemsCommand(new PaginationReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((PaginationReceiver) getCommand().getReceiver()).loadUserItems(requestContent);
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
    APPROVE_ITEM(new ApproveItemCommand(new AdminReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((AdminReceiver) getCommand().getReceiver()).approveItem(requestContent);
        }
    },
    LOAD_ITEMS_FOR_CHECK(new LoadItemsForCheckCommand(new PaginationReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((PaginationReceiver) getCommand().getReceiver()).loadItemsForCheck(requestContent);
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
    LOAD_BIDS(new LoadBidsCommand(new PaginationReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((PaginationReceiver) getCommand().getReceiver()).loadBids(requestContent);
        }
    },
    LOAD_ACTIVE_ITEMS(new LoadActiveItemsCommand(new PaginationReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((PaginationReceiver) getCommand().getReceiver()).loadActiveItems(requestContent);
        }
    },
    DISCARD_ITEM(new DiscardItemCommand(new AdminReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((AdminReceiver) getCommand().getReceiver()).discardItem(requestContent);
        }
    },
    LOAD_NOTIFICATIONS(new LoadNotificationsCommand(new PaginationReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((PaginationReceiver) getCommand().getReceiver()).loadNotifications(requestContent);
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
    LOAD_COMING_ITEMS(new LoadComingItemsCommand(new PaginationReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((PaginationReceiver) getCommand().getReceiver()).loadComingItems(requestContent);
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
    LOAD_PURCHASED_ITEMS(new LoadPurchasedItemsCommand(new PaginationReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((PaginationReceiver) getCommand().getReceiver()).loadPurchasedItems(requestContent);
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
    };

    private AbstractCommand command;

    CommandType(AbstractCommand command) {
        this.command = command;
    }

    public AbstractCommand getCommand() {
        return command;
    }

    public abstract void doReceiver(RequestContent requestContent) throws ReceiverException;

    public static CommandType takeCommandType(AbstractCommand command) {
        return Arrays.stream(CommandType.values())
                .filter(type -> type.getCommand().equals(command))
                .findFirst().orElse(LOAD_ACTIVE_ITEMS);
    }

}