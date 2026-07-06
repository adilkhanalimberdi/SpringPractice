package com.alimberdi.personalblog.controller;

import com.alimberdi.personalblog.dto.ArticleCreateRequest;
import com.alimberdi.personalblog.dto.ArticleUpdateRequest;
import com.alimberdi.personalblog.entity.Article;
import com.alimberdi.personalblog.service.ArticleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class BlogController {

	private final ArticleService articleService;
	private final Parser markdownParser;
	private final HtmlRenderer htmlRenderer;

	@GetMapping
	public String index() {
		return "redirect:/home";
	}

	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute("articles", articleService.getAllSummary());
		return "index";
	}

	@GetMapping("/article/{id}")
	public String viewArticle(@PathVariable Long id, Model model) {
		Article article = articleService.getById(id);

		Node parsed = markdownParser.parse(article.getContent());
		String htmlContent = htmlRenderer.render(parsed);

		model.addAttribute("article", article);
		model.addAttribute("htmlContent", htmlContent);
		return "article";
	}

	@GetMapping("/admin/")
	public String admin() {
		return "redirect:/admin";
	}

	@GetMapping("/admin")
	public String adminPage(Model model) {
		model.addAttribute("articles", articleService.getAllShort());
		return "admin";
	}

	@GetMapping("/new")
	public String newArticlePage(Model model) {
		if (!model.containsAttribute("createRequest")) {
			model.addAttribute("createRequest", new ArticleCreateRequest(null, LocalDate.now(), null));
		}
		return "create-article";
	}

	@PostMapping("/new")
	public String newArticle(@ModelAttribute ArticleCreateRequest request) {
		Article article = articleService.create(request);
		return "redirect:/article/" + article.getId();
	}

	@GetMapping("/edit/{id}")
	public String editArticlePage(@PathVariable Long id, Model model) {
		model.addAttribute("article", articleService.getById(id));
		return "edit-article";
	}

	@PostMapping("/edit/{id}")
	public String editArticle(
			@PathVariable Long id,
			@ModelAttribute ArticleUpdateRequest request
	) {
		articleService.update(id, request);
		return "redirect:/article/" + id;
	}

	@PostMapping("/delete/{id}")
	public String deleteArticle(@PathVariable Long id, @RequestParam(defaultValue = "/") String redirectUrl) {
		articleService.delete(id);
		return "redirect:" + redirectUrl;
	}

}
