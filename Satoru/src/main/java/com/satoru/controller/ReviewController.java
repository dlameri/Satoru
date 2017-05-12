package com.satoru.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.satoru.annotation.Layout;
import com.satoru.domain.ReviewSession;
import com.satoru.domain.ReviewSessionWord;
import com.satoru.service.ReviewSessionService;

@Controller
@RequestMapping("/review")
@Layout(value = Layout.DEFAULT)
public class ReviewController extends GenericController {

	@Autowired
	private ReviewSessionService reviewSessionService;

	@GetMapping(value={"", "/"})
	public String reviewWords(Model model) {
		ReviewSession reviewSession = reviewSessionService.findByUser(getLoggedUser());
		
		if (reviewSession.hasFinished()) {
			return "redirect:/home";
		}
		
		ReviewSessionWord nextWord = reviewSession.getNextWord();

		model.addAttribute("reviewSession", reviewSession);
		model.addAttribute("model", nextWord);
		model.addAttribute("options", reviewSession.generateOptions(nextWord));

		return "review/review";
	}

	@RequestMapping(value = {"/answer"}, method = RequestMethod.POST)
	public String processForm(@ModelAttribute(value = "model") ReviewSessionWord sessionWord) {
		Boolean hasFinished = reviewSessionService.processAnswerAndCheckFinish(getLoggedUser(), sessionWord);

		if (! hasFinished) {
			return "redirect:/review";
		} else {
			return "redirect:/home";
		}
	}
}