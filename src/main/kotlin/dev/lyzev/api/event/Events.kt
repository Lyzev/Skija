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

package dev.lyzev.api.event

import dev.lyzev.api.skia.WrappedBackendRenderTarget
import io.github.humbleui.skija.Canvas
import io.github.humbleui.skija.DirectContext

class EventSkiaInit(val width: Int, val height: Int) : Event

object EventSkiaDraw : Event

class EventSkiaDrawScene(val context: DirectContext, val renderTarget: WrappedBackendRenderTarget, val canvas: Canvas) :
    Event
