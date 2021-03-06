package com.satoru.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.satoru.annotation.Layout;
import com.satoru.domain.LessonWord;
import com.satoru.service.LessonWordService;

@Controller
@RequestMapping("/lessonWord")
@Layout(value = Layout.DEFAULT)
public class LessonWordController extends GenericController {

	@Autowired
	private LessonWordService lessonWordService;
	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("models", lessonWordService.listAll());
		
		return "lessonWord/list";
	}

	@GetMapping("/new")
	public String creationForm(Model model) {
		model.addAttribute("title", "Criação");
		model.addAttribute("model", new LessonWord());

		return "lessonWord/form";
	}
	
	@GetMapping("/edit/{id}")
	public String editForm(Model model, @PathVariable("id") String id) {
		LessonWord lessonWord = lessonWordService.findOne(id);
		model.addAttribute("title", "Edição do conteúdo " + lessonWord.getWord());
		model.addAttribute("model", lessonWord);

		return "lessonWord/form";
	}
		
	@GetMapping("/{id}/link")
	public String linkForm(Model model, @PathVariable("id") String id) {
		LessonWord lessonWord = lessonWordService.findOne(id);
		List<LessonWord> lessonWords = lessonWordService.listAll();
		lessonWords.remove(lessonWord);

		model.addAttribute("title", "Associar conteúdo para revisão " + lessonWord.getWord());		
		model.addAttribute("model", lessonWord);
		//TODO carregar essa lista incrementalmente
		model.addAttribute("lessonWords", lessonWords);

		return "lessonWord/link";
	}	
	
	@GetMapping("/remove/{id}")
	public String remove(Model model, @PathVariable("id") String id) {
		lessonWordService.delete(lessonWordService.findOne(id));
		
		return "redirect:/lessonWord";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String processForm(@ModelAttribute(value = "model") LessonWord lessonWord) {
		if (lessonWord.getId() != null) {
			LessonWord original = lessonWordService.findOne(lessonWord.getId());

			lessonWord.setReviewLinks(original.getReviewLinks());
		}
		
		lessonWordService.save(lessonWord);
		
		return "redirect:/lessonWord";
	}
	
	@RequestMapping(value = "/link/save", method = RequestMethod.POST)
	public String processLinkForm(@ModelAttribute(value = "model") LessonWord lessonWord) {
		LessonWord original = lessonWordService.findOne(lessonWord.getId());
		
		original.clearReviewLinks();
		for (LessonWord link : lessonWord.getReviewLinks()) {			
			original.addReviewLink(lessonWordService.findOne(link.getId()));	
		}
		
		lessonWordService.save(original);
		
		return "redirect:/lessonWord";
	}
}
