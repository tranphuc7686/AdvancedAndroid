package com.example.admin.androidadvance;

/**
 * Created by Admin on 29/01/2018.
 */

public class GithubData {
    private String id,name,full_name,owner,mPrivate,mHtml;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getmPrivate() {
        return mPrivate;
    }

    public void setmPrivate(String mPrivate) {
        this.mPrivate = mPrivate;
    }

    public String getmHtml() {
        return mHtml;
    }

    public void setmHtml(String mHtml) {
        this.mHtml = mHtml;
    }

    @Override
    public String toString() {
        return "GithubData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", full_name='" + full_name + '\'' +
                ", owner='" + owner + '\'' +
                ", mPrivate='" + mPrivate + '\'' +
                ", mHtml='" + mHtml + '\'' +
                '}';
    }

    public GithubData(String id, String name, String full_name, String owner, String mPrivate, String mHtml) {

        this.id = id;
        this.name = name;
        this.full_name = full_name;
        this.owner = owner;
        this.mPrivate = mPrivate;
        this.mHtml = mHtml;
    }
}
