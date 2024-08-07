package com.example.api.service;

import com.example.api.model.Term;
import com.example.api.repository.TermRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TermService {
    @Autowired
    private TermRepository termRepository;

    public Term addTerm(Term term) {
        return termRepository.save(term);
    }

    public List<Term> getAllTerms() {
        return termRepository.findAll();
    }
}
