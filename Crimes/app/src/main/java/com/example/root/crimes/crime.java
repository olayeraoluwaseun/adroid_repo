package com.example.root.crimes;

import java.util.UUID;

/**
 * Created by root on 3/3/15.
 */
public class crime
{
    private UUID mId;

    private String mTitle;

    public crime()
    {
        mId = UUID.randomUUID();
    }

    public UUID getID()
    {
        return  mId;
    }

    public String getmTitle()
    {
        return mTitle;
    }

    public void setmTitle (String title)
    {
         mTitle = title;
    }

}
