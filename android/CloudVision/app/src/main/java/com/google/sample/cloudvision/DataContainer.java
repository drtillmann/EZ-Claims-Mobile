package com.google.sample.cloudvision;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataContainer {

    private static List<Object> container;
    private static DataContainer dataContainer;
    private static Bitmap image;

    /**
     * @Description: Default Constructor
     */
    private DataContainer(){
        container = new ArrayList<>();
        image = null;
    }

    public void setImage(Bitmap bm){
        this.image = bm;
    }

    public byte[] getImage(){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, bos);
        return bos.toByteArray();
    }

    /**
     * @Description: Get the instance of this object. (Singleton)
     * @return
     */
    public static DataContainer instance(){
        if(dataContainer == null){
            return dataContainer = new DataContainer();
        }else{
            return dataContainer;
        }
    }

    /**
     * @Description: Add an object to the list
     * @param: info - the object to be added to the list
     */
    public void addData(Object info){
        container.add(info);
    }

    /**
     * @Description: Get the data in the list.
     * @return an iterator to traverse the list.
     */
    public Iterator getData(){
        return container.iterator();
    }

    /**
     * @Description: Remove all objects from the list.
     */
    public void clear(){ container.clear(); }

    /**
     * @Description: Get the number of objects in the list
     * @return the size of the list
     */
    public int size(){
        return container.size();
    }

    /**
     * @Description: Get the index of the Object containing the specified String
     * @param definition
     * @return
     */
    public int getIndex(String definition){
        for(int index = 0; index < size(); index++) {
            GoogleVisionItemInfo info = (GoogleVisionItemInfo) container.get(index);
            if(info.getDefinition().equals(definition)){
                return index;
            }
        }
        return -1;
    }

    /**
     * @Description: Get the GoogleVisionItemInfo object at the specified index
     * @param index
     * @return
     */
    public GoogleVisionItemInfo get(int index){
        return (GoogleVisionItemInfo) container.get(index);
    }

}
