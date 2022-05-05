package com.ls.community.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName QUESTION
 */
@Data
public class Question implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
    private Long gmtCreate;

    /**
     * 
     */
    private Long gmtModified;

    /**
     * 
     */
    private Integer creator;

    /**
     * 
     */
    private Integer commentCount;

    /**
     * 
     */
    private Integer viewCount;

    /**
     * 
     */
    private Integer likeCount;

    /**
     * 
     */
    private String tag;

    private static final long serialVersionUID = 1L;
}