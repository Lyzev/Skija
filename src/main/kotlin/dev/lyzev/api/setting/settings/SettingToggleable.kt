/*
 * This file is part of https://github.com/Lyzev/Skija.
 *
 * Copyright (c) 2025. Lyzev
 *
 * Skija is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, version 3 of the License, or
 * (at your option) any later version.
 *
 * Skija is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Skija. If not, see <https://www.gnu.org/licenses/>.
 */

package dev.lyzev.api.setting.settings

import dev.lyzev.api.setting.Setting
import kotlin.reflect.KClass

class SettingToggleable(
    container: KClass<*>,
    name: String,
    desc: String?,
    value: Boolean,
    hidden: () -> Boolean,
    onChange: (Boolean) -> Unit
) : Setting<Boolean>(container, name, desc, value, hidden, onChange)

fun Any.toggleable(
    name: String,
    desc: String? = null,
    value: Boolean = false,
    hidden: () -> Boolean = { false },
    onChange: (Boolean) -> Unit = {}
) = SettingToggleable(this::class, name, desc, value, hidden, onChange)
