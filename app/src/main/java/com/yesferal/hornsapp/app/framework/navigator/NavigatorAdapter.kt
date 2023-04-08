/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.navigator

import androidx.fragment.app.Fragment
import com.yesferal.hornsapp.core.domain.navigator.Navigator

interface FragmentNavigator {
    fun navigate(view: Fragment, navigator: Navigator)
}
