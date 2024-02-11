package com.dre.navi.httpwebserver.model;

public class Task
{

    private String name;
   private int points;
   private String userId;
   private int index;
    private String id;


    public Task(String name, int points, String userId, int index, String id)
    {
        this.points = points;
        this.userId = userId;
        this.index = index;
        this.id = id;
        this.name = name;
    }

    public int getPoints()
    {
        return points;
    }

    public void setPoints(int points)
    {
        this.points = points;
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
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
