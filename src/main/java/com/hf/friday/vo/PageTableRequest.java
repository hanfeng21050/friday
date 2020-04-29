package com.hf.friday.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageTableRequest extends BaseVO implements Serializable {
    private Integer page;
    private Integer limit;
    private Integer offset;
    private Integer userId;
    private Integer type;

    public void countOffset(){
        if(null == this.page || null == this.limit){
            this.offset = 0;
            return;
        }
        this.offset = (this.page - 1) * limit;
    }
}
