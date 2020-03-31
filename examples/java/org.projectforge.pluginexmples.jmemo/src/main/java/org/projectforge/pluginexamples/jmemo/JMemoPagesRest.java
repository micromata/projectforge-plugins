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

import org.projectforge.framework.persistence.user.api.ThreadLocalUserContext;
import org.projectforge.rest.core.AbstractDOPagesRest;
import org.projectforge.ui.LayoutUtils;
import org.projectforge.ui.UILayout;
import org.projectforge.ui.UITable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${Rest.URL}/ktmemo")
public class JMemoPagesRest extends AbstractDOPagesRest<JMemoDO, JMemoDao> {
    public JMemoPagesRest() {
        super(JMemoDao.class, "plugins.ktmemo.title");
    }

    /**
     * Initializes new memos for adding.
     */
    @Override
    public JMemoDO newBaseDO(HttpServletRequest request) {
        JMemoDO memo = super.newBaseDO(request);
        memo.setOwner(ThreadLocalUserContext.getUser());
        return memo;
    }

    /**
     * LAYOUT List page
     */
    @Override
            public UILayout createListLayout() {
        UILayout layout = super.createListLayout()
                .add(UITable.createUIResultSetTable()
                        .add(lc, "created", "lastUpdate", "subject", "memo"));
        return LayoutUtils.processListPage(layout, this);
    }

    /**
     * LAYOUT Edit page
     */
    @Override
public UILayout createEditLayout(JMemoDO dto, UILayout.UserAccess userAccess)   {
        UILayout layout = super.createEditLayout(dto, userAccess)
                .add(lc, "subject", "memo");
        return LayoutUtils.processEditPage(layout, dto, this);
    }
}
