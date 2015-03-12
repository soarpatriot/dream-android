package cn.dreamreality.entities;

/**
 * Created by liuhaibao on 15/3/8.
 */
public class DreamReality {

    private long id;
    private String dream;

    private String reality;
    private int percentage;

    private long createdAt;
    private long updatedAt;


    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }


    public String getDream() {
        return dream;
    }

    public void setDream(String dream) {
        this.dream = dream;
    }


    public String getReality() {
        return reality;
    }

    public void setReality(String reality) {
        this.reality = reality;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
