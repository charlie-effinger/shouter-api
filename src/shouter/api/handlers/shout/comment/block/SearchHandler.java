/*
 * $Id$
 * $HeadURL$
 */
package shouter.api.handlers.shout.comment.block;

import shouter.api.ApiConstants;
import shouter.api.beans.ApiError;
import shouter.api.beans.BlockedComment;
import shouter.api.beans.BlockedShout;
import shouter.api.handlers.BaseApiHandler;
import shouter.api.utils.DataUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * TODO: Enter class description...
 *
 * @author chuck (charlie.effinger@gmail.com)
 * @version $Revision$ $LastChangedDate$
 */
public class SearchHandler extends BaseApiHandler {

    private String userName;

    public SearchHandler(HttpServletRequest request) {
        super(request);

        this.responseString = "comments";
        this.userName = DataUtil.formatParameter(request, ApiConstants.PARAM_USER_NAME);
    }

    @Override
    protected boolean validateParameters() {
        // validate the latitude
        if (DataUtil.isEmpty(userName)) {
            errors.add(new ApiError(null, null, null));
        }

        return super.validateParameters();
    }

    @Override
    protected void performRequest() {
        // retrieve and post the shouts
        try {
            Collection<BlockedComment> comments = awsDao.getBlockedComments(userName);
            responseObjects.addAll(comments);
        } catch (Exception e) {
            this.responseString = "errors";
            responseObjects.add(new ApiError(null, null, null));
        }

    }
}
