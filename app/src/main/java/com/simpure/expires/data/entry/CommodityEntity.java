package com.simpure.expires.data.entry;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.simpure.expires.data.Date;
import com.simpure.expires.model.Commodity;

@Entity(tableName = "commodities")
public class CommodityEntity implements Commodity {
    @PrimaryKey
    private Long id;
    private String name;
    private Date date;

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public CommodityEntity() {
    }

    @Ignore
    public CommodityEntity(long id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public CommodityEntity(Commodity commodity) {
        this.id = commodity.getId();
        this.name = commodity.getName();
        this.date = commodity.getDate();
    }
}
