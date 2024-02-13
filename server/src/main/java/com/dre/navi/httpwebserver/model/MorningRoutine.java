package com.dre.navi.httpwebserver.model;

public class ChoresRoutine
{

    private String name;
    private String userId;
    private int index;
    private String id;

    public ChoresRoutine(String name, String userId, int index, String id)
    {
        this.name = name;
        this.userId = userId;
        this.index = index;
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }


}
