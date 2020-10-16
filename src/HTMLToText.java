import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;

import org.jsoup.Jsoup;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.logging.RedwoodConfiguration;

public class HTMLToText {

	//The array contains part of speech like Noun, adverb, verb, adjective
	public static String [] partOfSpeech = {"NN","NNS","NNP","NNPS","RB","RBR","RBS",
			"VB","VBD","VBG","VBN","VBP","JJ","JJR","JJS","FW"};

	public static boolean partofSpeechMatcher(String pos) {
		//Check if the words is a part of speech array which we desire.
		if(Arrays.asList(partOfSpeech).contains(pos))
			return true;
		return false;
	}

	public static void filesFolderOpr(final File folder) throws IOException, URISyntaxException {
		//Read the html files
		for (final File fileEntry : folder.listFiles()) {
			//System.out.println(fileEntry.getName()+" ---------> Completed");
			String path = "W3C_Web_Pages/"+fileEntry.getName();
		    Properties props = new Properties();
		    props.setProperty("annotators", "tokenize, ssplit, pos,lemma");
		    RedwoodConfiguration.current().clear().apply();
		    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
			StringBuilder contentBuilder = new StringBuilder();
			try {
			    BufferedReader in = new BufferedReader(new FileReader(path));
			    String str;
			    while ((str = in.readLine()) != null) {
			        contentBuilder.append(str);
			    }
			    in.close();
			} catch (IOException e) {
			}
			String content = contentBuilder.toString();
			String plainText= Jsoup.parse(content).text();

			//Text2 contains NLP tokenized certain part of speech words.
			Files.createDirectories(Paths.get("W3C_Web_Pages/Text2"));

			//Text3 contains the plain text representation of the html file
			Files.createDirectories(Paths.get("W3C_Web_Pages/Text3"));
            File detailsTextFile = new File("W3C_Web_Pages/Text2/"+fileEntry.getName().replace(".htm",".txt"));
            File newTextFile = new File("W3C_Web_Pages/Text3/"+fileEntry.getName().replace(".htm",".txt"));
            BufferedWriter detailsWriter = new BufferedWriter(new FileWriter(detailsTextFile));
            detailsWriter.write(plainText);
            detailsWriter.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(newTextFile));

            String []textData = plainText.split(" ");
            for(String textD : textData ) {
            	if(textD.length()>0) {
            		CoreDocument coreDocument = new CoreDocument(textD);
            		pipeline.annotate(coreDocument);
            		if(!coreDocument.tokens().isEmpty()) {
            			CoreLabel coreLabelList =  coreDocument.tokens().get(0);
                		String pos = coreLabelList.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                		if(partofSpeechMatcher(pos)) {
                		       writer.append(textD+ " ");
                		}
            		}

            	}
            }
            writer.close();
	}
	}

	public static void main(String[] args) throws IOException, URISyntaxException {
		// TODO Auto-generated method stub
		File folder = new File("W3C_Web_Pages/");
		System.out.println("---------------- NLP in Progress -------------------");
		filesFolderOpr(folder);
		System.out.println("---------------- Completed -------------------");



	}//end of public main()
}
