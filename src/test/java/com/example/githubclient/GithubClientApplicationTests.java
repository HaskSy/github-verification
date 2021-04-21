package com.example.githubclient;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GithubClientApplicationTests {

	@Test
	public void testProcessFunc() {
		String title1 = "LEETCODE 2022 Added solution for course-schedule";
		String title2 = "LEETCODE 2022 Added solution for squares-of-a-sorted-array";
		String title3 = "LEETCODE 1012 Added solution for valid-palindrome";

		String title4 = "Added My Generator";
		String title5 = "Design";
		String title6 = "LEETCODE 1012";
		String title7 = "Triangle solution 21.11.20";
		String title8 = "LEETCODE 3032 Added solution for sort-list";
		String title9 = "LEETCODE 2022 Added";
		String title10 = "SMTHELSE 2022 Added solution for flatten-nested-list-iterator)";

		Assert.assertTrue(MessageTemplateVerifier.process(title1));
		Assert.assertTrue(MessageTemplateVerifier.process(title2));
		Assert.assertTrue(MessageTemplateVerifier.process(title3));

		Assert.assertFalse(MessageTemplateVerifier.process(title4));
		Assert.assertFalse(MessageTemplateVerifier.process(title5));
		Assert.assertFalse(MessageTemplateVerifier.process(title6));
		Assert.assertFalse(MessageTemplateVerifier.process(title7));
		Assert.assertFalse(MessageTemplateVerifier.process(title8));
		Assert.assertFalse(MessageTemplateVerifier.process(title9));
		Assert.assertFalse(MessageTemplateVerifier.process(title10));

	}

}
