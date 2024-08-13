package com.dre.navi.httpwebserver.model;

public class User
{
    private String userName;
    private String id;
    private String pointsRequired;
    private String choresRequired;
    private String tasksRequired;

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


    public String getPointsRequired() {
        return pointsRequired;
    }

    public void setPointsRequired(String pointsRequired) {
        this.pointsRequired = pointsRequired;
    }

    public String getChoresRequired() {
        return choresRequired;
    }

    public void setChoresRequired(String choresRequired) {
        this.choresRequired = choresRequired;
    }

    public String getTasksRequired() {
        return tasksRequired;
    }

    public void setTasksRequired(String tasksRequired) {
        this.tasksRequired = tasksRequired;
    }
}
