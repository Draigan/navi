package com.dre.navi.httpwebserver.model;

public class User
{
    private String userName;
    private String id;

    public User(String id, String userName)
    {
        this.id = id;
        this.userName = userName;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
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
