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

package org.projectforge.pluginexamples.ktmemo

import org.projectforge.framework.access.OperationType
import org.projectforge.framework.persistence.api.BaseDao
import org.projectforge.framework.persistence.api.BaseSearchFilter
import org.projectforge.framework.persistence.api.QueryFilter
import org.projectforge.framework.persistence.user.api.ThreadLocalUserContext
import org.projectforge.framework.persistence.user.entities.PFUserDO
import org.springframework.stereotype.Repository

/**
 * This is the base data access object class. Most functionality such as access checking, select, insert, update, save,
 * delete etc. is implemented by the super class.
 *
 * @author Kai Reinhard (k.reinhard@micromata.de)
 */
@Repository
open class KTMemoDao : BaseDao<KTMemoDO>(KTMemoDO::class.java) {
    init {
        userRightId = KTMemoPluginUserRightId.PLUGIN_KT_MEMO

    }
    /**
     * Load only memo's of current logged-in user.
     *
     * @param filter
     * @return
     */
    override fun createQueryFilter(filter: BaseSearchFilter): QueryFilter {
        val queryFilter = super.createQueryFilter(filter)
        val user = PFUserDO()
        user.id = ThreadLocalUserContext.getUserId()
        queryFilter.add(QueryFilter.eq("owner", user))
        return queryFilter
    }

    override fun onSaveOrModify(obj: KTMemoDO) {
        super.onSaveOrModify(obj)
        obj.owner = ThreadLocalUserContext.getUser() // Set always the logged-in user as owner.
    }

    override fun newInstance(): KTMemoDO {
        return KTMemoDO()
    }
}
