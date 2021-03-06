/////////////////////////////////////////////////////////////////////////////
//
// Project ProjectForge Community Edition
//         www.projectforge.org
//
// Copyright (C) 2001-2020 Micromata GmbH, Germany (www.micromata.com)
//
// ProjectForge is dual-licensed.
//
// This community edition is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License as published
// by the Free Software Foundation; version 3 of the License.
//
// This community edition is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
// Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, see http://www.gnu.org/licenses/.
//
/////////////////////////////////////////////////////////////////////////////

package org.projectforge.pluginexamples.jmemo;

import org.projectforge.business.user.UserRightAccessCheck;
import org.projectforge.business.user.UserRightCategory;
import org.projectforge.business.user.UserRightValue;
import org.projectforge.framework.access.AccessChecker;
import org.projectforge.framework.access.OperationType;
import org.projectforge.framework.persistence.user.entities.PFUserDO;

import java.util.Objects;

/**
 * Define the access rights. In this example every user has access to memo functionality but only read and write access
 * to own memos.
 *
 * @author Kai Reinhard (k.reinhard@me.de)
 */
public class JMemoRight extends UserRightAccessCheck<JMemoDO> {
    public JMemoRight(AccessChecker accessChecker) {
        super(accessChecker, JMemoPluginUserRightId.PLUGIN_J_MEMO, UserRightCategory.PLUGINS, UserRightValue.TRUE);
    }

    /**
     * @return true if the owner is equals to the logged-in user, otherwise false.
     */
    @Override
    public boolean hasAccess(PFUserDO user, JMemoDO obj, JMemoDO oldObj, OperationType operationType) {
        if (obj == null) {
            return true; // General insert and select access given by default.
        }
        if (oldObj != null) {
            return Objects.equals(user.getId(), oldObj.getOwnerId());
        }
        return Objects.equals(user.getId(), obj.getOwnerId());
    }
}
