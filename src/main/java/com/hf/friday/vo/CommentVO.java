package com.hf.friday.vo;

import com.hf.friday.model.Comment;
import com.hf.friday.model.SysUser;
import lombok.Data;

import java.util.List;

/**
 * 从app接受消息的类
 * @Author CoolWind
 * @Date 2020/5/1 9:45
 */
@Data
public class CommentVO{
    private SysUser user;
    private Comment comment;//评论
    private List<Comment> commentList;//评论评论的评论
    private Integer isLIke;
}
