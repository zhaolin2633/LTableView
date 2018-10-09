package com.view.entitiy;

import java.util.List;

/**
 * Created by zhaolin2633 on 2018/9/21.
 */

public class ParentEntity  {
    public boolean isOpen;
    public String prarentName;
    public String name;
    public List<String> itemEntity;
    public List<ParentEntity> childs;
    public int itemType;
    public ParentEntity(int itemType) {
        this.itemType = itemType;
    }

    public int getItemType() {
        return itemType;
    }

    public static final int TYPE_TITLE = 1;
    public static final int TYPE_NROMAL = 2;
}
