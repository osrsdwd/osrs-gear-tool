package com.dwd.model.build;

public class EquippedItem {
    private Slot slot;
    private int itemId;

    public int getItemId() {
        return itemId;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
