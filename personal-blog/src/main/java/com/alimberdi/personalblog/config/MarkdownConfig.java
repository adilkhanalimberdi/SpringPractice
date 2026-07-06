package com.alimberdi.personalblog.config;

import org.commonmark.Extension;
import org.commonmark.ext.autolink.AutolinkExtension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.image.attributes.ImageAttributesExtension;
import org.commonmark.ext.task.list.items.TaskListItemsExtension;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MarkdownConfig {

	@Bean
	public List<Extension> markdownExtensions() {
		return List.of(
				TablesExtension.create(),
				AutolinkExtension.create(),
				StrikethroughExtension.create(),
				TaskListItemsExtension.create(),
				ImageAttributesExtension.create()

		);
	}

	@Bean
	public Parser markdownParser() {
		return Parser.builder()
				.extensions(markdownExtensions())
				.build();
	}

	@Bean
	public HtmlRenderer htmlRenderer() {
		return HtmlRenderer.builder()
				.extensions(markdownExtensions())
				.build();
	}

}
