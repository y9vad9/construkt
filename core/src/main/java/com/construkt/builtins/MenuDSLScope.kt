package com.construkt.builtins

import android.graphics.drawable.Drawable
import android.view.Menu
import android.view.MenuItem
import android.view.SubMenu
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.construkt.RootViewScope
import com.construkt.ViewGroupScope
import com.construkt.ViewScope
import com.construkt.annotation.InternalConstruktApi
import com.construkt.annotation.Unsupported

@DslMarker
public annotation class MenuDSL

@InternalConstruktApi
public fun Menu.menuDsl(): MenuDSLScope = MenuDSLImplScope(this)

/**
 * DSL Scope for functions that have [android.view.Menu] return type.
 * It will be automatically implemented by code generation.
 * ### Example
 * ```kotlin
 * bottomNavigationBar(frameLayoutParams().maxWidth()) {
 *  menu {
 *      item("Home")
 *      item(R.string.about)
 *      group("extra") {
 *          item("Quit app")
 *      }
 *  }
 * }
 * ```
 * @see [issue](https://github.com/y9vad9/construkt/issues/1)
 * @see [developer.android.com](https://developer.android.com/reference/android/view/Menu).
 */
public interface MenuDSLScope : MenuGroupDSLScope {
    public fun group(id: Int, block: MenuGroupDSLScope.() -> Unit)
}

@MenuDSL
public interface MenuGroupDSLScope {
    public fun submenu(id: Int = Menu.NONE, title: String, block: SubMenuDSLScope.() -> Unit)
    public fun submenu(id: Int = Menu.NONE, titleRes: Int, block: SubMenuDSLScope.() -> Unit)
    public fun item(text: CharSequence, id: Int = Menu.NONE): MenuItem
    public fun item(textRes: Int, id: Int = Menu.NONE): MenuItem
}

public fun MenuGroupDSLScope.item(text: CharSequence, id: Int = Menu.NONE, block: MenuItem.() -> Unit) {
    item(text, id).apply(block)
}

public fun MenuGroupDSLScope.item(textRes: Int, id: Int = Menu.NONE, block: MenuItem.() -> Unit) {
    item(textRes, id).apply(block)
}

public interface SubMenuDSLScope : MenuDSLScope {
    public fun headerIcon(@DrawableRes resource: Int)
    public fun headerIcon(drawable: Drawable)
    public fun headerTitle(@StringRes resource: Int)
    public fun headerTitle(title: String)
    public fun ViewScope<*>.header(block: ViewGroupScope<*>.() -> Unit)
    public fun icon(@DrawableRes resource: Int)
    public fun icon(resource: Drawable)

    /**
     * Submenu does not support submenus.
     */
    @Unsupported
    @Throws(UnsupportedOperationException::class)
    override fun submenu(id: Int, title: String, block: SubMenuDSLScope.() -> Unit) {
        throw UnsupportedOperationException("Sub menu does not support submenus.")
    }

    /**
     * Submenu does not support submenus.
     */
    @Unsupported
    @Throws(UnsupportedOperationException::class)
    override fun submenu(id: Int, titleRes: Int, block: SubMenuDSLScope.() -> Unit) {
        throw UnsupportedOperationException("Sub menu does not support submenus.")
    }
}

internal class MenuDSLImplScope(private val menu: Menu) : MenuDSLScope {
    override fun item(text: CharSequence, id: Int): MenuItem {
        return menu.add(Menu.NONE, id, Menu.NONE, text)
    }

    override fun item(textRes: Int, id: Int): MenuItem {
        return menu.add(Menu.NONE, id, Menu.NONE, textRes)
    }

    override fun submenu(id: Int, title: String, block: SubMenuDSLScope.() -> Unit) {
        val submenu = menu.addSubMenu(Menu.NONE, id, Menu.NONE, title)
        SubMenuDSLScopeImpl(submenu).apply(block)
    }

    override fun submenu(id: Int, titleRes: Int, block: SubMenuDSLScope.() -> Unit) {
        val submenu = menu.addSubMenu(Menu.NONE, id, Menu.NONE, titleRes)
        SubMenuDSLScopeImpl(submenu).apply(block)
    }

    override fun group(id: Int, block: MenuGroupDSLScope.() -> Unit) {
        MenuGroupDSLScopeImpl(id, menu).apply(block)
    }
}

internal class SubMenuDSLScopeImpl(private val menu: SubMenu) : SubMenuDSLScope {
    override fun item(text: CharSequence, id: Int): MenuItem {
        return menu.add(Menu.NONE, id, Menu.NONE, text)
    }

    override fun item(textRes: Int, id: Int): MenuItem {
        return menu.add(Menu.NONE, id, Menu.NONE, textRes)
    }

    override fun headerIcon(resource: Int) {
        menu.setHeaderIcon(resource)
    }

    override fun headerIcon(drawable: Drawable) {
        menu.setHeaderIcon(drawable)
    }

    override fun headerTitle(resource: Int) {
        menu.setHeaderTitle(resource)
    }

    override fun headerTitle(title: String) {
        menu.setHeaderTitle(title)
    }

    override fun ViewScope<*>.header(block: ViewGroupScope<*>.() -> Unit) {
        this@SubMenuDSLScopeImpl.menu.setHeaderView(RootViewScope(context, lifecycleOwner).apply(block).origin)
    }

    override fun icon(resource: Int) {
        menu.setIcon(resource)
    }

    override fun icon(resource: Drawable) {
        menu.setIcon(resource)
    }

    override fun group(id: Int, block: MenuGroupDSLScope.() -> Unit) {
        MenuGroupDSLScopeImpl(id, menu).apply(block)
    }
}

internal class MenuGroupDSLScopeImpl(private val id: Int, private val menu: Menu) : MenuGroupDSLScope {
    override fun item(text: CharSequence, id: Int): MenuItem {
        return menu.add(this@MenuGroupDSLScopeImpl.id, id, Menu.NONE, text)
    }

    override fun item(textRes: Int, id: Int): MenuItem {
        return menu.add(this@MenuGroupDSLScopeImpl.id, id, Menu.NONE, textRes)
    }

    override fun submenu(id: Int, titleRes: Int, block: SubMenuDSLScope.() -> Unit) {
        val submenu = menu.addSubMenu(this.id, id, Menu.NONE, titleRes)
        SubMenuDSLScopeImpl(submenu).apply(block)
    }

    override fun submenu(id: Int, title: String, block: SubMenuDSLScope.() -> Unit) {
        val submenu = menu.addSubMenu(this.id, id, Menu.NONE, title)
        SubMenuDSLScopeImpl(submenu).apply(block)
    }
}