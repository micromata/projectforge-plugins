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

package org.projectforge.pluginexamples.kotlindemo

import mu.KotlinLogging
import org.flywaydb.core.Flyway
import org.projectforge.Const
import org.projectforge.menu.builder.MenuCreator
import org.projectforge.menu.builder.MenuItemDef
import org.projectforge.menu.builder.MenuItemDefId
import org.projectforge.plugins.core.AbstractPlugin
import org.springframework.beans.factory.annotation.Autowired
import javax.sql.DataSource

private val log = KotlinLogging.logger {}

/**
 * Your plugin initialization. Register all your components such as i18n files, data-access object etc.
 *
 * @author Kai Reinhard (k.reinhard@micromata.de)
 */
class MemoPlugin : AbstractPlugin("ktmemo", "Kotlin Memo example", "Example plugin in Kotlin.") {

    @Autowired
    private lateinit var KTMemoDao: KTMemoDao

    @Autowired
    private lateinit var dataSource: DataSource

    @Autowired
    private lateinit var menuCreator: MenuCreator

    override fun initialize() {
        // Create the Flyway instance and point it to the database
        val flywayLocation = "${this::class.java.packageName}/flyway/dbmigration"
        val flywayLocations = "classpath:flyway/${id}/init/common,classpath:flyway/${id}/migrate/common,classpath:flyway/${id}/init/{vendor},classpath:flyway/${id}/migrate/{vendor},classpath:$flywayLocation"
        log.info("Initializing flyway with locations: $flywayLocations")
        val flyway = Flyway.configure()
                .dataSource(dataSource)
                .table("t_flyway_${id}_schema_version")
                .locations(flywayLocations)
                .baselineOnMigrate(true)
                .load()
        flyway.migrate()

        // Register it:
        register(id, KTMemoDao::class.java, KTMemoDao, "plugins.ktmemo")

        // Define the access management:
        registerRight(KTMemoRight(accessChecker))

        menuCreator.add(MenuItemDefId.MISC, MenuItemDef(info.id, "plugins.ktmemo.menu", "${Const.REACT_APP_PATH}ktmemo"));

        // All the i18n stuff:
        addResourceBundle(RESOURCE_BUNDLE_NAME)
    }

    companion object {
        const val RESOURCE_BUNDLE_NAME = "KTMemoI18nResources"
    }
}
