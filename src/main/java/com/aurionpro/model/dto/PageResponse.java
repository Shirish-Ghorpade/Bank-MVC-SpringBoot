package com.aurionpro.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PageResponse<T> {
	private int totalPages;
	private long totalElements;
	private int size;
	private List<T> content;
	private boolean lastPage;
}
