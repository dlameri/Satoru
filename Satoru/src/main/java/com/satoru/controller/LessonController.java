package com.satoru.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.satoru.annotation.Layout;
import com.satoru.domain.Course;
import com.satoru.domain.Lesson;
import com.satoru.domain.LessonWord;
import com.satoru.service.CourseService;
import com.satoru.service.LessonService;
import com.satoru.service.LessonWordService;

@Controller
@RequestMapping("/course/{courseId}/lesson")
@Layout(value = Layout.DEFAULT)
public class LessonController extends GenericController {
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private LessonService lessonService;
	
	@Autowired
	private LessonWordService lessonWordService;

	@GetMapping
	public String list(Model model, @PathVariable("courseId") String courseId) {
		Course course = courseService.findOne(courseId);
		
		model.addAttribute("course", course);
		model.addAttribute("models", lessonService.findByCourse(course));

		return "lesson/list";
	}
	
	@GetMapping("/new")
	public String creationForm(Model model, @PathVariable("courseId") String courseId) {
		Course course = courseService.findOne(courseId);
		
		model.addAttribute("title", "Criação");
		model.addAttribute("model", new Lesson());
		model.addAttribute("course", course);

		return "lesson/form";
	}
	
	@GetMapping("/edit/{id}")
	public String editForm(Model model, @PathVariable("id") String id, @PathVariable("courseId") String courseId) {
		Course course = courseService.findOne(courseId);
		Lesson lesson = lessonService.findOne(id);
		
		model.addAttribute("title", "Edição da lição " + lesson.getName());
		model.addAttribute("model", lesson);
		model.addAttribute("course", course);

		return "lesson/form";
	}
	
	@GetMapping("/remove/{id}")
	public String remove(Model model, @PathVariable("id") String id, @PathVariable("courseId") String courseId) {
		lessonService.delete(lessonService.findOne(id));
		
		return "redirect:/course/" + courseId + "/lesson";
	}

	@RequestMapping(value = {"/save", "/edit/save"}, method = RequestMethod.POST)
	public String processForm(@ModelAttribute(value = "model") Lesson lesson, @PathVariable("courseId") String courseId) {
		if (lesson.getId() != null) {
			Lesson original = lessonService.findOne(lesson.getId());
			lesson.setLessonWords(original.getLessonWords());
		}
		
		lesson.setCourse(courseService.findOne(courseId));
		
		
		lessonService.save(lesson);
		
		return "redirect:/course/" + courseId + "/lesson";
	}
	
	@GetMapping("/{id}/link")
	public String linkForm(Model model, @PathVariable("id") String id, @PathVariable("courseId") String courseId) {
		Course course = courseService.findOne(courseId);
		Lesson lesson = lessonService.findOne(id);

		model.addAttribute("title", "Associar conteúdo para lição " + lesson.getName());		
		model.addAttribute("model", lesson);
		//TODO carregar essa lista incrementalmente
		model.addAttribute("lessonWords", lessonWordService.listAll());
		model.addAttribute("course", course);
		
		return "lesson/link";
	}	
	
	@RequestMapping(value = "/link/save", method = RequestMethod.POST)
	public String processLinkForm(@ModelAttribute(value = "model") Lesson lesson, @PathVariable("courseId") String courseId) {
		Lesson original = lessonService.findOne(lesson.getId());
		
		original.clearLessonWords();
		for (LessonWord link : lesson.getLessonWords()) {			
			original.addLessonWord(lessonWordService.findOne(link.getId()));	
		}
		
		lessonService.save(original);
		
		return "redirect:/course/" + courseId + "/lesson";
	}
}