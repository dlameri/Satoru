package com.satoru.config.initializer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.satoru.domain.Course;
import com.satoru.domain.Lesson;
import com.satoru.domain.LessonWord;
import com.satoru.domain.Role;
import com.satoru.domain.User;
import com.satoru.domain.UserStatus;
import com.satoru.service.CourseService;
import com.satoru.service.LessonService;
import com.satoru.service.LessonWordService;
import com.satoru.service.RoleService;
import com.satoru.service.UserService;

public abstract class DataInitializer {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private LessonService lessonService;
	@Autowired
	private LessonWordService lessonWordService;

	@PostConstruct
	public abstract void init();

	protected LessonWord createLessonWordIfNotExist(String word, String romanizedWord, String meaning) {
		LessonWord lessonWord = lessonWordService.findByWord(word);

		if (lessonWord == null) {
			lessonWord = new LessonWord();

			lessonWord.setWord(word);
			lessonWord.setRomanizedWord(romanizedWord);
			lessonWord.setMeaning(meaning);

			lessonWordService.save(lessonWord);
		}

		return lessonWord;
	}
	
	protected LessonWord createLessonWordIfNotExist(String word, String romanizedWord, String meaning, List<LessonWord> reviewLinks) {
		LessonWord lessonWord = lessonWordService.findByWord(word);

		if (lessonWord == null) {
			lessonWord = new LessonWord();

			lessonWord.setWord(word);
			lessonWord.setRomanizedWord(romanizedWord);
			lessonWord.setMeaning(meaning);
			lessonWord.setReviewLinks(reviewLinks);

			lessonWordService.save(lessonWord);
		}

		return lessonWord;
	}

	protected Lesson createLessonIfNotExist(Course course, Integer order, String name, String description,
			List<LessonWord> lessonWords) {
		Lesson lesson = lessonService.findByNameAndCourse(name, course);

		if (lesson == null) {
			lesson = new Lesson();
			lesson.setOrder(order);
			lesson.setName(name);
			lesson.setDescription(description);
			lesson.setLessonWords(lessonWords);
			lesson.setCourse(course);

			lessonService.save(lesson);
		}

		return lesson;
	}

	protected Course createCourseIfNotExist(String name, String description) {
		Course course = courseService.findByName(name);

		if (course == null) {
			course = new Course();
			course.setName(name);
			course.setDescription(name);

			courseService.save(course);
		}

		return course;
	}

	protected Role createRoleIfNotExists(String roleName) {
		Role role = roleService.findOne(roleName);

		if (role == null) {
			role = new Role(roleName);
			roleService.save(role);
			logger.info("Role created: " + roleName);
		}

		return role;
	}

	protected void createUserIfNotExists(String firstName, String lastName, String email, String password,
			Role... roles) {
		User user = new User();
		user.setFirstname(firstName);
		user.setLastname(lastName);
		user.setPassword(password);
		user.setRoles(Arrays.asList(roles));
		user.setEmail(email);
		user.setEnabled(true);
		user.setStatus(UserStatus.STATUS_APPROVED);

		if (!userService.exists(user)) {
			logger.info("User created: " + email);
			userService.save(user);
		}
	}

	protected void createCourses() {
		Course hiragana = createCourseIfNotExist("Hiragana", "Curso de Hiragana");

		HashMap<String, LessonWord> mapSeion = createHiraganaSeion();

		createLessonIfNotExist(hiragana, 1, "Quem sou eu?", "Pronomes pessoais",
				Arrays.asList(createLessonWordIfNotExist("わたし", "wa-ta-shi", "Eu (formal masculino, normal feminino)"),
						createLessonWordIfNotExist("ぼく", "bo-ku", "Eu (normal masculino)", Arrays.asList(mapSeion.get("ぼ"), mapSeion.get("く"))),
						createLessonWordIfNotExist("かれ", "ka-re", "Ele", Arrays.asList(mapSeion.get("か"), mapSeion.get("れ"))),
						createLessonWordIfNotExist("かのじょ", "ka-no-jyo", "Ela", Arrays.asList(mapSeion.get("か"), mapSeion.get("の"), mapSeion.get("じょ"))),
						createLessonWordIfNotExist("あなた", "a-na-ta", "Você", Arrays.asList(mapSeion.get("あ"), mapSeion.get("な"), mapSeion.get("た")))));

		createLessonIfNotExist(hiragana, 2, "Obrigado e Tchau", "Palavras simples",
				Arrays.asList(createLessonWordIfNotExist("ありがとう", "a-ri-ga-to-u", "Obrigado", Arrays.asList(mapSeion.get("あ"), mapSeion.get("り"), mapSeion.get("が"), mapSeion.get("と"), mapSeion.get("う"))),
						createLessonWordIfNotExist("こんにちは", "ko-n-ni-chi-wa", "Olá", Arrays.asList(mapSeion.get("こ"), mapSeion.get("ん"), mapSeion.get("に"), mapSeion.get("ち"), mapSeion.get("は"))),
						createLessonWordIfNotExist("さよなら", "sa-yo-na-ra", "Tchau", Arrays.asList(mapSeion.get("さ"), mapSeion.get("よ"), mapSeion.get("な"), mapSeion.get("ら")))));
	}

	private HashMap<String, LessonWord> createHiraganaSeion() {
		List<List<String>> seions = Arrays.asList(Arrays.asList("あ", "a"), Arrays.asList("い", "i"), Arrays.asList("う", "u"),
				Arrays.asList("え", "e"), Arrays.asList("お", "o"), Arrays.asList("か", "ka"), Arrays.asList("き", "ki"),
				Arrays.asList("く", "ku"), Arrays.asList("け", "ke"), Arrays.asList("こ", "ko"), Arrays.asList("が", "ga"),
				Arrays.asList("ぎ", "gi"), Arrays.asList("ぐ", "gu"), Arrays.asList("げ", "ge"), Arrays.asList("ご", "go"),
				Arrays.asList("さ", "sa"), Arrays.asList("し", "shi"), Arrays.asList("す", "su"), Arrays.asList("せ", "se"),
				Arrays.asList("そ", "so"), Arrays.asList("ざ", "za"), Arrays.asList("じ", "ji"), Arrays.asList("ず", "zu"),
				Arrays.asList("ぜ", "ze"), Arrays.asList("ぞ", "zo"), Arrays.asList("た", "ta"), Arrays.asList("ち", "ti"),
				Arrays.asList("つ", "tsu"), Arrays.asList("て", "te"), Arrays.asList("と", "to"), Arrays.asList("だ", "da"),
				Arrays.asList("ぢ", "di"), Arrays.asList("づ", "zu"), Arrays.asList("で", "de"), Arrays.asList("ど", "do"),
				Arrays.asList("な", "na"), Arrays.asList("に", "ni"), Arrays.asList("ぬ", "nu"), Arrays.asList("ね", "ne"),
				Arrays.asList("の", "no"), Arrays.asList("は", "ha"), Arrays.asList("ひ", "hi"), Arrays.asList("ふ", "fu"),
				Arrays.asList("へ", "he"), Arrays.asList("ほ", "ho"), Arrays.asList("ば", "ba"), Arrays.asList("び", "bi"),
				Arrays.asList("ぶ", "bu"), Arrays.asList("べ", "be"), Arrays.asList("ぼ", "bo"), Arrays.asList("ぱ", "pa"),
				Arrays.asList("ぴ", "pi"), Arrays.asList("ぷ", "pu"), Arrays.asList("ぺ", "pe"), Arrays.asList("ぽ", "po"),
				Arrays.asList("ま", "ma"), Arrays.asList("み", "mi"), Arrays.asList("む", "mu"), Arrays.asList("め", "me"),
				Arrays.asList("も", "mo"), Arrays.asList("や", "ya"), Arrays.asList("ゆ", "yu"), Arrays.asList("よ", "yo"),
				Arrays.asList("ら", "ra"), Arrays.asList("り", "ri"), Arrays.asList("る", "ru"), Arrays.asList("れ", "re"),
				Arrays.asList("ろ", "ro"), Arrays.asList("わ", "wa"), Arrays.asList("を", "wo"), Arrays.asList("ん", "n"),
				Arrays.asList("じょ", "jyo"));
		
		HashMap<String, LessonWord> lessonWords = new HashMap<>();
		for (List<String> seion : seions) {
			lessonWords.put(seion.get(0), createLessonWordIfNotExist(seion.get(0), seion.get(1), "Um caractere do silabário Hiragana"));
		}
		
		return lessonWords;
	}
}
