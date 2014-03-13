package uk.ac.aber.dst1.newsarticleanalysis;

import java.util.ArrayList;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class TaggerVerbs {

	public String taggIT(String theText) {
		// String
		// a="A labourer, named Doyle, has been arrested by the Liverpool police on a charge of having committed a fiendish outrage on his child";
		// String b =
		// "CRIME. CHILD THROWN THROUGH THE WINDOW. A labourer, named Doyle, has been arrested by the Liverpool police on a charge of having committed a fiendish outrage on his child. It is alleged that early on Saturday mcrning he took the infant from bed and deliberately threw it from the bedroom window into the street, death ensuing two hours later. A LOVE TRAGEDY. Having been out of employment for a. very long time, Albert Walter Jarlett, a young groom, at Lambert's-cottages, Surbiton, was naturally somewhat uepressed. He left home on Tuesday to visit his brother, who is a hair- dresser, at 4, Tidal-place, Bermondsey. The brother accompanied him to Waterloo Station, where he caught the midnight train to Surbi- ton. On Wednesday morning Jarlett's dead body was discovered near the west signet box, about a quarter of a, mile from Surbiton Sta- tion, lying in the 6ft. way. On a piece of paper found on the body was written:—I have done this for one, that is, for the girl— the girl I love; and I ha.ve done it for her sake, so good-bye, good-bye to all. At the inquest on Saturday the father said his son had been engaged to be married for four years. There had been no quarrel, so far as lie knew. -";

		MaxentTagger tagger = new MaxentTagger(
				"lib/models/english-left3words-distsim.tagger");
		String tagged = tagger.tagString(theText);

		// System.out.println(tagger.addTag("VBZ"));
		// System.out.println(tagged.replace("_VBN", "").replace("_VBZ",
		// "").replace("_VBD", "").replace("_VBG", ""));

		// replacing the verbs tags with spaces
		String sentence = tagged.replace("_VBN", "").replace("_VBZ", "")
				.replace("_VBD", "").replace("_VBG", "");
		// getting verb array
		return sentence;
	}

	public ArrayList<String> getVerbs(String text) {

		ArrayList<String> verbList = new ArrayList<String>();
		// splitting string into words
		for (String word : text.split(" ")) {
			// System.out.println(word);
			// if words don't have the _ sign they will be verbs and will be
			// added to the array
			if (!word.contains("_")) {
				System.out.println(word);
				verbList.add(word);
			}

		}

		return verbList;

	}

}
