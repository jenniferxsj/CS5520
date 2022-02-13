package com.example.numad22sp_shujunxiao;

public class ItemCard implements ItemClickListener {
    private final int img;
    private final String name;
    private final String link;
    private boolean isChecked;

    public ItemCard(int img, String name, String link, boolean isChecked) {
        this.img = img;
        this.name = name;
        this.link = link;
        this.isChecked = isChecked;
    }

    public int getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public boolean getStatus() {
        return isChecked;
    }

    @Override
    public void onItemClick(int position) {
        isChecked = !isChecked;
    }

    @Override
    public void onCheckBoxClick(int position) {
        isChecked = !isChecked;
    }
}
