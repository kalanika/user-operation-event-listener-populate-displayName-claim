package org.wso2.carbon.custom.user.operation.event.listener;

import org.wso2.carbon.identity.core.AbstractIdentityUserOperationEventListener;
import org.wso2.carbon.user.core.UserStoreException;
import org.wso2.carbon.identity.core.util.IdentityCoreConstants;
import org.wso2.carbon.user.core.UserStoreManager;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import static org.wso2.carbon.custom.user.operation.event.listener.constants.Constants.DISPLAY_NAME_CLAIM;
import static org.wso2.carbon.custom.user.operation.event.listener.constants.Constants.EMAIL_ADDRESS_CLAIM;
import static org.wso2.carbon.custom.user.operation.event.listener.constants.Constants.GIVEN_NAME_CLAIM;
import static org.wso2.carbon.custom.user.operation.event.listener.constants.Constants.LAST_NAME_CLAIM;

public class CustomUserOperationEventListener extends AbstractIdentityUserOperationEventListener {

    @Override
    public int getExecutionOrderId() {

        int orderId = getOrderId();
        if (orderId != IdentityCoreConstants.EVENT_LISTENER_ORDER_ID) {
            return orderId;
        }
        // This listener should be executed before all the other listeners.
        // 0 and 1 are already reserved for audit loggers, hence using 3.
        return 3;
    }

    @Override
    public boolean doPreAddUser(String userName, Object credential, String[] roleList, Map<String, String> claims,
                                String profile, UserStoreManager userStoreManager) throws UserStoreException {

        String givenName = claims.get(GIVEN_NAME_CLAIM);
        String lastName = claims.get(LAST_NAME_CLAIM);
        String emailAddress = claims.get(EMAIL_ADDRESS_CLAIM);

        if (StringUtils.isBlank(claims.get(DISPLAY_NAME_CLAIM))) {

            if (StringUtils.isNotBlank(givenName) || StringUtils.isNotBlank(lastName)) {

                String displayName = StringUtils.stripToEmpty(givenName)
                        + ((StringUtils.isNotBlank(givenName) && StringUtils.isNotBlank(lastName)) ? " " : "")
                        + StringUtils.stripToEmpty(lastName);
                claims.put(DISPLAY_NAME_CLAIM, displayName);
            } else if (StringUtils.isBlank(givenName) && StringUtils.isBlank(lastName) &&
                    StringUtils.isNotBlank(emailAddress)) {
                claims.put(DISPLAY_NAME_CLAIM, emailAddress);
            }
        }

        return true;
    }

}
