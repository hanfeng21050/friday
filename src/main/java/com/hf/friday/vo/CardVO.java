package com.hf.friday.vo;

import com.hf.friday.model.Card;
import com.hf.friday.model.Image;
import com.hf.friday.model.SysUser;
import lombok.Data;

import java.util.List;

/**
 * @Author CoolWind
 * @Date 2020/5/5 11:40
 */
@Data
public class CardVO{
    private SysUser user;
    private Card card;
    private List<Image> imageList;
    private String host;
    private boolean isFollow;

}
