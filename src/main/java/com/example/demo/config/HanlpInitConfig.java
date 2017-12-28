package com.example.demo.config;
/**
* @author shanchenyang
* @time 创建时间：2017年9月28日 下午3:38:51
* 
*/

import org.springframework.stereotype.Component;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;

@Component
public class HanlpInitConfig {

	private static Segment tokenizer;
	static {
		tokenizer = HanLP.newSegment()
				// 启用自定义词典
				.enableCustomDictionary(true)
				// 开启词性标注
				.enablePartOfSpeechTagging(true)
				// 启用人名识别分词
				.enableNameRecognize(true)
				// 启用地名识别分词
				.enablePlaceRecognize(true);
	}
	private HanlpInitConfig() {
	}

	public static Segment getSegment() {
		return tokenizer;
	}

}
