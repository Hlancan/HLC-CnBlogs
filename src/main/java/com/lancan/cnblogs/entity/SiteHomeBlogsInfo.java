package com.lancan.cnblogs.entity;

/**
 * Created by lan on 16-10-31.
 */

public class SiteHomeBlogsInfo {

    private String id;
    private String title;
    private String summary;
    private String published;
    private String authorName;
    private String authorUrl;
    private String authorAvatar;
    private String link;
    private String sourceName;
    private String updated;
    private int diggs;
    private int views;
    private int comments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public int getDiggs() {
        return diggs;
    }

    public void setDiggs(int diggs) {
        this.diggs = diggs;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "SiteHomeBlogsInfo{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", published='" + published + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorUrl='" + authorUrl + '\'' +
                ", authorAvatar='" + authorAvatar + '\'' +
                ", link='" + link + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", updated='" + updated + '\'' +
                ", diggs=" + diggs +
                ", views=" + views +
                ", comments=" + comments +
                '}';
    }
}
