package uk.ac.aber.dst1.newsarticleanalysis;


/**
 * @author Diana Silvia Teodorescu
 *
 */

import java.util.ArrayList;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.util.CoreMap;

public class TaggerWords {
	
	/**
	 * Function that extract either verbs r nouns from a paragraph of text
	 * @param theText represents the parameter on which the tagger model will be applied on, it represents the article text
	 * @param option is a parameter used to help with using the function in two different cases: for verbs and for nouns
	 * @return the method returns a list of verbs/nouns in String format for further analysing 
	 */
	public String taggIT(String theText, String option) {
		StringBuilder attributeString = new StringBuilder();

		MaxentTagger tagger = new MaxentTagger(
				"lib/models/english-left3words-distsim.tagger");
		String tagged = tagger.tagString(theText);
		// replacing the verbs tags with spaces

		if (option=="verb"){
			String sentence = tagged.replace("_VBN", "").replace("_VBZ", "")
					.replace("_VBD", "").replace("_VBG", "");

			for (String word : sentence.split(" ")) {
				// if words don't have the _ sign they will be verbs and will be
				// added to the array
				if (!word.contains("_")) {
					attributeString.append(" " + word);
				}

			}
			//System.out.println("nouns before: " + attributeString.toString());


		}



		else if (option =="noun"){
			String sentence = tagged.replace("_NN ", " ").replace("_NNS", "");

			for (String word : sentence.split(" ")) {
				// if words don't have the _ sign they will be nouns and will be
				// added to the array
				if (!word.contains("_")) {
					attributeString.append(" " + word);
				}

			}
			//System.out.println("nouns before: " + attributeString.toString());

		}
		return attributeString.toString();
	}
	/**
	 * Method that transforms the words in a string in their lemma
	 * @param attributeString the string that contains the words needed to be brought to their base form
	 * @return
	 */
	public ArrayList<String> lemmAttributes(String attributeString) {
		//System.out.println("started function "+ attributeString);
		ArrayList<String> attributeList = new ArrayList<String>();

		Properties props = new Properties();

		props.put("annotators", "tokenize, ssplit, pos, lemma");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props, false);

		Annotation document = pipeline.process(attributeString);

		for (CoreMap sentence : document.get(SentencesAnnotation.class)) {
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				// String word = token.get(TextAnnotation.class);
				String lemma = token.get(LemmaAnnotation.class);
				//System.out.println("lemmatized version :" + lemma);
				if ((lemma.matches("[A-Za-z-_]+")) && (lemma.length()>=2)){
					//System.out.println("---clean lemma: " + lemma);
					attributeList.add(lemma);
				}
			}

		}

		return attributeList;
	}

}
