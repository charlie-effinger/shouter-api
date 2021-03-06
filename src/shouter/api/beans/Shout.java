/*
 * $Id$
 * $HeadURL$
 */
package shouter.api.beans;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import shouter.api.dao.AwsConstants;

import java.util.Collection;

/**
 * The Shout object bean. Mapped from the Dynamo DB.
 *
 * @author Charles Effinger (charles.effinger@cbsinteractive.com)
 * @version $Revision$, $LastChangedDate$
 */

@DynamoDBTable(tableName = AwsConstants.SHOUT_TABLE)
public class Shout implements Comparable {

    private String id;

    private Long timestamp;

    private String userName;

    protected String message;

    private Double latitude;

    private Double longitude;

    private int numLikes;

    private int numComments;

    private Long expirationTimestamp;

    private Collection<Comment> comments;

    private Boolean liked;

    public Shout() {
        this(null, null);
    }

    public Shout(String userName, String message) {
        this(userName, message, null, null);
    }

    public Shout(String userName, String message, Double latitude, Double longitude) {
        this.userName = userName;
        this.message = message;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    @DynamoDBHashKey(attributeName = AwsConstants.ID)
    @DynamoDBAutoGeneratedKey
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    @DynamoDBAttribute(attributeName = AwsConstants.USER_NAME)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @DynamoDBAttribute(attributeName = AwsConstants.TIMESTAMP)
    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @DynamoDBAttribute(attributeName = AwsConstants.MESSAGE)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @DynamoDBAttribute(attributeName = AwsConstants.LATITUDE)
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @DynamoDBAttribute(attributeName = AwsConstants.LONGITUDE)
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @DynamoDBIgnore
    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    @DynamoDBAttribute(attributeName = AwsConstants.NUM_LIKES)
    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    @DynamoDBAttribute(attributeName = AwsConstants.NUM_COMMENTS)
    public int getNumComments() {
        return numComments;
    }

    public void setNumComments(int numComments) {
        this.numComments = numComments;
    }

    @DynamoDBAttribute(attributeName = AwsConstants.EXPIRATION_TIMESTAMP)
    public Long getExpirationTimestamp() {
        return expirationTimestamp;
    }

    public void setExpirationTimestamp(Long expirationTimestamp) {
        this.expirationTimestamp = expirationTimestamp;
    }

    @DynamoDBIgnore
    public Boolean getLiked() {
        if (liked == null) {
            return false;
        } else {
            return liked;
        }
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    @Override
    public String toString() {
        return "Shout [id=" + id + ", message=" + message + ", timestamp=" + timestamp + ", latitude=" + latitude +
                ", longitude=" + longitude + ", userName=" + userName + ", numLikes=" + numLikes + ", numComments="
                + numComments + ", liked=" + liked + "]";
    }

    @Override
    public int compareTo(Object o) {
        return ((Shout) o).getTimestamp().compareTo(this.getTimestamp());
    }
}
