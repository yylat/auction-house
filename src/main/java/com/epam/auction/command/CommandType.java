package com.epam.auction.command;

import com.epam.auction.command.admin.ApproveItemCommand;
import com.epam.auction.command.admin.LoadItemsForCheckCommand;
import com.epam.auction.command.ajax.LoadAllImagesCommand;
import com.epam.auction.command.ajax.LoadImageCommand;
import com.epam.auction.command.ajax.LoadUsersCommand;
import com.epam.auction.command.common.ChangeLocaleCommand;
import com.epam.auction.command.common.OpenMainPageCommand;
import com.epam.auction.command.item.*;
import com.epam.auction.command.user.LogOutCommand;
import com.epam.auction.command.user.SignInCommand;
import com.epam.auction.command.user.SignUpCommand;
import com.epam.auction.content.RequestContent;
import com.epam.auction.exception.ReceiverLayerException;
import com.epam.auction.receiver.AdminReceiver;
import com.epam.auction.receiver.CommonReceiver;
import com.epam.auction.receiver.ItemReceiver;
import com.epam.auction.receiver.UserReceiver;
import com.epam.auction.receiver.impl.AdminReceiverImpl;
import com.epam.auction.receiver.impl.CommonReceiverImpl;
import com.epam.auction.receiver.impl.ItemReceiverImpl;
import com.epam.auction.receiver.impl.UserReceiverImpl;

import java.util.Arrays;

public enum CommandType {

    OPEN_MAIN_PAGE(new OpenMainPageCommand(new CommonReceiverImpl())) {
        @Override
        public boolean doReceiver(RequestContent requestContent) {
            return ((CommonReceiver) getCommand().getReceiver()).openMainPage(requestContent);
        }
    },
    SIGN_UP(new SignUpCommand(new UserReceiverImpl())) {
        @Override
        public boolean doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            return ((UserReceiver) getCommand().getReceiver()).signUp(requestContent);
        }
    },
    SIGN_IN(new SignInCommand(new UserReceiverImpl())) {
        @Override
        public boolean doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            return ((UserReceiver) getCommand().getReceiver()).signIn(requestContent);
        }
    },
    CHANGE_LOCALE(new ChangeLocaleCommand(new CommonReceiverImpl())) {
        @Override
        public boolean doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            return ((CommonReceiver) getCommand().getReceiver()).changeLocale(requestContent);
        }
    },
    LOG_OUT(new LogOutCommand(new UserReceiverImpl())) {
        @Override
        public boolean doReceiver(RequestContent requestContent) {
            return ((UserReceiver) getCommand().getReceiver()).logOut(requestContent);
        }
    },
    LOAD_USERS(new LoadUsersCommand(new AdminReceiverImpl())) {
        @Override
        public boolean doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            return ((AdminReceiver) getCommand().getReceiver()).loadUsers(requestContent);
        }
    },
    LOAD_USER_ITEMS(new LoadUserItemsCommand(new ItemReceiverImpl())) {
        @Override
        public boolean doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            return ((ItemReceiver) getCommand().getReceiver()).loadUserItems(requestContent);
        }
    },
    LOAD_CATEGORIES(new LoadUserItemsCommand(new ItemReceiverImpl())) {
        @Override
        public boolean doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            return ((ItemReceiver) getCommand().getReceiver()).loadCategories(requestContent);
        }
    },
    CREATE_ITEM(new CreateItemCommand(new ItemReceiverImpl())) {
        @Override
        public boolean doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            return ((ItemReceiver) getCommand().getReceiver()).createItem(requestContent);
        }
    },
    LOAD_IMAGE(new LoadImageCommand(new ItemReceiverImpl())) {
        @Override
        public boolean doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            return ((ItemReceiver) getCommand().getReceiver()).loadImage(requestContent);
        }
    },
    LOAD_CERTAIN_ITEMS(new LoadCertainItemsCommand(new ItemReceiverImpl())) {
        @Override
        public boolean doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            return ((ItemReceiver) getCommand().getReceiver()).loadCertainItems(requestContent);
        }
    },
    APPROVE_ITEM(new ApproveItemCommand(new AdminReceiverImpl())) {
        @Override
        public boolean doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            return ((AdminReceiver) getCommand().getReceiver()).approveItem(requestContent);
        }
    },
    LOAD_ITEMS_FOR_CHECK(new LoadItemsForCheckCommand(new ItemReceiverImpl())) {
        @Override
        public boolean doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            return ((ItemReceiver) getCommand().getReceiver()).loadItemsForCheck(requestContent);
        }
    },
    LOAD_ITEM(new LoadItemCommand(new ItemReceiverImpl())) {
        @Override
        public boolean doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            return ((ItemReceiver) getCommand().getReceiver()).loadItem(requestContent);
        }
    },
    LOAD_ALL_IMAGES(new LoadAllImagesCommand(new ItemReceiverImpl())) {
        @Override
        public boolean doReceiver(RequestContent requestContent) throws ReceiverLayerException {
            return ((ItemReceiver) getCommand().getReceiver()).loadAllImages(requestContent);
        }
    };

    private AbstractCommand command;

    CommandType(AbstractCommand command) {
        this.command = command;
    }

    public AbstractCommand getCommand() {
        return command;
    }

    public abstract boolean doReceiver(RequestContent requestContent) throws ReceiverLayerException;

    public static CommandType takeCommandType(AbstractCommand command) {
        return Arrays.stream(CommandType.values())
                .filter(type -> type.getCommand().equals(command))
                .findFirst().orElse(OPEN_MAIN_PAGE);
    }

}