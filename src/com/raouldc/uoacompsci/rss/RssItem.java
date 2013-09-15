package com.raouldc.uoacompsci.rss;
/**
 * This code encapsulates RSS item data.
 * Our application needs title and link data.
 *
 * @author ITCuties
 */
public class RssItem {
    // item title
    private String title;
    // item link
    private String link,description, date;
    public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    @Override
    public String toString() {
        return title;
    }
}
