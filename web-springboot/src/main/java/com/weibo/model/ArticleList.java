package com.weibo.model;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class ArticleList implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Article> list;

    private Integer total;

    private Integer pageNum;

    private Integer pageSize;
}
