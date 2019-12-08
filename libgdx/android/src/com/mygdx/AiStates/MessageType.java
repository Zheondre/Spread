package com.mygdx.AiStates;

public final class MessageType {

    private MessageType(){
    }
//Every Request should have a reply or something isnt working
    //Request
    public static final int HELP_ZOMBIE_SPOTTED = 0;
    public static final int HELP_INFECTED = 1;
    public static final int HELP_COP_NEEDS_EMT = 2;
    public static final int HELP_BACKUP_COPS = 3;
    public static final int HELP_ALLOCATE_MORE_COPS = 18;
    public static final int HELP_BACKUP_ARMY = 4;
    public static final int FOLLOW_ME = 5;
    public static final int FOLLOW_ME_COP = 5;
    public static final int GIVE_PER_LOCATION = 6;
    /*
    public static final int FOLLOW_ME_EMT = 5;
    public static final int FOLLOW_ME_SEC = 5;
    public static final int FOLLOW_ME_ARMY = 5;
    public static final int GIVE_PER_LOCATION = 6;
     */
    //Replies
    public static final int HELP_ZOMBIE_SPOTTED_REPLY = 7;
    public static final int HELP_INFECTED_REPLY = 8;
    public static final int HELP_COP_NEEDS_EMT_REPLY = 9;
    public static final int HELP_BACKUP_COPS_REPLY = 10;
    public static final int HELP_BACKUP_ARMY_REPLY = 11;
    public static final int FOLLOW_ME_REPLY = 12;
    public static final int GIVE_PER_LOCATION_REPLY = 13;
    public static final int HELP_INFECTED_REPLY_DENIED = 14;
    public static final int NO_HELP_NEEDED = 15;
    public static final int HELP_BOMB_INFECTED = 16;
    public static final int HELP_BOMB_INFECTED_REPLY = 17;
    public static final int HELP_EMT_NEEDS_COP = 18;

}
