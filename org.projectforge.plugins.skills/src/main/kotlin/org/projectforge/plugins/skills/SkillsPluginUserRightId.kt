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

import org.projectforge.Const
import org.projectforge.continuousdb.UpdateEntry
import org.projectforge.menu.builder.MenuCreator
import org.projectforge.menu.builder.MenuItemDef
import org.projectforge.menu.builder.MenuItemDefId
import org.projectforge.plugins.core.AbstractPlugin
import org.projectforge.plugins.skills.SkillDO
import org.projectforge.web.plugin.PluginWicketRegistrationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Your plugin initialization. Register all your components such as i18n files, data-access object etc.
 *
 * @author Kai Reinhard (k.reinhard@micromata.de)
 */
@Component
class SkillsPlugin : AbstractPlugin() {

    @Autowired
    private lateinit var skillDao: SkillDao

    @Autowired
    private lateinit var menuCreator: MenuCreator

    override fun initialize() {
        skillDao = applicationContext.getBean(SkillDao::class.java)
        // Register it:
        register(ID, SkillDao::class.java, skillDao, "plugins.skills")

        // Define the access management:
        registerRight(MemoRight(accessChecker))

        // All the i18n stuff:
        addResourceBundle(RESOURCE_BUNDLE_NAME)
    }

    fun setMemoDao(memoDao: MemoDao) {
        this.memoDao = memoDao
    }

    override fun getInitializationUpdateEntry(): UpdateEntry? {
        return MemoPluginUpdates.getInitializationUpdateEntry()
    }

    companion object {
        val ID = "skills"

        val RESOURCE_BUNDLE_NAME = "SkillsI18nResources"

        // The order of the entities is important for xml dump and imports as well as for test cases (order for deleting objects at the end of
        // each test).
        // The entities are inserted in ascending order and deleted in descending order.
        private val PERSISTENT_ENTITIES = arrayOf<Class<*>>(SkillDO::class.java)
    }
}