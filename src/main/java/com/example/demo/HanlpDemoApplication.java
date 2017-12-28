package com.example.demo;

import com.example.demo.config.HanlpInitConfig;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.common.Term;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class HanlpDemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(HanlpDemoApplication.class, args);

		initParticipleCustomDictionary(run);
	}

	private static void initParticipleCustomDictionary(ConfigurableApplicationContext run) {
		// 分词如果分不出这些词 添加自定义词库  就可以分出来
		List<String> customDictionary = Arrays.asList("火影忍者","死神","海贼王");
		for (String str : customDictionary) {
			CustomDictionary.insert(str, "n 1024");
		}

		// 分词中如果有这些词 去掉
		List<String> unusedWords = Arrays.asList("动漫","动漫电影");
		for (String unusedWord : unusedWords) {
			CoreStopWordDictionary.add(unusedWord);
		}

		//词性过滤
		CoreStopWordDictionary.FILTER = (Term term) -> {
			if (CoreStopWordDictionary.contains(term.word) || term.length() <= 1) {
				return false;
			}
			// 词性过滤
			return (term.nature == Nature.n || term.nature == Nature.nz || term.nature == Nature.nis
					|| term.nature == Nature.nt || term.nature == Nature.nr || term.nature == Nature.v
					|| term.nature == Nature.vi || term.nature == Nature.d || term.nature == Nature.vn
					|| term.nature == Nature.nth || term.nature == Nature.nf) ? true : false;
		};

		List<Term> termList = HanlpInitConfig.getSegment().seg("动漫包括很多,火影忍者死神海贼王");
		termList.stream().filter(CoreStopWordDictionary::shouldInclude).collect(Collectors.toList()).forEach(System.out::println);
	}
}
