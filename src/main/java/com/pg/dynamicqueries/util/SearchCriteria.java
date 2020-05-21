package com.pg.dynamicqueries.util;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {

	private String key;
    private Object value;
    private Date date;
    private SearchOperation operation;
	
}
