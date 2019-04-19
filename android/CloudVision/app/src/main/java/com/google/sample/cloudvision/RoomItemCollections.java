package com.google.sample.cloudvision;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RoomItemCollections {

    private final String[] livingRoom = {"tv", "couch", "entertainment", "console", "game", "gaming"};
    private final String[] lrPrices = {"1499.99", "2599.99", "249.99", "249.99", "59.99", "249.99"};

    private final String[] office = {"chair", "desk", "monitor", "computer", "laptop", "keyboard"};
    private final String[] officePrices = {"99.99", "499.99", "199.99", "999.99", "1999.99", "49.99"};

    private int roomNumber = 1;
    private int roomItemIndex = -1;
    private String definition;
    private String[] autoFillData;
    private List rooms = new ArrayList();

    public RoomItemCollections(String definition){
        this.definition = definition.trim();
        rooms.add(this.livingRoom); //roomNumber = 1
        rooms.add(this.office); // roomNumber = 2
        autoFillData = new String[3];
    }

    public String[] getAutoFillData(){
        autoFillData[0] = getRoom();
        autoFillData[1] = getPrice();
        autoFillData[2] = getPurchaseLocation();
        return autoFillData;
    }

    private String getRoom(){

        Iterator it = rooms.iterator();
        while(it.hasNext()){
            String[] room = (String[])it.next();
            for(int i = 0; i < room.length; i++){
                if(definition.toUpperCase().contains(room[i].toUpperCase())){
                    roomItemIndex = i;
                    switch(roomNumber){
                        case 1:
                            return "Living Room";
                        case 2:
                            return "Office";
                    }
                }
            }
            roomNumber++;
        }
        return "Unknown";
    }

    private String getPrice(){
        switch(roomNumber) {
            case 1:
                return lrPrices[roomItemIndex];
            case 2:
                return officePrices[roomItemIndex];
            default:
                return "Unknown";
        }
    }

    private String getPurchaseLocation(){
        switch(autoFillData[0].toUpperCase()){
            case "LIVING ROOM":
                return "Target";
            case "OFFICE":
                return "Best Buy";
            default:
                return "Unknown";
        }
    }
}
