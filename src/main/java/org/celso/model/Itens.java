package org.celso.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Itens {

    private String id;
    private int rank;
    private String title;
    private String fullTitle;
    private String year;
    private String image;
    private String crew;
    private float imDbRating;
    private String imDbRatingCount;

}
