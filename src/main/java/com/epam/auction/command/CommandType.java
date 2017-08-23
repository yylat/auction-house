package com.epam.auction.command;

import com.epam.auction.command.admin.ApproveItemCommand;
import com.epam.auction.command.admin.DiscardItemCommand;
import com.epam.auction.command.admin.LoadItemsForCheckCommand;
import com.epam.auction.command.notification.LoadNotificationsCommand;
import com.epam.auction.command.photo.LoadAllPhotosCommand;
import com.epam.auction.command.photo.LoadPhotoCommand;
import com.epam.auction.command.admin.LoadUsersCommand;
import com.epam.auction.command.bid.LoadBidsCommand;
import com.epam.auction.command.common.ChangeLocaleCommand;
import com.epam.auction.command.item.CreateItemCommand;
import com.epam.auction.command.item.LoadActiveItemsCommand;
import com.epam.auction.command.item.LoadItemCommand;
import com.epam.auction.command.item.LoadUserItemsCommand;
import com.epam.auction.command.user.LogOutCommand;
import com.epam.auction.command.user.SignInCommand;
import com.epam.auction.command.user.SignUpCommand;
import com.epam.auction.exception.ReceiverLayerException;
import com.epam.auction.receiver.*;
import com.epam.auction.receiver.impl.*;

import java.util.Arrays;

public enum CommandType {

    SIGN_UP(new SignUpCommand(new UserReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            ((UserReceiver) getCommand().getReceiver()).signUp(requestContent);
        }
    },
    SIGN_IN(new SignInCommand(new UserReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            ((UserReceiver) getCommand().getReceiver()).signIn(requestContent);
        }
    },
    CHANGE_LOCALE(new ChangeLocaleCommand(new CommonReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            ((CommonReceiver) getCommand().getReceiver()).changeLocale(requestContent);
        }
    },
    LOG_OUT(new LogOutCommand(new UserReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) {
            ((UserReceiver) getCommand().getReceiver()).logOut(requestContent);
        }
    },
    LOAD_USERS(new LoadUsersCommand(new AdminReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            ((AdminReceiver) getCommand().getReceiver()).loadUsers(requestContent);
        }
    },
    LOAD_USER_ITEMS(new LoadUserItemsCommand(new ItemReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            ((ItemReceiver) getCommand().getReceiver()).loadUserItems(requestContent);
        }
    },
    LOAD_CATEGORIES(new LoadUserItemsCommand(new ItemReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            ((ItemReceiver) getCommand().getReceiver()).loadCategories(requestContent);
        }
    },
    CREATE_ITEM(new CreateItemCommand(new ItemReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            ((ItemReceiver) getCommand().getReceiver()).createItem(requestContent);
        }
    },
    LOAD_PHOTO(new LoadPhotoCommand(new PhotoReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            ((PhotoReceiver) getCommand().getReceiver()).loadPhoto(requestContent);
        }
    },
    APPROVE_ITEM(new ApproveItemCommand(new AdminReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            ((AdminReceiver) getCommand().getReceiver()).approveItem(requestContent);
        }
    },
    LOAD_ITEMS_FOR_CHECK(new LoadItemsForCheckCommand(new ItemReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            ((ItemReceiver) getCommand().getReceiver()).loadItemsForCheck(requestContent);
        }
    },
    LOAD_ITEM(new LoadItemCommand(new ItemReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            ((ItemReceiver) getCommand().getReceiver()).loadItem(requestContent);
        }
    },
    LOAD_ALL_PHOTOS(new LoadAllPhotosCommand(new PhotoReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            ((PhotoReceiver) getCommand().getReceiver()).loadAllPhotos(requestContent);
        }
    },
    LOAD_BIDS(new LoadBidsCommand(new BidReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            ((BidReceiver) getCommand().getReceiver()).loadBids(requestContent);
        }
    },
    LOAD_ACTIVE_ITEMS(new LoadActiveItemsCommand(new ItemReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            ((ItemReceiver) getCommand().getReceiver()).loadActiveItems(requestContent);
        }
    },
    DISCARD_ITEM(new DiscardItemCommand(new AdminReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            ((AdminReceiver) getCommand().getReceiver()).discardItem(requestContent);
        }
    },
    LOAD_NOTIFICATIONS(new LoadNotificationsCommand(new NotificationReceiverImpl())) {
        @Override
        public void doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            ((NotificationReceiver) getCommand().getReceiver()).loadNotifications(requestContent);
        }
    };

    private AbstractCommand command;

    CommandType(AbstractCommand command) {
        this.command = command;
    }

    public AbstractCommand getCommand() {
        return command;
    }

    public abstract void doReceiver(RequestContent requestContent) throws ReceiverLayerException;

    public static CommandType takeCommandType(AbstractCommand command) {
        return Arrays.stream(CommandType.values())
                .filter(type -> type.getCommand().equals(command))
                .findFirst().orElse(LOAD_ACTIVE_ITEMS);
    }

}