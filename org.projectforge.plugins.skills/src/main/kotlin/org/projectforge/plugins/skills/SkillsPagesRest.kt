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

import org.projectforge.rest.config.Rest
import org.projectforge.rest.core.AbstractDOPagesRest
import org.projectforge.ui.LayoutUtils
import org.projectforge.ui.UILayout
import org.projectforge.ui.UITable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("${Rest.URL}/Skill")
class SkillPagesRest() : AbstractDOPagesRest<SkillDO, SkillDao>(SkillDao::class.java, "plugins.Skill.title") {
    /**
     * Initializes new Skills for adding.
     */
    override fun newBaseDO(request: HttpServletRequest?): SkillDO {
        val Skill = super.newBaseDO(request)
        return Skill
    }

    /**
     * LAYOUT List page
     */
    override fun createListLayout(): UILayout {
        val layout = super.createListLayout()
                .add(UITable.createUIResultSetTable()
                        .add(lc, "skill", "comment"))
        return LayoutUtils.processListPage(layout, this)
    }

    /**
     * LAYOUT Edit page
     */
    override fun createEditLayout(dto: SkillDO, userAccess: UILayout.UserAccess): UILayout {
        val layout = super.createEditLayout(dto, userAccess)
                .add(lc, "skill", "comment")
        return LayoutUtils.processEditPage(layout, dto, this)
    }
}
