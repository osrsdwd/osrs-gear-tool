package com.dwd.model.osrsapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.net.URL;

@Document(collection = "items")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    @Id
    private int id;

    @JsonIgnore
    private PriceTrend current;

    private String description;
    private URL icon;
    private URL icon_large;
    private boolean members;
    private String name;

    @JsonIgnore
    private PriceTrend today;

    public PriceTrend getCurrent() {
        return current;
    }

    public void setCurrent(PriceTrend current) {
        this.current = current;
    }

    private String type;
    private URL typeIcon;

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", icon=" + icon +
                ", icon_large=" + icon_large +
                ", members=" + members +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", typeIcon=" + typeIcon +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public URL getIcon() {
        return icon;
    }

    public void setIcon(URL icon) {
        this.icon = icon;
    }

    public URL getIcon_large() {
        return icon_large;
    }

    public void setIcon_large(URL icon_large) {
        this.icon_large = icon_large;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isMembers() {
        return members;
    }

    public void setMembers(boolean members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public URL getTypeIcon() {
        return typeIcon;
    }

    public void setTypeIcon(URL typeIcon) {
        this.typeIcon = typeIcon;
    }
}
