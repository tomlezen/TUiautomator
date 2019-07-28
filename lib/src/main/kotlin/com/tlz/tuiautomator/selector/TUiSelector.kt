package com.tlz.tuiautomator.selector

import androidx.annotation.Keep
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

/**
 * Created by Tomlezen.
 * Date: 2019-07-27.
 * Time: 09:55.
 */
@Keep
class TUiSelector internal constructor(
    var text: String? = null,
    var textContains: String? = null,
    var textMatches: String? = null,
    var textStartsWith: String? = null,
    var className: String? = null,
    var classNameMatches: String? = null,
    var description: String? = null,
    var descriptionContains: String? = null,
    var descriptionMatches: String? = null,
    var descriptionStartsWith: String? = null,
    var checkable: Boolean? = null,
    var checked: Boolean? = null,
    var clickable: Boolean? = null,
    var longClickable: Boolean? = null,
    var scrollable: Boolean? = null,
    var enabled: Boolean? = null,
    var focusable: Boolean? = null,
    var focused: Boolean? = null,
    var selected: Boolean? = null,
    var packageName: String? = null,
    var packageNameMatches: String? = null,
    var resourceId: String? = null,
    var resourceIdMatches: String? = null,
    var index: Int? = null,
    var instance: String? = null
) {

    private var mask = 0
    private val childOrSibling = arrayListOf<Any>()
    private val childOrSiblingSelector = arrayListOf<TUiSelector>()

    /**
     * 子ui.
     * @param selector [@kotlin.ExtensionFunctionType] Function1<TUiSelector, Unit>
     * @return TUiSelector
     */
    internal fun child(selector: TUiSelector.() -> Unit) =
        apply {
            childOrSibling += "child"
            childOrSiblingSelector += TUiSelector().apply(selector)
        }

    /**
     * 兄弟ui.
     * @param selector [@kotlin.ExtensionFunctionType] Function1<TUiSelector, Unit>
     * @return TUiSelector
     */
    internal fun sibling(selector: TUiSelector.() -> Unit) =
        apply {
            childOrSibling += "sibling"
            childOrSiblingSelector += TUiSelector().apply(selector)
        }

//    override fun toString(): String =
//        TUiSelector::class.declaredMemberProperties
//            .asSequence()
//            .filter {
//                it.name != this::mask.name
//            }
//            .filter {
//                (it.name != this::childOrSibling.name || childOrSibling.isNotEmpty()) && (it.name != this::childOrSiblingSelector.name || childOrSiblingSelector.isNotEmpty())
//            }
//            .filter {
//                it.isAccessible = true
//                it.get(this) != null
//            }
//            .map {
//                it.name + "=" + it.get(this).toString()
//            }.let {
//                "TUiSelector [" + it.joinToString { it } + "]"
//            }

    /**
     * 计算mask值.
     * @return TUiSelector
     */
    fun calculateMask() = apply {
        TUiSelector::class.declaredMemberProperties
            .asSequence()
            .filter {
                it.name != this::mask.name
            }
            .filter {
                (it.name != this::childOrSibling.name || childOrSibling.isNotEmpty()) && (it.name != this::childOrSiblingSelector.name || childOrSiblingSelector.isNotEmpty())
            }
            .filter {
                it.isAccessible = true
                it.get(this) != null
            }
            .forEach { property ->
                mask = mask or (Fields.values().find { it._name == property.name }?.mask ?: return@forEach)
            }
    }

    enum class Fields(val _name: String, val mask: Int, val value: Any?) {
        TEXT("text", 0x01, null),  // MASK_TEXT
        TEXT_CONTAINS("textContains", 0x02, null), // MASK_TEXTCONTAINS,
        TEXT_MATCHES("textMatches", 0x04, null),  // MASK_TEXTMATCHES
        TEXT_START_SWITH("textStartsWith", 0x08, null),  // MASK_TEXTSTARTSWITH
        CLASS_NAME("className", 0x10, null),  // MASK_CLASSNAME
        CLASS_NAME_MATCHES("classNameMatches", 0x20, null),  // MASK_CLASSNAMEMATCHES
        DESCRIPTION("description", 0x40, null),  // MASK_DESCRIPTION
        DESCRIPTION_CONTAINS("descriptionContains", 0x80, null),  // MASK_DESCRIPTIONCONTAINS
        DESCRIPTION_MATCHES("descriptionMatches", 0x0100, null),  // MASK_DESCRIPTIONMATCHES
        DESCRIPTION_START_SWITH("descriptionStartsWith", 0x0200, null),  // MASK_DESCRIPTIONSTARTSWITH
        CHECKABLE("checkable", 0x0400, false),  // MASK_CHECKABLE
        CHECKED("checked", 0x0800, false),  // MASK_CHECKED
        CLICKABLE("clickable", 0x1000, false),  // MASK_CLICKABLE
        LONGCLICKABLE("longClickable", 0x2000, false),  // MASK_LONGCLICKABLE
        SCROLLABLE("scrollable", 0x4000, false),  // MASK_SCROLLABLE
        ENABLED("enabled", 0x8000, false),  // MASK_ENABLED
        FOCUSABLE("focusable", 0x010000, false),  // MASK_FOCUSABLE
        FOCUSED("focused", 0x020000, false),  // MASK_FOCUSED
        SELECTED("selected", 0x040000, false),  // MASK_SELECTED
        PACKAGENAME("packageName", 0x080000, null),  // MASK_PACKAGENAME
        PACKAGENAME_MATCHES("packageNameMatches", 0x100000, null),  // MASK_PACKAGENAMEMATCHES
        RESOURCEID("resourceId", 0x200000, null),  // MASK_RESOURCEID
        RESOURCEID_MATCHES("resourceIdMatches", 0x400000, null),  // MASK_RESOURCEIDMATCHES
        INDEX("index", 0x800000, 0),  // MASK_INDEX
        INSTANCE("instance", 0x01000000, 0),  // MASK_INSTANCE
    }
}
