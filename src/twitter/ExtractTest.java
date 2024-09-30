package twitter;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.*;


class ExtractTest {
    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);

    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));

        assertEquals(d1, timespan.getStart());
        assertEquals(d2, timespan.getEnd());
    }
    @Test
    public void testGetTimespanSingleTweet() {
        Timespan timespan =
                Extract.getTimespan(Collections.singletonList(tweet1));
        assertEquals(d1, timespan.getStart());
        assertEquals(d1, timespan.getEnd());
    }

    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));

        assertTrue(mentionedUsers.isEmpty());
    }

    @Test
    public void testGetMentionedUsersOneMention() {
        Tweet tweet = new Tweet(3, "john", "Hey @alice, how are you?", Instant.parse("2016-02-17T12:00:00Z"));
        Set<String> mentionedUsers = Extract.getMentionedUsers(Collections.singletonList(tweet));
        Set<String> expectedMentions = new HashSet<>(Arrays.asList("alice"));
        assertEquals(expectedMentions, mentionedUsers);
    }
    @Test
    public void testGetMentionedUsersMultipleMentions() {
        Tweet tweet1 = new Tweet(4, "alice", "Hi @bob!", Instant.parse("2016-02-17T13:00:00Z"));
        Tweet tweet2 = new Tweet(5, "bob", "Hello @alice!", Instant.parse("2016-02-17T14:00:00Z"));
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet2));
        Set<String> expectedMentions = new HashSet<>(Arrays.asList("alice", "bob"));
        assertEquals(expectedMentions, mentionedUsers);
    }
}