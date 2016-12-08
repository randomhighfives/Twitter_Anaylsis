package twitter_app;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class searchTweets {
	
	public static ArrayList<String> tweetList = new ArrayList<String>();
	public static ArrayList<String> FinaltweetList = new ArrayList<String>();

	public static ArrayList<String> theGood = new ArrayList<String>();
	public static ArrayList<String> inconclusive = new ArrayList<String>();
	public static ArrayList<String> theNeutral = new ArrayList<String>();

    
    public static void execute(String input) throws TwitterException{
    	   Twitter twitter = TwitterFactory.getSingleton();
           Query query = new Query(input);	//what to search for; keyword
           query.count(50);
           QueryResult result = twitter.search(query);		//results from the search
           List<Status> tweets = result.getTweets();
           
           for (Status tweet : tweets) {		//puts the tweets into arrayList
               tweetList.add(tweet.getText());
           }     

           tweetList = removeSpecialchars(tweetList);  
           NLP.init();
           
           for(String t : tweetList){
               System.out.println(t + " : " + NLP.findSentiment(t));
               if(NLP.findSentiment(t) <= 55){
            	   inconclusive.add(t);
               }
               else if(NLP.findSentiment(t) <= 160){
            	   theNeutral.add(t);
               }
               else if(NLP.findSentiment(t) >= 161){
            	   theGood.add(t);
               }

           } 
    	
    	
    }
    
    
    public static ArrayList<String> removeSpecialchars(ArrayList<String> tweetList){
		//removes special characters from the twitter feed
		//returns a list of words that don't include special characters
		String words = "";
		ArrayList<String> noSpecials = new ArrayList<String>();
		for(int i = 0; i < tweetList.size(); i++){
				words = tweetList.get(i);
				if(words.startsWith("@") || words.startsWith("http") || words.startsWith("RT")){
					continue;
				}				
				noSpecials.add(words);
		}
		return (noSpecials);
	}
    
    
    public static void arrangeTweets(){
    	
    	
    }
    
}


