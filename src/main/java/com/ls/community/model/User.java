package com.ls.community.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName USER
 */
@Data
public class User implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String accountId;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String token;

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
    private String bio;

    /**
     * 
     */
    private String avatarUrl;

    private static final long serialVersionUID = 1L;
}