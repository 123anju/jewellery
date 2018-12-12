package com.JewelleryShop.Jewellery.data.fixtures;



import com.JewelleryShop.Jewellery.data.model.Message;
import com.JewelleryShop.Jewellery.data.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/*
 * Created by troy379 on 12.12.16.
 */
public final class MessagesFixtures extends FixturesData {
    static int count=0;
    private MessagesFixtures() {
        throw new AssertionError();
    }




    public static Message getTextMessage() {
        return getTextMessage(getRandomMessage());
    }

    public static Message getTextMessage(String text) {
        return new Message(getRandomId(), getUser(), text);
    }

    public static ArrayList<Message> getMessages(Date startDate) {
        ArrayList<Message> messages = new ArrayList<>();
        for (int i = 0; i < 10/*days count*/; i++) {

                Message message;

                    message = getTextMessage();

                messages.add(message);

        }
        return messages;
    }

    private static User getUser() {
        Random rnd=new Random();
        boolean even = rnd.nextBoolean();
        return new User(
                "3",
                 avatars.get(1),
                avatars.get(1),
                true);
    }
}
