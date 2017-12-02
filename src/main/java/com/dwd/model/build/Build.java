package com.dwd.model.build;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "builds")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Build {
    private String id;
    private long createdTimestamp;
    private String creatorName;
    private String buildName;
    private List<String> tags;
    private List<EquippedItem> equipped;
    private List<InventoryItem> inventory;

    @Override
    public String toString() {
        return "Build{" +
                "id='" + id + '\'' +
                ", createdTimestamp=" + createdTimestamp +
                ", creatorName='" + creatorName + '\'' +
                ", buildName='" + buildName + '\'' +
                ", tags=" + tags +
                ", equipped=" + equipped +
                ", inventory=" + inventory +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public long getCreatedTimestamp() {
        return createdTimestamp;

    }

    public void setCreatedTimestamp(long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public List<EquippedItem> getEquipped() {
        return equipped;
    }

    public void setEquipped(List<EquippedItem> equipped) {
        this.equipped = equipped;
    }

    public List<InventoryItem> getInventory() {
        return inventory;
    }

    public void setInventory(List<InventoryItem> inventory) {
        this.inventory = inventory;
    }
}
