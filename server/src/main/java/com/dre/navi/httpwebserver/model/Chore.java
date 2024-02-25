package com.dre.navi.httpwebserver.model;

public class Chore
{

    private String name;
    private String userId;
    private int index;
    private String id;
    private String day;

    public Chore(String name, String userId, int index, String id, String day)
    {
        this.name = name;
        this.userId = userId;
        this.index = index;
        this.id = id;
        this.day = day;
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

    public String getDay()
    {
        return day;
    }

    public void setDay(String day)
    {
        this.day = day;
    }

}
