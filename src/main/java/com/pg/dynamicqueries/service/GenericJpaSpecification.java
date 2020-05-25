package com.pg.dynamicqueries.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.pg.dynamicqueries.util.SearchCriteria;
import com.pg.dynamicqueries.util.SearchOperation;

public class GenericJpaSpecification<E> implements Specification<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6574627686289373028L;
	private List<SearchCriteria> list;

	public GenericJpaSpecification() {
		this.list = new ArrayList<>();
	}

	public void add(SearchCriteria criteria) {
		list.add(criteria);
	}

	@Override
	public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

		//create a new predicate list
		List<Predicate> predicates = new ArrayList<>();
		
		DateFormat format = new SimpleDateFormat("MM-dd-yyyy");

		//add add criteria to predicates
		for (SearchCriteria criteria : list) {
			if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
				predicates.add(builder.greaterThan(
						root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
				predicates.add(builder.lessThan(
						root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
				predicates.add(builder.greaterThanOrEqualTo(
						root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
				predicates.add(builder.lessThanOrEqualTo(
						root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
				predicates.add(builder.notEqual(
						root.get(criteria.getKey()), criteria.getValue()));
			} else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
				predicates.add(builder.equal(
						builder.lower(root.get(criteria.getKey())),
						criteria.getValue().toString().toLowerCase()));
			} else if (criteria.getOperation().equals(SearchOperation.EQUALS_IGNORE_CASE)) {
				predicates.add(builder.equal(
						root.get(criteria.getKey()), criteria.getValue()));
			} else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
				predicates.add(builder.like(
						builder.lower(root.get(criteria.getKey())),
						"%" + criteria.getValue().toString().toLowerCase() + "%"));
			} else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
				predicates.add(builder.like(
						builder.lower(root.get(criteria.getKey())),
						criteria.getValue().toString().toLowerCase() + "%"));
			} else if (criteria.getOperation().equals(SearchOperation.MATCH_START)) {
				predicates.add(builder.like(
						builder.lower(root.get(criteria.getKey())),
						"%" + criteria.getValue().toString().toLowerCase()));
			} else if (criteria.getOperation().equals(SearchOperation.IN)) {
				predicates.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()));
			} else if (criteria.getOperation().equals(SearchOperation.NOT_IN)) {
				predicates.add(builder.not(root.get(criteria.getKey())).in(criteria.getValue()));
			} else if(criteria.getOperation().equals(SearchOperation.BEFORE)) {
				try {
					predicates.add(builder.lessThanOrEqualTo(root.<Date>get(criteria.getKey()), format.parse(criteria.getValue().toString())));
				} catch (ParseException e) {
			}
			} else if(criteria.getOperation().equals(SearchOperation.AFTER)) {
				try {
					predicates.add(builder.greaterThanOrEqualTo(root.<Date>get(criteria.getKey()), format.parse(criteria.getValue().toString())));
				} catch (ParseException e) {
				}
			}
		}

		return builder.and(predicates.toArray(new Predicate[0]));
	}
}
