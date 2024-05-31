package com.movieboard.mboard.vo;

import lombok.Getter;

@Getter
public class PageVo {
    private int pageNum;
    private int listLimit;
    private int listCount;
    private int pageListLimit;
    private int maxPage;
    private int startPage;
    private int endPage;
}
