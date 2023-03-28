package org.celso.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Filme {

    private ArrayList<Itens> items;
    private String errorMessage;


}
