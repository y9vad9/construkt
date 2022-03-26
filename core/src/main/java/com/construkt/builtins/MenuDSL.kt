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
import com.construkt.annotation.ConstruktDSL
import com.construkt.annotation.InternalConstruktApi
import com.construkt.annotation.Unsupported

@DslMarker
public annotation class MenuDSLMarker

@InternalConstruktApi
public fun Menu.menuDsl(): MenuDSL = MenuDSLImpl(this)

public interface MenuDSL : MenuGroupDSL {
    public fun group(id: Int, block: MenuGroupDSL.() -> Unit)
}

@MenuDSLMarker
public interface MenuGroupDSL {
    public fun submenu(id: Int = Menu.NONE, title: String, block: SubMenuDSL.() -> Unit)
    public fun submenu(id: Int = Menu.NONE, titleRes: Int, block: SubMenuDSL.() -> Unit)
    public fun item(text: CharSequence, id: Int = Menu.NONE): MenuItem
    public fun item(textRes: Int, id: Int = Menu.NONE): MenuItem
}

public interface SubMenuDSL : MenuDSL {
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
    override fun submenu(id: Int, title: String, block: SubMenuDSL.() -> Unit) {
        throw UnsupportedOperationException("Sub menu does not support submenus.")
    }

    /**
     * Submenu does not support submenus.
     */
    @Unsupported
    @Throws(UnsupportedOperationException::class)
    override fun submenu(id: Int, titleRes: Int, block: SubMenuDSL.() -> Unit) {
        throw UnsupportedOperationException("Sub menu does not support submenus.")
    }
}

internal class MenuDSLImpl(private val menu: Menu) : MenuDSL {
    override fun item(text: CharSequence, id: Int): MenuItem {
        return menu.add(text)
    }

    override fun item(textRes: Int, id: Int): MenuItem {
        return menu.add(textRes)
    }

    override fun submenu(id: Int, title: String, block: SubMenuDSL.() -> Unit) {
        val submenu = menu.addSubMenu(Menu.NONE, id, Menu.NONE, title)
        SubMenuDSLImpl(submenu).apply(block)
    }

    override fun submenu(id: Int, titleRes: Int, block: SubMenuDSL.() -> Unit) {
        val submenu = menu.addSubMenu(Menu.NONE, id, Menu.NONE, titleRes)
        SubMenuDSLImpl(submenu).apply(block)
    }

    override fun group(id: Int, block: MenuGroupDSL.() -> Unit) {
        MenuGroupDSLImpl(id, menu).apply(block)
    }
}

internal class SubMenuDSLImpl(private val menu: SubMenu) : SubMenuDSL {
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
        this@SubMenuDSLImpl.menu.setHeaderView(RootViewScope(context, lifecycleOwner).apply(block).origin)
    }

    override fun icon(resource: Int) {
        menu.setIcon(resource)
    }

    override fun icon(resource: Drawable) {
        menu.setIcon(resource)
    }

    override fun group(id: Int, block: MenuGroupDSL.() -> Unit) {
        MenuGroupDSLImpl(id, menu).apply(block)
    }
}

internal class MenuGroupDSLImpl(private val id: Int, private val menu: Menu) : MenuGroupDSL {
    override fun item(text: CharSequence, id: Int): MenuItem {
        return menu.add(this@MenuGroupDSLImpl.id, id, Menu.NONE, text)
    }

    override fun item(textRes: Int, id: Int): MenuItem {
        return menu.add(this@MenuGroupDSLImpl.id, id, Menu.NONE, textRes)
    }

    override fun submenu(id: Int, titleRes: Int, block: SubMenuDSL.() -> Unit) {
        val submenu = menu.addSubMenu(this.id, id, Menu.NONE, titleRes)
        SubMenuDSLImpl(submenu).apply(block)
    }

    override fun submenu(id: Int, title: String, block: SubMenuDSL.() -> Unit) {
        val submenu = menu.addSubMenu(this.id, id, Menu.NONE, title)
        SubMenuDSLImpl(submenu).apply(block)
    }
}