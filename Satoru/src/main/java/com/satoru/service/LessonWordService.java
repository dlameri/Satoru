package com.satoru.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.satoru.domain.LessonWord;
import com.satoru.repository.LessonWordRepository;

@Service
public class LessonWordService extends GenericService<LessonWord, String, LessonWordRepository>{
	public LessonWordService() {
		this.sort = new Sort(Direction.DESC, "romanizedWord");
	}
	
	public LessonWord findByWord(String word) {
		return getRepository().findByWord(word);
	}
}