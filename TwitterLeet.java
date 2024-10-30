import java.util.*;

class TwitterLeet {

    public static void main(String[] args) {


        //["Twitter","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","getNewsFeed"]
        //[[],[1,5],[1,3],[1,101],[1,13],[1,10],[1,2],[1,94],[1,505],[1,333],[1]]
        Twitter twitter = new Twitter();

        twitter.postTweet(1, 5);
        twitter.postTweet(1, 3);
        twitter.postTweet(1, 101);

        twitter.postTweet(1, 13);
        twitter.postTweet(1, 10);
        twitter.postTweet(1, 2);

        twitter.postTweet(1, 94);
        twitter.postTweet(1, 505);
        twitter.postTweet(1, 333);

        System.out.println("Feed is " + twitter.getNewsFeed(1));

    }

    static class Twitter {

        final HashMap<Integer, HashSet<Integer>> followers = new HashMap<>();
        final PriorityQueue<Tweet> tweets = new PriorityQueue<>(Comparator.comparingInt(a -> -a.id));

        public Twitter() {

        }

        public void postTweet(int userId, int tweetId) {
            Tweet tweet = new Tweet(tweets.size() + 1, userId, tweetId);
            tweets.add(tweet);
        }

        public List<Integer> getNewsFeed(int userId) {
            List<Integer> feed = new ArrayList<>();
            List<Tweet> lookedAtFeeds = new ArrayList<>();
            int max = 10;
            HashSet<Integer> f1 = followers.getOrDefault(userId, new HashSet<>());

            while (!tweets.isEmpty()) {
                Tweet tweet = tweets.poll();
                if (f1.contains(tweet.userId) || tweet.userId == userId) feed.add(tweet.tweetId);

                lookedAtFeeds.add(tweet);
                if (feed.size() == max) break;
            }

            tweets.addAll(lookedAtFeeds);

            return feed;
        }

        public void follow(int followerId, int followeeId) {
            //add follower to following
            HashSet<Integer> f1 = followers.getOrDefault(followerId, new HashSet<>());
            if (f1.add(followeeId)) followers.put(followerId, f1);
        }

        public void unfollow(int followerId, int followeeId) {
            HashSet<Integer> f1 = followers.get(followerId);
            if (f1 == null) return;
            if (f1.remove(followeeId)) followers.put(followerId, f1);
        }

        static class Tweet {

            int id;
            int userId;
            int tweetId;


            public Tweet(int id, int userId, int tweetId) {
                this.id = id;
                this.userId = userId;
                this.tweetId = tweetId;
            }
        }
    }

}
