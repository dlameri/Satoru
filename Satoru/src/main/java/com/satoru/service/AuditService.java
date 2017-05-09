package com.satoru.service;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.satoru.domain.Audit;
import com.satoru.domain.User;
import com.satoru.repository.AuditRepository;

@Service
public class AuditService extends GenericService<Audit, String, AuditRepository>{
	public AuditService() {
		this.sort = new Sort(Direction.DESC, "date");
	}
	
	private Date truncate(Date date) {
		return DateUtils.truncate(date, Calendar.DATE);
	}
	
	private Audit findTodaysAudit(User loggedUser) {
		Date today = truncate(new Date());
		Audit audit = getRepository().findByUserAndDate(loggedUser, today);
		
		if (audit == null) {
			audit = new Audit(loggedUser, today);
		}
		
		return audit;
	}
	
	public void auditAnswer(User loggedUser, boolean answerIsRight) {
		Audit audit = findTodaysAudit(loggedUser);		
		
		audit.incrementStudy(answerIsRight);
		
		save(audit);
	}
	
	public void auditReview(User loggedUser, boolean answerIsRight) {
		Audit audit = findTodaysAudit(loggedUser);		
		
		audit.incrementReview(answerIsRight);
		
		save(audit);
	}

	public List<Audit> listForDashboard(User loggedUser) {
		Date today = new Date();
		
		Calendar c = Calendar.getInstance(); 
		c.setTime(today); 
		c.add(Calendar.DATE, -6);
		
		List<Audit> audits = getRepository().findByUserAndDateBetween(loggedUser, c.getTime(), today);
		
		while (! c.getTime().after(today)) {
			if (audits.stream().filter(a -> a.isSameDate(c.getTime())).count() == 0) {
				audits.add(new Audit(loggedUser, truncate(c.getTime())));
			}
			c.add(Calendar.DATE, 1);
		}
		
		Collections.sort(audits);
		
		return audits;
	}

	public Integer getTotalStudies(List<Audit> audits) {
		return audits
			.stream()
			.mapToInt(a -> a.getStudyErrors() + a.getStudySuccess())
			.sum();		
	}
	
	public Integer getTotalReviews(List<Audit> audits) {
		return audits
			.stream()
			.mapToInt(a -> a.getReviewErrors() + a.getReviewSuccess())
			.sum();		
	}
}