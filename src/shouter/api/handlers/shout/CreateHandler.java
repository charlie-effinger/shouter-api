/*
 * $Id$
 * $HeadURL$
 */
package shouter.api.handlers.shout;

import shouter.api.ApiConstants;
import shouter.api.beans.ApiError;
import shouter.api.beans.Shout;
import shouter.api.handlers.BaseApiHandler;
import shouter.api.utils.DataUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * Handler that allows for a new shout to be posted in the database. All relevant shouts are
 * returned.
 *
 * @author Charles Effinger (charles.effinger@cbsinteractive.com)
 * @version $Revision$, $LastChangedDate$
 */


public class CreateHandler extends BaseApiHandler {

    private final String message;
    private final Double longitude;
    private final Double latitude;
    private final String phoneId;

    public CreateHandler(HttpServletRequest request) {
        super(request);

        this.responseString = "shouts";

        this.message = DataUtil.formatParameter(request, ApiConstants.PARAM_MESSAGE);
        this.latitude = Double.parseDouble(DataUtil.formatParameter(request, ApiConstants.PARAM_LATITUDE));
        this.longitude = Double.parseDouble(DataUtil.formatParameter(request, ApiConstants.PARAM_LONGITUDE));
        this.phoneId = DataUtil.formatParameter(request, ApiConstants.PARAM_PHONE_ID);

    }

    @Override
    protected boolean validateParameters() {
        if (latitude != null) {
            if (!DataUtil.isDegrees(latitude)) {
                errors.add(new ApiError("400", ApiConstants.INVALID_PREFIX + ApiConstants.PARAM_LATITUDE,
                        "Invalid latitude. Please provide a valid latitude."));
            }
        } else {
            errors.add(new ApiError("400", ApiConstants.MISSING_PREFIX + ApiConstants.PARAM_LATITUDE,
                    "Missing latitude. Please provide a latitude."));
        }

        if (longitude != null) {
            if (!DataUtil.isDegrees(longitude)) {
                errors.add(new ApiError("400", ApiConstants.INVALID_PREFIX + ApiConstants.PARAM_LONGITUDE,
                        "Invalid longitude. Please provide a valid longitude."));
            }
        } else {
            errors.add(new ApiError("400", ApiConstants.MISSING_PREFIX + ApiConstants.PARAM_LONGITUDE,
                    "Missing longitude. Please provide a longitude"));
        }

        if (!DataUtil.isEmpty(message)) {
            if (!DataUtil.is256characters(message)) {
                errors.add(new ApiError("400", ApiConstants.INVALID_PREFIX + ApiConstants.PARAM_MESSAGE,
                        "Invalid message. Please provide that is 256 characters or less."));
            }
        } else {
            errors.add(new ApiError("400", ApiConstants.MISSING_PREFIX + ApiConstants.PARAM_MESSAGE,
                    "Missing message. Please provide a message."));
        }

        return super.validateParameters();
    }

    @Override
    protected void performRequest() {
        Shout shoutToPost = new Shout();
        shoutToPost.setLatitude(latitude);
        shoutToPost.setLongitude(longitude);
        shoutToPost.setMessage(message);
        shoutToPost.setPhoneId(phoneId);
        shoutToPost.setTimestamp(System.currentTimeMillis() / 1000L);
        Collection<Shout> shouts = awsDao.postShout(shoutToPost);

        responseObjects.addAll(shouts);
    }
}
