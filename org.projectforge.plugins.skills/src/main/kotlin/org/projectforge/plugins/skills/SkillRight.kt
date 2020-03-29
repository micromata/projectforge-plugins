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

package org.projectforge.plugins.skills

import org.hibernate.search.annotations.Indexed
import org.projectforge.business.user.UserRightAccessCheck
import org.projectforge.business.user.UserRightCategory
import org.projectforge.business.user.UserRightValue
import org.projectforge.framework.access.AccessChecker
import org.projectforge.framework.access.OperationType
import org.projectforge.framework.persistence.user.entities.PFUserDO
import javax.persistence.Entity
import javax.persistence.Table

/**
 * Beside entries of debitors and creditors invoices additional entries (for accommodation, taxes, planned salaries,
 * assurance etc.) are important for a complete liquidity planning.
 *
 * @author Kai Reinhard (k.reinhard@micromata.de)
 */
@Entity
@Indexed
@Table(name = "T_PLUGIN_SKILL", indexes = [javax.persistence.Index(name = "idx_fk_t_plugin_liqui_entry_tenant_id", columnList = "tenant_id")])
open class SkillRight(accessChecker: AccessChecker)
    : UserRightAccessCheck<SkillDO>(accessChecker, SkillsPluginUserRightId.PLUGIN_SKILLS, UserRightCategory.PLUGINS, UserRightValue.TRUE) {

    /**
     * @return true if the owner is equals to the logged-in user, otherwise false.
     */
    override fun hasAccess(user: PFUserDO, obj: SkillDO?, oldObj: SkillDO?,
                  operationType: OperationType?): Boolean {
        val memo: SkillDO = (if (oldObj != null) oldObj else obj)
                ?: return true // General insert and select access given by default.
        return true
    }
}
